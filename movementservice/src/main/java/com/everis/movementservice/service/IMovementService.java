package com.everis.movementservice.service;

import com.everis.movementservice.entity.Movement;

import reactor.core.publisher.Flux;

public interface IMovementService extends IMaintenanceService<Movement> {

	public Flux<Movement> getMovements(String numdoc);
}
