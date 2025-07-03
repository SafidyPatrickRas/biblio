package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Abonnement;
import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {
    
    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    private AdherantService adherantService;

    public boolean verifieChevauchementDates(int idAdherant, Date dateInscription, Date dateFinInscription) {
        List<Abonnement> abonnementsExistants = abonnementRepository.findByAdherantId(idAdherant);
        
        for (Abonnement abonnement : abonnementsExistants) {
            Date dateFinExistant = abonnement.getDateFinInscription();
            Date dateDebutExistant = abonnement.getDateInscription();
            
            // Vérifier si les dates se chevauchent
            if (dateInscription.before(dateFinExistant) && dateFinInscription.after(dateDebutExistant)) {
                return true; // Il y a un chevauchement
            }
        }
        return false; // Pas de chevauchement
    }

    public Abonnement creerAbonnement(int idAdherant, Date dateInscription, Date dateFinInscription) {
        Optional<Adherant> adherantOptional = adherantService.findById(idAdherant);
        if (adherantOptional.isPresent()) {
            if (verifieChevauchementDates(idAdherant, dateInscription, dateFinInscription)) {
                return null; // Il y a un chevauchement, on ne crée pas l'abonnement
            }
            
            Adherant adherant = adherantOptional.get();
            Abonnement abonnement = new Abonnement();
            abonnement.setDateInscription(dateInscription);
            abonnement.setDateFinInscription(dateFinInscription);
            abonnement.setAdherant(adherant);
            return abonnementRepository.save(abonnement);
        }
        return null;
    }

    public Abonnement findById(int id) {
        return abonnementRepository.findById(id).orElse(null);
    }
}
