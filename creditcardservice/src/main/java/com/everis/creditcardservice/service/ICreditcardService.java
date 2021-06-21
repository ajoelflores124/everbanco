package com.everis.creditcardservice.service;

import com.everis.creditcardservice.entity.Creditcard;

import reactor.core.publisher.Flux;

public interface ICreditcardService extends IMaintanceService<Creditcard>{
	
	public Flux<Creditcard> getCreditcards(String numdoc);
}
