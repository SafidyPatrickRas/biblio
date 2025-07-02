package com.bibliotheque.Bibliotheque.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String showHome(Model model) {
        model.addAttribute("page", "home");
        return "template";
    }
}
