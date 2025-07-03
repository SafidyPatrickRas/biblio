package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Penalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PenaliteRepository extends JpaRepository<Penalite, Integer> {
    // Trouver toutes les pénalités d'un adhérant
    List<Penalite> findByAdherantId(Integer adherantId);

    // Trouver les pénalités actives d'un adhérant (optionnel)
    List<Penalite> findByAdherantIdAndDateFinAfter(Integer adherantId, java.time.LocalDate date);

    List<Penalite> findByAdherant(Adherant adherant);

    Optional<Penalite> findTopByAdherantOrderByDateFinDesc(Adherant adherant);

}