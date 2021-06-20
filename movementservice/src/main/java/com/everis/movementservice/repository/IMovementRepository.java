package com.everis.movementservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.movementservice.entity.Movement;

@Repository
public interface IMovementRepository extends ReactiveMongoRepository<Movement, String> {

}
