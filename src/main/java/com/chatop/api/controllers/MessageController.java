package com.chatop.api.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.MessageRequestDto;
import com.chatop.api.services.MessageService;
import com.chatop.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Gère la création de messages
 */
@RestController
@Tag(name = "Messages", description = "Envoi de messages sur une location")
public class MessageController {

  private final UserService userService;
  private final MessageService messageService;

  public MessageController(UserService userService, MessageService messageService) {
    this.userService = userService;
    this.messageService = messageService;
  }

  /**
   * Crée un nouveau message associé à l'utilisateur actuellement connecté.
   */
  @Operation(summary = "Envoie un message", description = "Crée un message lié à l'utilisateur authentifié (identifié via le token JWT).", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Message envoyé avec succès"),
      @ApiResponse(responseCode = "400", description = "Données du message invalides"),
      @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré")
  })
  @PostMapping("/api/messages")
  public ResponseEntity<Map<String, String>> createMessage(
      @Valid @RequestBody MessageRequestDto messageRequestDto,
      Authentication authentication) {

    Integer currentUserId = userService.getIdByEmail(authentication.getName());
    messageService.createMessage(messageRequestDto, currentUserId);

    return ResponseEntity.ok(Map.of("message", "Message send with success"));
  }

}