package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.repository.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.StackWalker.Option;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PretService {
    @Autowired
    private PretRepository pretRepository;

    public List<Pret> getAllPretsOrderByDateDesc() {
        return pretRepository.findAllOrderByDateDesc();
    }

    public List<Pret> getAllPrets() {
        return pretRepository.findAll();
    }

    public Pret getPretById(Integer id) {
        Optional<Pret> pretOptional = pretRepository.findById(id);
        if (pretOptional.isPresent()) {
            return pretOptional.get();
        }
        return null; // ou lancer une exception si nécessaire
    }

    public List<Pret> getPretsByAdherant(Integer idAdherant) {
        return pretRepository.findByAdherant_Id(idAdherant);
    }

    public List<Pret> getPretsByDateRange(Date dateDebut, Date dateFin) {
        return pretRepository.findByDateFinBeforeAndDateFinAfter(dateDebut, dateFin);
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

    public void deletePret(Integer id) {
        pretRepository.deleteById(id);
    }

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
    }
}
