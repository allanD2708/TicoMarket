package com.example.ticomarket.controller;

import org.springframework.web.bind.annotation.*;

import com.example.ticomarket.entity.Producto;
import com.example.ticomarket.service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
     private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.getProductos();
    }

    @PostMapping
    public Producto registrarProducto(@RequestBody Producto producto) {
        return productoService.crearProducto(producto);
    }

    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.getProductoPorId(id);
    }

}
