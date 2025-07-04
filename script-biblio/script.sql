-- Création de la table TypePret
CREATE TABLE type_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL UNIQUE
);

-- Création de la table Status
CREATE TABLE status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL UNIQUE
);

-- Création de la table Profil
CREATE TABLE profil (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL
);

-- Création de la table Auteur
CREATE TABLE auteur (
    id_auteur INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    date_naissance DATE NOT NULL,
    nationalite VARCHAR(50) NOT NULL,
    date_deces DATE,
    biographie VARCHAR(50),
    photo VARCHAR(100)
);

-- Création de la table CategorieLivre
CREATE TABLE categorie_livre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL
);

-- Création de la table Livre
CREATE TABLE livre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(50) NOT NULL,
    date_publication DATE NOT NULL,
    nb_pages INT NOT NULL,
    langue VARCHAR(20) NOT NULL,
    tags VARCHAR(50) NOT NULL,
    age_restriction INT,
    id_auteur INT NOT NULL,
    FOREIGN KEY (id_auteur) REFERENCES auteur(id_auteur)
);

-- Table de jointure pour la relation many-to-many entre Livre et CategorieLivre
CREATE TABLE livre_categorie (
    livre_id INT NOT NULL,
    categorie_id INT NOT NULL,
    PRIMARY KEY (livre_id, categorie_id),
    FOREIGN KEY (livre_id) REFERENCES livre(id),
    FOREIGN KEY (categorie_id) REFERENCES categorie_livre(id)
);

-- Création de la table ExemplaireLivre
CREATE TABLE exemplaire_livre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    livre_id INT NOT NULL,
    nombre_exemplaires INT NOT NULL,
    FOREIGN KEY (livre_id) REFERENCES livre(id)
);

-- Création de la table Adherant
CREATE TABLE adherant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    mot_de_passe VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    date_naissance DATE NOT NULL,
    id_profil INT,
    FOREIGN KEY (id_profil) REFERENCES profil(id)
);

-- Création de la table Abonnement
CREATE TABLE abonnement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date_inscription DATE NOT NULL,
    date_fin_inscription DATE NOT NULL,
    id_adherant INT,
    FOREIGN KEY (id_adherant) REFERENCES adherant(id)
);

-- Création de la table ReglePret
CREATE TABLE regle_pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_type_pret INT NOT NULL,
    id_profil INT NOT NULL,
    nombre_livres INT NOT NULL,
    nombre_jours INT NOT NULL,
    FOREIGN KEY (id_type_pret) REFERENCES type_pret(id),
    FOREIGN KEY (id_profil) REFERENCES profil(id),
    UNIQUE KEY (id_profil, id_type_pret)
);

-- Création de la table RegleReservation
CREATE TABLE regle_reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    profil_id INT NOT NULL UNIQUE,
    max_reservations INT NOT NULL,
    duree_validite_jours INT NOT NULL,
    FOREIGN KEY (profil_id) REFERENCES profil(id)
);

-- Création de la table Pret
CREATE TABLE pret (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_adherent INT,
    id_livre INT,
    id_type_pret INT,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    is_retournee BOOLEAN NOT NULL DEFAULT FALSE,
    date_retour DATE,
    FOREIGN KEY (id_adherent) REFERENCES adherant(id),
    FOREIGN KEY (id_livre) REFERENCES livre(id),
    FOREIGN KEY (id_type_pret) REFERENCES type_pret(id)
);

-- Création de la table Reservation
CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    livre_id INT NOT NULL,
    date_reservation DATE NOT NULL,
    status_id INT NOT NULL,
    adherant_id INT NOT NULL,
    FOREIGN KEY (livre_id) REFERENCES livre(id),
    FOREIGN KEY (status_id) REFERENCES status(id),
    FOREIGN KEY (adherant_id) REFERENCES adherant(id)
);

-- Création de la table Penalite
CREATE TABLE penalite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    adherant_id INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    nombre_jours INT NOT NULL,
    FOREIGN KEY (adherant_id) REFERENCES adherant(id)
);
