package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.model.RegleReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RegleReservationRepository extends JpaRepository<RegleReservation, Integer> {
    // Ajoutez ici des méthodes personnalisées si besoin
    Optional<RegleReservation> findByProfil(Profil profil);
}