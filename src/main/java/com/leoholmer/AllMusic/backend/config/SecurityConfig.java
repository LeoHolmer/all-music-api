package com.leoholmer.AllMusic.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/actuator/**"),
                                new AntPathRequestMatcher("/error")).permitAll() // Permitir acceso público a la raíz y health checks
                        .requestMatchers(new AntPathRequestMatcher("/artist/auth"),
                                new AntPathRequestMatcher("/enthusiast/auth")).permitAll() // Permitir login sin autenticación
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-ui.html")).permitAll() // Permitir acceso a Swagger
                        .anyRequest().authenticated() // Proteger el resto de endpoints
                )
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF solo si usas autenticación JWT
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Configurar CORS correctamente

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:8080", "http://127.0.0.1:8080")); // Permitir frontend y acceso directo
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
