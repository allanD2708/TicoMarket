package com.example.ticomarket.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ticomarket.model.Imagen;
import com.example.ticomarket.model.Producto;
import com.example.ticomarket.model.Usuario;
import com.example.ticomarket.repository.ImagenRepository;
import com.example.ticomarket.repository.ProductoRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ImagenRepository imagenRepository; 

    
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
// 🆕 Método para eliminar imágenes
    public void eliminarImagenes(List<Integer> idsImagenes) {
        for (Integer id : idsImagenes) {
            Optional<Imagen> imagenOpt = imagenRepository.findById(id);
            if (imagenOpt.isPresent()) {
                Imagen imagen = imagenOpt.get();
                // Eliminar archivo físico (opcional)
                try {
                    Path rutaImagenes = Paths.get("src/main/resources/static");
                    Path rutaCompleta = rutaImagenes.resolve(imagen.getUrl().substring(1)); // Quitar "/"
                    Files.deleteIfExists(rutaCompleta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imagenRepository.delete(imagen); // Eliminar de la base de datos
            }
        }
    }
// ---------------------------------------------------------------------------------------
public Optional<Producto> buscarProductoConUsuarioPorId(Integer id) {
    return productoRepository.buscarProductoConUsuarioPorId(id);
}

// ---------------------------------------------------------------------------------------
    
public List<Producto> listarPorUsuario(Usuario usuario) {
    return productoRepository.findByUsuario(usuario);
}
// ------------------------------------------------------
public List<Producto> buscarPorNombre(String nombre) {
    return productoRepository.findByNombreContainingIgnoreCase(nombre);
}


}
