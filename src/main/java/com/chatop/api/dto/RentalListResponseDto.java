package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Résumé d'une location, utilisé dans la liste de toutes les locations")
public class RentalListResponseDto {

  @Schema(description = "Identifiant unique de la location", example = "1")
  private Integer id;

  @Schema(description = "Nom de la location", example = "Appartement centre-ville")
  private String name;

  @Schema(description = "Surface en mètres carrés", example = "45")
  private Integer surface;

  @Schema(description = "Prix de la location en euros", example = "850")
  private Integer price;

  @Schema(description = "URL de l'image principale de la location", example = "/uploads/abc123.jpg")
  private String picture;

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

  public String getName() {
    return name;
  }

  public Integer getSurface() {
    return surface;
  }

  public Integer getPrice() {
    return price;
  }

  public String getPicture() {
    return picture;
  }

  public String getDescription() {
    return description;
  }

  public Integer getOwnerId() {
    return ownerId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSurface(Integer surface) {
    this.surface = surface;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}