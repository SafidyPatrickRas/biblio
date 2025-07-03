package com.bibliotheque.Bibliotheque.model;

import java.time.LocalDate;

import org.hibernate.annotations.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity 
public class Penalite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "adherant_id", nullable = false)
    private Adherant adherant;

    @NotNull(message = "La date de début est obligatoire")
    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    @Column(name = "date_fin")
    private LocalDate dateFin;

    @NotNull(message = "Le nombre de jours est obligatoire")
    private Integer nombreJours;

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    

    public void setId(Integer id) {
        this.id = id;
    }

    public Adherant getAdherant() {
        return adherant;
    }

    public void setAdherant(Adherant adherant) {
        this.adherant = adherant;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }



    public Integer getNombreJours() {
        return nombreJours;
    }



    public void setNombreJours(Integer nombreJours) {
        this.nombreJours = nombreJours;
    }
}