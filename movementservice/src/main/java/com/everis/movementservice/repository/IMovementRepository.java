package com.everis.movementservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.movementservice.entity.Movement;

import reactor.core.publisher.Flux;

@Repository
public interface IMovementRepository extends ReactiveMongoRepository<Movement, String> {

	public Flux<Movement> findByNumDoc(String numdoc);
}
