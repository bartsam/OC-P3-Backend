package com.chatop.api.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
public class SpringSecurityConfig {

  // JWT secret key loaded from local.properties
  @Value("${jwt.secret}")
  private String jwtKey;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http.csrf(csrf -> csrf.disable()) // Disable CSRF (not needed for a stateless API)
        // Stateless session (no server-side session, each request carry its own token)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // All routes require authentication by default
        // TO DO : Add /register,/login,/Swagger exceptions
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        // TO REMOVE : Enables Basic Auth (username/password sent in header)
        .httpBasic(Customizer.withDefaults())
        // Enables JWT validation on incoming requests (Authorization: Bearer <token>
        // header)
        .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
        .build();
  }

  // Decodes and validates incoming JWT signatures using the shared secret key
  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length, "RSA");
    return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
  }

  // Generates signed JWTs using the same secret key (used by JWTService)
  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
  }

  // Password encoder: ensures passwords are never stored in plain text in the DB
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // TO DO: replace service backed by UserRepository once the User entity is set
  @Bean
  public UserDetailsService users() {
    UserDetails user = User.builder().username("user").password(passwordEncoder().encode("password")).roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);

  }

}
