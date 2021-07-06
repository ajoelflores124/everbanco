package com.everis.transactionservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Arrays;

import com.everis.transactionservice.entity.DebitAssociation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DatosDebitAssociation {

 
	
	public final static Flux<DebitAssociation> DEBIT_ASSOCIATIONS= Flux.just(
			new DebitAssociation("12345678","1234-2222-3333-4444","10001",true,new Date(),1),
			new DebitAssociation("10101010","1234-2222-3333-5555","10002",true,new Date(),1)
			);
	
	public final static Mono<DebitAssociation> DEBIT_ASSOCIATION= Mono.just(
			new DebitAssociation("12345678","1234-2222-3333-4444","10001",true,new Date(),1)
			);
	
	public final static Mono<DebitAssociation> NEW_MONO_DEBIT_ASSOCIATION= Mono.just(
			new DebitAssociation("20202020","2020-3030-4040-5050","10003",true,new Date(),1)
			);
	
	public final static DebitAssociation NEW_DEBIT_ASSOCIATION=  new DebitAssociation("20202020","2020-3030-4040-5050","10003",true,new Date(),1);
	
}
