package com.everis.transactionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.exception.EntityNotFoundException;
import com.everis.transactionservice.repository.ITransactionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Angel
 *
 */
@PropertySource("classpath:application.properties")
@Service
public class TransactionServiceImpl implements ITransactionService {

	
	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Value("${url.customer.service}")
	private String urlCustomerService;
	
	
	@Autowired
	private ITransactionRepository transactionRep;
	private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public TransactionServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    WebClient webClient = WebClient.create(urlCustomerService);
	
	@Override
	public Flux<Transaction> findAll() {
		return transactionRep.findAll();
	}

	@Override
	public Mono<Transaction> findEntityById(String id) {
		return transactionRep.findById(id);
	}

	@Override
	public Mono<Transaction> createEntity(Transaction transaction) {
	   return transactionRep.insert(transaction);
	}

	@Override
	public Mono<Transaction> updateEntity(Transaction transaction) {
		return  transactionRep.findById(transaction.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> transactionRep.save(transaction));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return  transactionRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> transactionRep.deleteById(id));
	}
	
	
}
