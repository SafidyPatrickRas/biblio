package com.bibliotheque.Bibliotheque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.Auteur;

public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
    //  Recuperer par le nom 
    Optional<Auteur> findByNom(String nom);
}