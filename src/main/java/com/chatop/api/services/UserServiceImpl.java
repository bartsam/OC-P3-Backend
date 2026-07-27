package com.chatop.api.services;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.UserResponseDto;
import com.chatop.api.exceptions.ResourceNotFoundException;
import com.chatop.api.models.UserEntity;
import com.chatop.api.repositories.UserRepository;

/**
 * Logiques métier liées aux users
 */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Crée un nouveau user en base
   * Lève une exception si email existant
   * Mot de passe encodé avec BCrypt
   * createdAt et updatedAt initialisés à la date courante
   */
  @Override
  public void createUser(String name, String email, String rawPassword) {
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already use");
    }

    LocalDateTime now = LocalDateTime.now();

    UserEntity user = new UserEntity();
    user.setName(name);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(rawPassword));
    user.setCreatedAt(now);
    user.setUpdatedAt(now);

    userRepository.save(user);
  }

  /**
   * Récupère un user par email
   * Lève une exception si aucun user correspond
   */
  @Override
  public UserResponseDto getByEmail(String email) {

    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return toDto(user);
  }

  /**
   * Récupère un user par id
   * Lève une exception si aucun user correspond
   */
  @Override
  public UserResponseDto getById(Integer id) {

    UserEntity user = userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return toDto(user);
  }

  /**
   * Retourne le user id
   * Lève une exception si aucun user correspond
   */
  @Override
  public Integer getIdByEmail(String email) {
    UserEntity user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

    return user.getId();
  }

  private UserResponseDto toDto(UserEntity user) {
    UserResponseDto dto = new UserResponseDto();
    dto.setId(user.getId());
    dto.setName(user.getName());
    dto.setEmail(user.getEmail());
    dto.setCreatedAt(user.getCreatedAt());
    dto.setUpdatedAt(user.getUpdatedAt());
    return dto;
  }
}
