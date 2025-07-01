package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.CategorieLivre;
import com.bibliotheque.Bibliotheque.repository.CategorieLivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieLivreService {

    @Autowired
    private CategorieLivreRepository categorieLivreRepository;

    public List<CategorieLivre> getAll() {
        return categorieLivreRepository.findAll();
    }

    public void save(CategorieLivre categorieLivre) throws Exception {
        if (categorieLivreRepository.findByNom(categorieLivre.getNom()).isPresent()) {
            throw new Exception("Une catégorie avec ce nom existe déjà");
        }
        categorieLivreRepository.save(categorieLivre);
    }

    public void update(CategorieLivre categorieLivre) {
        categorieLivreRepository.save(categorieLivre);
    }

    public CategorieLivre getById(Integer id) {
        return categorieLivreRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        categorieLivreRepository.deleteById(id);
    }
}