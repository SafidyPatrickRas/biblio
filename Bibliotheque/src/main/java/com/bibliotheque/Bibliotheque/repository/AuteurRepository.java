package com.bibliotheque.Bibliotheque.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bibliotheque.Bibliotheque.model.Auteur;

public interface AuteurRepository extends JpaRepository<Auteur, Integer> {

    Optional<Auteur> findByNomAndPrenom(String nom, String prenom); // Trouver par nom et prénom
    List<Auteur> findByNationalite(String nationalite); // Trouver par nationalité
    List<Auteur> findByNomContainingIgnoreCase(String nom); // Recherche par nom (insensible à la casse)
}