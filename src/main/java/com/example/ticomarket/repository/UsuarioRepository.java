package com.example.ticomarket.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ticomarket.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Aquí se pueden declarar métodos personalizados, por ejemplo:
    // Usuario findByCorreo(String correo);
}