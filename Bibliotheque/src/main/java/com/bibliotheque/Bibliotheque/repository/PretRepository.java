package com.bibliotheque.Bibliotheque.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Pret;

public interface PretRepository extends JpaRepository<Pret, Integer> {
<<<<<<< Updated upstream
    List<Pret> findByAdherantId(Integer idAdherant);
    
    long count();
    
    
    // Compter tous les prêts non retournés
    long countByIsRetourneeFalse();
=======

    @Query("SELECT p FROM Pret p WHERE p.dateRetour IS NULL AND p.livre.id = :livreId")
    List<Pret> findPretsNonRendusParLivre(@Param("livreId") Integer livreId);
>>>>>>> Stashed changes

    @Query("SELECT DISTINCT p.livre FROM Pret p WHERE p.adherant.id = :adherantId")
    List<Livre> findLivresPretesParAdherant(@Param("adherantId") Integer adherantId);

    // Pret non retourne  
    List<Pret> findByDateRetourIsNull();

    // Pret non rretourne par adherant
    @Query("SELECT p FROM Pret p WHERE p.dateRetour IS NULL AND p.adherant.id = :adherantId")
    List<Pret> findPretsNonRendusParAdherant(@Param("adherantId") Integer adherantId);

<<<<<<< Updated upstream
    /**
     * Trouve tous les prêts non retournés pour un livre spécifique
     * @param livre Le livre à rechercher
     * @return Liste des prêts non retournés pour ce livre
     */
    List<Pret> findByIsRetourneeFalseAndLivre(Livre livre);
    
    // Version alternative avec l'ID du livre directement
    List<Pret> findByIsRetourneeFalseAndLivreId(Integer livreId);

    @Query("SELECT p FROM Pret p ORDER BY p.dateDebut DESC")
    List<Pret> findAllOrderByDateDesc();

    // Recherche de prêts par plage de dates
    List<Pret> findByDateFinBetween(Date dateDebut, Date dateFin);
}
=======


}
>>>>>>> Stashed changes
