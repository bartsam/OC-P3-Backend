package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Données nécessaires à l'envoi d'un message concernant une location")
public class MessageRequestDto {

  @Schema(description = "Contenu du message", example = "Bonjour, ce logement est-il disponible ?")
  @NotNull(message = "Message required")
  private String message;

  @Schema(description = "Identifiant de la location concernée par le message", example = "5")
  @NotNull(message = "Rental required")
  @JsonProperty("rental_id")
  private Integer rentalId;

  @Schema(description = "Date de création du message", example = "2026/07/24")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @Schema(description = "Date de dernière modification du message", example = "2026/07/24")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getRentalId() {
    return rentalId;
  }

  public void setRentalId(Integer rentalId) {
    this.rentalId = rentalId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

}