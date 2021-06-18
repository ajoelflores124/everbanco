package com.everis.representativeservice.exception;

public class EntityNotFoundException extends RuntimeException  {

	public EntityNotFoundException(String message) {
        super(message);
        System.out.println("error manejado");
    }
}
