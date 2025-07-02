package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.repository.AdherantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdherantService {

    @Autowired
    private AdherantRepository adherantRepository;

    public List<Adherant> findAll() {
        return adherantRepository.findAll();
    }

    public Optional<Adherant> findById(Integer id) {
        return adherantRepository.findById(id);
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
}