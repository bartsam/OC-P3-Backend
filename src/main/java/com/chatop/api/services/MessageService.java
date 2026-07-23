package com.chatop.api.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.MessageRequestDto;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.models.MessageEntity;
import com.chatop.api.models.RentalEntity;
import com.chatop.api.models.UserEntity;
import com.chatop.api.repositories.MessageRepository;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.repositories.UserRepository;

/**
 * Logiques métier liées aux messages
 */
@Service
public class MessageService {

  private final UserRepository userRepository;
  private final MessageRepository messageRepository;
  private final RentalRepository rentalRepository;

  public MessageService(UserRepository userRepository, MessageRepository messageRepository,
      RentalRepository rentalRepository) {
    this.userRepository = userRepository;
    this.messageRepository = messageRepository;
    this.rentalRepository = rentalRepository;
  }

  /**
   * Crée et sauvegarde un nouveau message pour l'user connecté.
   */
  public MessageEntity saveMessage(MessageRequestDto messageRequestDto, Integer userId) {

    // Récupère le propriétaire réel depuis son ID, échoue si inexistant
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

    // Récupère le rental associé à rental_id, échoue si inexsitant
    RentalEntity rental = rentalRepository.findById(messageRequestDto.getRentalId())
        .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + messageRequestDto.getRentalId()));

    // Construit l'entité à partir des données reçues
    MessageEntity message = new MessageEntity();
    message.setMessage(messageRequestDto.getMessage());
    message.setRental(rental);
    message.setUser(user);
    message.setCreatedAt(LocalDateTime.now());
    message.setUpdatedAt(LocalDateTime.now());

    return messageRepository.save(message);
  }

}
