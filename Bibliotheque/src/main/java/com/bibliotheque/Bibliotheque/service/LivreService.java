package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LivreService {
    
    @Autowired
    private LivreRepository livreRepository;

    // CRUD basique
    public List<Livre> getAll() {
        return livreRepository.findAll();
    }

    public Optional<Livre> getById(Integer id) {
        return livreRepository.findById(id);
    }

    public Livre create(Livre livre) {
        return livreRepository.save(livre);
    }

    public Livre update(Integer id, Livre livre) {
        return livreRepository.findById(id)
                .map(existingLivre -> {
                    existingLivre.setTitre(livre.getTitre());
                    existingLivre.setDatePublication(livre.getDatePublication());
                    existingLivre.setNbPages(livre.getNbPages());
                    existingLivre.setLangue(livre.getLangue());
                    existingLivre.setTags(livre.getTags());
                    existingLivre.setAgeRestriction(livre.getAgeRestriction());
                    existingLivre.setAuteur(livre.getAuteur());
                    existingLivre.setCategories(livre.getCategories());
                    return livreRepository.save(existingLivre);
                })
                .orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID : " + id));
    }

    public void delete(Integer id) {
        livreRepository.deleteById(id);
    }

    // Méthodes spécifiques
    public Optional<Livre> getByTitre(String titre) {
        return livreRepository.findByTitre(titre);
    }

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
}
