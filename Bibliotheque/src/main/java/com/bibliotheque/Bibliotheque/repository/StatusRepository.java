package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByNom(String nom);
    boolean existsByNom(String nom);
}
