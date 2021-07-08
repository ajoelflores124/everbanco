package com.everis.yankiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.yankiservice.model.Customer;
import com.everis.yankiservice.topic.producer.YankiServiceProducer;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer-yanki")
public class CustomerYankiController {
	
	@Autowired
	YankiServiceProducer yankiServiceProducer;

	@PostMapping
	public Mono<Customer> saveCustomerYanki(@RequestBody Customer customer ) {

		yankiServiceProducer.sendSaveCustomerService(customer);
		return Mono.just(customer);
	}
	
}
