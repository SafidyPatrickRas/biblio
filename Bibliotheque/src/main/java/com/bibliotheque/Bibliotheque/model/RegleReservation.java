package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = "profil_id")
)
public class RegleReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "profil_id", nullable = false)
    private Profil profil;

    @NotNull(message = "Le nombre maximum de réservations est obligatoire")
    @Min(value = 1, message = "Le nombre maximum doit être au moins 1")
    @Column(name = "max_reservations")
    private Integer maxReservations;

    @NotNull(message = "Le nombre de jours de validité est obligatoire")
    @Min(value = 1, message = "Le nombre de jours doit être au moins 1")
    @Column(name = "duree_validite_jours")
    private Integer dureeValiditeJours;

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Integer getMaxReservations() {
        return maxReservations;
    }

    public void setMaxReservations(Integer maxReservations) {
        this.maxReservations = maxReservations;
    }

    public Integer getDureeValiditeJours() {
        return dureeValiditeJours;
    }

    public void setDureeValiditeJours(Integer dureeValiditeJours) {
        this.dureeValiditeJours = dureeValiditeJours;
    }
}