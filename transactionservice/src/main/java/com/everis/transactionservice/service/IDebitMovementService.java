package com.everis.transactionservice.service;

import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.entity.DebitMovement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IDebitMovementService extends IMaintenanceService<DebitMovement>  {

	public Flux<DebitMovement> listTop10DebitMovement(String numDebit);
}
