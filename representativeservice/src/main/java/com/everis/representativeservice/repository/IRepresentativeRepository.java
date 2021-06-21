package com.everis.representativeservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.everis.representativeservice.entity.Representative;

import reactor.core.publisher.Mono;

@Repository
public interface IRepresentativeRepository extends ReactiveMongoRepository<Representative, String> {
	
	
	Mono<Representative> findByNumDocRep(String numDocRep);
}
