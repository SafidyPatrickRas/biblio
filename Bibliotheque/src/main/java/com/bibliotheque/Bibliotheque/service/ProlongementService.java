package com.bibliotheque.Bibliotheque.service;

<<<<<<< Updated upstream
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
=======
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.Prolongement;
import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.repository.ProlongementRepository;
import com.bibliotheque.Bibliotheque.repository.StatusRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProlongementService {
    @Autowired
    ProlongementRepository prolongementRepository;

    @Autowired
    PretService pretService;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    StatusService statusService;

    public void acceptProlongement(Integer prolongementId) {
        Prolongement prolongement = prolongementRepository.findById(prolongementId).get();

        Pret pret = prolongement.getPret();

        Status status = statusService.findByNom("Accepté"); 
        
        prolongement.setStatus(status);
        this.updateProlongement(prolongement);
       
        Date dateFin = pret.getDateFin(); // Date initiale
        int nbJours = prolongement.getDureeProlongation(); // nombre de jours à ajouter

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFin);
        cal.add(Calendar.DAY_OF_MONTH, nbJours);

        pret.setDateFin(cal.getTime()); // nouvelle date de retour

        pret.setEstProlonge(true);
        pretService.updatePret(pret);

    }
    
    // findProlongementsParAdherantEnCoursDePret
    public List<Prolongement> findProlongementsParAdherantEnCoursDePret(Integer adherantId) {
        return prolongementRepository.findProlongementsParAdherantEnCoursDePret(adherantId);
    }

    // FIND BY PRET
     public Prolongement findByPret(Pret pret) {
        return prolongementRepository.findByPret(pret).orElse(null);
    }

    // CREATE
    public void createProlongement(Prolongement prolongement) {
        prolongementRepository.save(prolongement);
    }

    // DELETE
    public void deleteProlongement(Prolongement prolongement ) {
        prolongementRepository.delete(prolongement);
    }

    // READ
    public Prolongement getProlongementById(Integer id) {
        return prolongementRepository.findById(id).orElse(null);
    }

    // UPDATE
    public void updateProlongement(Prolongement prolongement) {
        prolongementRepository.save(prolongement);
    }

    // find all
    public List<Prolongement> getAllProlongement() {
        return prolongementRepository.findAll();
    }


>>>>>>> Stashed changes
}
