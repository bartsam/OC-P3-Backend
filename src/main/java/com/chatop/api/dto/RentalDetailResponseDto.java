package com.chatop.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Détail d'une location, incluant les métadonnées de création et du propriétaire")
public class RentalDetailResponseDto {

  @Schema(description = "Identifiant unique de la location", example = "1")
  private Integer id;

  @Schema(description = "Nom de la location", example = "Appartement centre-ville")
  private String name;

  @Schema(description = "Surface en mètres carrés", example = "45")
  private Integer surface;

  @Schema(description = "Prix de la location en euros", example = "850")
  private Integer price;

  @ArraySchema(schema = @Schema(description = "URL de l'image de la location", example = "/uploads/abc123.jpg"))
  private List<String> picture;

  @Schema(description = "Description détaillée de la location", example = "Appartement lumineux avec balcon")
  private String description;

  @Schema(description = "Identifiant du propriétaire de la location", example = "3")
  @JsonProperty("owner_id")
  private Integer ownerId;

  @Schema(description = "Date de création de l'annonce", example = "2026/07/24")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @Schema(description = "Date de dernière modification de l'annonce", example = "2026/07/24")
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSurface() {
    return surface;
  }

  public void setSurface(Integer surface) {
    this.surface = surface;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public List<String> getPicture() {
    return picture;
  }

  public void setPicture(List<String> picture) {
    this.picture = picture;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
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