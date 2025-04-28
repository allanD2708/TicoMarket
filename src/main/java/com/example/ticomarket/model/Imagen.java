package com.example.ticomarket.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Imagen {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_imagen;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    @JsonBackReference
    private Producto producto;
}
