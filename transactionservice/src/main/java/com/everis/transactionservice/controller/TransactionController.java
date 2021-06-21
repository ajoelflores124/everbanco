package com.everis.transactionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.service.ITransactionService;
import com.everis.transactionservice.service.TransactionServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Angel
 *
 */
@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {
	
	@Autowired
	private ITransactionService transactionService;
	
	@GetMapping
	public Flux<Transaction> getTransactions(){
		return transactionService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Transaction> getTransaction(@PathVariable String id){
		return transactionService.findEntityById(id);
	}
	
	@PostMapping
	public Mono<Transaction> saveProduct(@RequestBody Transaction transactionMono) throws Exception{
		return transactionService.createEntity(transactionMono);
	}
	
	@PutMapping
	public Mono<Transaction> updateTransaction(@RequestBody Transaction transactionMono){
		return transactionService.updateEntity(transactionMono);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteTransaction(@PathVariable String id){
		return transactionService.deleteEntity(id);
	}

}
