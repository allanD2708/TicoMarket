package com.example.ticomarket.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.example.ticomarket.model.Producto;
import com.example.ticomarket.service.ProductoService;

@Controller
public class HomeController {
      @Autowired
    private ProductoService productoService;

    
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("productos", productoService.listarProductos());
        return "index"; 
    }

    @GetMapping("/nosotros")
    public String mostrarNosotros() {
        return "nosotros"; 
    }

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto"; 
    }
    

    @GetMapping("/shop")
    public String mostrarShop(Model model) {
    model.addAttribute("productos", productoService.listarProductos());
        return "shop"; 
    }


   
   
    @GetMapping("/productos/categoria/{categoria}")
    public String productosPorCategoria(@PathVariable String categoria, Model model) {
        List<Producto> productos = productoService.buscarPorCategoria(categoria);
        model.addAttribute("productos", productos);
        return "shop"; 
    }
  

    @GetMapping("/producDetalle/{id}")
    public String mostrarproducDetalle(@PathVariable Integer id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        return "producDetalle"; 
    }
 
    
// ---------------------------------------------------------
@GetMapping("/producto/{id}")
public String verProducto(@PathVariable("id") Integer id, Model model) {
    Producto producto = productoService.obtenerPorId(id);
    model.addAttribute("producto", producto);

    List<Producto> relacionados = productoService.buscarRelacionados(producto.getId_producto());

    // Evita null en la vista
    if (relacionados == null) {
        relacionados = new ArrayList<>();
    }

    model.addAttribute("relacionados", relacionados);
    return "producDetalle"; // o el nombre real de tu plantilla
}

// ---------------------------------------------------------


}
