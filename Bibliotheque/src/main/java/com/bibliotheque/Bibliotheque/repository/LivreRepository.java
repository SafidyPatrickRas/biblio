package com.bibliotheque.Bibliotheque.repository;

import com.bibliotheque.Bibliotheque.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {
    
    // Trouver un livre par son titre
    Optional<Livre> findByTitre(String titre);
    
    // Trouver tous les livres d'un auteur
    List<Livre> findByAuteurId(Integer idAuteur);
    
    // Trouver tous les livres d'une catégorie
    List<Livre> findByCategoriesId(Integer idCategorie);
    
    // Trouver tous les livres d'un auteur et d'une catégorie
    @Query("SELECT l FROM Livre l WHERE l.auteur.id = :idAuteur AND :idCategorie MEMBER OF l.categories")
    List<Livre> findByAuteurAndCategorie(
            @Param("idAuteur") Integer idAuteur,
            @Param("idCategorie") Integer idCategorie);
    
    // Trouver tous les livres par titre et auteur
    List<Livre> findByTitreAndAuteurId(String titre, Integer idAuteur);
    
    // Vérifier si un livre existe déjà
    boolean existsByTitreAndAuteurId(String titre, Integer idAuteur);

    // Trouver tous les livres par titre et nom/prénom de l'auteur
    @Query("SELECT l FROM Livre l JOIN l.auteur a WHERE l.titre = :titre AND a.nom = :nom AND a.prenom = :prenom")
    List<Livre> findByTitreAndAuteurNomAndAuteurPrenom(
            @Param("titre") String titre,
            @Param("nom") String nom,
            @Param("prenom") String prenom);

    // Trouver tous les livres avec leurs exemplaires
    @Query("SELECT l FROM Livre l JOIN FETCH l.exemplaires")
    List<Livre> findAllWithExemplaires();
}
