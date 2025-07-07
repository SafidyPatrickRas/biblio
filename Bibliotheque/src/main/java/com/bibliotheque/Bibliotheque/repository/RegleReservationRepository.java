package com.bibliotheque.Bibliotheque.repository;

<<<<<<< Updated upstream
import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.model.RegleReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
=======
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.model.RegleReservation;
>>>>>>> Stashed changes

public interface RegleReservationRepository extends JpaRepository<RegleReservation, Integer> {
<<<<<<< Updated upstream
    // Ajoutez ici des méthodes personnalisées si besoin
    Optional<RegleReservation> findByProfil(Profil profil);
=======

    // Recuperer la regle par le profil
    Optional<RegleReservation> findByProfil(Profil profil);

>>>>>>> Stashed changes
}