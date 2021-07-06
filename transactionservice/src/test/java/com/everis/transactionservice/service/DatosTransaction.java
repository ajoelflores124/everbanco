package com.everis.transactionservice.service;

import java.util.Date;

import com.everis.transactionservice.dto.BalanceMainDTO;
import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.entity.Representative;
import com.everis.transactionservice.entity.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DatosTransaction {

	public final static Representative[] DATA_REP= {new Representative("30303030","Hugo","Campos","Firmante"),
			new Representative("40404040","Carlos","Dias Campos","Firmante")}; 
	
	public final static Transaction DATA_TRA= new Transaction();
	
	public final static Mono<Transaction> TRANSACTION= Mono.just(
			new Transaction()
			);
	
	public final static Flux<ResumeDTO> RESUME = Flux.just( new ResumeDTO() );
	
	public final static Mono<BalanceMainDTO> BALANCE_MAIN= Mono.just(new BalanceMainDTO());
	
}
