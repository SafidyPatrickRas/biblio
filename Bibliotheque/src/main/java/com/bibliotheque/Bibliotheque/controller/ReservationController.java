package com.bibliotheque.Bibliotheque.controller;

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

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LivreService livreService; // Assurez-vous d'avoir un service pour gérer les livres

    @Autowired
    private StatusService statusService; // Assurez-vous d'avoir un service pour gérer les stat

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