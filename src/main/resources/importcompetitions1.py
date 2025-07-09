import requests
from bs4 import BeautifulSoup
import json
import urllib.parse
import re
import io
import camelot
import dateparser
from datetime import datetime, date
import unicodedata
import pymysql

def clean_text(text):
    """Fix encoding issues and normalize text"""
    if not text:
        return text
    text = text.replace('�', 'é').replace('�', 'è')
    text = unicodedata.normalize('NFKC', text)
    text = re.sub(r'^(du|de|à|au|le|la|les?|jusqu\'au)\s+', '', text, flags=re.IGNORECASE)
    text = re.sub(r'\s*/\s*', '/', text)
    return text.strip()

def parse_date_range(date_str):
    """Robustly parses French date ranges with encoding fixes"""
    date_str = clean_text(date_str)
    if not date_str or date_str.lower() == 'dates':
        return None, None

    month_fixes = {
        'fevrier': 'février', 'fev': 'fév', 'fvr': 'fév', 'f�vrier': 'février',
        'decembre': 'décembre', 'dec': 'déc', 'd�cembre': 'décembre',
        'jan': 'janvier', 'feb': 'février', 'mar': 'mars', 'apr': 'avril',
        'mai': 'mai', 'jun': 'juin', 'jul': 'juillet', 'aug': 'août',
        'sep': 'septembre', 'oct': 'octobre', 'nov': 'novembre', 'dec': 'décembre'
    }
    for wrong, correct in month_fixes.items():
        date_str = re.sub(r'\b' + wrong + r'\b', correct, date_str, flags=re.IGNORECASE)

    patterns = [
        r'(\d{1,2})\s+([^\W\d_]+)\s+(?:au|à|et|\-)\s+(\d{1,2})\s+([^\W\d_]+)\s+(\d{4})',
        r'(\d{1,2})\s*(?:au|à|et|\-)\s*(\d{1,2})\s+([^\W\d_]+)\s+(\d{4})',
        r'(\d{1,2})\s+([^\W\d_]+)\s+(\d{4})',
        r'(\d{1,2}/\d{1,2}/\d{4})\s+(?:au|à|et|\-)\s+(\d{1,2}/\d{1,2}/\d{4})',
        r'(\d{1,2})\s*-\s*(\d{1,2})/(\d{1,2})/(\d{4})',
        r'(\d{1,2})\s*-\s*(\d{1,2})\s*-\s*(\d{1,2})/(\d{1,2})/(\d{4})',
        r'du\s*(\d{1,2})\s*au\s*(\d{1,2})/(\d{1,2})/(\d{4})',
        r'(début|debut)\s+du\s+mois\s+de\s+([^\W\d_]+)\s+(\d{4})'
    ]

    for pattern in patterns:
        match = re.search(pattern, date_str, re.IGNORECASE)
        if match:
            groups = match.groups()
            if len(groups) == 5:
                day1, month1, day2, month2, year = groups
                start_str = f"{day1} {month1} {year}"
                end_str = f"{day2} {month2} {year}"
            elif len(groups) == 4:
                if '/' in groups[0]:
                    start_str = groups[0]
                    end_str = groups[1]
                elif re.match(r'du\s*\d{1,2}', date_str, re.IGNORECASE):
                    day1, day2, month, year = groups
                    start_str = f"{day1}/{month}/{year}"
                    end_str = f"{day2}/{month}/{year}"
                else:
                    day1, day2, month, year = groups
                    start_str = f"{day1} {month} {year}"
                    end_str = f"{day2} {month} {year}"
            elif len(groups) == 6:
                day1, day2_mid, day3, month, year = groups
                start_str = f"{day1}/{month}/{year}"
                end_str = f"{day3}/{month}/{year}"
            elif len(groups) == 3 and 'debut' in groups[0].lower():
                month, year = groups[1], groups[2]
                start_str = f"1 {month} {year}"
                end_str = f"15 {month} {year}"
            else:
                day, month, year = groups
                start_str = end_str = f"{day} {month} {year}"

            start_date = dateparser.parse(
                start_str,
                languages=['fr'],
                settings={'DATE_ORDER': 'DMY', 'PREFER_DAY_OF_MONTH': 'first'}
            )
            end_date = dateparser.parse(
                end_str,
                languages=['fr'],
                settings={'DATE_ORDER': 'DMY', 'PREFER_DAY_OF_MONTH': 'first'}
            ) if start_str != end_str else start_date

            if start_date:
                if end_date and start_date > end_date and start_date.month > end_date.month:
                    start_date = start_date.replace(year=end_date.year - 1)
                return start_date.date(), end_date.date() if end_date else None

    parsed_date = dateparser.parse(
        date_str,
        languages=['fr'],
        settings={'DATE_ORDER': 'DMY', 'PREFER_DAY_OF_MONTH': 'first'}
    )
    return (parsed_date.date(), None) if parsed_date else (None, None)

