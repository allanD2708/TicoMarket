package com.example.ticomarket.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ticomarket.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Aquí se pueden declarar métodos personalizados, por ejemplo:
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    



}