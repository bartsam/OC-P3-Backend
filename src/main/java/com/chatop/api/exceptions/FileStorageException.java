package com.chatop.api.exceptions;

/**
 * Exception levée en cas d'échec lors du stockage physique d'un fichier
 * (erreur disque, chemin invalide, path-traversal détecté).
 */
public class FileStorageException extends RuntimeException {

  public FileStorageException(String message) {
    super(message);
  }

  public FileStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}