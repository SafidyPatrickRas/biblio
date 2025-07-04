package com.bibliotheque.Bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.LivreService;
import com.bibliotheque.Bibliotheque.service.PenaliteService;
import com.bibliotheque.Bibliotheque.service.PretService;
import com.bibliotheque.Bibliotheque.service.ProlongementService;
import com.bibliotheque.Bibliotheque.service.ReservationService;

import java.util.Date;

@Controller
public class DashController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private PretService pretService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private PenaliteService penaliteService;

    @Autowired
    private ProlongementService prolongementService;

    @GetMapping("/dash")
    public String show(Model model) {
        // Statistiques des livres
        model.addAttribute("totalLivres", livreService.countAll());

        // Statistiques des prêts
        model.addAttribute("pretsActifs", pretService.getNombrePretsNonRetournes());

        // Calcul des prêts en retard
        Date now = new Date();
        long lateLoans = pretService.getAllPrets().stream()
                .filter(p -> !p.getIsRetournee()
                        && p.getDateFin() != null
                        && now.after(p.getDateFin()))
                .count();
        model.addAttribute("pretsEnRetard", lateLoans);

        // Statistiques des adhérents
        model.addAttribute("totalAdherants", adherantService.countAll());

        model.addAttribute("adherantsAvecPretActif", adherantService.findAll().stream()
                .filter(a -> pretService.getNombrePretsNonRetournesParAdherent(a.getId()) > 0)
                .count());

        // Statistiques des réservations
        model.addAttribute("totalReservations", reservationService.countAll());
        model.addAttribute("reservationsEnCours", reservationService.countEnCours());

        // Statistiques des prolongements
        model.addAttribute("totalProlongements", prolongementService.countAll());

        // Pour affichage dans le template
        model.addAttribute("page", "dash");

        return "template"; // Assure-toi que template.html existe bien dans /templates
    }
}
