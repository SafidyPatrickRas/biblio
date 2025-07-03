package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
public class Adherant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50)
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50)
    private String prenom;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(max = 50)
    private String motDePasse;

    @NotBlank(message = "L'email est obligatoire")
    @Size(max = 50)
    private String email;

    @NotNull(message = "La date de naissance est obligatoire")
    private LocalDate dateNaissance;

    @ManyToOne
    @JoinColumn(name = "id_auteur", nullable = true)
    private Profil profil;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }
}