package com.chatop.api.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Données pour la mise à jour d'une location existante. Tous les champs sont optionnels : seuls ceux fournis seront modifiés.")
public class RentalUpdateRequestDto {

  @Schema(description = "Nom de la location", example = "Appartement centre-ville")
  private String name;

  @Schema(description = "Surface en mètres carrés", example = "50")
  private Integer surface;

  @Schema(description = "Prix de la location en euros", example = "900")
  private Integer price;

  @Schema(description = "Description détaillée de la location", example = "Appartement avec cuisine rénové")
  private String description;

  @Schema(description = "Nouvelle image de la location au format multipart", type = "string", format = "binary")
  private MultipartFile picture;

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MultipartFile getPicture() {
    return picture;
  }

  public void setPicture(MultipartFile picture) {
    this.picture = picture;
  }

}