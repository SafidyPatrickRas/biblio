package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Adherant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdherantRepository extends JpaRepository<Adherant, Integer> {
    // Ajoutez ici des méthodes personnalisées si besoin
    Optional<Adherant> findByEmail(String email);

}