package com.bibliotheque.Bibliotheque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bibliotheque.Bibliotheque.model.TypePret;
import com.bibliotheque.Bibliotheque.repository.TypePretRepository;

@Service
public class TypePretService {
    @Autowired
    private TypePretRepository typePretRepository;

    // getTypePretByName
    public TypePret getTypePretByName(String nom) {
        return typePretRepository.findByNom(nom).orElse(null);
    }

    // create
    public void createTypePret(TypePret typePret) {
        typePretRepository.save(typePret);
    }
    // read
    public TypePret getTypePretById(Integer id) {
        return typePretRepository.findById(id).orElse(null);
    }

    // update
    public void updateTypePret(TypePret typePret) {
        typePretRepository.save(typePret);
    }

    // delete
    public void deleteTypePret(Integer id) {
        typePretRepository.deleteById(id);  
    }    

    // get all
    public List<TypePret> getAllTypePrets() {
        return typePretRepository.findAll();
    }
}
