package com.bibliotheque.Bibliotheque.service;

import com.bibliotheque.Bibliotheque.model.TypePret;
import com.bibliotheque.Bibliotheque.repository.TypePretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TypePretService {
    
    @Autowired
    private TypePretRepository typePretRepository;

    public List<TypePret> getAll() {
        return typePretRepository.findAll();
    }

    public Optional<TypePret> getById(Long id) {
        return typePretRepository.findById(id);
    }

    public Optional<TypePret> getByNom(String nom) {
        return Optional.ofNullable(typePretRepository.findByNom(nom));
    }

    public TypePret create(TypePret typePret) {
        return typePretRepository.save(typePret);
    }

    public TypePret update(Long id, TypePret typePret) {
        return typePretRepository.findById(id)
                .map(existingTypePret -> {
                    existingTypePret.setNom(typePret.getNom());
                    return typePretRepository.save(existingTypePret);
                })
                .orElseThrow(() -> new RuntimeException("Type de prêt non trouvé avec l'ID : " + id));
    }

    public void delete(Long id) {
        typePretRepository.deleteById(id);
    }
}