package com.example.ticomarket.controller;
import com.example.ticomarket.model.Usuario;
import com.example.ticomarket.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthController {


    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/login")
    public String mostrarLogin(
        @RequestParam(value = "error", required = false) String error,
        @RequestParam(value = "logout", required = false) String logout,
        Model model) {
        
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }
        
        if (logout != null) {
            model.addAttribute("message", "Has cerrado sesión correctamente");
        }
        
        return "login";
    }

     @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }
// ---------------------------------------------------------------
@Autowired
private PasswordEncoder passwordEncoder;
@PostMapping("/registro")
public String registrarUsuario(
        @Valid @ModelAttribute("usuario") Usuario usuario,
        BindingResult result,
        @RequestParam("confirmPassword") String confirmPassword,
        RedirectAttributes redirectAttributes) {
    
    // Validar que las contraseñas coincidan
    if (!usuario.getPassword().equals(confirmPassword)) {
        result.reject("global", "Las contraseñas no coinciden");
    }

    if (usuarioService.existeUsuarioConEmail(usuario.getEmail())) {
        result.rejectValue("email", "error.usuario", "Este correo ya está registrado");
    }

    
    if (result.hasErrors()) {
        return "registro";
    }

    // Asignar rol de vendedor (VE)
    usuario.setRol("VE");
    
     // 🔐 Encriptar la contraseña antes de guardar
     usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

    // Guardar el usuario
    usuarioService.guardarUsuario(usuario);

    // redirectAttributes.addFlashAttribute("success", true);
    redirectAttributes.addFlashAttribute("mensajeExito", "¡Registro exitoso! Por favor inicia sesión.");
    return "redirect:/login";
}

    
}
