package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.TypePret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePretRepository extends JpaRepository<TypePret, Long> {
    TypePret findByNom(String nom);
    boolean existsByNom(String nom);
}