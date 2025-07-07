package com.bibliotheque.Bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.RegleReservation;
import com.bibliotheque.Bibliotheque.model.Reservation;
import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.LivreService;
import com.bibliotheque.Bibliotheque.service.RegleReservationService;
import com.bibliotheque.Bibliotheque.service.ReservationService;
import com.bibliotheque.Bibliotheque.service.StatusService;

import jakarta.servlet.http.HttpSession;
<<<<<<< Updated upstream
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
=======
>>>>>>> Stashed changes

@Controller
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private LivreService livreService;

<<<<<<< Updated upstream
    @Autowired
    private AdherantService adherantService;

    @Autowired
    private RegleReservationService regleReservationService;

    @GetMapping("/liste")
    public String listeReservations(Model model) {
        List<Reservation> reservations = reservationService.findByStatusNom("Envoyé");
        model.addAttribute("reservations", reservations);
        model.addAttribute("page", "reservation/liste");
        return "template";
    }

    @PostMapping("/action")
    public String gererActionReservation(@RequestParam Integer reservationId,
                                        @RequestParam String action,
                                        RedirectAttributes redirectAttributes) {
        Optional<Reservation> optionalReservation = reservationService.findById(reservationId);
        
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            
            if (action.equals("accepter")) {
                Status statusAccepte = statusService.findByNom("Accepté").orElseThrow(() -> 
                    new RuntimeException("Status Accepté non trouvé"));
                reservation.setStatus(statusAccepte);
                reservationService.save(reservation);
                redirectAttributes.addFlashAttribute("successMessage", "La réservation a été acceptée avec succès.");
            } else if (action.equals("refuser")) {
                Status statusRefuse = statusService.findByNom("Refusé").orElseThrow(() -> 
                    new RuntimeException("Status Refusé non trouvé"));
                reservation.setStatus(statusRefuse);
                reservationService.save(reservation);
                redirectAttributes.addFlashAttribute("successMessage", "La réservation a été refusée avec succès.");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Réservation non trouvée.");
        }
        
        return "redirect:/reservations/liste";
    }

    @PostMapping("/ajouter")
