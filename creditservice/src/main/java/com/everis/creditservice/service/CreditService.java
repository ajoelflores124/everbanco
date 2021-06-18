package com.everis.creditservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.everis.creditservice.entity.Credit;
import com.everis.creditservice.exception.EntityNotFoundException;
import com.everis.creditservice.repository.ICreditRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Service
public class CreditService {

private String msgNotFound;
	
	@Autowired
	private ICreditRepository creditRep;
	
	public Flux<Credit> getCredits(){
		return creditRep.findAll();
	}
	
	public Mono<Credit> getCredit(String id){
		return creditRep.findById(id);
	}
	
	public Mono<Credit> saveCredit(Credit credit){
		return creditRep.insert(credit);
	}
	
	public Mono<Credit> updateCredit(Credit credit){
		
		return  creditRep.findById(credit.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditRep.save(credit));
	}

	public Mono<Void> deleteCredit(String id){
		
		return  creditRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditRep.deleteById(id));
	}
}
