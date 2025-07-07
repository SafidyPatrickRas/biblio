package com.bibliotheque.Bibliotheque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.ExemplaireLivre;
import com.bibliotheque.Bibliotheque.model.Livre;

public interface ExemplaireLivreRepository extends JpaRepository<ExemplaireLivre, Integer> {
    // recuperer par un livre
    Optional<ExemplaireLivre> findByLivre(Livre livre);
}