package com.everis.creditservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.creditservice.entity.Credit;

public interface ICreditRepository extends ReactiveMongoRepository<Credit, String>{

}
