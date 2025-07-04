package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Reservation;
import com.bibliotheque.Bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public String listeReservations(Model model) {
        List<Reservation> reservations = reservationService.findByStatusNom("envoyee");
        model.addAttribute("reservations", reservations);
        model.addAttribute("page", "reservation/liste"); // à adapter selon ton template
        return "template"; // à adapter selon ton template
    }

    // Ajoute d'autres routes selon tes besoins
}