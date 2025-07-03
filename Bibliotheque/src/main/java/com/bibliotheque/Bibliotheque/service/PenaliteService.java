package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Penalite;
import com.bibliotheque.Bibliotheque.repository.PenaliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PenaliteService {

    @Autowired
    private PenaliteRepository penaliteRepository;

    public List<Penalite> findAll() {
        return penaliteRepository.findAll();
    }

    public Optional<Penalite> findById(Integer id) {
        return penaliteRepository.findById(id);
    }

    public List<Penalite> findByAdherantId(Integer adherantId) {
        return penaliteRepository.findByAdherantId(adherantId);
    }

    public List<Penalite> findPenalitesActives(Integer adherantId) {
        return penaliteRepository.findByAdherantIdAndDateFinAfter(adherantId, LocalDate.now());
    }

    public Penalite save(Penalite penalite) {
        return penaliteRepository.save(penalite);
    }

    public void deleteById(Integer id) {
        penaliteRepository.deleteById(id);
    }
    public boolean estEnPenalite(Adherant adherant) {
    LocalDate aujourdHui = LocalDate.now();
    List<Penalite> penalites = penaliteRepository.findByAdherant(adherant);

    for (Penalite p : penalites) {
        if (p.getDateDebut() != null && p.getDateFin() != null) {
            if (!aujourdHui.isBefore(p.getDateDebut()) && !aujourdHui.isAfter(p.getDateFin())) {
                return true; // pénalité active trouvée
            }
        }
    }
    return false; // aucune pénalité active
}
public Penalite trouverDernierePenalite(Adherant adherant) {
    return penaliteRepository.findTopByAdherantOrderByDateFinDesc(adherant).orElse(null);
}

}