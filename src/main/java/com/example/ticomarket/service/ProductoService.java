package com.example.ticomarket.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticomarket.model.Producto;
import com.example.ticomarket.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

   
    public Optional<Producto> buscarProductoPorId(Integer id) {
        return productoRepository.findById(id);
    }

   
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

  
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.buscarPorCategoriaIgnorandoCase(categoria);
    }
    
    public Producto obtenerPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }
// ---------------------------------------------------------------------------------------

public List<Producto> buscarRelacionados(Integer idProducto) {
    return productoRepository.findAllProductos(); // ¡solo para test!
}


// ---------------------------------------------------------------------------------------
    

}
