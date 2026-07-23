package com.chatop.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.RentalCreateRequestDto;
import com.chatop.api.dto.RentalUpdateRequestDto;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.models.RentalEntity;
import com.chatop.api.models.UserEntity;
import com.chatop.api.repositories.RentalRepository;
import com.chatop.api.repositories.UserRepository;

/**
 * Logiques métier liées aux rentals
 */
@Service
public class RentalService {

  private final RentalRepository rentalRepository;
  private final UserRepository userRepository;
  private final PictureStorageService fileStorageService;

  RentalService(RentalRepository rentalRepository, UserRepository userRepository,
      PictureStorageService fileStorageService) {
    this.rentalRepository = rentalRepository;
    this.userRepository = userRepository;
    this.fileStorageService = fileStorageService;
  }

  /**
   * Crée et sauvegarde un nouveau rental pour l'user connecté.
   * Stocke l'image sur le serveur, puis construit et persiste l'entité.
   */
  public RentalEntity saveRental(RentalCreateRequestDto rentalCreateRequestDto, Integer userId) {

    // Récupère le propriétaire réel depuis son ID, échoue si inexistant
    UserEntity owner = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

    // Sauvegarde l'image sur le disque et récupère son URL publique
    String pictureUrl = fileStorageService.storeFile(rentalCreateRequestDto.getPicture());

    // Construit l'entité à partir des données reçues et de l'URL de l'image
    RentalEntity rental = new RentalEntity();
    rental.setName(rentalCreateRequestDto.getName());
    rental.setSurface(rentalCreateRequestDto.getSurface());
    rental.setPrice(rentalCreateRequestDto.getPrice());
    rental.setDescription(rentalCreateRequestDto.getDescription());
    rental.setPicture(pictureUrl);
    rental.setOwner(owner);
    rental.setCreatedAt(LocalDateTime.now());
    rental.setUpdatedAt(LocalDateTime.now());

    return rentalRepository.save(rental);
  }

  /**
   * Update un rental pour l'user connecté.
   */
  public RentalEntity updateRental(Integer rentalId, RentalUpdateRequestDto rentalUpdateRequestDto, Integer userId) {

    // Récupère le rental associé à rentalId, échoue si inexsitant
    RentalEntity rental = rentalRepository.findById(rentalId)
        .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + rentalId));

    // Récupère le propriétaire réel depuis son ID, échoue si inexistant
    UserEntity owner = userRepository.findById(userId)
        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

    // Vérifie si l'user connecté est bien le propriétaire du rental
    if (!rental.getOwner().getId().equals(owner.getId())) {
      throw new AccessDeniedException("You are not allowed to update this rental");
    }

    // Modifie l'entité à partir des données reçues
    if (rentalUpdateRequestDto.getName() != null) {
      rental.setName(rentalUpdateRequestDto.getName());
    }
    if (rentalUpdateRequestDto.getSurface() != null) {
      rental.setSurface(rentalUpdateRequestDto.getSurface());
    }
    if (rentalUpdateRequestDto.getPrice() != null) {
      rental.setPrice(rentalUpdateRequestDto.getPrice());
    }
    if (rentalUpdateRequestDto.getDescription() != null) {
      rental.setDescription(rentalUpdateRequestDto.getDescription());
    }
    if (rentalUpdateRequestDto.getPicture() != null && !rentalUpdateRequestDto.getPicture().isEmpty()) {
      String pictureUrl = fileStorageService.storeFile(rentalUpdateRequestDto.getPicture());
      rental.setPicture(pictureUrl);
    }

    rental.setUpdatedAt(LocalDateTime.now());

    return rentalRepository.save(rental);
  }

  /**
   * Retourne la liste de toutes les rentals enregistrées en base
   */
  public List<RentalEntity> getRentals() {
    return (List<RentalEntity>) rentalRepository.findAll();
  }

  /**
   * Retourne un rental selon son id, échoue si inexistant
   */
  public RentalEntity getRental(Integer rentalId) {
    return rentalRepository.findById(rentalId)
        .orElseThrow(() -> new ResourceNotFoundException("Rental not found: " + rentalId));
  }
}
