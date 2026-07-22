package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.chatop.api.models.RentalEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RentalsResponseDto {
  private int id;
  private String name;
  private Integer surface;
  private Integer price;
  private String picture;
  private String description;
  @JsonProperty("owner_id")
  private int ownerId;
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("created_at")
  private LocalDateTime createdAt;
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  public static RentalsResponseDto fromEntity(RentalEntity rental) {
    RentalsResponseDto dto = new RentalsResponseDto();
    dto.id = rental.getId();
    dto.name = rental.getName();
    dto.surface = rental.getSurface();
    dto.price = rental.getPrice();
    dto.picture = rental.getPicture();
    dto.description = rental.getDescription();
    dto.ownerId = rental.getOwner().getId();
    dto.createdAt = rental.getCreatedAt();
    dto.updatedAt = rental.getUpdatedAt();
    return dto;
  }

  public int getId() {
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

  public int getOwnerId() {
    return ownerId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }
}