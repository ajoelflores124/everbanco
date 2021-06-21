package com.everis.creditcardservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.creditcardservice.entity.Creditcard;
import com.everis.creditcardservice.exception.EntityNotFoundException;
import com.everis.creditcardservice.repository.ICreditcardRepository;

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
	
	//Consultar todos los movimientos e un producto bancario que tiene un cliente 
	@Override
	public Flux<Creditcard> getCreditcards(String numdoc) {
		// TODO Auto-generated method stub
		return creditcardRep.findByNumDoc(numdoc);
	}
	
}