def extract_and_organize_competition_data(pdf_url):
    print(f"Processing PDF: {pdf_url}")
    organized_data = []
    try:
        response = requests.get(pdf_url, stream=True, timeout=60)
        response.raise_for_status()
        pdf_content = io.BytesIO(response.content)

        tables = []
        try:
            tables = camelot.read_pdf(pdf_content, flavor='stream', pages='all')
        except Exception as e:
            print(f"ERROR: Camelot failed to read PDF '{pdf_url}': {e}")
            return []

        found_table = False
        for table in tables:
            target_table_df = table.df
            table_content_str = target_table_df.astype(str).to_string().lower()
            if not any(keyword in table_content_str for keyword in ['calendrier national', 'programme', 'calendrier general', 'date', 'compétition', 'lieu']):
                continue
            found_table = True

            df = target_table_df
            headers = [clean_text(str(df.iloc[0, col])) for col in range(df.shape[1])]
            headers = [h if h else f"Col_{col}" for col, h in enumerate(headers)]

            date_col = -1
            nom_col = -1
            lieu_col = -1
            categorie_col = -1

            for i, header in enumerate(headers):
                header_lower = header.lower()
                if re.search(r'date|jour|period|période', header_lower):
                    date_col = i
                elif re.search(r'compétition|evenement|event|name|épreuve|intitulé|intitule|nature', header_lower):
                    nom_col = i
                elif re.search(r'lieu|ville|endroit|location|site|emplacement', header_lower):
                    lieu_col = i
                elif re.search(r'categorie|catégorie|cat|type|groupe|age|âge|min', header_lower):
                    categorie_col = i

            if date_col == -1 and df.shape[1] > 0: date_col = 0
            if nom_col == -1 and df.shape[1] > 1: nom_col = 1
            if categorie_col == -1 and df.shape[1] > 2: categorie_col = 2
            if lieu_col == -1 and df.shape[1] > 3: lieu_col = 3

            for i in range(1, df.shape[0]):
                date_str = clean_text(str(df.iloc[i, date_col])) if date_col != -1 and date_col < df.shape[1] else None
                nom = clean_text(str(df.iloc[i, nom_col])) if nom_col != -1 and nom_col < df.shape[1] else None
                lieu = clean_text(str(df.iloc[i, lieu_col])) if lieu_col != -1 and lieu_col < df.shape[1] else None

                if not date_str or not nom or date_str.lower() == 'dates' or nom.lower() in ['nom', 'compétition', 'evenement', 'intitulé', 'nature']:
                    continue

                date_debut, date_fin = parse_date_range(date_str)

                est_active = None
                today = date.today()
                if date_fin:
                    est_active = 0 if date_fin < today else 1
                elif date_debut:
                    est_active = 0 if date_debut < today else 1

                if date_debut:
                    organized_data.append({
                        'date_debut': date_debut.isoformat(),
                        'date_fin': date_fin.isoformat() if date_fin else None,
                        'nom': nom,
                        'lieu': lieu if lieu and lieu.lower().strip() not in ["", "n/a", "nc", "x", "lieu", "emplacement", "site", "à déterminer"] else None,
                        'url_source': pdf_url,
                        'date_importation': datetime.now().isoformat(),
                        'description': None,
                        'est_active': est_active,
                        'created_notification_sent': 0,
                        'last_modified': datetime.now().isoformat(),
                        'updated_notification_sent': 0
                    })

        if not found_table:
            print(f"No competition table found in {pdf_url}.")
        return organized_data

    except Exception as e:
        print(f"Error processing PDF {pdf_url}: {str(e)}")
    return organized_data

def get_existing_competitions(conn):
    """Fetch all existing competitions as a dict for comparison"""
    with conn.cursor() as cursor:
        cursor.execute("""
            SELECT 
                nom, date_debut, lieu, 
                date_fin, url_source, est_active,
                date_importation, last_modified
            FROM competition
        """)
        return {
            (row['nom'], row['date_debut'], row['lieu']): row
            for row in cursor.fetchall()
        }

