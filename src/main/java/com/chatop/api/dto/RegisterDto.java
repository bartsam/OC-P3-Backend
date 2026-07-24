package com.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Données nécessaires à l'inscription d'un nouvel utilisateur")
public class RegisterDto {

  @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont")
  @NotBlank(message = "Name required")
  private String name;

  @Schema(description = "Adresse email de l'utilisateur, utilisée comme identifiant de connexion", example = "jean.dupont@example.com")
  @NotBlank(message = "Email required")
  @Email(message = "Invalid email format")
  private String email;

  @Schema(description = "Mot de passe choisi par l'utilisateur", example = "MotDePasse123")
  @NotBlank(message = "Password required")
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

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