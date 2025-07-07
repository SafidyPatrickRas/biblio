package com.bibliotheque.Bibliotheque.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Abonnement;
import com.bibliotheque.Bibliotheque.repository.AbonnementRepository;

@Service
public class AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository;

    // findPretsNonRendusParLivre
    public List<Abonnement> findPretsNonRendusParLivre(Integer livreId) {
        return abonnementRepository.findPretsNonRendusParLivre(livreId);
    }


    public boolean estAbonneeAdherant(Date date ,  Integer adherantId) {
    List<Abonnement> abonnements = abonnementRepository.estAbonneeAdherant(date, adherantId);

    // Correct : test si la liste est vide ou non
    return abonnements != null && !abonnements.isEmpty();
}


    // findAbonnementsChevauchantsByAdherant
    public List<Abonnement> findAbonnementsChevauchantsByAdherant(Integer adherantId,Date debut,Date fin) {
        return abonnementRepository.findAbonnementsChevauchantsByAdherant(debut,fin, adherantId);
    }


    // create
    public void createAbonnement(Abonnement abonnement) {
        abonnementRepository.save(abonnement);
    }
    
    // read
    public Abonnement getAbonnementById(Integer id) {
        return abonnementRepository.findById(id).orElse(null);
    }

    // update
    public void updateAbonnement(Abonnement abonnement) {
        abonnementRepository.save(abonnement);
    }

    // delete
    public void deleteAbonnement(Integer id) {
        abonnementRepository.deleteById(id);
    }

    // get all abonnements
    public List<Abonnement> getAllAbonnements() {
        return abonnementRepository.findAll();
    }
}
