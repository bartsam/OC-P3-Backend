package com.chatop.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.RentalsRequestDto;
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
  public RentalEntity saveRental(RentalsRequestDto rentalRequestDto, int userId) {

    // Récupère le propriétaire réel depuis son ID, échoue si inexistant
    UserEntity ownerId = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Sauvegarde l'image sur le disque et récupère son URL publique
    String pictureUrl = fileStorageService.storeFile(rentalRequestDto.getPicture());

    // Construit l'entité à partir des données reçues et de l'URL de l'image
    RentalEntity rental = new RentalEntity();
    rental.setName(rentalRequestDto.getName());
    rental.setSurface(rentalRequestDto.getSurface());
    rental.setPrice(rentalRequestDto.getPrice());
    rental.setDescription(rentalRequestDto.getDescription());
    rental.setPicture(pictureUrl);
    rental.setOwner(ownerId);
    rental.setCreatedAt(LocalDateTime.now());
    rental.setUpdatedAt(LocalDateTime.now());

    return rentalRepository.save(rental);
  }

  /**
   * Retourne la liste de toutes les rentals enregistrées en base
   */
  public List<RentalEntity> getRentals() {
    return (List<RentalEntity>) rentalRepository.findAll();
  }

}
