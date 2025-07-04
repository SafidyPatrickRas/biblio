package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
@Entity
@Table(name = "prolongement")
public class Prolongement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pret_id", nullable = false)
    private Pret pret;

    @Column(nullable = false)
    private Integer nombreJoursProlonger;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    // Getters
    public Integer getId() {
        return id;      
    }

    public Integer getNombreJoursProlonger() {
        return nombreJoursProlonger;
    }

    public Status getStatus() {
        return status;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

    public void setNombreJoursProlonger(Integer nombreJoursProlonger) {
        this.nombreJoursProlonger = nombreJoursProlonger;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
