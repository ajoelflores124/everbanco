package com.everis.transactionservice.webclient;

import org.springframework.data.mongodb.core.mapping.Field;

import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;
import com.everis.transactionservice.entity.Representative;

public class DatosTransactionServiceClient {
	
	public final static Customer DATOS_CUSTOMER= new Customer("12345678","Juan", "Perez", "Personal");
			
	public final static Product DATOS_PRODUCT= new Product("100","Cnta Ahorros", "Activo");
	
	public final static Representative DATOS_REPRESENTATIVE= new Representative("30303030","Hugo","Campos","Firmante");
	
 
		 
}
