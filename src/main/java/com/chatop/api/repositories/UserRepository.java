package com.chatop.api.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.UserEntity;

/**
 * Accès aux données de la table USERS
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

  /**
   * Recherche un user par email
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Vérifie si un email est déjà utilisé
   */
  boolean existsByEmail(String email);
}
