package com.chatop.api.services;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatop.api.models.UserEntity;
import com.chatop.api.repositories.UserRepository;

/**
 * Charge les utilisateurs depuis la base MySQL pour l'authentification
 * Spring Security. Utilisé par l'AuthenticationManager lors du login.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Recherche un utilisateur par email (utilisé comme "username")
   * et construit un UserDetails à partir des données stockées en base.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    UserEntity userEntity = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

    return new User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
  }
}