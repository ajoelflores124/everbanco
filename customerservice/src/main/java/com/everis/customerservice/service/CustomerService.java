package com.everis.customerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.everis.customerservice.entity.Customer;
import com.everis.customerservice.exception.EntityNotFoundException;
import com.everis.customerservice.repository.ICustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Service
public class CustomerService {

	private String msgNotFound;
	@Autowired
	private ICustomerRepository customerRep;
	
	public Flux<Customer> getCustomers(){
		return customerRep.findAll();
	}
	
	public Mono<Customer> getCustomer(String id){
		return customerRep.findById(id);
	}
	
	public Mono<Customer> saveCustomer(Customer customer){
		return customerRep.insert(customer);
	}
	
	public Mono<Customer> updateCustomer(Customer customer){
		return  customerRep.findById(customer.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> customerRep.save(customer));
	}

	public Mono<Void> deleteCustomer(String id){
		return  customerRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> customerRep.deleteById(id));
	}

}
