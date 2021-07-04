package com.everis.transactionservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.entity.DebitMovement;
import com.everis.transactionservice.entity.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IDebitMovementRepository extends ReactiveMongoRepository<DebitMovement, String> {
	
	
	public Flux<DebitMovement> findTop10ByCardNumDebitOrderByDateMovDesc(String numDebit);
}
