package com.example.ticomarket.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.example.ticomarket.model.Usuario;
import com.example.ticomarket.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;
 

    
    @GetMapping
    public String listarUsuarios(Model model) {
    model.addAttribute("usuarios", usuarioService.listarUsuarios());
    model.addAttribute("usuario", new Usuario()); // NECESARIO para el formulario
    return "usuarios";
}


    // Muestra el formulario para crear un nuevo usuario
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios";  // Ubicación de la vista "form.html"
    }

    // Procesa el formulario para guardar o actualizar un usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "usuarios";
        }
         // Encriptar si la contraseña no está en formato BCrypt
    if (!usuario.getPassword().startsWith("$2a$") && !usuario.getPassword().startsWith("$2b$")) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    }
        usuarioService.guardarUsuario(usuario);
        return "redirect:/usuarios";
    }

    // Muestra el formulario para editar un usuario existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") int id, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(id);
        if (!usuarioOpt.isPresent()) {
            // Redirigir a la lista o mostrar una página de error según lo que consideres apropiado
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", usuarioOpt.get());
        return "usuarios";
    }

    // Elimina un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") int id) {
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }
}
