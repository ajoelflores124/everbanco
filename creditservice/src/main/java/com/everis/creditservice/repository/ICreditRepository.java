package com.everis.creditservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.creditservice.entity.Credit;
import reactor.core.publisher.Flux;

public interface ICreditRepository extends ReactiveMongoRepository<Credit, String>{

	public Flux<Credit> findByNumDoc(String numdoc);
}
