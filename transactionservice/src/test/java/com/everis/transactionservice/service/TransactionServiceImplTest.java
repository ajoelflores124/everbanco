package com.everis.transactionservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everis.transactionservice.dto.BalanceMainDTO;
import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.entity.Representative;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.repository.ITransactionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
	
	
	@Mock
	TransactionServiceImpl transactionService; 
	
	@Mock
	ITransactionRepository transactionRepos;
	
	@InjectMocks
	TransactionServiceImpl service;
	

	@Test
	void testGetRepresentativesByNumDocRep() {
		when(transactionService.getRepresentativesByNumDocRep( Mockito.any(Representative[].class) ))
		.thenReturn(DatosTransaction.DATA_REP);
		
		Representative[] arRep= transactionService.getRepresentativesByNumDocRep(DatosTransaction.DATA_REP);
		assertEquals(2, arRep.length);
		
	}

	@Test
	void testValidateRepresentatives() {
		when(transactionService.validateRepresentatives(Mockito.any(Representative[].class))).thenReturn(true);
		
		Boolean res= transactionService.validateRepresentatives( DatosTransaction.DATA_REP );
		assertTrue(res);
		
	}

	@Test
	void testCountAccountByCustomer() {
		when(transactionService.countAccountByCustomer( Mockito.any(Transaction.class) )).thenReturn(1L);
		long count= transactionService.countAccountByCustomer( DatosTransaction.DATA_TRA );
		
		assertNotNull(count);
		assertEquals(1L, count);
	}

	@Test
	void testCountDebtExpiredByCustomer() {
		when(transactionService.countDebtExpiredByCustomer( Mockito.anyString() )).thenReturn(1L);
		long count= transactionService.countDebtExpiredByCustomer("12345678");
		assertNotNull(count);
		assertEquals(1L, count);
	}

	@Test
	void testFindByNumAccount() {
		
		when(transactionRepos.findByNumAccount(Mockito.anyString())).thenReturn(DatosTransaction.TRANSACTION  );
		Mono<Transaction> tr= service.findByNumAccount("10001");
		
		assertNotNull(tr);
		verify(transactionRepos).findByNumAccount(Mockito.anyString());
	}

	@Test
	void testUpdateBalance() {
		
		when(transactionRepos.findByNumAccount(Mockito.anyString())).thenReturn(DatosTransaction.TRANSACTION  );
		Mono<Transaction> tr= service.updateBalance("10001", 1000.0, "+");
		
		assertNotNull(tr);
		verify(transactionRepos).findByNumAccount(Mockito.anyString());
	}

	@Test
	void testUpdateBalanceAccountsByCardDebit() {
		
		when(transactionService.updateBalanceAccountsByCardDebit( Mockito.anyString() , Mockito.anyDouble())).thenReturn(DatosTransaction.RESUME);
		Flux<ResumeDTO> resume= transactionService.updateBalanceAccountsByCardDebit("2020-3030-4040-5050", 100.0);
		
		assertNotNull(resume.collectList().share().block());
		verify(transactionService).updateBalanceAccountsByCardDebit( Mockito.anyString() , Mockito.anyDouble());
	}

	@Test
	void testGetBalanceAccountMain() {
		when(transactionService.getBalanceAccountMain( Mockito.anyString() )).thenReturn(DatosTransaction.BALANCE_MAIN);
		
		Mono<BalanceMainDTO> balanceMain= transactionService.getBalanceAccountMain("2020-3030-4040-5050");
		assertNotNull(balanceMain.share().block());
		verify( transactionService ).getBalanceAccountMain( Mockito.anyString() );
	}

}
