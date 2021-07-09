package com.everis.yankiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.yankiservice.model.Customer;
import com.everis.yankiservice.model.Movement;
import com.everis.yankiservice.topic.producer.YankiServiceProducer;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movement-yanki")
public class MovementYankiController {
	
	@Autowired
	YankiServiceProducer yankiServiceProducer;

	@PostMapping
	public Mono<Movement> saveCustomerYanki(@RequestBody Movement movement) {

		yankiServiceProducer.sendSaveMovementService(movement);
		return Mono.just(movement);
	}
	
}
