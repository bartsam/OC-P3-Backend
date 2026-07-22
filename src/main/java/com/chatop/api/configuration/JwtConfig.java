package com.chatop.api.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

/**
 * Déclare les beans nécessaires à l'encodage et au décodage des JWT.
 */
@Configuration
public class JwtConfig {

  // Clé secrète JWT chargée depuis local.properties
  @Value("${jwt.secret}")
  private String jwtKey;

  /**
   * Décode et valide la signature des JWT entrants avec la clé secrète partagée.
   */
  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKeySpec secretKey = new SecretKeySpec(jwtKey.getBytes(), "HmacSHA256");
    return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
  }

  /**
   * Génère des JWT signés avec la même clé secrète (utilisé par JWTService).
   */
  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(jwtKey.getBytes()));
  }
}