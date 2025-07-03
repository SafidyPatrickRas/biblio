package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Abonnement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_inscription", nullable = false)
    private Date dateInscription;

    @Column(name = "date_fin_inscription", nullable = false)
    private Date dateFinInscription;

    @ManyToOne
    @JoinColumn(name = "id_adherant", referencedColumnName = "id")
    private Adherant adherant;

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Date getDateFinInscription() {
        return dateFinInscription;
    }

    public void setDateFinInscription(Date dateFinInscription) {
        this.dateFinInscription = dateFinInscription;
    }

    public Adherant getAdherant() {
        return adherant;
    }

    public void setAdherant(Adherant adherant) {
        this.adherant = adherant;
    }
}
