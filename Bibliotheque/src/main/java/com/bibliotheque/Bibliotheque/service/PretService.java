package com.bibliotheque.Bibliotheque.service;

<<<<<<< Updated upstream
import com.bibliotheque.Bibliotheque.model.Adherant;
=======
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

>>>>>>> Stashed changes
import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.repository.AdherantRepository;
import com.bibliotheque.Bibliotheque.repository.PretRepository;
<<<<<<< Updated upstream
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.StackWalker.Option;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
=======
>>>>>>> Stashed changes

@Service
public class PretService {
    @Autowired
    private PretRepository pretRepository;

    // findLivresPretesParAdherant
    public List<Livre> findLivresPretesParAdherant(Integer adherantId) {
        return pretRepository.findLivresPretesParAdherant(adherantId);
    }

    // findPretsNonRendusParLivre
    public List<Pret> findPretsNonRendusParLivre(Integer livreId) {
        return pretRepository.findPretsNonRendusParLivre(livreId);
    }

    public List<Pret> findPretsNonRendusParAdherant(Integer adherantId) {
        return pretRepository.findPretsNonRendusParAdherant(adherantId);
    }

    // create
    public void createPret(Pret pret) {
        pretRepository.save(pret);
    }

    // read
    public Pret getPretById(Integer id) {
        return pretRepository.findById(id).orElse(null);
    }

<<<<<<< Updated upstream
    public List<Pret> getPretsByAdherantId(Integer idAdherant) {
        return pretRepository.findByAdherantId(idAdherant);
    }

    public List<Pret> getPretsByDateRange(Date dateDebut, Date dateFin) {
        return pretRepository.findByDateFinBetween(dateDebut, dateFin);
    }

    // Compter tous les prêts non retournés
    public long getNombrePretsNonRetournes() {
        return pretRepository.countByIsRetourneeFalse();
    }

    // Compter les prêts non retournés pour un adhérent
    public Integer getNombrePretsNonRetournesParAdherent(Integer idAdherant) {
        return pretRepository.countByAdherant_IdAndIsRetourneeFalse(idAdherant);
    }

    // Récupérer les prêts non retournés par adhérent
    public List<Pret> getLivresNonRetournesParAdherent(Integer idAdherant) {
        return pretRepository.findByAdherant_IdAndIsRetourneeFalse(idAdherant);
    }

    public Pret createPret(Pret pret) {
        return pretRepository.save(pret);
    }

    public Pret updatePret(Pret pret) {
        return pretRepository.save(pret);
    }

    @Transactional
    public String rendrePret(Integer id, Date dateRendu) {
        String message = "";
        Pret pret = pretRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé"));

        if (pret.getIsRetournee()) {
            message = "Ce prêt a déjà été rendu";
        }

        pret.setIsRetournee(true);
        pret.setDateRendu(dateRendu);
        pretRepository.save(pret);

        if (dateRendu.after(pret.getDateFin())) {
            message = "Retard détecté. Redirection vers le formulaire de pénalité";
        }
        return message;
    }

=======
    // update
    public void updatePret(Pret pret) {
        pretRepository.save(pret);
    }
    // delete
>>>>>>> Stashed changes
    public void deletePret(Integer id) {
        pretRepository.deleteById(id);
    }

<<<<<<< Updated upstream
    // Compter les livres non retournés par ID de livre
    public long getNombreLivresNonRetournesParIdLivre(Integer idLivre) {
        return pretRepository.countByLivre_IdAndIsRetourneeFalse(idLivre);
    }

    public List<Pret> getPretsActifsPourLivre(Integer livreId) {
        return pretRepository.findByIsRetourneeFalseAndLivreId(livreId);
    }

    // Alternative avec l'entité Livre
    public List<Pret> getPretsActifsPourLivre(Livre livre) {
        return pretRepository.findByIsRetourneeFalseAndLivre(livre);
=======
    // get add
    public List<Pret> getAllPrets() {
        return pretRepository.findAll();
>>>>>>> Stashed changes
    }
}
