package com.bibliotheque.Bibliotheque.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliotheque.Bibliotheque.model.Abonnement;

public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {

    // 
    @Query("SELECT p FROM Pret p WHERE p.dateRetour IS NULL AND p.livre.id = :livreId")
    List<Abonnement> findPretsNonRendusParLivre(@Param("livreId") Integer livreId);


    @Query("SELECT a FROM Abonnement a WHERE a.dateInscription <= :date AND a.dateFinInscription >= :date  AND a.adherant.id = :adherantId")
    List<Abonnement> estAbonneeAdherant(@Param("date") Date date , @Param("adherantId") Integer adherantId);


    @Query("SELECT a FROM Abonnement a WHERE a.dateInscription <= :fin AND a.dateFinInscription >= :debut AND a.adherant.id = :adherantId")
List<Abonnement> findAbonnementsChevauchantsByAdherant(
    @Param("debut") Date debut,
    @Param("fin") Date fin,
    @Param("adherantId") Integer adherantId
);


}