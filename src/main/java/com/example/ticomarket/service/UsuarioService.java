package com.example.ticomarket.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ticomarket.model.Usuario;
import com.example.ticomarket.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

     @Autowired
    private UsuarioRepository usuarioRepository;

   
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

   
    public Optional<Usuario> buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

   
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

 
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean existeUsuarioConEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // -------------------------------------------------------------
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    // -------------------------------------------------------------

}
