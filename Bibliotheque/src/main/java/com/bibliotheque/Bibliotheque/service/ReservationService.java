package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Reservation;
import com.bibliotheque.Bibliotheque.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(Integer id) {
        return reservationRepository.findById(id);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        reservationRepository.deleteById(id);
    }

    // Récupérer toutes les réservations par nom de status
    public List<Reservation> findByStatusNom(String nom) {
        return reservationRepository.findByStatus_Nom(nom);
    }

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