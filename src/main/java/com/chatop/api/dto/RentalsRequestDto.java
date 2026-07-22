package com.chatop.api.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class RentalsRequestDto {

  @NotNull(message = "Name required")
  private String name;
  @NotNull(message = "Surface required")
  private Integer surface;
  @NotNull(message = "Price required")
  private Integer price;
  @NotNull(message = "Description required")
  private String description;
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
