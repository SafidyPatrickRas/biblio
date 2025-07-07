package com.bibliotheque.Bibliotheque.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.repository.AdherantRepository;

@Service
public class AdherantService {
    @Autowired
    private AdherantRepository adherantRepository;
    // CRUD

    // Create
    public void createAdherant(Adherant adherant) {
        adherantRepository.save(adherant);
    }

    // Read
    public Adherant getAdherantById(Integer id) {
        return adherantRepository.findById(id).orElse(null);
    }

<<<<<<< Updated upstream
    public Adherant getAdherantById(Integer id) {
        return adherantRepository.findById(id).orElse(null);
    }

    public Adherant createAdherant(Adherant adherant) {
        return adherantRepository.save(adherant);
    }

    public Adherant updateAdherant(Adherant adherant) {
        return adherantRepository.save(adherant);
    }

    public void deleteAdherant(Integer id) {
        adherantRepository.deleteById(id);
    }

    public boolean estAbonneEnCeMoment(int idAdherant) {
        Date dateCourante = new Date();
        List<Abonnement> abonnements = abonnementRepository.findByAdherantId(idAdherant);
        return abonnements.stream()
                .anyMatch(abonnement -> abonnement.getDateInscription().before(dateCourante) && 
                           (abonnement.getDateFinInscription() == null || abonnement.getDateFinInscription().after(dateCourante)));
=======
    // Update
    public void updateAdherant(Adherant adherant) {
        adherantRepository.save(adherant);
>>>>>>> Stashed changes
    }

    // Delete
    public void deleteAdherant(Integer id) {
        adherantRepository.deleteById(id);
    }

    // Get all adherants
    public List<Adherant> getAllAdherants() {
        return adherantRepository.findAll();
    }

<<<<<<< Updated upstream
    // Méthodes de comptage
    public long countAll() {
        return adherantRepository.count();
    }

}
=======
    // Additional methods can be added as needed

    // Trouver par email
    public Adherant findByEmail(String email) {
        Optional<Adherant> optionalAdherant = adherantRepository.findByEmail(email);
        if (optionalAdherant.isPresent()) {
            return optionalAdherant.get();
        }
        return null; 
    }

    // Trouver par profil
    public List<Adherant> findByProfilId(Integer profilId) {
        return adherantRepository.findByProfilId(profilId);
    }

}
>>>>>>> Stashed changes
