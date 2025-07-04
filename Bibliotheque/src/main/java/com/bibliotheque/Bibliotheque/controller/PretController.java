package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Adherant;

import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.ReglePret;
import com.bibliotheque.Bibliotheque.model.TypePret;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.ExemplaireLivreService;
import com.bibliotheque.Bibliotheque.service.LivreService;
import com.bibliotheque.Bibliotheque.service.PenaliteService;
import com.bibliotheque.Bibliotheque.service.PretService;
import com.bibliotheque.Bibliotheque.service.ReglePretService;
import com.bibliotheque.Bibliotheque.service.TypePretService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/pret")
public class PretController {

    @Autowired
    private PretService pretService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private ExemplaireLivreService exemplaireLivreService;

    @Autowired
    private TypePretService typePretService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private ReglePretService reglePretService;

    @Autowired
    PenaliteService penaliteService;

    @PostMapping("/ajouter")
    public String enregistrerPret(
            @RequestParam String adherantEmail,
            @RequestParam String livreTitre,
            @RequestParam Integer typePretId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
            RedirectAttributes redirectAttrs) {

        // 1. Récupérer l'adhérent par email
        Adherant adherant = adherantService.findByEmail(adherantEmail)
                .orElseThrow(() -> new IllegalArgumentException("Adhérent non trouvé"));

        if (!adherantService.estAbonneEnCeMoment(adherant.getId())) {
            redirectAttrs.addFlashAttribute("message", "L'adhérent n'est pas abonné en ce moment");
            redirectAttrs.addFlashAttribute("alertClass", "alert-warning");

            return "redirect:/pret/ajouter";
        }

        // si l adherant es en penalite
        if (penaliteService.estEnPenalite(adherant)) {
            redirectAttrs.addFlashAttribute("message", "L'adhérent est en pénalité et ne peut pas emprunter de livres");
            redirectAttrs.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/pret/ajouter";
        }

        // 2. Récupérer le livre par titre complet (vous devrez adapter cette méthode)
        // Séparation des parties
        String[] parts = livreTitre.split(" - ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Format du titre du livre invalide");
        }

        String titre = parts[0].trim();
        String[] auteurParts = parts[1].trim().split(" ", 2); // Split sur première espace seulement
        String nomAuteur = auteurParts[0];
        String prenomAuteur = auteurParts.length > 1 ? auteurParts[1] : "";

        Livre livre = livreService.findByTitreAndAuteur(titre, nomAuteur, prenomAuteur)
                .orElseThrow(() -> new IllegalArgumentException("Livre non trouvé"));

        // 3. Récupérer le type de prêt
        TypePret typePret = typePretService.getTypePretById(typePretId);

        // 4. Verifier le nombre de livre deja empruntes par l'adhérent
        Integer nombreLivresEmpruntes = pretService.getNombrePretsNonRetournesParAdherent(adherant.getId());

        System.out.println("Nombre de livres empruntes: " + nombreLivresEmpruntes);

        ReglePret reglePret = reglePretService.getReglePretByProfilAndTypePret(adherant.getProfil().getId(),
                typePret.getId());
        if (nombreLivresEmpruntes >= reglePret.getNombreLivres()) {
            redirectAttrs.addFlashAttribute("message", "L'adhérent a déjà emprunté le maximum de livres autorisé");
            redirectAttrs.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/pret/ajouter";
        }

        // 5. Verifier le nombre d exmplaire disponible
        Integer nombreExemplaireLivreDemander = exemplaireLivreService.nombreExemplaireLivre(livre.getId());
        Integer nombreExemplaireLivreEmpruntes = pretService.getPretsActifsPourLivre(livre.getId()).size();
        if (nombreExemplaireLivreDemander <= nombreExemplaireLivreEmpruntes) {
            redirectAttrs.addFlashAttribute("message", "Le livre n'est plus disponible");
            redirectAttrs.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/pret/ajouter";
        }

        // 6. Verifier le nombre de livre deja empruntes par l'adhérent
        Integer nombreLivresEmpruntesParAdherent = pretService.getNombrePretsNonRetournesParAdherent(adherant.getId());
        if (nombreLivresEmpruntesParAdherent >= reglePret.getNombreLivres()) {
            redirectAttrs.addFlashAttribute("message", "L'adhérent a déjà emprunté le maximum de livres autorisé");
            redirectAttrs.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/pret/ajouter";
        }

        // 7. Success de pret

        Pret pret = new Pret(adherant, livre, typePret, Date.valueOf(dateDebut), Date.valueOf(dateFin));
        pret.setIsRetournee(false);
        pret.setDateRendu(null);
        pretService.createPret(pret);
        redirectAttrs.addFlashAttribute("message", "Le pret a été enregistré avec succès");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/pret/ajouter";
    }

    @GetMapping("/ajouter")
    public String afficherFormulairePret(Model model) {
        model.addAttribute("adherants", adherantService.findAll());
        model.addAttribute("livres", livreService.getAll());
        model.addAttribute("typesPret", typePretService.getAllTypePrets());
        model.addAttribute("page", "pret/formulaire");
        return "template";
    }

    @GetMapping("/liste")
    public String afficherListePrets(Model model) {
        model.addAttribute("prets", pretService.getAllPretsOrderByDateDesc());
        model.addAttribute("page", "pret/liste");
        return "template";
    }

    @GetMapping("/recherche")
    public String afficherRecherchePrets(Model model) {
        return "pret/recherche";
    }

    @PostMapping("/recherche")
    public String rechercherPrets(@RequestParam("dateDebut") String dateDebut,
            @RequestParam("dateFin") String dateFin,
            Model model) {
        try {
            LocalDate debut = LocalDate.parse(dateDebut);
            LocalDate fin = LocalDate.parse(dateFin);

            List<Pret> prets = pretService.getPretsByDateRange(Date.valueOf(debut), Date.valueOf(fin));
            model.addAttribute("prets", prets);
            model.addAttribute("dateDebut", dateDebut);
            model.addAttribute("dateFin", dateFin);
        } catch (Exception e) {
            model.addAttribute("erreur", "Format de date invalide. Utilisez YYYY-MM-DD");
        }
        return "pret/liste";
    }

    @PostMapping("/rendre")
    public String rendrePret(@RequestParam Integer id,
            @RequestParam(required = false) String dateRendu,
            RedirectAttributes redirectAttrs) {
        try {
            java.sql.Date sqlDate = dateRendu == null || dateRendu.isEmpty()
                    ? new java.sql.Date(System.currentTimeMillis())
                    : java.sql.Date.valueOf(dateRendu); // Utilisez LocalDate si possible

            String message = pretService.rendrePret(id, sqlDate);

            redirectAttrs.addFlashAttribute("message", message);
            return "redirect:/pret/liste";
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/pret/liste";
        }
    }

    @GetMapping("/rendre/{id}")
    public String rendreLivre(@PathVariable("id") Integer id, RedirectAttributes redirectAttrs) {
        Pret pret = pretService.getPretById(id);
        if (pret == null) {
            redirectAttrs.addFlashAttribute("message", "Prêt non trouvé");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/pret/liste";
        }
        if (pret.getIsRetournee()) {
            redirectAttrs.addFlashAttribute("message", "Le livre a déjà été rendu");
            redirectAttrs.addFlashAttribute("alertClass", "alert-warning");
            return "redirect:/pret/liste";
        }
        pret.setIsRetournee(true);
        pret.setDateRendu(Date.valueOf(LocalDate.now()));
        pretService.updatePret(pret);
        redirectAttrs.addFlashAttribute("message", "Le livre a été rendu avec succès");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/pret/liste";
    }
}