package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.ExemplaireLivre;
import com.bibliotheque.Bibliotheque.repository.ExemplaireLivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExemplaireLivreService {

    @Autowired
    private ExemplaireLivreRepository exemplaireLivreRepository;

    // Retourne le nombre d'exemplaires pour un livre donné
    public long countExemplairesByLivreId(Integer livreId) {
        return exemplaireLivreRepository.countByLivreId(livreId);
    }

    // Obtenir le nombre d'exemplaires d'un livre spécifique
    public Integer nombreExemplaireLivre(Integer livreId) {
    Optional<ExemplaireLivre> exemplaire = exemplaireLivreRepository.findExemplaireByLivreId(livreId);
    if (exemplaire.isPresent()) {
        return exemplaire.get().getNombreExemplaires();
    }
    return 0;
}   

    // Récupère un exemplaire par son id
    public Optional<ExemplaireLivre> findById(Integer id) {
        return exemplaireLivreRepository.findById(id);
    }

    public List<ExemplaireLivre> findAll() {
        return exemplaireLivreRepository.findAll();
    }


    // Créer un nouvel exemplaire de livre
    public ExemplaireLivre createExemplaire(ExemplaireLivre exemplaireLivre) {
        return exemplaireLivreRepository.save(exemplaireLivre);
    }

    // Mettre à jour un exemplaire existant
    public ExemplaireLivre updateExemplaire(ExemplaireLivre exemplaireLivre) {
        return exemplaireLivreRepository.save(exemplaireLivre);
    }

    // Supprimer un exemplaire par son ID
    public void deleteExemplaire(Integer id) {
        exemplaireLivreRepository.deleteById(id);
    }

    // Récupérer l'exemplaire par ID de livre
    public ExemplaireLivre getExemplaireByLivreId(Integer livreId) {
        Optional<ExemplaireLivre> exemplaireLivre = exemplaireLivreRepository.findByLivreId(livreId);
        return exemplaireLivre.orElse(null);
    }

    // Récupérer le nombre d'exemplaires pour un livre
    public long getNombreExemplairesByLivreId(Integer livreId) {
        return exemplaireLivreRepository.countByLivreId(livreId);
    }
}
