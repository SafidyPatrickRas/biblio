package com.bibliotheque.Bibliotheque.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotheque.Bibliotheque.model.Profil;

public interface ProfilRepository extends JpaRepository<Profil, Long> {
}