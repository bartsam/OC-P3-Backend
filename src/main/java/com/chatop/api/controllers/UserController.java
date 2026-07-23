package com.chatop.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.UserResponseDto;
import com.chatop.api.models.UserEntity;
import com.chatop.api.services.UserService;

/**
 * Gère l'affichage des users
 */
@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retourne le détail d'un user selon son id
   */
  @GetMapping("/users/{userId}")
  public ResponseEntity<UserResponseDto> getUserDetail(@PathVariable Integer userId) {

    UserEntity user = userService.getById(userId);
    return ResponseEntity.ok(UserResponseDto.fromEntity(user));

  }

}