public String ajouterReservation(
        @RequestParam("livreId") Integer livreId,
        @RequestParam("dateReservation") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dateReservation,
        HttpSession session,
        RedirectAttributes redirectAttributes) {

    try {
        // 1. Vérification de l'authentification
        Adherant adherant = (Adherant) session.getAttribute("adherant");
        if (adherant == null) {
            redirectAttributes.addFlashAttribute("message", "Vous devez être connecté pour effectuer une réservation.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/livres";
        }

        

        // 2. Vérification de l'abonnement
        if (!adherantService.estAbonneEnCeMoment(adherant.getId())) {
            redirectAttributes.addFlashAttribute("message", "Votre abonnement a expiré. Veuillez renouveler votre abonnement pour effectuer une réservation.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/livres";
        }

        // 3. Vérification du nombre maximum de réservations
        Optional<RegleReservation> regleOpt = regleReservationService.findByProfil(adherant.getProfil());
        if (regleOpt.isPresent()) {
            RegleReservation regle = regleOpt.get();
            Integer maxReservations = regle.getMaxReservations();
            
            // Compter les réservations actives (non-refusées)
            List<Reservation> reservationsActives = reservationService.findByAdherantId(adherant.getId());
            long nbReservationsActives = reservationsActives.stream()
                .filter(r -> !r.getStatus().getNom().equals("Refusé"))
                .count();

            if (nbReservationsActives >= maxReservations) {
                redirectAttributes.addFlashAttribute("message", "Vous avez déjà atteint le nombre maximum de réservations autorisées pour votre profil.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/livres";
            }
        }

        // 3. Vérification du livre
        Optional<Livre> livreOpt = livreService.getById(livreId);
        if (livreOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Livre introuvable.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/livres";
        }

        // 3. Vérification du statut "Envoyé"
        Optional<Status> statusOpt = statusService.findByNom("Envoyé");
        if (statusOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Statut 'Envoyé' introuvable.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/livres";
        }

        // 4. Création de la réservation
        Reservation reservation = new Reservation();
        reservation.setAdherant(adherant);
        reservation.setDateReservation(java.sql.Date.valueOf(dateReservation));
        reservation.setLivre(livreOpt.get());
        reservation.setStatus(statusOpt.get());

        // 5. Sauvegarde
        reservationService.save(reservation);

        redirectAttributes.addFlashAttribute("message", "Votre demande de réservation a été envoyée.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("message", "Une erreur est survenue: " + e.getMessage());
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/livres";
    }

    return "redirect:/livres";
}


    // Ajoute d'autres routes selon tes besoins
    // Removed duplicate method definition to resolve the error.
}
=======

    @Autowired
    private StatusService statusService;
    
     @PostMapping("/accept")
    public String acceptReservation(
            @RequestParam("reservationId") Integer reservationId,
            RedirectAttributes redirectAttributes) {
        
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);

            if(reservation == null) {
                redirectAttributes.addFlashAttribute("message", "Réservation non trouvée.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/reservations";
            }

            Status status = statusService.findByNom("Accepté");
            reservation.setStatus(status);

            statusService.updateStatus(status);

            redirectAttributes.addFlashAttribute("message", "Réservation acceptée avec succès");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de l'acceptation: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        
        return "redirect:/livres/list";
    }

     @PostMapping("/refuse")
    public String rejectReservation(
            @RequestParam("reservationId") Integer reservationId,
            RedirectAttributes redirectAttributes) {
        
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);

            if(reservation == null) {
                redirectAttributes.addFlashAttribute("message", "Réservation non trouvée.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
                return "redirect:/reservations";
            }

            Status status = statusService.findByNom("Refusé");
            reservation.setStatus(status);

            statusService.updateStatus(status);

            redirectAttributes.addFlashAttribute("message", "Réservation acceptée avec succès");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de l'acceptation: " + e.getMessage());
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        }
        
        return "redirect:/livres/list";
    }

    // Page d'accueil des réservations
    @GetMapping("/list")
    public String listReservations(Model model) {
        List<Reservation> reservations = reservationService.findAllReservations();
        model.addAttribute("reservations", reservations);
        model.addAttribute("page", "reservation/list");
        return "template";
    }

    @PostMapping("/create")
    public String createReservation(
            @RequestParam("livreId") Integer livreId,
            @RequestParam("dateReservation") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateReservation,
            RedirectAttributes redirectAttributes,
            HttpSession session) {
        try {
            Adherant adherant = (Adherant) session.getAttribute("adherant");
            
            if(adherant == null) {
                redirectAttributes.addFlashAttribute("message", "Vous devez être connecté pour effectuer une réservation.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger"); 
                return "redirect:/adherants/login";
            }

            
            Reservation reservation = new Reservation();
            Livre livre = livreService.getLivreById(livreId);
            if (livre == null) {
                redirectAttributes.addFlashAttribute("message", "Prêt non trouvé.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger"); 
                return "redirect:/livres/list";
            }
            Status status = statusService.findByNom("Envoyé");

            if(status == null) {
                redirectAttributes.addFlashAttribute("message", "Status non trouvé.");
                redirectAttributes.addFlashAttribute("alertClass", "alert-danger"); 
                return "redirect:/livres/list";
            }

            reservation.setLivre(livre);
            reservation.setDateReservation(dateReservation);
            reservation.setAdherant(adherant); // Assuming you have a method to get the current adherant
            reservation.setStatus(status);

            reservationService.createReservation(reservation);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation créée avec succès!");
            return "redirect:/reservations/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la création de la réservation: " + e.getMessage());
            return "redirect:/reservations/list";
        }
    }

}
>>>>>>> Stashed changes
