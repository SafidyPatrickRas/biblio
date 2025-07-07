package com.bibliotheque.Bibliotheque.repository;

<<<<<<< Updated upstream
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.Prolongement;

import java.util.List;
=======

import java.util.List;
import java.util.Optional;
>>>>>>> Stashed changes

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
<<<<<<< Updated upstream
import org.springframework.stereotype.Repository;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    Prolongement findByPret(Pret pret);
    
    @Query("SELECT p FROM Prolongement p JOIN p.pret pr WHERE pr.adherant.id = :adherantId")
    List<Prolongement> findByAdherantId(@Param("adherantId") Integer adherantId);
    
    long countByStatusNom(String nom);
}
=======

import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.Prolongement;

public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {

    @Query("SELECT pr FROM Prolongement pr " +
       "WHERE pr.pret.adherant.id = :adherantId " +
       "AND pr.pret.dateRetour IS NULL")
    List<Prolongement> findProlongementsParAdherantEnCoursDePret(@Param("adherantId") Integer adherantId);

    Optional<Prolongement> findById(Integer id);

    Optional<Prolongement> findByPret(Pret pret);

    // Recuperer par pret
    // Nouvelle méthode pour vérifier si un prêt a déjà un prolongement
    boolean existsByPret(Pret pret);
}
>>>>>>> Stashed changes
