package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    
    // Ajoutez ici des méthodes personnalisées si besoin
    Optional<Status> findByNom(String nom);

}