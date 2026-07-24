package com.chatop.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.LoginDto;
import com.chatop.api.dto.RegisterDto;
import com.chatop.api.dto.TokenResponseDto;
import com.chatop.api.dto.UserResponseDto;
import com.chatop.api.models.UserEntity;
import com.chatop.api.services.JWTService;
import com.chatop.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Gère l'inscription, la connexion et la lecture de l'user connecté
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Inscription, connexion et gestion du profil de l'utilisateur connecté")
public class AuthController {

  private final JWTService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  public AuthController(JWTService jwtService, AuthenticationManager authenticationManager, UserService userService) {
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.userService = userService;
  }

  /**
   * Crée un nouveau user, puis authentifie immédiatement
   * pour renvoyer un token JWT.
   */
  @Operation(summary = "Inscription d'un nouvel utilisateur", description = "Crée un compte utilisateur puis l'authentifie immédiatement. Retourne un token JWT utilisable pour les appels authentifiés.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Compte créé et token JWT renvoyé"),
      @ApiResponse(responseCode = "400", description = "Données invalides (email déjà utilisé, format incorrect, etc.)")
  })
  @SecurityRequirements // route publique : pas de token requis pour s'inscrire
  @PostMapping("/register")
  public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterDto registerDto) {

    // Persiste user en base (email vérifié + password hashé dans UserService)
    userService.createUser(registerDto.getName(), registerDto.getEmail(), registerDto.getPassword());

    // Authentifie l'user avec les mêmes credentials
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(registerDto.getEmail(), registerDto.getPassword()));

    // Génère un JWT à partir de l'authentication validée (subject = email)
    String token = jwtService.generateToken(authentication);
    return ResponseEntity.ok(new TokenResponseDto(token));
  }

  /**
   * Authentifie un user existant et renvoie un token JWT.
   */
  @Operation(summary = "Connexion d'un utilisateur", description = "Vérifie l'email et le mot de passe fournis, puis retourne un token JWT en cas de succès.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Connexion réussie, token JWT renvoyé"),
      @ApiResponse(responseCode = "401", description = "Email ou mot de passe incorrect")
  })
  @SecurityRequirements // route publique : pas de token requis pour se connecter
  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(@RequestBody LoginDto loginDto) {

    // Authentifie l'user en comparant email + password avec les données en base
    // Lève 401 BadCredentialsException si incorrect
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

    // Génère un JWT à partir de l'authentication validée (subject = email)
    String token = jwtService.generateToken(authentication);
    return ResponseEntity.ok(new TokenResponseDto(token));
  }

  /**
   * Renvoie les informations de l'user actuellement connecté,
   * identifié à partir du token JWT (Authentication.getName() = email).
   */
  @Operation(summary = "Récupère le profil de l'utilisateur connecté", description = "Retourne les informations de l'utilisateur associé au token JWT fourni dans l'en-tête Authorization.", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Profil utilisateur renvoyé"),
      @ApiResponse(responseCode = "401", description = "Token JWT manquant, invalide ou expiré")
  })
  @GetMapping("/me")
  public ResponseEntity<UserResponseDto> me(Authentication authentication) {

    // Authentication validé à partir du token envoyé dans le header
    UserEntity user = userService.getByEmail(authentication.getName());
    return ResponseEntity.ok(UserResponseDto.fromEntity(user));
  }
}