package com.chatop.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chatop.api.models.RentalEntity;

/**
 * Accès aux données de la table RENTALS
 */
@Repository
public interface RentalRepository extends CrudRepository<RentalEntity, Integer> {

}
