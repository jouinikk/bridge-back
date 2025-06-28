import requests
from bs4 import BeautifulSoup
import pymysql
import urllib.parse
from datetime import datetime
import unicodedata
import sys
import os

DB_CONFIG = {
    'host': '127.0.0.1',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'bridge',
    'charset': 'utf8mb4',
    'cursorclass': pymysql.cursors.DictCursor
}

def clean_text(text):
    """Cleans and normalizes text"""
    if not text:
        return text
    text = text.replace('\xa0', ' ').replace('\u00e9', 'é').replace('\u00e8', 'è')
    text = unicodedata.normalize('NFKC', text)
    return text.strip()

def store_data_in_db(data):
    """Stores the scraped data with duplicate prevention"""
    conn = None
    try:
        conn = pymysql.connect(**DB_CONFIG)
        cursor = conn.cursor()

        cursor.execute('''
            CREATE TABLE IF NOT EXISTS resultat (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                club VARCHAR(255) NULL,
                competition_id BIGINT NULL,
                naissance VARCHAR(255) NULL,
                nation VARCHAR(255) NULL,
                nom_nageur VARCHAR(255) NOT NULL,
                place VARCHAR(255) NULL,
                points VARCHAR(255) NULL,
                temps VARCHAR(255) NOT NULL,
                temps_de_passage VARCHAR(255) NULL,
                date_importation DATETIME NOT NULL,
                UNIQUE KEY unique_result (nom_nageur, temps, competition_id)
            ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci
        ''')
        conn.commit()

        for entry in data:
            nom_nageur = entry.get('Nom Nageur')
            temps = entry.get('Temps')

            if not nom_nageur or temps is None:
                continue

            cursor.execute('''
                INSERT INTO resultat (
                    club, competition_id, naissance, nation, nom_nageur, 
                    place, points, temps, temps_de_passage, date_importation
                )
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                ON DUPLICATE KEY UPDATE
                    place = VALUES(place),
                    points = VALUES(points),
                    date_importation = VALUES(date_importation)
            ''', (
                entry.get('Club'),
                None,
                entry.get('Naissance'),
                entry.get('Nation'),
                nom_nageur,
                entry.get('Place'),
                entry.get('Points'),
                temps,
                entry.get('Temps de passage'),
                datetime.now()
            ))
        conn.commit()
        print(f"Stored {len(data)} result rows")

    except pymysql.Error as e:
        print(f"Database error: {e}")
    finally:
        if conn:
            conn.close()

def scrape_tables_from_url(url):
    """Extracts table data from a URL"""
    header_mapping = {
        'Nom': 'Nom Nageur',
        'Nom et prénom': 'Nom Nageur',
        'Temps': 'Temps',
        'Nation': 'Nation',
        'Naissance': 'Naissance',
        'Club': 'Club',
        'Place': 'Place',
        'Points': 'Points',
        'Temps de passage': 'Temps de passage',
    }

    print(f"Scraping: {url}")
    try:
        response = requests.get(url, timeout=10)
        response.encoding = 'utf-8'

        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')
            tables = soup.find_all('table')
            all_table_data = []

            for table in tables:
                headers = []
                rows = []

                header_row = table.find('tr')
                if header_row:
                    for th in header_row.find_all(['th', 'td']):
                        headers.append(clean_text(th.get_text(strip=True)))

                for row in table.find_all('tr')[1:]:
                    cells = row.find_all('td')
                    if len(cells) == len(headers) and len(headers) > 0:
                        row_data = {
                            header_mapping.get(headers[i], headers[i]): clean_text(cells[i].get_text(strip=True)) 
                            for i in range(len(headers))
                        }
                        rows.append(row_data)

                if rows:
                    all_table_data.extend(rows)
            return all_table_data
        else:
            print(f"Error fetching {url}. Status code: {response.status_code}")
            return []
    except requests.exceptions.RequestException as e:
        print(f"Request failed for {url}: {e}")
        return []

if __name__ == "__main__":
    main_results_url = "http://ftnatation.tn/natation-resultats/"
    base_url = "http://ftnatation.tn/"
    all_scraped_data = []

    print(f"Fetching main results page: {main_results_url}")
    try:
        main_response = requests.get(main_results_url, timeout=10)
        main_response.encoding = 'utf-8'

        if main_response.status_code == 200:
            main_soup = BeautifulSoup(main_response.text, 'html.parser')
            result_links = []

            for link in main_soup.find_all('a', href=True):
                href = link['href']
                full_url = urllib.parse.urljoin(base_url, href)
                if ('res_' in full_url or 'resultats' in full_url) and full_url.endswith('.html'):
                    result_links.append(full_url)

            if result_links:
                print(f"Found {len(set(result_links))} result links")
                for link_url in sorted(list(set(result_links))):
                    data_from_linked_page = scrape_tables_from_url(link_url)
                    if data_from_linked_page:
                        all_scraped_data.extend(data_from_linked_page)

    except requests.exceptions.RequestException as e:
        print(f"Request failed for main results page: {e}")

    if all_scraped_data:
        store_data_in_db(all_scraped_data)
    else:
        print("No data was scraped")