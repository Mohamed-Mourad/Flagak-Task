package com.flagak.task_backend.config;

import com.flagak.task_backend.utils.JwtUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/products", "/api/vendors/login", "/api/vendors/register", "/api/customers/login", "/api/customers/register")
                        .permitAll() // Public access to these endpoints
                        .requestMatchers("/api/products/add", "/api/products/edit/{id}", "/api/products/delete/{id}")
                        .authenticated() // Requires authentication for these endpoints
                        .anyRequest().authenticated() // Other endpoints are public
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); // Adding the JWT filter to the chain

        return http.build();
    }
}

