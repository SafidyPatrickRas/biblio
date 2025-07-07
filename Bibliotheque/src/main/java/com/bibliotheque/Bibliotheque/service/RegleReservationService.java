package com.bibliotheque.Bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.model.RegleReservation;
import com.bibliotheque.Bibliotheque.repository.RegleReservationRepository;

@Service
public class RegleReservationService {
    @Autowired
    private RegleReservationRepository regleReservationRepository;

    // Create
    public void createRegleReservation(RegleReservation regleReservation) {
        regleReservationRepository.save(regleReservation);
    }

    // Read
    public RegleReservation getRegleReservationById(Integer id) {
        return regleReservationRepository.findById(id).orElse(null);
    }

    public RegleReservation findByProfil(Profil profil) {
        return regleReservationRepository.findByProfil(profil).orElse(null);
    }

    // Update
    public void updateRegleReservation(RegleReservation regleReservation) {
        regleReservationRepository.save(regleReservation);
    }

    // Delete
    public void deleteRegleReservation(Integer id) {
        regleReservationRepository.deleteById(id);
    }
}
