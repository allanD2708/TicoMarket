package com.example.ticomarket.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "direccion", nullable = false)
    private String direccion;
    
    @Column(name = "rol", nullable = false)
    private String rol;

    @OneToMany(mappedBy = "usuario")
    // @JsonManagedReference
    @JsonBackReference
    private List<Producto> productos;

    
}
