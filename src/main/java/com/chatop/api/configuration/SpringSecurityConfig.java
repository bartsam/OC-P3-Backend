package com.chatop.api.configuration;

import javax.crypto.spec.SecretKeySpec;

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
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

/**
 * Met en place une authentification JWT stateless :
 * - /api/auth/register, /api/auth/login et Swagger sont publics
 * - toutes les autres routes nécessitent un token JWT valide
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  /**
   * Clé secrète JWT chargé depuis local.properties
   */
  @Value("${jwt.secret}")
  private String jwtKey;

  private final CustomUserDetailsService customUserDetailsService;

  public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
    this.customUserDetailsService = customUserDetailsService;
  }

  // ----------------------------------------------------------------
  // Chaîne de filtres de sécurité
  // ----------------------------------------------------------------

  /**
   * Définit les règles d'accès HTTP et active la validation JWT
   * sur toutes les routes protégées.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        // CSRF pas nécessaire pour API stateless
        .csrf(csrf -> csrf.disable())
        // API stateless pas de session serveur-side
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Routes publiques : register, login et docs Swagger
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
            .requestMatchers("/swagger-ui/**", "/swagger-ui").permitAll()
            .anyRequest().authenticated())
        // Valide le token JWT (Authorization: Bearer <token>) sur les routes protégées
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .build();
  }

  // ----------------------------------------------------------------
  // Encodage / décodage JWT
  // ----------------------------------------------------------------

  /**
   * Décode et valide la signature des JWT entrants avec la clé secrète partagée.
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(), 0, jwtKey.getBytes().length, "RSA");
    return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
  }

  /**
   * Génère des JWT signés avec la même clé secrète (utilisé par JWTService).
   */
  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
  }

  // ----------------------------------------------------------------
  // Authentification base de données
  // ----------------------------------------------------------------

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