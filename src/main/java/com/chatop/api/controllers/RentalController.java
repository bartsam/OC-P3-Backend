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
import com.chatop.api.dto.RentalDetailResponseDto;
import com.chatop.api.dto.RentalListResponseDto;
import com.chatop.api.dto.RentalUpdateRequestDto;
import com.chatop.api.models.RentalEntity;
import com.chatop.api.models.UserEntity;
import com.chatop.api.services.RentalService;
import com.chatop.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Gère la création, l'édition et l'affichage des rentals
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Rentals", description = "Création, modification et consultation des locations")
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
  @Operation(summary = "Crée une nouvelle location", description = "Crée un rental associé à l'utilisateur authentifié. Accepte les données en multipart/form-data (incluant une image).", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Rental créé avec succès"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré")
  })
  @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> createRental(
      @Valid @ModelAttribute RentalCreateRequestDto rentalCreateRequestDto,
      Authentication authentication) {

    UserEntity currentUser = userService.getByEmail(authentication.getName());
    rentalService.createRental(rentalCreateRequestDto, currentUser.getId());

    return ResponseEntity.ok(Map.of("message", "Rental created !"));
  }

  /**
   * Update un rental associé à l'user connecté
   */
  @Operation(summary = "Met à jour une location existante", description = "Modifie un rental appartenant à l'utilisateur authentifié.", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Rental mis à jour avec succès"),
      @ApiResponse(responseCode = "400", description = "Données invalides"),
      @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré"),
      @ApiResponse(responseCode = "404", description = "Rental introuvable")
  })
  @PutMapping(value = "/rentals/{rentalId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> updateRental(
      @Parameter(description = "Identifiant du rental à mettre à jour", example = "1") @PathVariable Integer rentalId,
      @Valid @ModelAttribute RentalUpdateRequestDto rentalUpdateRequestDto,
      Authentication authentication) {

    UserEntity currentUser = userService.getByEmail(authentication.getName());
    rentalService.updateRental(rentalId, rentalUpdateRequestDto, currentUser.getId());

    return ResponseEntity.ok(Map.of("message", "Rental updated !"));
  }

  /**
   * Retourne la liste de tous les rentals
   */
  @Operation(summary = "Liste toutes les locations", description = "Retourne l'ensemble des rentals disponibles.", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Liste des rentals renvoyée avec succès"),
      @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré")
  })
  @GetMapping("/rentals")
  public ResponseEntity<Map<String, List<RentalListResponseDto>>> getRentalList() {

    List<RentalEntity> rentals = rentalService.findAllRentals();

    List<RentalListResponseDto> rentalDtos = rentals.stream()
        // .map(entity -> RentalsResponseDto.fromEntity(entity))
        .map(RentalListResponseDto::fromEntity)
        .toList();

    return ResponseEntity.ok(Map.of("rentals", rentalDtos));
  }

  /**
   * Retourne le détail d'un rental selon son id
   */
  @Operation(summary = "Récupère le détail d'une location", description = "Retourne les informations complètes d'un rental à partir de son identifiant.", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Rental trouvé"),
      @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré"),
      @ApiResponse(responseCode = "404", description = "Rental introuvable")
  })
  @GetMapping("/rentals/{rentalId}")
  public ResponseEntity<RentalDetailResponseDto> getRentalDetail(
      @Parameter(description = "Identifiant du rental à consulter", example = "1") @PathVariable Integer rentalId) {

    RentalEntity rental = rentalService.findRentalById(rentalId);
    RentalDetailResponseDto rentalDto = RentalDetailResponseDto.fromEntity(rental);

    return ResponseEntity.ok(rentalDto);
  }
}