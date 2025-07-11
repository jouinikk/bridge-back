
-- Insert a Coach
INSERT INTO coach (nom, prenom, email, telephone, specialite, annee_experience) 
VALUES ('Dupont', 'Jean', 'jean.dupont@email.com', '0612345678', 'Freestyle', 5);

-- Insert a Piscine
INSERT INTO piscine (nom, adresse, ville, code_postal, telephone, email, nombre_lignes_eau, latitude, longitude)
VALUES ('Piscine Municipale', '123 Rue de la Natation', 'Paris', '75001', '0123456789', 'contact@piscine.fr', 8, 48.8566, 2.3522);

-- Insert a Ligne d'Eau
INSERT INTO ligne_eau (numero, description, piscine_id)
VALUES ('L1', 'Ligne rapide', LAST_INSERT_ID());

-- Insert Nageurs
INSERT INTO nageur (nom, prenom, email, telephone, niveau)
VALUES 
('Martin', 'Sophie', 'sophie.martin@email.com', '0612345679', 'Intermediate'),
('Bernard', 'Pierre', 'pierre.bernard@email.com', '0612345680', 'Advanced'),
('Thomas', 'Julie', 'julie.thomas@email.com', '0612345681', 'Beginner');

-- Insert a Groupe (using the first coach)
INSERT INTO groupe (nom, niveau, nombre_max_nageurs, coach_id)
VALUES ('Groupe Elite', 'Advanced', 10, (SELECT id FROM coach ORDER BY id ASC LIMIT 1));

-- Link Nageurs to Groupe through the junction table
INSERT INTO groupe_nageur (groupe_id, nageur_id)
SELECT 
    (SELECT id FROM groupe ORDER BY id ASC LIMIT 1),
    id
FROM nageur;

-- Insert Disponibilite for the Ligne d'Eau
INSERT INTO disponibilite (jour_semaine, heure_ouverture, heure_fermeture, piscine_id, ligne_eau_id, est_disponible)
VALUES 
('MONDAY', '08:00:00', '20:00:00', 
 (SELECT id FROM piscine ORDER BY id ASC LIMIT 1),
 (SELECT id FROM ligne_eau ORDER BY id ASC LIMIT 1),
 true);

