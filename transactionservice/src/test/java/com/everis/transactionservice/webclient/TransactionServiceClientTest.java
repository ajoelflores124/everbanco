package com.everis.transactionservice.webclient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;
import com.everis.transactionservice.entity.Representative;

@ExtendWith(MockitoExtension.class)
class TransactionServiceClientTest {
	
	
	@Mock
	TransactionServiceClient transactionServiceCli;

	@Test
	void testGetCustomerByNumDoc() {
		 when(transactionServiceCli.getCustomerByNumDoc(Mockito.anyString())).thenReturn(DatosTransactionServiceClient.DATOS_CUSTOMER);
		 Customer customer= transactionServiceCli.getCustomerByNumDoc("12345678");
		 
		 assertNotNull(customer.getNumDoc());
		 assertEquals("12345678", customer.getNumDoc());
		 verify(transactionServiceCli).getCustomerByNumDoc(Mockito.anyString());
	}

	@Test
	void testGetProductByIdProduct() {
		when(transactionServiceCli.getProductByIdProduct(Mockito.anyString())).thenReturn(DatosTransactionServiceClient.DATOS_PRODUCT );
		Product product= transactionServiceCli.getProductByIdProduct("12345678");
		
		assertNotNull(product.getIdProduct());
		assertEquals("100", product.getIdProduct());
		verify(transactionServiceCli).getProductByIdProduct(Mockito.anyString());
	}

	@Test
	void testGetDataRepresentative() {
		when(transactionServiceCli.getDataRepresentative( Mockito.any(Representative.class) )).thenReturn(DatosTransactionServiceClient.DATOS_REPRESENTATIVE);
		Representative rep= transactionServiceCli.getDataRepresentative( DatosTransactionServiceClient.DATOS_REPRESENTATIVE );
		
		assertNotNull(rep.getNumDocRep());
		assertEquals("30303030", rep.getNumDocRep());
		verify(transactionServiceCli).getDataRepresentative(Mockito.any(Representative.class));
	}

}
