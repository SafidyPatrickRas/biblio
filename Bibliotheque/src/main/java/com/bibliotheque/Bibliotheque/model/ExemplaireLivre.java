package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "exemplaire_livre")
public class ExemplaireLivre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @NotNull(message = "Le nombre d'exemplaires est obligatoire")
    @Min(value = 1, message = "Le nombre d'exemplaires doit être supérieur à 0")
    private Integer nombreExemplaires;

    // Getters et Setters
    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Integer getNombreExemplaires() {
        return nombreExemplaires;
    }

    public void setNombreExemplaires(Integer nombreExemplaires) {
        this.nombreExemplaires = nombreExemplaires;
    }
}
