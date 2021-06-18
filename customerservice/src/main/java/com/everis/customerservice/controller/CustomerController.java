package com.everis.customerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.customerservice.entity.Customer;
import com.everis.customerservice.service.CustomerService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping
	public Flux<Customer> getTransactions(){
		return customerService.getTransactions();
	}
	
	@GetMapping("/{id}")
	public Mono<Customer> getTransaction(@PathVariable String id){
		return customerService.getTransacion(id);
	}
	
	@PostMapping
	public Mono<Customer> saveProduct(@RequestBody Customer customerMono){
		return customerService.saveTransaction(customerMono);
	}
	
	@PutMapping
	public Mono<Customer> updateTransaction(@RequestBody Customer customerMono){
		return customerService.updateTransaction(customerMono);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Void> deleteTransaction(@PathVariable String id){
		return customerService.deleteTransaction(id);
	}
}