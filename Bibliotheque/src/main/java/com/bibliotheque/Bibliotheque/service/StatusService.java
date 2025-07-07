package com.bibliotheque.Bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.repository.StatusRepository;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    // findByNom
    public Status findByNom(String nom) {
        return statusRepository.findByNom(nom).orElse(null);
    }
    // Create
    public void createStatus(Status status) {
        statusRepository.save(status);
    }

    // Read
    public Status getStatusById(Integer id) {
        return statusRepository.findById(id).orElse(null);
    }

    public Status findStatusByNom(String nom) {
        return statusRepository.findByNom(nom).orElse(null);
    }

    // Update
    public void updateStatus(Status status) {
        statusRepository.save(status);
    }

    // Delete
    public void deleteStatus(Integer id) {
        statusRepository.deleteById(id);
    }

    // find all
    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }
}