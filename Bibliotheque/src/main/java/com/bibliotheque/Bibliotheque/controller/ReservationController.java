package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.model.Reservation;
import com.bibliotheque.Bibliotheque.model.Status;
import com.bibliotheque.Bibliotheque.service.LivreService;
import com.bibliotheque.Bibliotheque.service.ReservationService;
import com.bibliotheque.Bibliotheque.service.StatusService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/liste")
    public String listeReservations(Model model) {
        List<Reservation> reservations = reservationService.findByStatusNom("Envoyé");
        model.addAttribute("reservations", reservations);
        model.addAttribute("page", "reservation/liste"); // à adapter selon ton template
        return "template"; // à adapter selon ton template
    }


@PostMapping("/ajouter")
public String ajouterReservation(
        @RequestParam("livreId") Integer livreId,
        @RequestParam("dateReservation") @DateTimeFormat(pattern = "yyyy-MM-dd") java.sql.Date dateReservation,
        HttpSession session,
        RedirectAttributes redirectAttributes
) {
    Adherant adherant = (Adherant) session.getAttribute("adherant");

    if (adherant == null) {
        redirectAttributes.addFlashAttribute("message", "Vous devez être connecté pour effectuer une réservation.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/livres";
    }

    // Création de la réservation
    Reservation reservation = new Reservation();
    reservation.setAdherant(adherant);
    reservation.setDateReservation(dateReservation);

    // Récupérer le livre (ou Pret) selon ton modèle
    // Ici, exemple avec LivreService (à adapter si besoin)
    Livre livre = livreService.getById(livreId).orElse(null);
    if (livre == null) {
        redirectAttributes.addFlashAttribute("message", "Livre introuvable.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/livres";
    }
    reservation.setLivre(livre);

    // Récupérer le status "Envoyé"
    Status status = statusService.findByNom("Envoyé").orElse(null);
    if (status == null) {
        // Si le status n'existe pas, tu peux le créer ou afficher une erreur
        redirectAttributes.addFlashAttribute("message", "Statut 'Envoyé' introuvable.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/livres";
    }
    reservation.setStatus(status);

    reservationService.save(reservation);

    redirectAttributes.addFlashAttribute("message", "Votre demande de réservation a été envoyée.");
    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
    return "redirect:/livres";
}
    // Ajoute d'autres routes selon tes besoins

@PostMapping("/action")
public String traiterReservation(
    @RequestParam("reservationId") Integer reservationId,
    @RequestParam("action") String action,
    RedirectAttributes redirectAttributes
) {
    // Récupérer la réservation
    Optional<Reservation> optionalReservation = reservationService.findById(reservationId);
    if (optionalReservation.isEmpty()) {
        redirectAttributes.addFlashAttribute("message", "Réservation introuvable.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/reservations/liste";
    }

    Reservation reservation = optionalReservation.get();

    // Déterminer le nouveau statut
    String nomStatus;
    if ("accepter".equalsIgnoreCase(action)) {
        nomStatus = "Accepté";
    } else if ("refuser".equalsIgnoreCase(action)) {
        nomStatus = "Refusé";
    } else {
        redirectAttributes.addFlashAttribute("message", "Action inconnue.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/reservations/liste";
    }

    // Récupérer le status correspondant
    Status status = statusService.findByNom(nomStatus).orElse(null);
    if (status == null) {
        redirectAttributes.addFlashAttribute("message", "Statut '" + nomStatus + "' introuvable.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        return "redirect:/reservations/liste";
    }

    // Mettre à jour et sauvegarder la réservation
    reservation.setStatus(status);
    reservationService.save(reservation);

    redirectAttributes.addFlashAttribute("message", "La réservation a été " + nomStatus.toLowerCase() + "e.");
    redirectAttributes.addFlashAttribute("alertClass", "alert-success");
    return "redirect:/reservations/liste";
}
}