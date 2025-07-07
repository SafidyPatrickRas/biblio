package com.bibliotheque.Bibliotheque.controller;

<<<<<<< Updated upstream
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
import com.bibliotheque.Bibliotheque.service.ProlongementService;
import com.bibliotheque.Bibliotheque.model.Prolongement;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
=======
import java.util.List;
>>>>>>> Stashed changes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.Prolongement;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.PretService;
import com.bibliotheque.Bibliotheque.service.ProfilService;
import com.bibliotheque.Bibliotheque.service.ProlongementService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/adherants")
public class AdherantController {
    @Autowired
    private AdherantService adherantService;

    @Autowired
    private ProfilService profilService;

    @Autowired
    private PretService pretService;

    @Autowired
    ProlongementService prolongementService;

<<<<<<< Updated upstream
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private ProlongementService prolongementService;
=======
    @GetMapping("/profil")
    public String profil(Model model, HttpSession session, RedirectAttributes redirectAttrs) {
        Adherant adherant = (Adherant) session.getAttribute("adherant");

        if(adherant == null) {
            redirectAttrs.addFlashAttribute("message", "Vous devez vous connecter.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/login";
        }

        // Statistiques de l'adhérent
        // findLivresPretesParAdherant
        List<Livre> livresPretes = pretService.findLivresPretesParAdherant(adherant.getId());
        model.addAttribute("nombrelivresPretes", livresPretes.size());

        model.addAttribute("adherant", adherant);
        model.addAttribute("page", "adherant/profil");
        return "template";
    }
    
    @GetMapping("/prets")
    public String prets(Model model, HttpSession session , RedirectAttributes redirectAttrs) {
        Adherant adherant = (Adherant) session.getAttribute("adherant");

        if(adherant != null) {
            redirectAttrs.addFlashAttribute("message", "Vous devez vous connecter.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
        }

        // findPretsNonRendusParAdherant
        List<Pret> pretsNonRendus = pretService.findPretsNonRendusParAdherant(adherant.getId());
        model.addAttribute("pretsNonRendus", pretsNonRendus);


        model.addAttribute("page", "adherant/prets");
>>>>>>> Stashed changes

        return "template";
    }

    @PostMapping("/login")
    public String traitementLogin(
        @RequestParam("email") String email,
        @RequestParam("motDePasse") String motDePasse, 
        Model model,
        RedirectAttributes redirectAttrs , 
         HttpSession session
    ) {
        // Vérifier si l'email et le mot de passe sont valides
        Adherant existingAdherant = adherantService.findByEmail(email);
        if (existingAdherant == null || !existingAdherant.getMotDePasse().equals(motDePasse)) {
            // Si l'email ou le mot de passe est incorrect, ajouter un message d'erreur
            redirectAttrs.addFlashAttribute("message", "Email ou mot de passe incorrect.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/adherants/login"; // Rediriger vers la page de connexion
        }

        session.setAttribute("adherant", existingAdherant); // Sauvegarde en session
        // Si la connexion est réussie, rediriger vers la page d'accueil ou une autre page
        redirectAttrs.addFlashAttribute("message", "Connexion réussie !");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/"; // Rediriger vers la page d'accueil ou une autre page
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttrs) {
        session.invalidate(); // Supprime la session
        redirectAttrs.addFlashAttribute("message", "Vous avez été déconnecté avec succès !");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/"; // Redirige vers la page d'accueil
    }
<<<<<<< Updated upstream

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

        // Récupérer les prolongements de l'adhérent
        List<Prolongement> prolongements = prolongementService.findByAdherant(adherant);
        model.addAttribute("prolongements", prolongements);

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
        
=======
    // Login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("page", "adherant/login");
>>>>>>> Stashed changes
        return "template";
    }

    // Inscription
    @GetMapping("/inscription")
    public String showInscriptionForm(Model model) {
        model.addAttribute("adherant", new com.bibliotheque.Bibliotheque.model.Adherant());
        model.addAttribute("profils", profilService.getAllProfils());
        model.addAttribute("page", "adherant/inscription");

        return "template";
    }

    @PostMapping("/inscription")
    public String traitementInscription(
        @Valid @ModelAttribute Adherant adherant, 
                                    BindingResult bindingResult, 
                                    Model model,
                                    RedirectAttributes redirectAttrs
    ){
        // Vérifier si l'email existe déjà
        if (adherantService.findByEmail(adherant.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.adherant", "Cet email est déjà utilisé.");
        }


        // Si des erreurs existent, retourner au formulaire
        if (bindingResult.hasErrors()) {
            model.addAttribute("profils", profilService.getAllProfils());
            model.addAttribute("page", "adherant/inscription");
            return "template";
        }

        try {
            // Enregistrer l'adhérent
            adherantService.createAdherant(adherant);
            redirectAttrs.addFlashAttribute("message", "Inscription réussie !");
            redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
             redirectAttrs.addFlashAttribute("message", "Une erreur est survenue lors de l'inscription. Veuillez réessayer.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
        }

        return "redirect:/adherants/inscription";
    }
}
