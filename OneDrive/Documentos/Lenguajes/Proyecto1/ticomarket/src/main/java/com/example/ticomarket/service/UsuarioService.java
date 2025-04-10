package com.example.ticomarket.service;

import org.springframework.stereotype.Service;
import com.example.ticomarket.entity.Usuario;
import com.example.ticomarket.repository.UsuarioRepository;
import java.util.List;


@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // public Usuario crearUsuario(Usuario usuario) {
    //     if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
    //         throw new IllegalArgumentException("El correo ya está registrado");
    //     }
    //     return usuarioRepository.save(usuario);
    // }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // public Usuario getUsuarioPorId(Long id) {
    //     return usuarioRepository.findById(id)
    //             .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    // }



    public Usuario autenticarUsuario(String correo, String contraseña) {
        return usuarioRepository.findByCorreoAndContraseña(correo, contraseña)
                .orElse(null); // Retorna null si no encuentra coincidencia
    }
}
