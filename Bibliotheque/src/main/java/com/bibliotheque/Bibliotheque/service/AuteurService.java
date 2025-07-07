package com.bibliotheque.Bibliotheque.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Auteur;
import com.bibliotheque.Bibliotheque.repository.AuteurRepository;

@Service
public class AuteurService {
    @Autowired
    private AuteurRepository auteurRepository;
    
    
    // Recuperer par nom
    public Auteur getAuteurByNom(String nom) {
        Optional<Auteur> optionalAuteur = auteurRepository.findByNom(nom);
        if (optionalAuteur.isPresent()) {
            return optionalAuteur.get();
        }
        return null; // Si aucun auteur n'est trouvé, retourner null
    }
    
    // CRUD

    // Create
    public void createAuteur(Auteur auteur) {
        auteurRepository.save(auteur);
    }

    // Read
    public Auteur getAuteurById(Integer id) {
        return auteurRepository.findById(id).orElse(null);
    }

    // Update
    public void updateAuteur(Auteur auteur) {
        auteurRepository.save(auteur);
    }

    // Delete
    public void deleteAuteur(Integer id) {
        auteurRepository.deleteById(id);
    }


}
