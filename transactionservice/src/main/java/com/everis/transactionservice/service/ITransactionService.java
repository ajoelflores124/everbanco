package com.everis.transactionservice.service;

import java.util.List;

import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;
import com.everis.transactionservice.entity.Representative;
import com.everis.transactionservice.entity.Transaction;

public interface ITransactionService extends IMaintenanceService<Transaction> {

	public Customer getCustomerByNumDoc(String numDoc);
	public Product getProductByIdProduct(String idProduct);
	public Representative getDataRepresentative(Representative representative);
	public Representative[] getRepresentativesByNumDocRep(Representative[] representatives);
	public boolean validateRepresentatives(Representative[] representatives);
}
