package com.everis.representativeservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.everis.representativeservice.entity.Representative;

@Repository
public interface IRepresentativeRepository extends ReactiveMongoRepository<Representative, String> {
	
	
}
