package com.example.ticomarket.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ticomarket.model.Usuario;
import com.example.ticomarket.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new CustomUserDetails(usuario);
    }


    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     Usuario usuario = usuarioRepository.findByEmail(email)
    //         .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    
        
    //     List<GrantedAuthority> authorities = List.of(
    //  new SimpleGrantedAuthority("ROLE_" + usuario.getRol()) // Ej: ROLE_AD, ROLE_VE
    //  );
    
    //     return new org.springframework.security.core.userdetails.User(
    //         usuario.getEmail(),
    //         usuario.getPassword(),
    //         authorities
    //     );
    // }
    

    
}
