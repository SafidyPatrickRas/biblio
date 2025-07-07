package com.bibliotheque.Bibliotheque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.model.ReglePret;
import com.bibliotheque.Bibliotheque.model.TypePret;

public interface ReglePretRepository extends JpaRepository<ReglePret, Integer> {

    // Recuperer la regle par le profil
    Optional<ReglePret> findByProfil(Profil profil);

    // Recuperer la regle par le type de pret
    Optional<ReglePret> findByTypePret(TypePret typePret);

    // Recuperer la regle par le type de pret et le profil
    Optional<ReglePret> findByTypePretAndProfil(TypePret typePret, Profil profil);
}