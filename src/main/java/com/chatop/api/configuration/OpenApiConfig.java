package com.chatop.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration OpenAPI/Swagger pour la documentation de l'API.
 */
@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        // Métadonnées affichées en haut de page Swagger UI
        .info(new Info()
            .title("Chatop API")
            .version("1.0")
            .description(
                "API de gestion de locations : inscription, authentification JWT, création et consultation d'annonces"))

        // Impose "bearerAuth" par défaut sur toutes les routes de l'API
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

        // Définit le schéma "bearerAuth" : token JWT dans l'en-tête Authorization
        .components(new Components()
            .addSecuritySchemes("bearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")));
  }
}