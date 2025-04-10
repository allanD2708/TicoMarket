package com.example.ticomarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {
   
    @GetMapping("/")
    public String home() {
        return "index"; // Carga index.html de la carpeta templates
    }
   
    @GetMapping("/registro")
    public String registro() {
        return "registro"; // Carga registro.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Carga login.html
    }

    @GetMapping("/mis-productos")
    public String misProductos() {
        return "mis-productos"; // Carga mis-productos.html
    }

}