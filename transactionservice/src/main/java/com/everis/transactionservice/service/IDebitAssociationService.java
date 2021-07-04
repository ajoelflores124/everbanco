package com.everis.transactionservice.service;

import com.everis.transactionservice.entity.DebitAssociation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IDebitAssociationService extends IMaintenanceService<DebitAssociation>  {

	public Mono<DebitAssociation> findAccountMainByCardDebit(String cardDebit);
	public Flux<DebitAssociation> listAccountsByCardDebit(String cardDebit);
	
}
