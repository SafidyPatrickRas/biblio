package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CategorieLivre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(max = 50, message = "Le nom de la catégorie ne doit pas dépasser 50 caractères")
    private String nom;

    @ManyToMany(mappedBy = "categories")
    private Set<Livre> livres = new HashSet<>();

    // Constructeurs
    public CategorieLivre() {
    }

    public CategorieLivre(String nom) {
        this.nom = nom;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "CategorieLivre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}