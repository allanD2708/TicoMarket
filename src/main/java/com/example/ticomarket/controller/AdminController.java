package com.example.ticomarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
@RequestMapping("/usuarios")
public class AdminController {
    
    @GetMapping("/usuarios")
    public String adminDashboard(Model model) {
        model.addAttribute("titulo", "Panel de Administración");
        return "usuarios";
    }
}
