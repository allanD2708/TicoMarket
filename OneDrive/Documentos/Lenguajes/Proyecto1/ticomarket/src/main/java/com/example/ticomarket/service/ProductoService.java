package com.example.ticomarket.service;

import org.springframework.stereotype.Service;

import com.example.ticomarket.entity.Producto;
import com.example.ticomarket.repository.ProductoRepository;
import java.util.List;

@Service

public class ProductoService {
    
    private final ProductoRepository productoRepository;


    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto getProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }
}
