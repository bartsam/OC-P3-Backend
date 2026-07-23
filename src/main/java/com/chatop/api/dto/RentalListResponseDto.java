package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.chatop.api.models.RentalEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RentalListResponseDto {

  private Integer id;

  private String name;

  private Integer surface;

  private Integer price;

  private String picture;

  private String description;

  @JsonProperty("owner_id")
  private Integer ownerId;

  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("created_at")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("updated_at")
  private LocalDateTime updatedAt;

  public static RentalListResponseDto fromEntity(RentalEntity rental) {
    RentalListResponseDto dto = new RentalListResponseDto();
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
}