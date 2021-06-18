package com.everis.creditcardservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.everis.creditcardservice.entity.Creditcard;
import com.everis.creditcardservice.exception.EntityNotFoundException;
import com.everis.creditcardservice.repository.ICreditcardRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Service
public class CreditcardService {

	private String msgNotFound;
	@Autowired
	private ICreditcardRepository creditcardRep;
	
	public Flux<Creditcard> getCreditcards(){
		return creditcardRep.findAll();
	}
	
	public Mono<Creditcard> getCreditcard(String id){
		return creditcardRep.findById(id);
	}
	
	public Mono<Creditcard> saveCreditcard(Creditcard creditcard){
		return creditcardRep.insert(creditcard);
	}
	
	public Mono<Creditcard> updateCreditcard(Creditcard creditcard){
		
		return  creditcardRep.findById(creditcard.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditcardRep.save(creditcard));
	}

	public Mono<Void> deleteCreditcard(String id){
		
		return  creditcardRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditcardRep.deleteById(id));
	}
	
}
