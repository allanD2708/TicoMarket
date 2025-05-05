package com.example.ticomarket.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.ticomarket.model.Usuario;
import com.example.ticomarket.service.UsuarioService;


@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;


     // Mostrar formulario de perfil
     @GetMapping
     public String mostrarPerfil(Model model, Principal principal) {
         String email = principal.getName();
         Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);
         if (usuarioOpt.isEmpty()) {
             return "redirect:/login"; // Por seguridad, redirige si no encuentra
         }
 
         model.addAttribute("usuario", usuarioOpt.get());
         return "perfil"; // Crea perfil.html en templates
     }
 
     // Procesar cambios del perfil
     @PostMapping
     public String actualizarPerfil(@ModelAttribute("usuario") Usuario usuarioForm, Principal principal) {
         String email = principal.getName();
         Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);
 
         if (usuarioOpt.isEmpty()) {
             return "redirect:/login";
         }
 
         Usuario usuarioActual = usuarioOpt.get();
 
         // Actualizar datos básicos
         usuarioActual.setNombre(usuarioForm.getNombre());
         usuarioActual.setTelefono(usuarioForm.getTelefono());
         usuarioActual.setDireccion(usuarioForm.getDireccion());
 
         // Si la contraseña fue cambiada, encriptar
         if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isBlank()
                 && !usuarioForm.getPassword().startsWith("$2a$")) {
             usuarioActual.setPassword(passwordEncoder.encode(usuarioForm.getPassword()));
         }
 
         usuarioService.guardarUsuario(usuarioActual);
         return "redirect:/perfil?actualizado";
     }
}
