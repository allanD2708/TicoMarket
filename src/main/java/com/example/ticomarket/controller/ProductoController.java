package com.example.ticomarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.example.ticomarket.model.Imagen;
import com.example.ticomarket.model.Producto;
import com.example.ticomarket.model.Usuario;
import com.example.ticomarket.service.ProductoService;
import com.example.ticomarket.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioService usuarioService;

    
    @GetMapping
    public String listarProductos(Model model) {
        List<Producto> productos = productoService.listarProductos();
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Crear producto vacío para el formulario del modal
        Producto producto = new Producto();
        producto.setUsuario(new Usuario()); // Esto evita el error de null

        model.addAttribute("productos", productos);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("producto", producto);

        return "productos";
    }

  


@PostMapping("/guardar")
public String guardarProducto(@ModelAttribute Producto producto,
                              @RequestParam("files") MultipartFile[] files) {

    List<Imagen> imagenes = new ArrayList<>();
    String imagenPrincipal = null;

    if (files != null && files.length > 0) {
        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (!file.isEmpty()) {
                try {
                    String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path rutaImagenes = Paths.get("src/main/resources/static/imagenes");
                    Files.createDirectories(rutaImagenes);
                    Path rutaCompleta = rutaImagenes.resolve(nombreArchivo);
                    Files.copy(file.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

                    String url = "/imagenes/" + nombreArchivo;

                    if (i == 0) {
                        imagenPrincipal = url;
                    }

                    Imagen imagen = new Imagen();
                    imagen.setUrl(url);
                    imagen.setProducto(producto);
                    imagenes.add(imagen);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    producto.setImagen(imagenPrincipal);
    producto.setImagenes(imagenes);
    producto.setImagenes(imagenes);
    productoService.guardarProducto(producto); // Guarda todo en cascada

    return "redirect:/productos";
}






    // Muestra el formulario para crear un nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        // Se añade la lista de usuarios para relacionar el producto con un usuario (si es necesario)
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "productos";  // Ubicación de la vista "form.html"
    }

  

    // Muestra el formulario para editar un producto existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") int id, Model model) {
        Optional<Producto> productoOpt = productoService.buscarProductoPorId(id);
        if (!productoOpt.isPresent()) {
            return "redirect:/productos";
        }
        model.addAttribute("producto", productoOpt.get());
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "productos";
    }

    // Elimina un producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") int id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }

 
   @GetMapping("/{id}")
public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
    Optional<Producto> productoOpt = productoService.buscarProductoPorId(id);
    return productoOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
}

    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------

}
