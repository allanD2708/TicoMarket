package com.example.ticomarket.security;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {

        var roles = authentication.getAuthorities().toString();

        if (roles.contains("ROLE_AD")) {
            response.sendRedirect("/usuarios");
        } else if (roles.contains("ROLE_VE")) {
            response.sendRedirect("/productos");
        } else {
            response.sendRedirect("/");
        }
    }
    
}
