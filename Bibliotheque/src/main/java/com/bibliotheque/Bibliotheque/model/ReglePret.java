package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id_profil", "id_type_pret"}))
public class ReglePret {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_type_pret", nullable = false)
    @NotNull(message = "Le type de prêt est obligatoire")
    private TypePret typePret;

    @ManyToOne
    @JoinColumn(name = "id_profil", nullable = false)
    @NotNull(message = "Le profil est obligatoire")
    private Profil profil;

    @NotNull(message = "Le nombre de livres est obligatoire")
    @Min(value = 1, message = "Le nombre de livres doit être supérieur à 0")
    private Integer nombreLivres;

    @NotNull(message = "Le nombre de jours est obligatoire")
    @Min(value = 1, message = "Le nombre de jours doit être supérieur à 0")
    private Integer nombreJours;

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypePret getTypePret() {
        return typePret;
    }

    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Integer getNombreLivres() {
        return nombreLivres;
    }

    public void setNombreLivres(Integer nombreLivres) {
        this.nombreLivres = nombreLivres;
    }

    public Integer getNombreJours() {
        return nombreJours;
    }

    public void setNombreJours(Integer nombreJours) {
        this.nombreJours = nombreJours;
    }
}
