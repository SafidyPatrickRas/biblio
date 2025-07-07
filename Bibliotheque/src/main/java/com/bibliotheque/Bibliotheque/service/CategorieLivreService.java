package com.bibliotheque.Bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.CategorieLivre;
import com.bibliotheque.Bibliotheque.repository.CategorieLivreRepository;

@Service
public class CategorieLivreService {
    @Autowired
    private CategorieLivreRepository categorieLivreRepository;
    // CRUD
    
    // Create
    public void createCategorieLivre(CategorieLivre categorieLivre) {
        categorieLivreRepository.save(categorieLivre);
    }

    // Read
    public CategorieLivre getCategorieLivreById(Integer id) {
        return categorieLivreRepository.findById(id).orElse(null); 
    }

    // Update
    public void updateCategorieLivre(CategorieLivre categorieLivre) {
        categorieLivreRepository.save(categorieLivre);
    }

    // Delete
    public void deleteCategorieLivre(Integer id) {
        categorieLivreRepository.deleteById(id);
    }
}
