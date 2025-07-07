package com.bibliotheque.Bibliotheque.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.TypePret;

public interface TypePretRepository extends JpaRepository<TypePret, Integer> {
    // Trouver par nom
    Optional<TypePret> findByNom(String nom);
    
    // Trouver par id
}