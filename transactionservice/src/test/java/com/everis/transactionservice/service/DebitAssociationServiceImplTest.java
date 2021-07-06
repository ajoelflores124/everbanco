package com.everis.transactionservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.repository.IDebitAssociationRepository;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class DebitAssociationServiceImplTest {

	@Mock
	IDebitAssociationRepository debitAssociationRepos;
	
	@Mock
	ReactiveMongoTemplate mongoTemplate;
	
	@InjectMocks
	DebitAssociationServiceImpl debitAssociationService;
	
	
	@Mock
	DebitAssociationServiceImpl service;

 
	@Test
	void testFindAll() {
		when(debitAssociationRepos.findAll()).thenReturn( DatosDebitAssociation.DEBIT_ASSOCIATIONS  );
		Flux<DebitAssociation> list= debitAssociationService.findAll();
		StepVerifier.create(list)
		.expectSubscription()
		.expectNextCount(2)//0
		.verifyComplete();
	}

	@Test
	void testFindEntityById() {
		String expectativa="12345678";//10101010
		when(debitAssociationRepos.findById(Mockito.anyString()))
		.thenReturn( DatosDebitAssociation.DEBIT_ASSOCIATION );
		Mono<DebitAssociation> debitAssoc= debitAssociationService.findEntityById(expectativa);
		DebitAssociation da= debitAssoc.share().block();
		assertEquals(expectativa, da.getId());
	}

	@Test
	void testCreateEntity() throws Exception {
		
		when(debitAssociationRepos.insert(Mockito.any(DebitAssociation.class)))
		.thenReturn(DatosDebitAssociation.NEW_MONO_DEBIT_ASSOCIATION);
		Mono<DebitAssociation> debitAssoc= debitAssociationService.createEntity(DatosDebitAssociation.NEW_DEBIT_ASSOCIATION);
		
		DebitAssociation debit= debitAssoc.share().block();
		assertNotNull(debit.getId());
		assertEquals("20202020", debit.getId());
		assertEquals("2020-3030-4040-5050", debit.getCardNumDebit());
		verify(debitAssociationRepos).insert(Mockito.any(DebitAssociation.class));

	}

	@Test
	void testUpdateEntity() {
		when(debitAssociationRepos.save(Mockito.any(DebitAssociation.class)))
		.thenReturn(DatosDebitAssociation.DEBIT_ASSOCIATION);
		
		when(debitAssociationRepos.findById(Mockito.anyString()))
		.thenReturn( DatosDebitAssociation.DEBIT_ASSOCIATION );
		
		Mono<DebitAssociation> debitAssoc= debitAssociationService.updateEntity(DatosDebitAssociation.NEW_DEBIT_ASSOCIATION);
		
		DebitAssociation debit= debitAssoc.share().block();
		assertNotNull(debit.getId());
		assertEquals("12345678", debit.getId());
		assertEquals("1234-2222-3333-4444", debit.getCardNumDebit());
		verify(debitAssociationRepos).save(Mockito.any(DebitAssociation.class));
	}

	@Test
	@Disabled
	void testDeleteEntity() {
		fail("Not yet implemented");
	}

	@Test
	void testFindAccountMainByCardDebit() {
		when(service.findAccountMainByCardDebit( Mockito.anyString() ))
		.thenReturn(DatosDebitAssociation.DEBIT_ASSOCIATION);
		Mono<DebitAssociation> debitAssoc= service.findAccountMainByCardDebit("12345678"); 
		
		StepVerifier.create(debitAssoc)
		.expectSubscription()
		.expectNextCount(1)//0
		.verifyComplete();
		
	}

	@Test
	void testListAccountsByCardDebit() {
		when(service.listAccountsByCardDebit(Mockito.anyString())).thenReturn( DatosDebitAssociation.DEBIT_ASSOCIATIONS  );
		Flux<DebitAssociation> list= service.listAccountsByCardDebit("1234-2222-3333-4444");
		StepVerifier.create(list)
		.expectSubscription()
		.expectNextCount(2)//0
		.verifyComplete();
	}

}
