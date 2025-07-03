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

    public boolean estAbonneEnCeMoment(int idAdherant) {
        Date dateCourante = new Date();
        List<Abonnement> abonnements = abonnementRepository.findByAdherantId(idAdherant);
        
        for (Abonnement abonnement : abonnements) {
            Date dateDebut = abonnement.getDateInscription();
            Date dateFin = abonnement.getDateFinInscription();
            
            // Vérifier si la date courante est entre la date de début et la date de fin
            if (dateCourante.after(dateDebut) && dateCourante.before(dateFin)) {
                return true; // L'adhérent est abonné
            }
        }
        return false; // L'adhérent n'est pas abonné en ce moment
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
}