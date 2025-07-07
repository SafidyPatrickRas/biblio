package com.bibliotheque.Bibliotheque.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Penalite;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.PenaliteService;

@Controller
@RequestMapping("/penalites")
public class PenaliteController {
    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    AdherantService adherantService;

    // Create Penalite
    @PostMapping("/create")
    public String createPenalite(
        @RequestParam("adherant") String email,
        @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
        @RequestParam("nombreJours") Integer nombreJours,
            RedirectAttributes redirectAttributes
    ) {

        // Validation des données de la pénalité
        if (email == null || email.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "L'email de l'adhérent est obligatoire.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/penalites/create";
        }

        if (dateDebut == null) {
            redirectAttributes.addFlashAttribute("message", "La date de début est obligatoire.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/penalites/create";
        }

        if (nombreJours == null || nombreJours <= 0) {
            redirectAttributes.addFlashAttribute("message", "Le nombre de jours doit être supérieur à zéro.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/penalites/create";
        }

        // Verifier si l'adhérent existe
        Adherant adherant = adherantService.findByEmail(email);
        if (adherant == null) {
            redirectAttributes.addFlashAttribute("message", "Adhérent non trouvé.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/penalites/create";
        }

        // Vérifier si une pénalité existe déjà pour cet adhérent
        List<Penalite> penalitesExistantes = penaliteService.findPenalitesActivesByAdherant(dateDebut , adherant.getId());
         if (!penalitesExistantes.isEmpty()) {
        // Prendre directement la dernière pénalité de la liste
        Penalite dernierePenalite = penalitesExistantes.get(penalitesExistantes.size() - 1);
        Date derniereDateFin = dernierePenalite.getDateFin();
        
        if (dateDebut.before(derniereDateFin)) {
            dateDebut = derniereDateFin;
        }
    }

        // Créer la pénalité
        Penalite penalite = new Penalite();
        penalite.setAdherant(adherant);
        penalite.setDateDebut(dateDebut);
        penalite.setNombreJours(nombreJours);

        // Calculer la date de fin
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateDebut);
        cal.add(Calendar.DAY_OF_MONTH, nombreJours);
        Date dateFin = cal.getTime();

        penalite.setDateFin(dateFin);

        // Enregistrer la pénalité
        penaliteService.createPenalite(penalite);
        
        return "redirect:/penalites/list";
    }

    // Create
    @GetMapping("/create")
    public String createPenaliteForm(Model model) {
        model.addAttribute("adherants", adherantService.getAllAdherants());
        model.addAttribute("page", "penalite/create");
        return "template";
    }

<<<<<<< Updated upstream
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
=======

    // List
    @GetMapping("/list")
    public String listPenalites(Model model) {
        List<Penalite> penalites = penaliteService.getAllPenalites();
        model.addAttribute("penalites", penalites);
        model.addAttribute("page", "penalite/list");
        return "template";
    }
}
>>>>>>> Stashed changes
