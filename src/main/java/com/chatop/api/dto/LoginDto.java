package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Identifiants nécessaires à la connexion d'un utilisateur")
public class LoginDto {

  @Schema(description = "Adresse email de l'utilisateur", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Mot de passe de l'utilisateur", example = "MotDePasse123")
  private String password;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}