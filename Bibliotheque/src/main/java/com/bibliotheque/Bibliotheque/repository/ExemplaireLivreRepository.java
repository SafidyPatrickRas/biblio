package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.ExemplaireLivre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemplaireLivreRepository extends JpaRepository<ExemplaireLivre, Integer> {
    // Récupérer tous les exemplaires d'un livre donné
    List<ExemplaireLivre> findByLivreId(Integer livreId);

    // Compter le nombre d'exemplaires pour un livre donné
    long countByLivreId(Integer livreId);
}
