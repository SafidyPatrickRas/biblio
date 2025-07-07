package com.bibliotheque.Bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.repository.ProfilRepository;

@Service
public class ProfilService {
    @Autowired
    private ProfilRepository profilRepository;
    // CRUD 

    // Create 
    public void createProfil(Profil profil) {
        profilRepository.save(profil);
    }

    // Read
    public Profil getProfilById(Integer id) {
        return profilRepository.findById(id).orElse(null);
    }

    // Update
    public void updateProfil(Profil profil) {
        profilRepository.save(profil); 
    }

    // Delete
    public void deleteProfil(Integer id) {
        profilRepository.deleteById(id);  
    }

    // select all 
    public List<Profil> getAllProfils() {
        return profilRepository.findAll();
    }

    // Additional methods can be added as needed
}
