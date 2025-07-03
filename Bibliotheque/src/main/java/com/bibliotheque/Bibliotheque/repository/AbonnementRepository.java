package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AbonnementRepository extends JpaRepository<Abonnement, Integer> {
    List<Abonnement> findByAdherantId(Integer id);
}
