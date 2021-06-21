package com.everis.creditcardservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.creditcardservice.entity.Creditcard;

import reactor.core.publisher.Flux;

public interface ICreditcardRepository extends ReactiveMongoRepository<Creditcard, String>{

	public Flux<Creditcard> findByNumDoc(String numdoc);
}
