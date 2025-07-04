#!/bin/bash

UTILISATEUR="root"
MOT_DE_PASSE=""
BASE="spring_db_bibliotheque"

# Récupérer toutes les tables
TABLES=$(mysql -u "$UTILISATEUR" -p"$MOT_DE_PASSE" -Nse "SELECT table_name FROM information_schema.tables WHERE table_schema = '$BASE';")

for TABLE in $TABLES
do
  echo "Vidage de la table : $TABLE"
  mysql -u "$UTILISATEUR" -p"$MOT_DE_PASSE" -e "SET FOREIGN_KEY_CHECKS=0; TRUNCATE TABLE $BASE.$TABLE; SET FOREIGN_KEY_CHECKS=1;"
done

