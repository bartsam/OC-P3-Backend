package com.chatop.api.dto;

import java.time.LocalDateTime;

import com.chatop.api.models.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponseDto {

  private int id;
  private String name;
  private String email;
  @JsonFormat(pattern = "yyyy/MM/dd")
  @JsonProperty("created_at")
  private LocalDateTime createdAt;
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

  public int getId() {
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