package com.everis.customerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.customerservice.entity.Customer;
import com.everis.customerservice.repository.ICustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

	@Autowired
	private ICustomerRepository customerRep;
	
	public Flux<Customer> getTransactions(){
		return customerRep.findAll();
	}
	
	public Mono<Customer> getTransacion(String id){
		return customerRep.findById(id);
	}
	
	public Mono<Customer> saveTransaction(Customer customer){
		return customerRep.insert(customer);
	}
	
	public Mono<Customer> updateTransaction(Customer customer){
		return customerRep.save(customer);
	}

	public Mono<Void> deleteTransaction(String id){
		return customerRep.deleteById(id);
	}

}
