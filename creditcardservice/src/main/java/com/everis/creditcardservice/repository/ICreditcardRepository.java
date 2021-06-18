package com.everis.creditcardservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.creditcardservice.entity.Creditcard;

public interface ICreditcardRepository extends ReactiveMongoRepository<Creditcard, String>{

}
