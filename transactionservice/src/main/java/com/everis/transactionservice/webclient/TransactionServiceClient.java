package com.everis.transactionservice.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;
import com.everis.transactionservice.entity.Representative;
import com.everis.transactionservice.exception.EntityNotFoundException;
import reactor.core.publisher.Mono;

@Component
public class TransactionServiceClient {

	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Value("${url.apigateway.service}")
	private String urlApiGatewayService;
	
	
	
	public Customer getCustomerByNumDoc(String numDoc) {
		WebClient webClient = WebClient.create(urlApiGatewayService);
		System.out.println("num_doc=>"+ numDoc);
	    return  webClient.get()
	    		.uri("/api/customer-service/customer/find-by-numdoc/{numdoc}",numDoc)
	    		.retrieve()
	    		.bodyToMono(Customer.class)
	    		.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
	    		.share().block();
	}
	
	public Product getProductByIdProduct(String idProduct) {
		WebClient webClient = WebClient.create(urlApiGatewayService);
		System.out.println("idProduct=>"+ idProduct);
	    return  webClient.get()
	    		.uri("/api/product-service/product/find-by-product/{idProduct}",idProduct)
	    		.retrieve()
	    		.bodyToMono(Product.class)
	    		.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
	    		.share().block();
	}
	
	
	public Representative getDataRepresentative(Representative rep) {
		WebClient webClient = WebClient.create(urlApiGatewayService);
		Representative represetante=  webClient.get()
	    		.uri("/api/representative-service/representative/find-by-numdoc/{numDocRep}",rep.getNumDocRep())
	    		.retrieve()
	    		.bodyToMono(Representative.class)
	    		.switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
	    		.share().block();
		represetante.setTypeRep(rep.getTypeRep());
		
		return represetante;
	}
	
	
	
}
