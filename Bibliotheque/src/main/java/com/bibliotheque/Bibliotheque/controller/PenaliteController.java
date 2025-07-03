package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Penalite;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.PenaliteService;

import jakarta.validation.Valid;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/penalites")
public class PenaliteController {
    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private AdherantService adherantService;

    // Affiche le formulaire d'ajout d'une pénalité
    @GetMapping("/ajouter")
    public String showForm(Model model) {
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("penalite", new Penalite());
        model.addAttribute("page", "penalite/formulaire");
        return "template"; // à adapter selon le nom de ton template
    }

    @GetMapping("/liste")
    public String showListe(Model model) {
        model.addAttribute("penalites", penaliteService.findAll());
        model.addAttribute("page", "penalite/liste");
        return "template";
    }

    @PostMapping
public String ajouterPenalite(@ModelAttribute Penalite penalite,
                              @RequestParam("adherant") Integer adherantId) {

    Adherant adherant = adherantService.findById(adherantId)
            .orElseThrow(() -> new IllegalArgumentException("Adhérant introuvable"));

    penalite.setAdherant(adherant);

    Integer nombreJours = penalite.getNombreJours();

    if (nombreJours == null) {
        // Gérer le cas d'erreur (optionnel)
        return "redirect:/penalites/liste?erreur=nbJours";
    }

    // 🟡 Récupérer la dernière pénalité (par dateFin)
    Penalite dernierePenalite = penaliteService.trouverDernierePenalite(adherant);

    LocalDate nouvelleDateDebut;

    if (dernierePenalite != null && dernierePenalite.getDateFin() != null) {
        nouvelleDateDebut = dernierePenalite.getDateFin(); // enchaîne après l’ancienne
    } else {
        nouvelleDateDebut = LocalDate.now(); // sinon on commence aujourd’hui
    }

    LocalDate nouvelleDateFin = nouvelleDateDebut.plusDays(nombreJours);

    penalite.setDateDebut(nouvelleDateDebut);
    penalite.setDateFin(nouvelleDateFin);

    penaliteService.save(penalite);

    return "redirect:/penalites/liste";
}
}