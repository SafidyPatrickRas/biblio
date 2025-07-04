package com.bibliotheque.Bibliotheque.controller;

import com.bibliotheque.Bibliotheque.model.Prolongement;
import com.bibliotheque.Bibliotheque.service.ProlongementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/prolongements")
public class ProlongementController {

    @Autowired
    private ProlongementService prolongementService;

    @GetMapping("/")
    public String listerProlongements(Model model) {
        List<Prolongement> prolongements = prolongementService.getAllProlongements();
        model.addAttribute("prolongements", prolongements);
        model.addAttribute("page", "prolongement/liste");
        return "template";
    }

    
}
