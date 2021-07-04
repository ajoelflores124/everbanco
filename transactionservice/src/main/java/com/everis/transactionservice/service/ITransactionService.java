package com.everis.transactionservice.service;

import java.util.List;

import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;
import com.everis.transactionservice.entity.Representative;
import com.everis.transactionservice.entity.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService extends IMaintenanceService<Transaction> {

	//public Customer getCustomerByNumDoc(String numDoc);
	//public Product getProductByIdProduct(String idProduct);
	//public Representative getDataRepresentative(Representative representative);
	public Representative[] getRepresentativesByNumDocRep(Representative[] representatives);
	public boolean validateRepresentatives(Representative[] representatives);
	public long countAccountByCustomer(Transaction transaction);
	public long countDebtExpiredByCustomer(String numDoc);
	public Mono<Transaction> findByNumAccount(String numAcc );
	public Mono<Transaction> updateBalance(String numAcc, Double balance, String oper );
	public Flux<ResumeDTO> updateBalanceAccountsByCardDebit(String cardDebit,Double balance);
	public Mono<Transaction> getBalanceAccountMain(String cardDebit);
}
