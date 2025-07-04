package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Abonnement;
import com.bibliotheque.Bibliotheque.repository.AdherantRepository;
import com.bibliotheque.Bibliotheque.repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdherantService {

    @Autowired
    private AdherantRepository adherantRepository;

    @Autowired
    private AbonnementRepository abonnementRepository;

    public List<Adherant> findAll() {
        return adherantRepository.findAll();
    }

    public Optional<Adherant> findById(Integer id) {
        return adherantRepository.findById(id);
    }

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
    }

    public Optional<Adherant> findByEmail(String email) {
        return adherantRepository.findByEmail(email);
    }

    public Adherant save(Adherant adherant) {
        return adherantRepository.save(adherant);
    }

    public void deleteById(Integer id) {
        adherantRepository.deleteById(id);
    }

    public Adherant authenticate(String email, String motDePasse) {
        Optional<Adherant> adherant = adherantRepository.findByEmail(email);
        if (adherant.isPresent() && adherant.get().getMotDePasse().equals(motDePasse)) {
            return adherant.get();
        }
        return null;
    }

    // Méthodes de comptage
    public long countAll() {
        return adherantRepository.count();
    }

}