package com.bibliotheque.Bibliotheque.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.Adherant;

public interface AdherantRepository extends JpaRepository<Adherant, Integer> {

    // Trouver par email
    Optional<Adherant> findByEmail(String email);

<<<<<<< Updated upstream
=======
    // Trouver par profil
    List<Adherant> findByProfilId(Integer profilId);
>>>>>>> Stashed changes
}