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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

  


// //--------------------------------------------------------------------------

@PostMapping("/guardar")
public String guardarProducto(@ModelAttribute Producto producto,
                               @RequestParam("files") MultipartFile[] files,
                               @RequestParam(value = "imagenesParaEliminar", required = false) String imagenesParaEliminarJson) {

    List<Imagen> nuevasImagenes = new ArrayList<>();

    // Cargar el producto existente desde la BD si es edición
    Producto productoExistente = productoService.buscarProductoPorId(producto.getId_producto()).orElse(null);

    List<Imagen> imagenesExistentes = new ArrayList<>();
    if (productoExistente != null) {
        imagenesExistentes.addAll(productoExistente.getImagenes());
    }

    // Eliminar imágenes si corresponde
    if (imagenesParaEliminarJson != null && !imagenesParaEliminarJson.isEmpty()) {
        List<Integer> idsImagenesEliminar = new Gson().fromJson(imagenesParaEliminarJson, new TypeToken<List<Integer>>() {}.getType());
        productoService.eliminarImagenes(idsImagenesEliminar);

        // Filtrar las imágenes existentes, quitando las eliminadas
        imagenesExistentes.removeIf(imagen -> idsImagenesEliminar.contains(imagen.getId_imagen()));
    }

    // Subir nuevas imágenes
    if (files != null && files.length > 0) {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    Path rutaImagenes = Paths.get("src/main/resources/static/imagenes");
                    Files.createDirectories(rutaImagenes);
                    Path rutaCompleta = rutaImagenes.resolve(nombreArchivo);
                    Files.copy(file.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

                    String url = "/imagenes/" + nombreArchivo;

                    Imagen imagen = new Imagen();
                    imagen.setUrl(url);
                    imagen.setProducto(producto);
                    nuevasImagenes.add(imagen);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Unir las imágenes existentes que quedaron + las nuevas subidas
    List<Imagen> imagenesFinales = new ArrayList<>();
    imagenesFinales.addAll(imagenesExistentes);
    imagenesFinales.addAll(nuevasImagenes);

    producto.setImagenes(imagenesFinales);

    productoService.guardarProducto(producto);

    return "redirect:/productos";
}


// @PostMapping("/guardar")
// public String guardarProducto(@ModelAttribute Producto producto,
//                                @RequestParam("files") MultipartFile[] files,
//                                @RequestParam(value = "imagenesParaEliminar", required = false) String imagenesParaEliminarJson) {

//     List<Imagen> nuevasImagenes = new ArrayList<>();

//     // Si hay imágenes a eliminar
//     if (imagenesParaEliminarJson != null && !imagenesParaEliminarJson.isEmpty()) {
//         List<Integer> idsImagenesEliminar = new Gson().fromJson(imagenesParaEliminarJson, new TypeToken<List<Integer>>() {}.getType());
//         productoService.eliminarImagenes(idsImagenesEliminar);
//     }

//     // Subir nuevas imágenes
//     if (files != null && files.length > 0) {
//         for (MultipartFile file : files) {
//             if (!file.isEmpty()) {
//                 try {
//                     String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
//                     Path rutaImagenes = Paths.get("src/main/resources/static/imagenes");
//                     Files.createDirectories(rutaImagenes);
//                     Path rutaCompleta = rutaImagenes.resolve(nombreArchivo);
//                     Files.copy(file.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

//                     String url = "/imagenes/" + nombreArchivo;

//                     Imagen imagen = new Imagen();
//                     imagen.setUrl(url);
//                     imagen.setProducto(producto);
//                     nuevasImagenes.add(imagen);

//                 } catch (IOException e) {
//                     e.printStackTrace();
//                 }
//             }
//         }
//     }

//     // Si agregaron nuevas imágenes, las añadimos
//     if (!nuevasImagenes.isEmpty()) {
//         producto.getImagenes().addAll(nuevasImagenes);
//     }

//     productoService.guardarProducto(producto); // Guarda en cascada

//     return "redirect:/productos";
// }
//-------------------------------------------------------------




    // Muestra el formulario para crear un nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("producto", new Producto());
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "productos";
    }


  

    // Muestra el formulario para editar un producto existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Integer id, Model model) {
        // Optional<Producto> productoOpt = productoService.buscarProductoPorId(id);
        Optional<Producto> productoOpt = productoService.buscarProductoConUsuarioPorId(id);

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
    // Optional<Producto> productoOpt = productoService.buscarProductoPorId(id);
    Optional<Producto> productoOpt = productoService.buscarProductoConUsuarioPorId(id);
    return productoOpt.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
}

    // ---------------------------------------------------------------------------------------
    
    // ---------------------------------------------------------------------------------------

}
