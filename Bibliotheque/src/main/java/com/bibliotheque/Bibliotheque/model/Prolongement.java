package com.bibliotheque.Bibliotheque.model;

<<<<<<< Updated upstream
import jakarta.persistence.*;
@Entity
@Table(name = "prolongement")
public class Prolongement {
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Prolongement {

>>>>>>> Stashed changes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

<<<<<<< Updated upstream
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
=======
    @OneToOne
    @JoinColumn(name = "id_pret", unique = true)
    private Pret pret;

    @NotNull(message = "Le nombre de jours de prolongation est obligatoire")
    @Min(value = 1, message = "Le nombre d'exemplaires doit être supérieur à 0")
    private Integer dureeProlongation; // Durée de prolongation en jours

    

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    // Getters et setters
    public Integer getId() {
        return id;
    }

>>>>>>> Stashed changes
    public void setId(Integer id) {
        this.id = id;
    }

    public Pret getPret() {
        return pret;
    }

    public void setPret(Pret pret) {
        this.pret = pret;
    }

<<<<<<< Updated upstream
    public void setNombreJoursProlonger(Integer nombreJoursProlonger) {
        this.nombreJoursProlonger = nombreJoursProlonger;
=======
    public Status getStatus() {
        return status;
>>>>>>> Stashed changes
    }

    public void setStatus(Status status) {
        this.status = status;
    }
<<<<<<< Updated upstream
}
=======

    public Integer getDureeProlongation() {
        return dureeProlongation;
    }

    public void setDureeProlongation(Integer dureeProlongation) {
        this.dureeProlongation = dureeProlongation;
    }
}
>>>>>>> Stashed changes
