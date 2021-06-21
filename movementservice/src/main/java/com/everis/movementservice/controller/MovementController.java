package com.everis.movementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.movementservice.entity.Movement;
import com.everis.movementservice.service.IMovementService;
import com.everis.movementservice.service.MovementServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/movement")
public class MovementController {

	@Autowired
	private IMovementService movementService;
	
	@GetMapping
	public Flux<Movement> getMovement(){
		return movementService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Movement> getMovement(@PathVariable String id){
		return movementService.findEntityById(id);
	}
	
	@PostMapping
	public Mono<Movement> saveMovement(@RequestBody Movement movementMono){
		return movementService.createEntity(movementMono);
	}
	
	@PutMapping
	public Mono<Movement> updateMovement(@RequestBody Movement movementMono){
		return movementService.updateEntity(movementMono);
	}
		
	@DeleteMapping("/{id}")
	public Mono<Void> deleteMovement(@PathVariable String id){
		return movementService.deleteEntity(id);
	}
	
	@GetMapping("/numDoc/{id}")
	public Flux<Movement> getMovements(@PathVariable String id){
		//System.out.println("Valor"+id);
		return movementService.getMovements(id);
	}
}
