package com.example.ticomarket.entity;

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
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false)
    private String categoria;


    @ManyToOne
    @JoinColumn(name = "ususario_id", nullable = false)
    private Usuario ususario;
    
}
