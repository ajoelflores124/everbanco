package com.everis.creditservice.service;

import com.everis.creditservice.entity.Credit;

import reactor.core.publisher.Flux;

public interface ICreditService extends IMaintanceService<Credit>{

	public Flux<Credit> getCredits(String numdoc);
}
