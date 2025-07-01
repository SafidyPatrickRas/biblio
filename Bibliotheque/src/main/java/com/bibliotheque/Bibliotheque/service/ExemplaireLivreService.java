package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.ExemplaireLivre;
import com.bibliotheque.Bibliotheque.repository.ExemplaireLivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExemplaireLivreService {

    @Autowired
    private ExemplaireLivreRepository exemplaireLivreRepository;

    // Retourne le nombre d'exemplaires pour un livre donné
    public long countExemplairesByLivreId(Integer livreId) {
        return exemplaireLivreRepository.countByLivreId(livreId);
    }

    // Récupère un exemplaire par son id
    public Optional<ExemplaireLivre> findById(Integer id) {
        return exemplaireLivreRepository.findById(id);
    }
}
