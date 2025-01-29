package com.EventManagementSystem.EventManagementSystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectURL = request.getContextPath();

        // Check user roles and set the redirect URL accordingly
        if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            redirectURL = "/admin/admin-all-users";
        } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
            redirectURL = "/";
        }
        response.sendRedirect(redirectURL);
    }
}
