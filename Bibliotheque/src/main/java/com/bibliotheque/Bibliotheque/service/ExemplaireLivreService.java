package com.bibliotheque.Bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.ExemplaireLivre;
import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.repository.ExemplaireLivreRepository;

@Service
public class ExemplaireLivreService {
    @Autowired
    private ExemplaireLivreRepository exemplaireLivreRepository;

    // findByLivre
    public ExemplaireLivre findByLivre(Livre livre) {
        return exemplaireLivreRepository.findByLivre(livre).orElse(null);
    }

    // CRUD

    // Create
    public void createExemplaireLivre(ExemplaireLivre exemplaireLivre) {
        exemplaireLivreRepository.save(exemplaireLivre);
    }
    // Read
    public ExemplaireLivre getExemplaireLivreById(Integer id) {
        return exemplaireLivreRepository.findById(id).orElse(null);
    }
    // Update
    public void updateExemplaireLivre(ExemplaireLivre exemplaireLivre) {
        exemplaireLivreRepository.save(exemplaireLivre);
    }
    // Delete
    public void deleteExemplaireLivre(Integer id) {
        exemplaireLivreRepository.deleteById(id);
    }

}
