package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;

@Entity
public class TypePret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nom;

    // Constructeurs
    public TypePret() {
    }

    public TypePret(String nom) {
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

    @Override
    public String toString() {
        return "TypePret{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
