package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Abonnement;
import com.bibliotheque.Bibliotheque.service.AbonnementService;
import com.bibliotheque.Bibliotheque.service.AdherantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Optional;
import com.bibliotheque.Bibliotheque.model.Adherant;

@Controller
@RequestMapping("/abonnement")
public class AbonnementController {

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private AdherantService adherantService;

    @GetMapping("/ajouter")
    public String afficherFormulaire(Model model) {
        model.addAttribute("abonnement", new Abonnement());
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("page", "abonnement/ajouter");
        return "template";
    }

    @PostMapping("/ajouter")
    public String traiterFormulaire(
            @RequestParam("emailAdherant") String emailAdherant,
            @RequestParam("dateInscription") String dateInscription,
            @RequestParam("dateFinInscription") String dateFinInscription,
            RedirectAttributes redirectAttrs,
            Model model) {

        try {
            // Convertir les dates
            java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
            Date dateInsc = formatter.parse(dateInscription);
            Date dateFin = formatter.parse(dateFinInscription);

            // Créer l'abonnement
            Optional<Adherant> adherantOpt = adherantService.findByEmail(emailAdherant);
            if (adherantOpt.isPresent()) {
                Abonnement abonnement = abonnementService.creerAbonnement(
                        adherantOpt.get().getId(),
                        dateInsc,
                        dateFin
                );
                if (abonnement != null) {
                    redirectAttrs.addFlashAttribute("message", "L'abonnement a été créé avec succès !");
                    redirectAttrs.addFlashAttribute("alertClass", "alert-success");
                } else {
                    // Vérifier si c'est un chevauchement de dates
                    if (abonnementService.verifieChevauchementDates(adherantOpt.get().getId(), dateInsc, dateFin)) {
                        redirectAttrs.addFlashAttribute("message", "L'adhérent est déjà abonné pour ces dates. Veuillez choisir d'autres dates.");
                        redirectAttrs.addFlashAttribute("alertClass", "alert-warning");
                    } else {
                        redirectAttrs.addFlashAttribute("message", "Erreur lors de la création de l'abonnement.");
                        redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
                    }
                }
            } else {
                redirectAttrs.addFlashAttribute("message", "Aucun adhérent trouvé avec cet email.");
                redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            }

        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("message", "Erreur lors de la création de l'abonnement : " + e.getMessage());
            model.addAttribute("alertClass", "alert-danger");
        }
        
        return "redirect:/abonnement/ajouter";
    }
}
