package com.everis.transactionservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.transactionservice.entity.Transaction;

@Repository
public interface ITransactionRepository extends ReactiveMongoRepository<Transaction, String> {
	
	
	

}
