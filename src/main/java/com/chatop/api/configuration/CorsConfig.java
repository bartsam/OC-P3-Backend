package com.chatop.api.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Définit les règles CORS appliquées à toute l'API :
 * origines, méthodes HTTP et headers autorisés dans les requêtes cross-origin.
 */
@Configuration
public class CorsConfig {

  @Value("${app.cors.allowed-origins}")
  private String allowedOrigins;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration config = new CorsConfiguration();
    // Origines autorisées à appeler l'API
    config.setAllowedOrigins(List.of(allowedOrigins));

    // Méthodes HTTP acceptées (OPTIONS compris pour le preflight)
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    // Headers autorisés dans la requête
    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

    // Autorise l'envoi de credentials (ex: cookies, headers d'auth) cross-origin
    config.setAllowCredentials(true);

    // Applique la configuration CORS à tous les endpoints de l'API
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}