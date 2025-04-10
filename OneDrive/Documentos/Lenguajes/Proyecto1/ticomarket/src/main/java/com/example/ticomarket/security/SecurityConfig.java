package com.example.ticomarket.security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .csrf().disable() // Desactivar CSRF para facilitar pruebas iniciales
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/", "/index.html", "/api/productos", "/css/**", "/js/**", "/assets/**").permitAll() // Rutas públicas
//                 .anyRequest().authenticated() // Rutas protegidas
//             )
//             .formLogin(login -> login
//                 .loginPage("/login") // Página de inicio de sesión personalizada
//                 .permitAll() // Permitir acceso público al login
//             )
//             .logout(logout -> logout.permitAll()); // Permitir cierre de sesión
//         return http.build();
//     }
}
