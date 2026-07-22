package com.chatop.api.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.models.UserEntity;
import com.chatop.api.repositories.UserRepository;

/**
 * Logiques métier liées aux users
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Crée un nouveau user en base
   * Lève une exception si email existant
   * Mot de passe encodé avec BCrypt
   */
  public UserEntity register(String name, String email, String rawPassword) {
    if (userRepository.existsByEmail(email)) {
      throw new IllegalArgumentException("Email already use");
    }

    UserEntity user = new UserEntity();
    user.setName(name);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(rawPassword));

    return userRepository.save(user);
  }

  /**
   * Récupère un user par email
   * Lève une exception si aucun user correspond
   */
  public UserEntity getByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }
}