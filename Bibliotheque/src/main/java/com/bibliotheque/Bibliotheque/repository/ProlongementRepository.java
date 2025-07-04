package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.Prolongement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProlongementRepository extends JpaRepository<Prolongement, Integer> {
    Prolongement findByPret(Pret pret);
    
    @Query("SELECT p FROM Prolongement p JOIN p.pret pr WHERE pr.adherant.id = :adherantId")
    List<Prolongement> findByAdherantId(@Param("adherantId") Integer adherantId);
    
    long countByStatusNom(String nom);
}
