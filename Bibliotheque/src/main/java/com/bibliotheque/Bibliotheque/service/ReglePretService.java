package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.ReglePret;
import com.bibliotheque.Bibliotheque.repository.ReglePretRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReglePretService {
    
    @Autowired
    private ReglePretRepository reglePretRepository;

    public ReglePret getReglePretByProfilAndTypePret(Integer idProfil, Integer idTypePret) {
        return reglePretRepository.findFirstByProfilIdAndTypePretId(idProfil, idTypePret).orElse(null);
    }

    public List<ReglePret> getReglesByProfil(Integer idProfil) {
        return reglePretRepository.findByProfilId(idProfil);
    }

    public List<ReglePret> getReglesByTypePret(Integer idTypePret) {
        return reglePretRepository.findByTypePretId(idTypePret);
    }

    public ReglePret save(ReglePret reglePret) {
        return reglePretRepository.save(reglePret);
    }

    public void delete(Integer id) {
        reglePretRepository.deleteById(id);
    }
}
