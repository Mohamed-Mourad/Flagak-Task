package com.flagak.task_backend.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.flagak.task_backend.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public JwtAuthenticationFilter(){
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);

        if (token != null && JwtUtil.isValidToken(token)) {  // Validate the token
            // Extract the email from the token
            String email = JwtUtil.extractEmailFromToken(token);

            // Create an Authentication object based on the email (or user details)
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, token, Collections.emptyList());

            // Set the Authentication object to the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Extracts the token
        }
        return null;
    }
}

