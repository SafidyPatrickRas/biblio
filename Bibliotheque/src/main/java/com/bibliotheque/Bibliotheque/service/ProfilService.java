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

    public List<Profil> getAll() {
        return profilRepository.findAll();
    }

    public void save(Profil profil) {
        profilRepository.save(profil);
    }

    public Profil getById(Integer id) {
        return profilRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        profilRepository.deleteById(id);
    }
}
