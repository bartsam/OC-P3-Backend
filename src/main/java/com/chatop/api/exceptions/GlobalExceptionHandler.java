package com.chatop.api.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Centralise la gestion des erreurs métier pour tous les controllers REST.
 * Ne gère pas les 401 liées au token JWT (catché par oauth2ResourceServer),
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Mauvais credentials lors du login → 401
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Map<String, String>> handleBadCredentials() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Map.of("message", "error"));
  }

  /**
   * Ressource métier introuvable en base lors de findById().orElseThrow()) → 404
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("message", ex.getMessage()));
  }

  /**
   * Utilisateur authentifié mais non autorisé à effectuer l'action
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, String>> handleAccessDenied() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(Map.of("message", "Access denied"));
  }

  /**
   * Erreur de validation métier levée manuellement dans le code
   * (ex: email déjà utilisé, argument incohérent) → 400
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("message", ex.getMessage()));
  }

  /**
   * Champs invalides ou manquants sur un @RequestBody avec @Valid → 400
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("message", message));
  }

  /**
   * Corps de requête mal formé (JSON invalide, body vide, incompatible) → 400
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleMalformedJson() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("message", "Invalid request body"));
  }

  /**
   * Toute erreur imprévue non gérée explicitement par les autres handlers → 500
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, String>> handleGeneric(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("message", "An internal error has occurred"));
  }

  /**
   * Échec lors du stockage d'un upload (disque plein, chemin invalide) → 500
   */
  @ExceptionHandler(FileStorageException.class)
  public ResponseEntity<Map<String, String>> handleFileStorage(FileStorageException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("message", "Failed to save the file"));
  }
}