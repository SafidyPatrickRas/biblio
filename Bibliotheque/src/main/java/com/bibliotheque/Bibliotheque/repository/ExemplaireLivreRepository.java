package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.ExemplaireLivre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface ExemplaireLivreRepository extends JpaRepository<ExemplaireLivre, Integer> {
    // Trouver l'exemplaire par ID de livre
    Optional<ExemplaireLivre> findByLivreId(Integer livreId);

    // Compter le nombre d'exemplaires pour un livre donné
    long countByLivreId(Integer livreId);

    // Trouver un exemplaire par ID de livre et ID de profil
    
    // Obtenir le nombre d'exemplaires pour un livre spécifique
    @Query("SELECT el FROM ExemplaireLivre el JOIN el.livre l WHERE l.id = :livreId")
    Optional<ExemplaireLivre> findExemplaireByLivreId(@Param("livreId") Integer livreId);

}
