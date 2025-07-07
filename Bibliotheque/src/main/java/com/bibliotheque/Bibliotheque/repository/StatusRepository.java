package com.bibliotheque.Bibliotheque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    // Recuperer le status par son nom
    Optional<Status> findByNom(String nom);
}
