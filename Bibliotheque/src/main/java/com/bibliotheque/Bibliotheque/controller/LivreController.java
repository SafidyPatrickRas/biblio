package com.bibliotheque.Bibliotheque.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.LivreService;

@Controller
@RequestMapping("/livres")
public class LivreController {
    @Autowired
    private LivreService livreService;
    @Autowired
    private AdherantService adherantService;

    // Liste des livres
     @GetMapping("/list")
     public String list(Model model) {
         List<Livre> livres = livreService.getAllLivres();
         List<Adherant> adherants = adherantService.getAllAdherants();
         model.addAttribute("livres", livres);
         model.addAttribute("adherants", adherants);
         model.addAttribute("page" , "livre/list");
         return "template";
     }
}
