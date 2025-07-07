package com.bibliotheque.Bibliotheque.repository;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliotheque.Bibliotheque.model.Penalite;

public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    // Trouver les pénalités actives par adherant
    @Query("SELECT p FROM Penalite p WHERE p.adherant.id = :adherantId AND :date BETWEEN p.dateDebut AND p.dateFin")
List<Penalite> findPenalitesActivesByAdherant(
    @Param("adherantId") Integer adherantId,
    @Param("date") Date date
);


}