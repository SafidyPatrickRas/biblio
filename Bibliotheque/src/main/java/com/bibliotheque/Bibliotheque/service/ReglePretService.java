package com.bibliotheque.Bibliotheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.model.ReglePret;
import com.bibliotheque.Bibliotheque.model.TypePret;
import com.bibliotheque.Bibliotheque.repository.ReglePretRepository;

@Service
public class ReglePretService {
    @Autowired
    private ReglePretRepository reglePretRepository;
    
    public ReglePret findByTypePretAndAdherantId(TypePret typePret, Profil profil) {
        return reglePretRepository.findByTypePretAndProfil(typePret , profil).orElse(null);
    }

    // create 
    public void createReglePret(ReglePret reglePret) {
        reglePretRepository.save(reglePret);
    }

    // read
    public ReglePret getReglePretById(Integer id) {
        return reglePretRepository.findById(id).orElse(null);
    }

    // update
    public void updateReglePret(ReglePret reglePret) {
        reglePretRepository.save(reglePret);
    }
    // delete
    public void deleteReglePret(Integer id) {
        reglePretRepository.deleteById(id);
    }
}
