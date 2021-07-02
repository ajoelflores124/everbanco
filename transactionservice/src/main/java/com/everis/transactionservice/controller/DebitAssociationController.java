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

import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.service.IDebitAssociationService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Angel
 *
 */
@RestController
@RequestMapping(path = "/debit-assoc")
public class DebitAssociationController {
	
	
	@Autowired
	private IDebitAssociationService debitAssociationService;
	
	@GetMapping
	public Flux<DebitAssociation> getDebitAssociations(){
		return debitAssociationService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<DebitAssociation> getDebitAssociation(@PathVariable String id){
		return debitAssociationService.findEntityById(id);
	}
	
	@PostMapping
	public Mono<DebitAssociation> saveDebitAssociation(@RequestBody DebitAssociation debitAssociation) throws Exception{
		return debitAssociationService.createEntity(debitAssociation);
	}
	
	@PutMapping
	public Mono<DebitAssociation> updateDebitAssociation(@RequestBody DebitAssociation debitAssociation){
		return debitAssociationService.updateEntity(debitAssociation);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteDebitAssociation(@PathVariable String id){
		return debitAssociationService.deleteEntity(id);
	}

}
