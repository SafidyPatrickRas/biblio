package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Adherant;
import com.bibliotheque.Bibliotheque.model.Profil;
import com.bibliotheque.Bibliotheque.service.AdherantService;
import com.bibliotheque.Bibliotheque.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adherants")
public class AdherantController {

    @Autowired
    private AdherantService adherantService;

    @Autowired
    private ProfilService profilService;

    // Afficher le formulaire d'insertion d'un nouvel adhérant
    @GetMapping("/inscription")
    public String showForm(Model model) {
        model.addAttribute("adherant", new Adherant());
        List<Profil> profils = profilService.getAll();
        model.addAttribute("profils", profils);
         model.addAttribute("page", "adherant/inscription");
        return "template"; // à adapter selon le nom de ton template
    }
}