package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    @NotNull(message = "La date de réservation est obligatoire")
    @Column(name = "date_reservation", nullable = false)
    private Date dateReservation;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
@JoinColumn(name = "adherant_id", nullable = false)
private Adherant adherant;

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Livre getPret() {
        return livre;
    }

    public void setPret(Livre livre) {
        this.livre =livre;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}