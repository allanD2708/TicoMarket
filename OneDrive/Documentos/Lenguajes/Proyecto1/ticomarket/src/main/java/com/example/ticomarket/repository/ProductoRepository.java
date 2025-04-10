package com.example.ticomarket.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ticomarket.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByususario_id(Long ususario_id);
}

