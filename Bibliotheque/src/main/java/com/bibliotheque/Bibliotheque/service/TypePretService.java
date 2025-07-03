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

    public List<TypePret> getAllTypePrets() {
        return typePretRepository.findAll();
    }

    public TypePret getTypePretById(Integer id) {
        Optional<TypePret> typePret = typePretRepository.findById(id);
        return typePret.orElse(null);
    }

    public TypePret getTypePretByNom(String nom) {
        return typePretRepository.findByNom(nom);
    }

    public TypePret createTypePret(TypePret typePret) {
        return typePretRepository.save(typePret);
    }

    public TypePret updateTypePret(TypePret typePret) {
        return typePretRepository.save(typePret);
    }

    public void deleteTypePret(Integer id) {
        typePretRepository.deleteById(id);
    }
}
