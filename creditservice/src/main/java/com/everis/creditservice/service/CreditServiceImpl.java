package com.everis.creditservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.creditservice.entity.Credit;
import com.everis.creditservice.exception.EntityNotFoundException;
import com.everis.creditservice.repository.ICreditRepository;
import com.everis.creditservice.webclient.TransactionServiceClient;
import com.everis.creditservice.webclient.model.DebitMovementDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@PropertySource("classpath:application.properties")
@Service
public class CreditServiceImpl implements ICreditService{

	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Value("${url.customer.service}")
	private String urlCustomerService;
	
	@Autowired
	private ICreditRepository creditRep;
	
	@Autowired
	private TransactionServiceClient transactionServiceClient;
	
	private final ReactiveMongoTemplate mongoTemplate;
	
	@Autowired
	public CreditServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	    
	WebClient webClient = WebClient.create(urlCustomerService);
	
	@Override
	public Flux<Credit> findAll() {
		return creditRep.findAll();
	}
	
	@Override
	public Mono<Credit> findEntityById(String id) {
		return creditRep.findById(id);
	}

	@Override
	public Mono<Credit> createEntity(Credit credit) {
	  //verificar si el pago es con debito
	  if(credit.getDebitCardPay()!=null) {
		  
		  DebitMovementDTO debitM= new DebitMovementDTO();
		  debitM.setCardNumDebit(credit.getDebitCardPay());
		  debitM.setDesMov(credit.getDescription());
		  debitM.setTypeOper("Pago");
		  debitM.setAmountMov( credit.getAmountPay() );
		  
		  transactionServiceClient.updateBalanceAccountsByCardDebitDet(debitM).subscribe();
	  }
		
	   credit.setDatePay(new Date());
	   return creditRep.insert(credit);
	}

	@Override
	public Mono<Credit> updateEntity(Credit credit) {
		return  creditRep.findById(credit.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditRep.save(credit));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return  creditRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditRep.deleteById(id));
	}

	//Consultar todos los movimientos e un producto bancario que tiene un cliente 
	@Override
	public Flux<Credit> getCredits(String numdoc) {
		return creditRep.findByNumDoc(numdoc);
	}
	
	
}
