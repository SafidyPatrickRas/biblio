package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatusService {
    
    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    public Optional<Status> getById(Long id) {
        return statusRepository.findById(id);
    }

    public Optional<Status> getByNom(String nom) {
        return Optional.ofNullable(statusRepository.findByNom(nom));
    }

    public Status create(Status status) {
        return statusRepository.save(status);
    }

    public Status update(Long id, Status status) {
        return statusRepository.findById(id)
                .map(existingStatus -> {
                    existingStatus.setNom(status.getNom());
                    return statusRepository.save(existingStatus);
                })
                .orElseThrow(() -> new RuntimeException("Status non trouvé avec l'ID : " + id));
    }

    public void delete(Long id) {
        statusRepository.deleteById(id);
    }
}
