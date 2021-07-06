package com.everis.transactionservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everis.transactionservice.entity.DebitMovement;
import com.everis.transactionservice.repository.IDebitMovementRepository;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DebitMovementServiceImplTest {
	
	
	@Mock
	IDebitMovementRepository debitMovementRepos;
	
	@InjectMocks
	DebitMovementServiceImpl debitMovementService;
	

	@Test
	void testListTop10DebitMovement(){
		when(debitMovementRepos.findTop10ByCardNumDebitOrderByDateMovDesc(Mockito.anyString()))
		.thenReturn( DatosDebitMovement.DEBIT_MOVEMENTS);
		
		Flux<DebitMovement> listaTop10= debitMovementService.listTop10DebitMovement("1234-2222-3333-1111");
		
		StepVerifier.create(listaTop10)
		.expectSubscription()
		.expectNextCount(10)//0
		.verifyComplete();
	}

}
