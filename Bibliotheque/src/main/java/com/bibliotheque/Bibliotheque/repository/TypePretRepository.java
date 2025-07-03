package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.TypePret;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePretRepository extends JpaRepository<TypePret, Integer> {
    TypePret findByNom(String nom);
}
