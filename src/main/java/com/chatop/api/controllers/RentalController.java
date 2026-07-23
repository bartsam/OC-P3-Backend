package com.chatop.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.RentalCreateRequestDto;
import com.chatop.api.dto.RentalUpdateRequestDto;
import com.chatop.api.dto.RentalsResponseDto;
import com.chatop.api.models.RentalEntity;
import com.chatop.api.models.UserEntity;
import com.chatop.api.services.RentalService;
import com.chatop.api.services.UserService;

import jakarta.validation.Valid;

/**
 * Gère la création, l'édition et l'affichage des rentals
 */
@RestController
@RequestMapping("/api")
public class RentalController {

  private final RentalService rentalService;
  private final UserService userService;

  public RentalController(RentalService rentalService, UserService userService) {
    this.rentalService = rentalService;
    this.userService = userService;
  }

  /**
   * Crée un nouveau rental associé à l'user connecté
   */
  @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> createRental(
      @Valid @ModelAttribute RentalCreateRequestDto rentalCreateRequestDto,
      Authentication authentication) {

    UserEntity currentUser = userService.getByEmail(authentication.getName());
    rentalService.saveRental(rentalCreateRequestDto, currentUser.getId());

    return ResponseEntity.ok(Map.of("message", "Rental created !"));
  }

  /**
   * Update un rental associé à l'user connecté
   */
  @PutMapping(value = "/rentals/{rentalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> updateRental(
      @PathVariable Integer rentalId,
      @Valid @ModelAttribute RentalUpdateRequestDto rentalUpdateRequestDto,
      Authentication authentication) {

    UserEntity currentUser = userService.getByEmail(authentication.getName());
    rentalService.updateRental(rentalId, rentalUpdateRequestDto, currentUser.getId());

    return ResponseEntity.ok(Map.of("message", "Rental updated !"));
  }

  /**
   * Retourne la liste de tous les rentals
   */
  @GetMapping("/rentals")
  public ResponseEntity<Map<String, List<RentalsResponseDto>>> listRentals() {

    List<RentalEntity> rentals = rentalService.getRentals();

    List<RentalsResponseDto> rentalDtos = rentals.stream()
        // .map(entity -> RentalsResponseDto.fromEntity(entity))
        .map(RentalsResponseDto::fromEntity)
        .toList();

    return ResponseEntity.ok(Map.of("rentals", rentalDtos));
  }

}
