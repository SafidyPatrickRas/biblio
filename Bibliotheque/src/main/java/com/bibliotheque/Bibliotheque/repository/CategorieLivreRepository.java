package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.CategorieLivre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieLivreRepository extends JpaRepository<CategorieLivre, Integer> {
    Optional<CategorieLivre> findByNom(String nom);
}