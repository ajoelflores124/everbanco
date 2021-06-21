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
import com.everis.creditcardservice.service.CreditcardServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/creditcard")

public class CredictcardController {

	@Autowired
	private CreditcardServiceImpl creditcardService;
	
	@GetMapping
	public Flux<Creditcard> getCreditcard(){
		return creditcardService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Creditcard> getCredit(@PathVariable String id){
		return creditcardService.findEntityById(id);
	}
	
	@PostMapping
	public Mono<Creditcard> saveCredit(@RequestBody Creditcard CreditcardMono){
		return creditcardService.createEntity(CreditcardMono);
	}
	
	@PutMapping
	public Mono<Creditcard> updateCredit(@RequestBody Creditcard CreditcardMono){
		return creditcardService.updateEntity(CreditcardMono);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteCreditcard(@PathVariable String id){
		return creditcardService.deleteEntity(id);
	}
	
	@GetMapping("/numDoc/{id}")
	public Flux<Creditcard> getCredits(@PathVariable String id){
		return creditcardService.getCreditcards(id);
	}
}
