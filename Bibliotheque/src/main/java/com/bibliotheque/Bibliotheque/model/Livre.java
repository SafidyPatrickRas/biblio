package com.bibliotheque.Bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Livre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 50, message = "Le titre ne doit pas dépasser 50 caractères")
    private String titre;
    
    @NotNull(message = "La date de publication est obligatoire")
    @Past(message = "La date de publication doit être une date passée")
    private LocalDate datePublication;
    
    @NotNull(message = "Le nombre de pages est obligatoire")
    @Min(value = 1, message = "Le nombre de pages doit être supérieur à 0")
    private Integer nbPages;
    
    @NotBlank(message = "La langue est obligatoire")
    @Size(max = 20, message = "La langue ne doit pas dépasser 20 caractères")
    private String langue;
    
    @NotBlank(message = "Les tags sont obligatoires")
    @Size(max = 50, message = "Les tags ne doivent pas dépasser 50 caractères")
    private String tags;
    
    @Min(value = 0, message = "L'âge de restriction doit être positif")
    private Integer ageRestriction;
    
    @ManyToOne
    @JoinColumn(name = "Id_Auteur", nullable = false)
    private Auteur auteur;

    @ManyToMany
    @JoinTable(
        name = "livre_categorie",
        joinColumns = @JoinColumn(name = "livre_id"),
        inverseJoinColumns = @JoinColumn(name = "categorie_id")
    )
    private Set<CategorieLivre> categories = new HashSet<>();

    public Set<CategorieLivre> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategorieLivre> categories) {
        this.categories = categories;
    }

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public Integer getNbPages() {
        return nbPages;
    }

    public void setNbPages(Integer nbPages) {
        this.nbPages = nbPages;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }
}
