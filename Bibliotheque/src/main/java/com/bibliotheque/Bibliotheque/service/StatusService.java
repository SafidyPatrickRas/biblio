package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Optional<Status> findById(Integer id) {
        return statusRepository.findById(id);
    }

    public Status save(Status status) {
        return statusRepository.save(status);
    }

    public void deleteById(Integer id) {
        statusRepository.deleteById(id);
    }
    public Optional<Status> findByNom(String nom) {
        return statusRepository.findByNom(nom); 
    }
}