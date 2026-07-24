package com.chatop.api.exceptions;

/**
 * Exception levée en cas d'échec lors du stockage physique d'un fichier
 * (erreur disque, chemin invalide, path-traversal détecté).
 */
public class PictureStorageException extends RuntimeException {

  public PictureStorageException(String message) {
    super(message);
  }

  public PictureStorageException(String message, Throwable cause) {
    super(message, cause);
  }
}