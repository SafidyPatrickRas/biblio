package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;

@Entity
@Table(name = "regle_prolongement")
public class RegleProlongement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer nombreJoursMax;

    @Column(nullable = false)
    private Integer nombreProlongationsMax;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Double penaliteParJour;

    @OneToOne
    @JoinColumn(name = "type_pret_id")
    private TypePret typePret;

    // Getters
    public Long getId() {
        return id;
    }

    public Integer getNombreJoursMax() {
        return nombreJoursMax;
    }

    public Integer getNombreProlongationsMax() {
        return nombreProlongationsMax;
    }

    public Boolean getActive() {
        return active;
    }

    public Double getPenaliteParJour() {
        return penaliteParJour;
    }

    public TypePret getTypePret() {
        return typePret;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNombreJoursMax(Integer nombreJoursMax) {
        this.nombreJoursMax = nombreJoursMax;
    }

    public void setNombreProlongationsMax(Integer nombreProlongationsMax) {
        this.nombreProlongationsMax = nombreProlongationsMax;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setPenaliteParJour(Double penaliteParJour) {
        this.penaliteParJour = penaliteParJour;
    }

    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }

    public RegleProlongement() {
    }

    public RegleProlongement(Long id, Integer nombreJoursMax, Integer nombreProlongationsMax, Boolean active, Double penaliteParJour, TypePret typePret) {
        this.id = id;
        this.nombreJoursMax = nombreJoursMax;
        this.nombreProlongationsMax = nombreProlongationsMax;
        this.active = active;
        this.penaliteParJour = penaliteParJour;
        this.typePret = typePret;
    }

    
}

