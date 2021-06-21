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
import com.everis.creditservice.service.CreditServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/credit")

public class CreditController {

	@Autowired
	private CreditServiceImpl creditService;
	
	@GetMapping
	public Flux<Credit> getCredit(){
		return creditService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Credit> getCredit(@PathVariable String id){
		return creditService.findEntityById(id);
	}
	
	@PostMapping
	public Mono<Credit> saveCredit(@RequestBody Credit CreditMono){
		return creditService.createEntity(CreditMono);
	}
	
	@PutMapping
	public Mono<Credit> updateCredit(@RequestBody Credit CreditMono){
		return creditService.updateEntity(CreditMono);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteCredit(@PathVariable String id){
		return creditService.deleteEntity(id);
	}
	
	@GetMapping("/numDoc/{id}")
	public Flux<Credit> getCredits(@PathVariable String id){
		return creditService.getCredits(id);
	}
	
	
}
