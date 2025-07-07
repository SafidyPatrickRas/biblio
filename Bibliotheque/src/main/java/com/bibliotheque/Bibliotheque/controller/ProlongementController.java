package com.bibliotheque.Bibliotheque.controller;

<<<<<<< Updated upstream
import com.bibliotheque.Bibliotheque.model.Prolongement;
import com.bibliotheque.Bibliotheque.service.ProlongementService;
=======
import java.util.Calendar;
import java.util.Date;
import java.util.List;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< Updated upstream
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/prolongements")
public class ProlongementController {

    @Autowired
    private ProlongementService prolongementService;

    @GetMapping("/")
    public String listerProlongements(Model model) {
        List<Prolongement> prolongements = prolongementService.getAllProlongements();
        model.addAttribute("prolongements", prolongements);
        model.addAttribute("page", "prolongement/liste");
        return "template";
    }

    
=======
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.Prolongement;
import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.service.PretService;
import com.bibliotheque.Bibliotheque.service.ProlongementService;
import com.bibliotheque.Bibliotheque.service.StatusService;


@Controller
@RequestMapping("/prolongement")
public class ProlongementController {
    @Autowired
    private ProlongementService prolongementService;
    
    @Autowired
    private PretService pretService;

    @Autowired
    StatusService statusService;


    @PostMapping("/refuse")
    public String refuseProlongement(
            @RequestParam("prolongementId") Integer prolongementId,
            RedirectAttributes redirectAttributes) {
        // Vérification des paramètres
        if (prolongementId == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: ID de prolongement invalide");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prolongement/list"; // Redirige vers la liste des prolongements
        }

        // Verifier si le prolongement existe
        if (prolongementService.getProlongementById(prolongementId) == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Prolongement non trouvé");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prolongement/list"; // Redirige vers la liste des prolongements
        }

        Prolongement prolongement = prolongementService.getProlongementById(prolongementId);


        // Vérification du statut
        Status status = statusService.findByNom("Refusé"); // 2 pour "Accepté"
        if (status == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Statut non trouvé");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prolongement/list"; // Redirige vers la liste des prolongements
        }

        prolongement.setStatus(status);
        prolongementService.updateProlongement(prolongement);
        redirectAttributes.addFlashAttribute("message", "Prolongement refuser");
        redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
        return "redirect:/prolongement/list";

    }

    @PostMapping("/accept")
    public String acceptProlongement(
            @RequestParam("prolongementId") Integer prolongementId,
            RedirectAttributes redirectAttributes) {
        // Vérification des paramètres
        if (prolongementId == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: ID de prolongement invalide");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prolongement/list"; // Redirige vers la liste des prolongements
        }

        // Verifier si le prolongement existe
        if (prolongementService.getProlongementById(prolongementId) == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Prolongement non trouvé");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prolongement/list"; // Redirige vers la liste des prolongements
        }



        // Vérification du statut
        Status status = statusService.findByNom("Accepté"); // 2 pour "Accepté"
        if (status == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Statut non trouvé");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prolongement/list"; // Redirige vers la liste des prolongements
        }

        try {
            prolongementService.acceptProlongement(prolongementId);
        } catch (Exception e) {
            System.out.println("//////////////////////////////////////");
            e.printStackTrace();
            System.out.println("//////////////////////////////////////");
            
            redirectAttributes.addFlashAttribute("message", "Erreur: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prolongement/list";
        }

      

        redirectAttributes.addFlashAttribute("message", "Prolongement refuser");
        redirectAttributes.addFlashAttribute("alertClass", "alert-warning");
        return "redirect:/prolongement/list";

    }


    // CREATE
    @PostMapping("/create")
    public String createProlongement(
            @RequestParam("pretId") Integer pretId,
            @RequestParam("nombreJours") Integer nombreJours,
            RedirectAttributes redirectAttributes) {
        
        // Vérification des paramètres
        if (pretId == null || nombreJours == null || nombreJours <= 0) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Paramètres invalides");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/prets"; // Redirige vers la page des prêts
        }

        // Verifier si le prêt existe
        if (pretService.getPretById(pretId) == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Prêt non trouvé");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/prets"; // Redirige vers la page des prêts
        }
        Pret pret = pretService.getPretById(pretId);
        // Verification du nombre de jours
        if (nombreJours <= 0) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Nombre de jours invalide");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/prets"; // Redirige vers la page des prêts
        }

        // Recherchde du status
        Status status = statusService.findByNom("Envoyé"); // 1 pour "En attente" 
        
        if( status == null) {
            redirectAttributes.addFlashAttribute("message", "Erreur: Statut non trouvé");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/prets"; // Redirige vers la page des prêts
        }
        
        try {

            

             if (prolongementService.findByPret(pret) != null) {
                redirectAttributes.addFlashAttribute("message", "Erreur: Ce prêt a déjà un prolongement");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/adherants/prets";
            }   

            Prolongement prolongement = new Prolongement();
            prolongement.setPret(pret);
            prolongement.setDureeProlongation(nombreJours);
            prolongement.setStatus(status);
            prolongementService.createProlongement(prolongement);

            pret.setEstProlonge(true);
            pretService.updatePret(pret);

            redirectAttributes.addFlashAttribute("message", "Demande de prolongement envoyée avec succès");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        
        return "redirect:/adherants/prets"; // Redirige vers la page des prêts
    }
    
    // Liste des prolongements
    @GetMapping("/list")
    public String list(Model model) {
        List<Prolongement> prolongements = prolongementService.getAllProlongement() ;
        model.addAttribute("prolongements", prolongements);
        model.addAttribute("page", "prolongement/list");
        return "template";
    }
>>>>>>> Stashed changes
}
