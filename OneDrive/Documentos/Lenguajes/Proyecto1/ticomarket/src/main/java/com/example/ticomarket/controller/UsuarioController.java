package com.example.ticomarket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.ticomarket.entity.Usuario;
import com.example.ticomarket.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioService.getUsuarioPorId(id);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario, HttpSession session) {
        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuario.getCorreo(), usuario.getContraseña());
        if (usuarioAutenticado != null) {
            // Guardar el usuario en la sesión
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            return ResponseEntity.ok("Inicio de sesión exitoso");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Invalidar la sesión
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }


}
