package com.bibliotheque.Bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Auteur;
import com.bibliotheque.Bibliotheque.repository.AuteurRepository;

@Service
public class AuteurService {

    @Autowired
    private AuteurRepository auteurRepository;

    // Récupérer tous les auteurs
    public List<Auteur> getAll() {
        return auteurRepository.findAll();
    }

    // Enregistrer un auteur (vérifie s'il existe déjà)
    public void save(Auteur auteur) throws Exception {
        if (auteurRepository.findByNomAndPrenom(auteur.getNom(), auteur.getPrenom()).isPresent()) {
            throw new Exception("Un auteur avec ce nom et prénom existe déjà.");
        }
        auteurRepository.save(auteur);
    }

    // Mettre à jour un auteur
    public void update(Auteur auteur) {
        auteurRepository.save(auteur);
    }

    // Obtenir un auteur par son ID
    public Auteur getById(Integer id) {
        return auteurRepository.findById(id).orElse(null);
    }

    // Supprimer un auteur
    public void delete(Integer id) {
        auteurRepository.deleteById(id);
    }

    // Recherche par nationalité
    public List<Auteur> findByNationalite(String nationalite) {
        return auteurRepository.findByNationalite(nationalite);
    }

    // Recherche par nom (insensible à la casse)
    public List<Auteur> findByNomContaining(String nom) {
        return auteurRepository.findByNomContainingIgnoreCase(nom);
    }
}