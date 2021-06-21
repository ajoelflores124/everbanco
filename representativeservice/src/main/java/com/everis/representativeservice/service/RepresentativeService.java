package com.everis.representativeservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.everis.representativeservice.entity.Representative;
import com.everis.representativeservice.repository.IRepresentativeRepository;
import com.everis.representativeservice.exception.EntityNotFoundException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RepresentativeService {

	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Autowired
	IRepresentativeRepository representativeRep;
	
	public Flux<Representative> getRepresentatives(){
		return representativeRep.findAll();
	}
	
	public Mono<Representative> getRepresentative(String id){
		return representativeRep.findById(id);
	}
	
	public Mono<Representative> saveRepresentative(Representative representative){
		return representativeRep.insert(representative);
	}
	
	public Mono<Representative> updateRepresentative(Representative representative){
		
		return representativeRep.findById( representative.getId())
			.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
			.flatMap(item-> representativeRep.save(representative) );
	}
	
	public Mono<Void> deleteRepresentative(String id){
		
		return representativeRep.findById(id)
				.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				.flatMap(item-> representativeRep.deleteById(id) );
		
	}
	
	public Mono<Representative> getRepresentativeByNumDocRep(String numDocRep){
		return representativeRep.findByNumDocRep(numDocRep)
				.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ));
	}
	
}
