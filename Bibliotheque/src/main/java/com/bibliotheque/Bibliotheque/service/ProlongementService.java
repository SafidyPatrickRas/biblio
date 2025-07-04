package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Prolongement;
import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.repository.ProlongementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProlongementService {

    @Autowired
    private ProlongementRepository prolongementRepository;

    // Sauvegarder une nouvelle prolongation
    public Prolongement createProlongement(Prolongement prolongement) {
        return prolongementRepository.save(prolongement);
    }

    // Récupérer toutes les prolongations
    public List<Prolongement> getAllProlongements() {
        return prolongementRepository.findAll();
    }

    // Récupérer une prolongation par ID
    public Optional<Prolongement> getProlongementById(Integer id) {
        return prolongementRepository.findById(id);
    }

    // Récupérer une prolongation par ID de prêt
    public Prolongement getProlongementByPret(Pret pret) {
        return prolongementRepository.findByPret(pret);
    }

    // Mettre à jour une prolongation
    public Prolongement updateProlongement(Prolongement prolongement) {
        return prolongementRepository.save(prolongement);
    }

    // Supprimer une prolongation
    public void deleteProlongement(Integer id) {
        prolongementRepository.deleteById(id);
    }

    // Méthodes de comptage
    public long countAll() {
        return prolongementRepository.count();
    }

    public long countEnCours() {
        return prolongementRepository.countByStatusNom("En cours");
    }

    // Récupérer les prolongements d'un adhérent
    public List<Prolongement> findByAdherant(Adherant adherant) {
        return prolongementRepository.findByAdherantId(adherant.getId());
    }
}
