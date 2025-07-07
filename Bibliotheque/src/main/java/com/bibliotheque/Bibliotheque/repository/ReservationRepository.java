package com.bibliotheque.Bibliotheque.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Reservation;
import com.bibliotheque.Bibliotheque.model.Status;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
<<<<<<< Updated upstream
    // Récupérer toutes les réservations par nom de status
    List<Reservation> findByStatus_Nom(String nom);
    List<Reservation> findByAdherant_Id(Integer adherantId);
    
    long countByStatus_Nom(String statut);
    
    Optional<Reservation> findById(Integer id);
    
    Reservation save(Reservation reservation);
    
    long count();
}
=======
    // Récupérer les réservations d'un adhérent
    List<Reservation> findByAdherant(Adherant adherent);


    // Récupérer les réservations par statut
    List<Reservation> findByStatus(Status status);

    // Récupérer une réservation par adhérent et document

    // Récupérer les réservations par date
    List<Reservation> findByDateReservation(Date dateReservation);
}
>>>>>>> Stashed changes
