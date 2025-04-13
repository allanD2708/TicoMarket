package com.example.ticomarket.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    
    @ManyToOne
    @JoinColumn(name =  "id_usuario")
    private Usuario usuario;
}
