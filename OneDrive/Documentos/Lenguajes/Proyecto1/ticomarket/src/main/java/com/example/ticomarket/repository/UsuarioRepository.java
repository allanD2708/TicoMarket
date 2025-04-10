package com.example.ticomarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ticomarket.entity.Usuario;
import java.util.Optional;

// public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
//     Usuario findByCorreo(String correo);
// }

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoAndContraseña(String correo, String contraseña);
}