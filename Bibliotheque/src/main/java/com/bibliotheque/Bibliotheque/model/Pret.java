package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Pret {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_adherent")
    private Adherant adherant;

    @ManyToOne
    @JoinColumn(name = "id_livre")
    private Livre livre;

    @ManyToOne
    @JoinColumn(name = "id_type_pret")
    private TypePret typePret;

    @Column(nullable = false)
    private Date dateDebut;

    @Column(nullable = false)
    private Date dateFin;

    @Column(nullable = false)
    private Boolean isRetournee;
    private Date dateRendu;

    // Constructeurs
    public Pret() {
    }

    public Pret(Adherant adherant, Livre livre, TypePret typePret, Date dateDebut, Date dateFin) {
        this.adherant = adherant;
        this.livre = livre;
        this.typePret = typePret;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

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

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public TypePret getTypePret() {
        return typePret;
    }

    public void setTypePret(TypePret typePret) {
        this.typePret = typePret;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Boolean getIsRetournee() {
        return isRetournee;
    }

    public void setIsRetournee(Boolean retournee) {
        isRetournee = retournee;
    }

    public Date getDateRendu() {
        return dateRendu;
    }

    public void setDateRendu(Date dateRendu) {
        this.dateRendu = dateRendu;
    }
}
