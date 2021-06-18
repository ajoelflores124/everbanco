package com.everis.transactionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
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
public class TransactionService {

	
	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	
	@Autowired
	private ITransactionRepository transactionRep;
	
	public Flux<Transaction> getTransactions(){
		return transactionRep.findAll();
	}
	
	public Mono<Transaction> getTransacion(String id){
		return transactionRep.findById(id);
	}
	
	public Mono<Transaction> saveTransaction(Transaction transaction){
		return transactionRep.insert(transaction);
	}
	
	public Mono<Transaction> updateTransaction(Transaction transaction){
		
		return  transactionRep.findById(transaction.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> transactionRep.save(transaction));
	}

	public Mono<Void> deleteTransaction(String id){
		
		return  transactionRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> transactionRep.deleteById(id));
	}
	
	
}
