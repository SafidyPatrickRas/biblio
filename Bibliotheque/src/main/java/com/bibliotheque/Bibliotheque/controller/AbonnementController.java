package com.bibliotheque.Bibliotheque.controller;

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

import com.bibliotheque.Bibliotheque.model.Abonnement;
import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.service.AbonnementService;
import com.bibliotheque.Bibliotheque.service.AdherantService;

@Controller
@RequestMapping("/abonnement")
public class AbonnementController {
    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private AdherantService adherantService;

    @PostMapping("/create")
    public String createAbonnement(
        @RequestParam("adherant") String email,
        @RequestParam("dateInscription") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateInscription,
        @RequestParam("dateFinInscription") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFinInscription,
        RedirectAttributes redirectAttrs
    ) {
        // Validation des données d'abonnement
        Adherant adherant = adherantService.findByEmail(email);
        if (adherant == null) {
            redirectAttrs.addFlashAttribute("message", "Adhérent non trouvé.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/abonnement/create";
        }

        if( dateInscription == null || dateFinInscription == null) {
            redirectAttrs.addFlashAttribute("message", "Dates d'inscription invalides.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/abonnement/create";
        }

        if(dateFinInscription == null || dateInscription == null) {
            redirectAttrs.addFlashAttribute("message", "Les dates d'inscription et de fin d'inscription sont obligatoires.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/abonnement/create";
        }

        if( dateFinInscription.before(dateInscription)) {
            redirectAttrs.addFlashAttribute("message", "La date de fin d'inscription doit être postérieure à la date d'inscription.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/abonnement/create";
        }

        // Vérification des chevauchements d'abonnement
        List<Abonnement> chevauchants = abonnementService.findAbonnementsChevauchantsByAdherant(
            adherant.getId(),
            dateInscription,
            dateFinInscription
        );
        if (!chevauchants.isEmpty()) {
            redirectAttrs.addFlashAttribute("message", "Un abonnement chevauche déjà cette période pour cet adhérent.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/abonnement/create";
        }

        
        Abonnement abonnement = new Abonnement();
        abonnement.setAdherant(adherant);
        abonnement.setDateInscription(dateInscription);
        abonnement.setDateFinInscription(dateFinInscription);

        abonnementService.createAbonnement(abonnement);

        redirectAttrs.addFlashAttribute("message", "Abonnement créé avec succès.");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/abonnement/create";
    }

    // Création
    @GetMapping("/create")
    public String createAbonnementForm(Model model) {
        model.addAttribute("abonnement", new Abonnement());
        model.addAttribute("adherants", adherantService.getAllAdherants());
        model.addAttribute("page", "abonnement/create");
        return "template";
    }
    // Liste
    @GetMapping("/list") 
    public String listAbonnements(Model model) {
        List<Abonnement> abonnements = abonnementService.getAllAbonnements();
        model.addAttribute("abonnements", abonnements);
        model.addAttribute("page", "abonnement/list");
        return "template";
    }
}
