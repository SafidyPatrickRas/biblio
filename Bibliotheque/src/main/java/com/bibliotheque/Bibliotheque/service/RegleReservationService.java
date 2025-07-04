package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.RegleReservation;
import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.repository.RegleReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegleReservationService {

    @Autowired
    private RegleReservationRepository regleReservationRepository;

    public List<RegleReservation> findAll() {
        return regleReservationRepository.findAll();
    }

    public Optional<RegleReservation> findById(Integer id) {
        return regleReservationRepository.findById(id);
    }

    public Optional<RegleReservation> findByProfil(Profil profil) {
        return regleReservationRepository.findByProfil(profil);
    }

    public RegleReservation save(RegleReservation regleReservation) {
        return regleReservationRepository.save(regleReservation);
    }

    public void deleteById(Integer id) {
        regleReservationRepository.deleteById(id);
    }
}