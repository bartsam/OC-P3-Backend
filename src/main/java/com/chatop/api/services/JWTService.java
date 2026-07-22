package com.chatop.api.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * Génère les tokens JWT remis aux user après authentification réussie.
 */
@Service
public class JWTService {

  // Encodeur signe les tokens avec une clé secrète partagée
  private JwtEncoder jwtEncoder;

  public JWTService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  /**
   * Construit et signe un JWT pour l'user authentifié (subject et durée)
   */
  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();

    // Claims : contenu (payload) du JWT
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self") // identifie l'app comme émetteur du token
        .issuedAt(now) // date de création du token
        .expiresAt(now.plus(1, ChronoUnit.DAYS)) // date d'expiration (1j après création)
        .subject(authentication.getName()).build(); // id de l'user authentifié (email)

    // Assemble claims et l'algorithme de signature HS256 pour encodage du token
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(),
        claims);

    return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }
}
