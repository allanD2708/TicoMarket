package com.example.ticomarket.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;
    

    SecurityConfig(CustomSuccessHandler customSuccessHandler) {
        this.customSuccessHandler = customSuccessHandler;
    }
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers(
                    "/", "/index", "/nosotros", "/contacto", 
                    "/login", "/registro", "/shop", "/producto/**",
                    "/producDetalle/**", "/productos/categoria/**",
                    "/css/**", "/js/**", "/img/**", "/assets/**", "/imagenes/**",
                    "/error"
                ).permitAll()
                
                // Rutas protegidas
                .requestMatchers("/usuarios/**").hasRole("AD")
                // .requestMatchers("/productos/**").hasRole("VE")
                .requestMatchers("/productos/**").hasAnyRole("AD", "VE")




                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler) // Página después de login exitoso
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Página después de logout
                .permitAll()
            );
            
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//     return NoOpPasswordEncoder.getInstance();
// }

}
