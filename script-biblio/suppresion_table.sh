#!/bin/bash

UTILISATEUR="safidy"
MOT_DE_PASSE="safidy"
BASE="spring_db_bibliotheque"

# 1. Désactiver les contraintes pour permettre la suppression
mysql -u "$UTILISATEUR" -p"$MOT_DE_PASSE" "$BASE" -e "SET FOREIGN_KEY_CHECKS = 0;"

# 2. Récupérer toutes les tables de la base
TABLES=$(mysql -u "$UTILISATEUR" -p"$MOT_DE_PASSE" -Nse "SELECT table_name FROM information_schema.tables WHERE table_schema = '$BASE';")

# 3. Boucle pour les supprimer
for t in $TABLES
do
  echo "Suppression de la table : $t"
  mysql -u "$UTILISATEUR" -p"$MOT_DE_PASSE" "$BASE" -e "DROP TABLE IF EXISTS \`$t\`;"
done

# 4. Réactiver les contraintes
mysql -u "$UTILISATEUR" -p"$MOT_DE_PASSE" "$BASE" -e "SET FOREIGN_KEY_CHECKS = 1;"
