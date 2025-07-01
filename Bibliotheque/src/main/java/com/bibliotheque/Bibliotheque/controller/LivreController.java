package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Livre;
import com.bibliotheque.Bibliotheque.service.LivreService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LivreController {
    
    @Autowired
    private LivreService livreService;

    @GetMapping("/livres")
    public String listerLivres(Model model) {
        List<Livre> livres = livreService.getAll();
        model.addAttribute("livres", livres);
        model.addAttribute("page", "livre/liste");
        return "template";
    }
}
