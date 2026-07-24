package com.chatop.api.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Données nécessaires à la création d'une location")
public class RentalCreateRequestDto {

  @Schema(description = "Nom de la location", example = "Appartement centre-ville")
  @NotNull(message = "Name required")
  private String name;

  @Schema(description = "Surface en mètres carrés", example = "45")
  @NotNull(message = "Surface required")
  private Integer surface;

  @Schema(description = "Prix de la location en euros", example = "850")
  @NotNull(message = "Price required")
  private Integer price;

  @Schema(description = "Description détaillée de la location", example = "Appartement lumineux avec balcon")
  @NotNull(message = "Description required")
  private String description;

  @Schema(description = "Image de la location au format multipart", type = "string", format = "binary")
  @NotNull(message = "Picture required")
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
