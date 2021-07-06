package com.everis.transactionservice.service;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.entity.DebitMovement;

import reactor.core.publisher.Flux;

public class DatosDebitMovement {

	public final static Flux<DebitMovement> DEBIT_MOVEMENTS= Flux.just(
			new DebitMovement("12345678","1234-2222-3333-1111","desc mov 1","Pago",1000.0,new Date(),1),
			new DebitMovement("10101010","1234-2222-3333-1111","desc mov 2","Pago",2000.0,new Date(),1),
			new DebitMovement("20202020","1234-2222-3333-1111","desc mov 3","Pago",3000.0,new Date(),1),
			new DebitMovement("30303030","1234-2222-3333-1111","desc mov 4","Pago",4000.0,new Date(),1),
			new DebitMovement("40404040","1234-2222-3333-1111","desc mov 5","Pago",5000.0,new Date(),1),
			new DebitMovement("50505055","1234-2222-3333-1111","desc mov 6","Pago",6000.0,new Date(),1),
			new DebitMovement("60606660","1234-2222-3333-1111","desc mov 7","Pago",7000.0,new Date(),1),
			new DebitMovement("70707070","1234-2222-3333-1111","desc mov 8","Pago",8000.0,new Date(),1),
			new DebitMovement("80808088","1234-2222-3333-1111","desc mov 9","Pago",9000.0,new Date(),1),
			new DebitMovement("90909090","1234-2222-3333-1111","desc mov 10","Pago",10000.0,new Date(),1)
			);

}
