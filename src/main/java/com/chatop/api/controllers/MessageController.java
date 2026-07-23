package com.chatop.api.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.MessageRequestDto;
import com.chatop.api.models.UserEntity;
import com.chatop.api.services.MessageService;
import com.chatop.api.services.UserService;

import jakarta.validation.Valid;

/**
 * Gère la création de messages
 */
@RestController
public class MessageController {

  private final UserService userService;
  private final MessageService messageService;

  public MessageController(UserService userService, MessageService messageService) {
    this.userService = userService;
    this.messageService = messageService;
  }

  @PostMapping("/api/messages")
  public ResponseEntity<Map<String, String>> createMessage(
      @Valid @RequestBody MessageRequestDto messageRequestDto,
      Authentication authentication) {

    UserEntity currentUser = userService.getByEmail(authentication.getName());
    messageService.saveMessage(messageRequestDto, currentUser.getId());

    return ResponseEntity.ok(Map.of("message", "Message send with success"));
  }

}
