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

import com.everis.transactionservice.dto.BalanceMainDTO;
import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.entity.DebitMovement;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.service.IDebitMovementService;
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
	
	@Autowired
	private IDebitMovementService debitMovementService;
	
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
	
	@GetMapping("/find-numacc/{numacc}")
	public Mono<Transaction> updateTransaction(@PathVariable String numacc){
		return transactionService.findByNumAccount(numacc);
	}
	
	@PutMapping("/update-balance/{numacc}/{balance}/{oper}")
	public Mono<Transaction> updateTransaction(@PathVariable String numacc,@PathVariable Double balance, @PathVariable String oper){
		return transactionService.updateBalance(numacc, balance, oper);
	}
	
	@PostMapping("/make-pay-debit-det")
	public Flux<ResumeDTO> updateBalanceAccountsByCardDebitDet(@RequestBody DebitMovement debitMov) throws Exception{
				
		debitMovementService.createEntity(debitMov).subscribe();
		return transactionService.updateBalanceAccountsByCardDebit(debitMov.getCardNumDebit(), debitMov.getAmountMov());
	}
	
	@GetMapping("/balance-main-account/{cardDebit}")
	public Mono<BalanceMainDTO> listAccounts(@PathVariable String cardDebit){
		return transactionService.getBalanceAccountMain(cardDebit);
	}

}
