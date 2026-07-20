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

@Service
public class JWTService {

  // Encoder defined in SpringSecurityConfig, signs tokens with shared secret key
  private JwtEncoder jwtEncoder;

  public JWTService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  // Builds and signs a JWT for the given authenticated user
  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();

    // Claims: JWT payload fields (issuer, timestamps, subject = username)
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.DAYS))
        .subject(authentication.getName()).build();

    // Combines claims with the signing MacAlgorithm.HS256 to produce the token
    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(),
        claims);

    return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }
}
