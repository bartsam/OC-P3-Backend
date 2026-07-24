package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.chatop.api.models.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Informations publiques d'un utilisateur")
public class UserResponseDto {

  @Schema(description = "Identifiant unique de l'utilisateur", example = "1")
  private Integer id;

  @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont")
  private String name;

  @Schema(description = "Adresse email de l'utilisateur", example = "jean.dupont@example.com")
  private String email;

  @Schema(description = "Date de création du compte", example = "2026/07/24")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @Schema(description = "Date de dernière modification du compte", example = "2026/07/24")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  public static UserResponseDto fromEntity(UserEntity user) {
    UserResponseDto dto = new UserResponseDto();
    dto.id = user.getId();
    dto.name = user.getName();
    dto.email = user.getEmail();
    dto.createdAt = user.getCreatedAt();
    dto.updatedAt = user.getUpdatedAt();
    return dto;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}