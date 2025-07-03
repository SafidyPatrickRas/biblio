package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.ProfilService;

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

import java.util.List;

@Controller
@RequestMapping("/adherants")
public class AdherantController {

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private ProfilService profilService;

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
            return "redirect:/adherants/success";
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
            Long profilId = adherant.getProfil().getId();
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
    public String showProfil(Model model, HttpSession session) {
        Adherant adherant = (Adherant) session.getAttribute("adherant");
        if (adherant == null) {
            return "redirect:/adherants/login";
        }
        model.addAttribute("adherant", adherant);
        model.addAttribute("page", "adherant/profil");
        return "template";
    }

    
}