def store_data_in_db(data):
    """Smart insertion that checks for existing competitions before updating"""
    conn = None
    try:
        conn = pymysql.connect(
            host='127.0.0.1',
            port=3306,
            user='root',
            password='',
            database='bridge',
            charset='utf8mb4',
            cursorclass=pymysql.cursors.DictCursor
        )
        
        existing_competitions = get_existing_competitions(conn)
        now = datetime.now().isoformat()
        
        stats = {'new': 0, 'updated': 0, 'unchanged': 0}

        for entry in data:
            key = (entry['nom'], entry['date_debut'], entry.get('lieu'))
            
            if key in existing_competitions:
                existing = existing_competitions[key]
                changes = []
                
                # Check each field for changes
                if entry.get('date_fin') != existing['date_fin']:
                    changes.append(('date_fin', entry.get('date_fin')))
                if entry.get('lieu') != existing['lieu']:
                    changes.append(('lieu', entry.get('lieu')))
                if entry.get('url_source') != existing['url_source']:
                    changes.append(('url_source', entry.get('url_source')))
                if entry.get('est_active') != existing['est_active']:
                    changes.append(('est_active', entry.get('est_active')))
                
                if changes:
                    set_clause = ", ".join([f"{field} = %s" for field, _ in changes])
                    values = [val for _, val in changes] + [entry['nom'], entry['date_debut'], entry.get('lieu')]
                    
                    with conn.cursor() as cursor:
                        cursor.execute(f"""
                            UPDATE competition SET
                                {set_clause},
                                last_modified = %s,
                                updated_notification_sent = 0
                            WHERE nom = %s AND date_debut = %s AND lieu = %s
                        """, values + [now] + values[-3:])
                    stats['updated'] += 1
                    print(f"Updated competition: {entry['nom']} ({entry['date_debut']})")
                else:
                    stats['unchanged'] += 1
            else:
                with conn.cursor() as cursor:
                    cursor.execute("""
                        INSERT INTO competition (
                            date_debut, date_fin, nom, lieu, url_source,
                            date_importation, description, est_active,
                            created_notification_sent, last_modified
                        ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                    """, (
                        entry['date_debut'],
                        entry.get('date_fin'),
                        entry['nom'],
                        entry.get('lieu'),
                        entry.get('url_source'),
                        now,
                        entry.get('description'),
                        entry.get('est_active'),
                        0,  # created_notification_sent
                        now  # last_modified
                    ))
                stats['new'] += 1
                print(f"Added new competition: {entry['nom']} ({entry['date_debut']})")
        
        conn.commit()
        print(f"""
        Processing complete:
        - New competitions: {stats['new']}
        - Updated competitions: {stats['updated']}
        - Unchanged competitions: {stats['unchanged']}
        Total processed: {len(data)}
        """)

    except pymysql.Error as e:
        print(f"Database error: {e}")
    finally:
        if conn:
            conn.close()

def scrape_competition_links(url):
    print(f"Fetching competition calendar page: {url}")
    try:
        response = requests.get(url, timeout=10)
        response.encoding = 'utf-8'
        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')
            pdf_links = []

            for link in soup.find_all('a', href=True):
                href = link['href']
                full_url = urllib.parse.urljoin(base_url, href)
                if full_url.lower().endswith('.pdf'):
                    pdf_links.append(full_url)

            return list(set(pdf_links))
        else:
            print(f"Error fetching {url}. Status code: {response.status_code}")
            return []
    except requests.exceptions.RequestException as e:
        print(f"Request failed for {url}: {e}")
        return []

if __name__ == "__main__":
    competition_calendar_url = "http://ftnatation.tn/natation-calendrier-national/"
    base_url = "http://ftnatation.tn/"
    all_organized_data = []

    competition_pdf_links = scrape_competition_links(competition_calendar_url)

    if competition_pdf_links:
        print(f"Found {len(competition_pdf_links)} PDF links")
        for pdf_link in sorted(list(set(competition_pdf_links))):
            organized_data = extract_and_organize_competition_data(pdf_link)
            if organized_data:
                all_organized_data.extend(organized_data)

    if all_organized_data:
        store_data_in_db(all_organized_data)
    else:
        print("No competition data extracted")