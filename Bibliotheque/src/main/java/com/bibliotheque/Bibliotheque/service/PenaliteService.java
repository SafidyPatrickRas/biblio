package com.bibliotheque.Bibliotheque.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Penalite;
import com.bibliotheque.Bibliotheque.repository.PenaliteRepository;

@Service
public class PenaliteService {
    @Autowired
    private PenaliteRepository penaliteRepository;

    // findPenalitesActivesByAdherant
    public List<Penalite> findPenalitesActivesByAdherant(Date date ,  Integer adherantId) {
        return penaliteRepository.findPenalitesActivesByAdherant(adherantId , date);
    }

    // Create
    public void createPenalite(Penalite penalite) {
        penaliteRepository.save(penalite);
    }

    // Read
    public Penalite getPenaliteById(Integer id) {
        return penaliteRepository.findById(id).orElse(null);
    }
    // Update
    public Penalite updatePenalite(Penalite penalite) {
        return penaliteRepository.save(penalite);
    }
    // Delete
    public void deletePenalite(Integer id) {
        penaliteRepository.deleteById(id);
    }

    // List all penalites
    public List<Penalite> getAllPenalites() {
        return penaliteRepository.findAll();
    }


}
