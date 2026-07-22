package com.chatop.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterDto {

  @NotBlank(message = "Name required")
  private String name;

  @NotBlank(message = "Email required")
  @Email(message = "Invalid email format")
  private String email;

  @NotBlank(message = "Password required")
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}