package com.everis.creditcardservice.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.creditcardservice.entity.Creditcard;
import com.everis.creditcardservice.exception.EntityNotFoundException;
import com.everis.creditcardservice.repository.ICreditcardRepository;
import com.everis.creditcardservice.webclient.TransactionServiceClient;
import com.everis.creditcardservice.webclient.model.DebitMovementDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Service
public class CreditcardServiceImpl implements ICreditcardService{

	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Value("${url.customer.service}")
	private String urlCustomerService;
	
	@Autowired
	private ICreditcardRepository creditcardRep;
	
	@Autowired
	private TransactionServiceClient transactionServiceClient;
	
	private final ReactiveMongoTemplate mongoTemplate;
	
	@Autowired
	public CreditcardServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	    
	WebClient webClient = WebClient.create(urlCustomerService);
	
	@Override
	public Flux<Creditcard> findAll() {
		return creditcardRep.findAll();
	}
	
	@Override
	public Mono<Creditcard> findEntityById(String id) {
		return creditcardRep.findById(id);
	}

	@Override
	public Mono<Creditcard> createEntity(Creditcard creditcard) {
		
	   if(creditcard.getDebitCardPay()!=null && creditcard.getTypeOpe().equalsIgnoreCase("Pago") ) {
		DebitMovementDTO debit= new DebitMovementDTO();
		debit.setAmountMov(creditcard.getAmountOpe());
		debit.setDesMov(creditcard.getDescription());
		debit.setCardNumDebit(creditcard.getDebitCardPay());
		debit.setTypeOper( creditcard.getTypeOpe());
		transactionServiceClient.updateBalanceAccountsByCardDebitDet(debit).subscribe();   
	   }
	   creditcard.setDateOpe( new Date() );
	   return creditcardRep.insert(creditcard);
	}

	@Override
	public Mono<Creditcard> updateEntity(Creditcard creditcard) {
		return  creditcardRep.findById(creditcard.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditcardRep.save(creditcard));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return  creditcardRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> creditcardRep.deleteById(id));
	}
	
	
	/**
	 *Consultar todos los movimientos e un producto bancario que tiene un cliente
	 */
	@Override
	public Flux<Creditcard> getCreditcards(String numdoc) {
		return creditcardRep.findByNumDoc(numdoc);
	}

	
	/**
	 * Lista los 10 ultimos movimietos de la tarjeta de credito por num doc
	 */
	@Override
	public Flux<Creditcard> listTop10MovementCredCard(String numdoc) {
		return creditcardRep.findTop10ByNumDocOrderByDateOpeDesc(numdoc);
	}
	
	
	
}
