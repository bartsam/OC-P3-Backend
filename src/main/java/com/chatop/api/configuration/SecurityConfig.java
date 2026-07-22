package com.chatop.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import com.chatop.api.services.CustomUserDetailsService;

/**
 * Met en place une authentification JWT stateless :
 * - /api/auth/register, /api/auth/login et Swagger sont publics
 * - toutes les autres routes nécessitent un token JWT valide
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Liste des origines autorisées à appeler l'API
   */
  @Value("${app.cors.allowed-origins}")
  private String allowedOrigins;

  private final CustomUserDetailsService customUserDetailsService;
  private final CorsConfigurationSource corsConfigurationSource;

  public SecurityConfig(CustomUserDetailsService customUserDetailsService,
      CorsConfigurationSource corsConfigurationSource) {
    this.customUserDetailsService = customUserDetailsService;
    this.corsConfigurationSource = corsConfigurationSource;
  }

  /**
   * Définit les règles d'accès HTTP et active la validation JWT
   * sur toutes les routes protégées.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        // CSRF pas nécessaire pour API stateless
        .csrf(csrf -> csrf.disable())
        // Active le support CORS
        .cors(cors -> cors.configurationSource(corsConfigurationSource))
        // API stateless pas de session serveur-side
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Définis les routes publiques : register, login et docs Swagger
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
            .requestMatchers("/swagger-ui/**", "/swagger-ui").permitAll()
            .anyRequest().authenticated())
        // Déclenche l'authentification avec token JWT sur les routes protégées
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .build();
  }

  /**
   * Encode les mots de passe avec BCrypt avant stockage en base
   * et vérifie les mots de passe saisis lors de l'authentification.
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Authentifie les utilisateurs via CustomUserDetailsService
   * (chargement des credentials depuis MySQL) et BCryptPasswordEncoder.
   */
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder)
      throws Exception {

    AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);

    return authBuilder.build();
  }
}