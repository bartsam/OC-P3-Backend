package com.chatop.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.chatop.api.models.RentalEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RentalDetailResponseDto {

  private Integer id;

  private String name;

  private Integer surface;

  private Integer price;

  private List<String> picture;

  private String description;

  @JsonProperty("owner_id")
  private Integer ownerId;

  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  public static RentalDetailResponseDto fromEntity(RentalEntity rental) {
    RentalDetailResponseDto dto = new RentalDetailResponseDto();
    dto.id = rental.getId();
    dto.name = rental.getName();
    dto.surface = rental.getSurface();
    dto.price = rental.getPrice();
    dto.description = rental.getDescription();
    dto.picture = rental.getPicture() != null ? List.of(rental.getPicture()) : List.of();
    dto.ownerId = rental.getOwner() != null ? rental.getOwner().getId() : null;
    dto.createdAt = rental.getCreatedAt();
    dto.updatedAt = rental.getUpdatedAt();
    return dto;
  }

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