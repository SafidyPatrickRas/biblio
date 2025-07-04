#!/bin/bash

MYSQL="/opt/lampp/bin/mysql"
UTILISATEUR="root"
MOT_DE_PASSE=""
BASE="spring_db_bibliotheque"

$MYSQL -u "$UTILISATEUR" -p"$MOT_DE_PASSE" "$BASE" <<EOF

-- Insertion des auteurs
INSERT INTO auteur (nom, prenom, date_naissance, nationalite, date_deces, biographie, photo) VALUES
('Hugo', 'Victor', '1802-02-26', 'Française', '1885-05-22', 'Auteur de "Les Misérables"', 'hugo.jpg'),
('Zola', 'Émile', '1840-04-02', 'Française', '1902-09-29', 'Père du naturalisme', 'zola.jpg'),
('Camus', 'Albert', '1913-11-07', 'Française', '1960-01-04', 'Prix Nobel 1957', 'camus.jpg'),
('Sartre', 'Jean-Paul', '1905-06-21', 'Française', '1980-04-15', 'Philosophe existentialiste', 'sartre.jpg'),
('Duras', 'Marguerite', '1914-04-04', 'Française', '1996-03-03', 'Écrivaine et cinéaste', 'duras.jpg'),
('Nothomb', 'Amélie', '1966-07-13', 'Belge', NULL, 'Auteur contemporaine belge', 'nothomb.jpg');

-- Insertion des catégories
INSERT INTO categorie_livre (nom) VALUES
('Roman'),
('Poésie'),
('Théâtre'),
('Essai'),
('Biographie'),
('Science-Fiction'),
('Fantasy'),
('Policier'),
('Historique'),
('Philosophie');

-- Insertion des livres (6 seulement)
INSERT INTO livre (age_restriction, date_publication, langue, nb_pages, tags, titre, id_auteur) VALUES
(12, '1862-04-03', 'Français', 1232, 'classique,drame,social', 'Les Misérables', 1),
(NULL, '1885-03-01', 'Français', 591, 'naturaliste,réalisme,social', 'Germinal', 2),
(16, '1942-05-01', 'Français', 159, 'absurde,philosophie', 'L\'Étranger', 3),
(18, '1943-06-15', 'Français', 255, 'philosophie,existentialisme', 'L\'Être et le Néant', 4),
(NULL, '1985-09-12', 'Français', 205, 'autobiographique,féminin', 'L\'Amant', 5),
(10, '1992-01-01', 'Français', 128, 'contemporain,humour', 'Hygiène de l\'assassin', 6);

-- Insertion des liens livre-catégorie
INSERT INTO livre_categorie (livre_id, categorie_id) VALUES
(1, 1), (1, 5),
(2, 1), (2, 9),
(3, 1), (3, 10),
(4, 4), (4, 10),
(5, 5), (5, 1),
(6, 1), (6, 7);

-- Insertion des profils
INSERT INTO profil (nom) VALUES
('Étudiant'),
('Professeur'),
('Chercheur'),
('Personnel administratif'),
('Lecteur externe'),
('Auteur'),
('Bibliothécaire'),
('Enseignant-chercheur');

-- Insertion des types de prêt
INSERT INTO type_pret (nom) VALUES
('Domicile'),
('Sur place');

-- Insertion des règles de prêt (profil × type_pret)
INSERT INTO regle_pret (nombre_jours, nombre_livres, id_profil, id_type_pret) VALUES
(15, 2, 1, 1), (1, 1, 1, 2),
(30, 2, 2, 1), (1, 2, 2, 2),
(45, 15, 3, 1), (2, 3, 3, 2),
(20, 5, 4, 1), (1, 1, 4, 2),
(10, 2, 5, 1), (1, 1, 5, 2),
(25, 4, 6, 1), (1, 2, 6, 2),
(60, 20, 7, 1), (3, 5, 7, 2),
(40, 12, 8, 1), (2, 3, 8, 2);

-- Insertion des exemplaires (6 livres seulement)
INSERT INTO exemplaire_livre (livre_id, nombre_exemplaires) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1);

-- Insertion des statuts
INSERT INTO status (nom) VALUES
('Envoyé'),
('Refusé'),
('Accepté');

INSERT INTO regle_reservation (max_reservations, profil_id) VALUES
(5, 1),  -- Étudiant
(10, 2), -- Professeur
(8, 3),  -- Chercheur
(4, 4),  -- Personnel administratif
(3, 5),  -- Lecteur externe
(6, 6),  -- Auteur
(7, 7),  -- Bibliothécaire
(9, 8);  -- Enseignant-chercheur


EOF

