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
import com.everis.transactionservice.entity.DebitMovement;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.service.IDebitAssociationService;
import com.everis.transactionservice.service.IDebitMovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Angel
 *
 */
@RestController
@RequestMapping(path = "/debit-mov")
public class DebitMovementController {
	
	
	@Autowired
	private IDebitMovementService debitMovementService;
	
	 
	
	 
	
	@PostMapping
	public Mono<DebitMovement> saveDebitAssociation(@RequestBody DebitMovement debitMovement) throws Exception{
		return debitMovementService.createEntity(debitMovement);
	}
	
	@GetMapping("/list-mov/{numDebit}")
	public Flux<DebitMovement> listDebit10Movement(@PathVariable String numDebit){
		return debitMovementService.listTop10DebitMovement(numDebit);
	}
	
	 
		
	 

	 
	 
}
