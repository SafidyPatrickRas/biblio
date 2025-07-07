package com.bibliotheque.Bibliotheque.controller;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.ExemplaireLivre;
import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Penalite;
import com.bibliotheque.Bibliotheque.model.Pret;
import com.bibliotheque.Bibliotheque.model.ReglePret;
import com.bibliotheque.Bibliotheque.service.AbonnementService;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.ExemplaireLivreService;
import com.bibliotheque.Bibliotheque.service.LivreService;
import com.bibliotheque.Bibliotheque.service.PenaliteService;
import com.bibliotheque.Bibliotheque.service.PretService;
import com.bibliotheque.Bibliotheque.service.ReglePretService;
import com.bibliotheque.Bibliotheque.service.TypePretService;

<<<<<<< Updated upstream
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

=======
>>>>>>> Stashed changes
@Controller
@RequestMapping("/prets")
public class PretController {

    
    @Autowired
    private PretService pretService;

    @Autowired
    private ReglePretService reglePretService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private TypePretService typePretService;

    @Autowired 
    private LivreService livreService;

    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    ExemplaireLivreService exemplaireLivreService;

    @Autowired
    PenaliteService penaliteService;

<<<<<<< Updated upstream
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
=======
     @PostMapping("/retour")
    public String retournerPret(
            @RequestParam("pretId") Integer pretId,
            @RequestParam("dateRetour") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateRetour,
            RedirectAttributes redirectAttributes) {

            // Vérification de l'existence du prêt
            Pret pret = pretService.getPretById(pretId);
            if (pret == null) {
                redirectAttributes.addFlashAttribute("message", "Prêt non trouvé.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger"); 
            }
            // Vérification de la date de retour
            if (dateRetour == null) {
                redirectAttributes.addFlashAttribute("message", "La date de retour est obligatoire.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }

            if (dateRetour.before(pret.getDateDebut()) || dateRetour.after(pret.getDateFin())) {
                redirectAttributes.addFlashAttribute("message", "La date de retour doit être comprise entre la date de début et la date de fin du prêt.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            }

            // Mise à jour du prêt
            pret.setDateRetour(dateRetour);
            pretService.updatePret(pret);
            redirectAttributes.addFlashAttribute("message", "Prêt retourné avec succès.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");


                return "redirect:/prets/list"; // Redirection vers la liste des prêts
            }

    
    // Create Pret
    @PostMapping("/create")
    public String createPret(
        @RequestParam("adherant") String email,
        @RequestParam("typePret") Integer typePret,
        @RequestParam("livre") String livre,
        @RequestParam("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
        @RequestParam("dateFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin,
         RedirectAttributes redirectAttrs
        ) {

        // Existance d adherant
        if (adherantService.findByEmail(email) == null) {
            redirectAttrs.addFlashAttribute("message", "Adhérent non trouvé.");
>>>>>>> Stashed changes
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }
<<<<<<< Updated upstream
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
=======

        Adherant adherant = adherantService.findByEmail(email);

        // Vérification de l'abonnement de l'adhérent
        if (!abonnementService.estAbonneeAdherant(dateDebut , adherant.getId())) {
            redirectAttrs.addFlashAttribute("message", "L'adhérent n'est pas abonné.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        // Varification des dates
        if (dateDebut == null || dateFin == null) {
            redirectAttrs.addFlashAttribute("message", "Les dates de début et de fin sont obligatoires.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        // Verifcation si l adherant est en penalite 
         List<Penalite> penalitesExistantes = penaliteService.findPenalitesActivesByAdherant(dateDebut , adherant.getId());

        if (!penalitesExistantes.isEmpty()) {
             redirectAttrs.addFlashAttribute("message", "L adherant es encore en penalite.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        if (dateFin.before(dateDebut)) {
            redirectAttrs.addFlashAttribute("message", "La date de fin doit être postérieure à la date de début.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        // Existance de livre
        if (livreService.findByTitre(livre) == null) {
            redirectAttrs.addFlashAttribute("message", "Livre non trouvé.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        Livre livreEntity = livreService.findByTitre(livre);



        // Existance de type de pret
        if (typePretService.getTypePretById(typePret) == null) {
            redirectAttrs.addFlashAttribute("message", "Type de prêt non trouvé.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
        }

        // Vérification des règles de prêt

        // Nombre de pret actif pour l'adhérent
        int nombrePretsActifs = pretService.findPretsNonRendusParAdherant(
            adherantService.findByEmail(email).getId()
        ).size();

        ReglePret reglePret  = reglePretService.getReglePretById(typePret);

        if (nombrePretsActifs >= reglePret.getNombreLivres()) {
            redirectAttrs.addFlashAttribute("message", "Nombre maximum de prêts actifs atteint pour cet adhérent.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        //  Nombre d exemplaire du livre
        ExemplaireLivre exemplaireLivre =  exemplaireLivreService.findByLivre(livreEntity);

        if (exemplaireLivre == null || exemplaireLivre.getNombreExemplaires() <= 0) {
            redirectAttrs.addFlashAttribute("message", "Aucun exemplaire disponible pour ce livre.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        int nombreExemplaireLivre = exemplaireLivre.getNombreExemplaires();
        int nombreExemplaireLivreDisponible = nombreExemplaireLivre - pretService.findPretsNonRendusParLivre(livreEntity.getId()).size();

        if (nombreExemplaireLivreDisponible <= 0) {
            redirectAttrs.addFlashAttribute("message", "Aucun exemplaire disponible pour ce livre.");
            redirectAttrs.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
        }

        // Création du prêt
        Pret pret = new Pret(
            adherantService.findByEmail(email),
            livreService.findByTitre(livre),
            typePretService.getTypePretById(typePret),
            dateDebut,
            dateFin
        );

        pretService.createPret(pret);
        redirectAttrs.addFlashAttribute("message", "Prêt créé avec succès.");
        redirectAttrs.addFlashAttribute("alertClass", "alert-success");

            return "redirect:/prets/create"; // Redirection vers le formulaire de creation
    }


    // Create
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("page", "pret/create");
        model.addAttribute("adherants", adherantService.getAllAdherants());
        model.addAttribute("typePrets", typePretService.getAllTypePrets());
        model.addAttribute("livres", livreService.getAllLivres());

        return "template";
    }

    // List 
    @GetMapping("/list")
    public String listPrets(Model model) {
        List<Pret> prets = pretService.getAllPrets();
        model.addAttribute("prets", prets);
        model.addAttribute("page", "pret/list");
        return "template";
    }
}
>>>>>>> Stashed changes
