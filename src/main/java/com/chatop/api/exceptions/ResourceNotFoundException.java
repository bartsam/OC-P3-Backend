package com.chatop.api.exceptions;

/**
 * Exception levée quand une ressource métier n'est pas trouvée en BDD
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}