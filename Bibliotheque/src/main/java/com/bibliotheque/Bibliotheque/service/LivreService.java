package com.bibliotheque.Bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.repository.LivreRepository;

@Service
public class LivreService {
    @Autowired
    private LivreRepository livreRepository;

    // findByTitre
    public Livre findByTitre(String titre) {

        return livreRepository.findByTitre(titre).orElse(null);
    }

    // CRUD operations can be added here as needed

    // Create 
    public void createLivre(Livre livre) {
        livreRepository.save(livre);
    }
    // Read
    public Livre getLivreById(Integer id) {
        return livreRepository.findById(id).orElse(null);
    }
    // Update
    public void updateLivre(Livre livre) {
        livreRepository.save(livre);
    }
    // Delete
    public void deleteLivre(Integer id) {
        livreRepository.deleteById(id);
    }

    public List<Livre> getAllLivres() {
        return livreRepository.findAll();
    }
<<<<<<< Updated upstream

    public List<Livre> getByAuteurId(Integer idAuteur) {
        return livreRepository.findByAuteurId(idAuteur);
    }

    public List<Livre> getByCategorieId(Integer idCategorie) {
        return livreRepository.findByCategoriesId(idCategorie);
    }

    public List<Livre> getByAuteurAndCategorie(Integer idAuteur, Integer idCategorie) {
        return livreRepository.findByAuteurAndCategorie(idAuteur, idCategorie);
    }

    public List<Livre> getByTitreAndAuteur(String titre, Integer idAuteur) {
        return livreRepository.findByTitreAndAuteurId(titre, idAuteur);
    }

    public boolean existsByTitreAndAuteur(String titre, Integer idAuteur) {
        return livreRepository.existsByTitreAndAuteurId(titre, idAuteur);
    }

    public int getNombreExemplaires(Integer livreId) {
        Livre livre = livreRepository.findById(livreId)
            .orElseThrow(() -> new RuntimeException("Livre non trouvé"));
        return livre.getExemplaires().size();
    }

    // Méthodes de comptage
    public long countAll() {
        return livreRepository.count();
    }

=======
>>>>>>> Stashed changes
}
