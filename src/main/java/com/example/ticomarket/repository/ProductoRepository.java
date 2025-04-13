package com.example.ticomarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ticomarket.model.Producto;
import com.example.ticomarket.model.Usuario;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Método para listar productos de un usuario específico
    List<Producto> findByUsuario(Usuario usuario);
}