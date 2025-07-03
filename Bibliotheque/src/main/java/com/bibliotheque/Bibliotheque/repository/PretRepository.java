package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Pret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PretRepository extends JpaRepository<Pret, Integer> {
    List<Pret> findByAdherant_Id(Integer idAdherant);
    List<Pret> findByDateFinBeforeAndDateFinAfter(Date dateDebut, Date dateFin);

    // Compter tous les prêts non retournés
    long countByIsRetourneeFalse();

    // Compter les prêts non retournés pour un adhérent
    Integer countByAdherant_IdAndIsRetourneeFalse(Integer idAdherant);

    // Récupérer les prêts non retournés par adhérent
    List<Pret> findByAdherant_IdAndIsRetourneeFalse(Integer idAdherant);

    // Compter les prêts non retournés pour un livre
    long countByLivre_IdAndIsRetourneeFalse(Integer idLivre);

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
}
