package com.bibliotheque.Bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Reservation;
import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    // Create
    public void createReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    // Read
    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> getReservationsByAdherant(Adherant adherent) {
        return reservationRepository.findByAdherant(adherent);
    }

<<<<<<< Updated upstream
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

=======

    public List<Reservation> getReservationsByStatus(Status status) {
        return reservationRepository.findByStatus(status);
    }

    public List<Reservation> getReservationsByDate(Date dateReservation) {
        return reservationRepository.findByDateReservation(dateReservation);
    }

    // Update
    public void updateReservation(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    // Delete
>>>>>>> Stashed changes
    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    // get all
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
<<<<<<< Updated upstream

    public List<Reservation> findByAdherantId(Integer adherantId) {
        return reservationRepository.findByAdherant_Id(adherantId);
    }

    // Méthodes de comptage
    public long countAll() {
        return reservationRepository.count();
    }

    public long countEnCours() {
        return reservationRepository.countByStatus_Nom("En cours");
    }
}
=======
}
>>>>>>> Stashed changes
