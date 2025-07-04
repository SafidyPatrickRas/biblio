#!/bin/bash

UTILISATEUR="safidy"
MOT_DE_PASSE="safidy"
BASE="spring_db_bibliotheque"

mysql -u "$UTILISATEUR" -p"$MOT_DE_PASSE" "$BASE" <<EOF
CREATE TABLE Auteur (
    Id_Auteur INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    date_naissance DATE NOT NULL
);

CREATE TABLE Livre (
    Id_Livre INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    annee_publication INT,
    Id_Auteur INT,
    FOREIGN KEY (Id_Auteur) REFERENCES Auteur(Id_Auteur)
);
EOF

