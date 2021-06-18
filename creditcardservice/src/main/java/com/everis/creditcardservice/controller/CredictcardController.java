package com.everis.creditcardservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.creditcardservice.entity.Creditcard;
import com.everis.creditcardservice.service.CreditcardService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/creditcard")

public class CredictcardController {

	@Autowired
	private CreditcardService creditcardService;
	
	@GetMapping
	public Flux<Creditcard> getCreditcards(){
		return creditcardService.getCreditcards();
	}
	
	@GetMapping("/{id}")
	public Mono<Creditcard> getCreditcard(@PathVariable String id){
		return creditcardService.getCreditcard(id);
	}
	
	@PostMapping
	public Mono<Creditcard> saveCreditcard(@RequestBody Creditcard creditcardMono){
		return creditcardService.saveCreditcard(creditcardMono);
	}
	
	@PutMapping
	public Mono<Creditcard> updateCreditcard(@RequestBody Creditcard creditcardMono){
		return creditcardService.updateCreditcard(creditcardMono);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteCreditcard(@PathVariable String id){
		return creditcardService.deleteCreditcard(id);
	}
}
