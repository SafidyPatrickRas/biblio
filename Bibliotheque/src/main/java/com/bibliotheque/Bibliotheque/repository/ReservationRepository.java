package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    // Récupérer toutes les réservations par nom de status
    List<Reservation> findByStatus_Nom(String nom);
    List<Reservation> findByAdherant_Id(Integer adherantId);
    
    long countByStatus_Nom(String statut);
    
    Optional<Reservation> findById(Integer id);
    
    Reservation save(Reservation reservation);
    
    long count();
}