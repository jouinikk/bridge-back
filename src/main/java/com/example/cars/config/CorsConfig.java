package com.example.cars.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Configuration des origines autorisées
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://127.0.0.1:4200");
        // Pour un environnement de production, ajoutez votre domaine ici
        // config.addAllowedOrigin("https://votre-domaine.com");

        // Méthodes HTTP autorisées
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");

        // Headers autorisés
        config.addAllowedHeader("Origin");
        config.addAllowedHeader("Content-Type");
        config.addAllowedHeader("Accept");
        config.addAllowedHeader("Authorization");
        config.addAllowedHeader("X-Requested-With");
        config.addAllowedHeader("Access-Control-Request-Method");
        config.addAllowedHeader("Access-Control-Request-Headers");

        // Exposition des headers dans la réponse
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Type");
        config.addExposedHeader("Content-Disposition");

        // Autorisation des credentials
        config.setAllowCredentials(true);

        // Configuration du max age pour les pré-requêtes OPTIONS
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}