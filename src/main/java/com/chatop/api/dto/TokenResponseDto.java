package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Réponse contenant le token JWT généré après une connexion ou une inscription réussie")
public class TokenResponseDto {

  @Schema(description = "Token JWT à utiliser dans l'en-tête Authorization pour les appels authentifiés (préfixé par 'Bearer ')")
  private String token;

  public TokenResponseDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}