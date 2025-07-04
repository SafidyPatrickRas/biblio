package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Penalite;
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.Abonnement;
import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.model.Reservation;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.PenaliteService;
import com.bibliotheque.Bibliotheque.service.PretService;
import com.bibliotheque.Bibliotheque.service.AbonnementService;
import com.bibliotheque.Bibliotheque.service.ProfilService;
import com.bibliotheque.Bibliotheque.service.ReservationService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/adherants")
public class AdherantController {

    @Autowired
    private AdherantService adherantService;
    
    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    private ProfilService profilService;

    @Autowired
    private PretService pretService;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private ReservationService reservationService;

    // Afficher le formulaire d'insertion d'un nouvel adhérant
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("page", "adherant/login");
        return "template";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("email") String email,
                              @ModelAttribute("motDePasse") String motDePasse,
                              RedirectAttributes redirectAttrs,
                              HttpSession session) {
        Adherant adherant = adherantService.authenticate(email, motDePasse);
        
        if (adherant != null) {
            session.setAttribute("adherant", adherant); // Sauvegarde en session
            redirectAttrs.addFlashAttribute("message", "Connexion réussie !");
            redirectAttrs.addFlashAttribute("alertClass", "alert-success");
            return "redirect:/";
        } else {
            redirectAttrs.addFlashAttribute("message", "Email ou mot de passe incorrect");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/login";
        }
    }

    @GetMapping("/inscription")
    public String showForm(Model model) {
        model.addAttribute("adherant", new Adherant());
        List<Profil> profils = profilService.getAll();
        model.addAttribute("profils", profils);
        model.addAttribute("page", "adherant/inscription");
        return "template";
    }

    // Traiter le formulaire d'inscription
    @PostMapping("/inscription")
    public String processInscription(@Valid @ModelAttribute Adherant adherant, 
                                    BindingResult bindingResult, 
                                    Model model,
                                    RedirectAttributes redirectAttrs) {
        
        // Vérifier si l'email existe déjà
        if (adherantService.findByEmail(adherant.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.email", "Cet email est déjà utilisé");
        }

        // Vérifier si le mot de passe est conforme
        if (!isValidPassword(adherant.getMotDePasse())) {
            bindingResult.rejectValue("motDePasse", "error.password", "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule et un chiffre");
        }

        // Si des erreurs existent, retourner au formulaire
        if (bindingResult.hasErrors()) {
            model.addAttribute("profils", profilService.getAll());
            model.addAttribute("page", "adherant/inscription");
            return "template";
        }

        try {
            // Récupérer le profil sélectionné
            Integer profilId = adherant.getProfil().getId();
            Profil profil = profilService.getById(profilId);
            if (profil == null) {
                throw new RuntimeException("Profil non trouvé");
            }
            
            // Mettre à jour le profil de l'adhérent
            adherant.setProfil(profil);
            
            // Sauvegarder l'adhérent
            adherantService.save(adherant);
            redirectAttrs.addFlashAttribute("message", "Votre inscription a été effectuée avec succès !");
            redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("message", "Une erreur est survenue lors de l'inscription. Veuillez réessayer.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/inscription";
        }
        
        // Rediriger vers une page de confirmation
        return "redirect:/";
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*\\d.*");
    }

    @GetMapping("/success")
    public String showSuccess(Model model) {
        model.addAttribute("page", "adherant/success");
        return "template";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttrs) {
        session.invalidate(); // Supprime la session
        redirectAttrs.addFlashAttribute("message", "Vous avez été déconnecté avec succès !");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/"; // Redirige vers la page d'accueil
    }

    @GetMapping("/profil")
    public String afficherProfil(Model model, HttpSession session) {
        Adherant adherant = (Adherant) session.getAttribute("adherant");

        if(adherant == null) {
            return "redirect:/adherants/login";
        }

        List<Reservation> reservations = reservationService.findByAdherantId(adherant.getId());
        model.addAttribute("reservations", reservations);

        //  liste des livres en pret actuel

        List<Pret> prets = pretService.getLivresNonRetournesParAdherent(adherant.getId());

        model.addAttribute("prets", prets);
        model.addAttribute("adherant", adherant);

        // En penalite ou pas 
        boolean enPenalite = penaliteService.estEnPenalite(adherant);


        if(enPenalite){
            // Récupérer la dernière pénalité active
            Penalite dernierePenalite = penaliteService.trouverDernierePenalite(adherant);
            model.addAttribute("dernierePenalite", dernierePenalite);
        } else {
            model.addAttribute("dernierePenalite", null);
        }

        model.addAttribute("enPenalite", enPenalite);
        
        // Vérifier si l'adhérent est abonné
        boolean estAbonne = adherantService.estAbonneEnCeMoment(adherant.getId());
        model.addAttribute("estAbonne", estAbonne);
        
        // Si abonné, trouver la date de fin la plus proche
        if (estAbonne) {
            Date dateCourante = new Date();
            List<Abonnement> abonnements = abonnementService.findAllByAdherantId(adherant.getId());
            Date dateFinPlusProche = null;
            
            for (Abonnement abonnement : abonnements) {
                Date dateDebut = abonnement.getDateInscription();
                Date dateFin = abonnement.getDateFinInscription();
                
                // Vérifier si l'abonnement est actif et si c'est la date de fin la plus proche
                if (dateCourante.after(dateDebut) && dateCourante.before(dateFin)) {
                    if (dateFinPlusProche == null || dateFin.before(dateFinPlusProche)) {
                        dateFinPlusProche = dateFin;
                    }
                }
            }
            
            if (dateFinPlusProche != null) {
                model.addAttribute("dateFinAbonnement", dateFinPlusProche);
            }
        }

        model.addAttribute("page", "adherant/profil");
        
        return "template";
    }
}