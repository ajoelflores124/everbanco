package com.everis.representativeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.representativeservice.entity.Representative;
import com.everis.representativeservice.service.RepresentativeService;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/representative")
public class RepresentativeController {

	@Autowired
	private RepresentativeService representativeService;
	
	@GetMapping
	public Flux<Representative> getRepresentatives(){
		return representativeService.getRepresentatives();
	}
	
	@GetMapping("/{id}")
	public Mono<Representative> getRepresentative(@PathVariable String id){
		return representativeService.getRepresentative(id);
	}
	
	@PostMapping
	public Mono<Representative> saveRepresentative(@RequestBody Representative representative){
		return representativeService.saveRepresentative(representative);
	}
	
	@PutMapping
	public Mono<Representative> updateRepresentative(@RequestBody Representative representative){
		
		return representativeService.updateRepresentative(representative);
	}
	
	@DeleteMapping("/{id}")
	public Mono<Void> deleteRepresentative(@PathVariable String id){
		return representativeService.deleteRepresentative(id);
	}
	
	@GetMapping("/find-by-numdoc/{numDocRep}")
	public Mono<Representative> getRepresentativeByNumDocRep(@PathVariable String numDocRep){
		return representativeService.getRepresentativeByNumDocRep(numDocRep);
	}
}
