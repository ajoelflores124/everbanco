package com.everis.creditservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.creditservice.entity.Credit;
import com.everis.creditservice.service.CreditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/credit")

public class CreditController {

	@Autowired
	private CreditService creditService;
	
	@GetMapping
	public Flux<Credit> getCredits(){
		return creditService.getCredits();
	}
	
	@GetMapping("/{id}")
	public Mono<Credit> getCredit(@PathVariable String id){
		return creditService.getCredit(id);
	}
	
	@PostMapping
	public Mono<Credit> saveCredit(@RequestBody Credit creditMono){
		return creditService.saveCredit(creditMono);
	}
	
	@PutMapping
	public Mono<Credit> updateCredit(@RequestBody Credit creditMono){
		return creditService.updateCredit(creditMono);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteCredit(@PathVariable String id){
		return creditService.deleteCredit(id);
	}
}
