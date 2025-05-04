package com.example.ticomarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ticomarket.model.Producto;
import com.example.ticomarket.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByUsuario(Usuario usuario);
    @Query("SELECT p FROM Producto p WHERE LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    List<Producto> buscarPorCategoriaIgnorandoCase(@Param("categoria") String categoria);
    // -------------------------------------------------------------------------------------
    @Query("SELECT p FROM Producto p WHERE p.id_producto <> :idProducto")
    List<Producto> buscarRelacionadosExcluyendoId(@Param("idProducto") Integer idProducto);
    @Query("SELECT p FROM Producto p")
    List<Producto> findAllProductos();
    //-----------------------------------------------------------------------------------------
    @Query("SELECT p FROM Producto p JOIN FETCH p.usuario WHERE p.id_producto = :id")
    Optional<Producto> buscarProductoConUsuarioPorId(@Param("id") Integer id);

        




    

}