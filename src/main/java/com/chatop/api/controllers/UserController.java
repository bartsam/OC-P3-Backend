package com.chatop.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.UserResponseDto;
import com.chatop.api.models.UserEntity;
import com.chatop.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Gère l'affichage des users
 */
@RestController
@RequestMapping("/api")
@Tag(name = "User", description = "Opérations liées à la consultation d'un utilisateur")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retourne le détail d'un user selon son id
   */
  @Operation(summary = "Récupère le détail d'un utilisateur", description = "Retourne les informations publiques d'un utilisateur à partir de son identifiant. Nécessite un token JWT valide.", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
      @ApiResponse(responseCode = "401", description = "Non authentifié - token JWT manquant ou invalide"),
      @ApiResponse(responseCode = "404", description = "Utilisateur introuvable")
  })
  @GetMapping("/user/{userId}")
  public ResponseEntity<UserResponseDto> getUserDetail(
      @Parameter(description = "Identifiant de l'utilisateur à récupérer", example = "5") @PathVariable Integer userId) {

    UserEntity user = userService.getById(userId);
    return ResponseEntity.ok(UserResponseDto.fromEntity(user));
  }

}