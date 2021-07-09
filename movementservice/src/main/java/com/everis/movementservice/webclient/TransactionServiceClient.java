package com.everis.movementservice.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.movementservice.webclient.model.CustomerDTO;
import com.everis.movementservice.webclient.model.DebitAssociationDTO;
import com.everis.movementservice.webclient.model.DebitMovementDTO;
import com.everis.movementservice.webclient.model.ResumeDTO;
import com.everis.movementservice.webclient.model.TransactionDTO;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionServiceClient {

	@Value("${url.apigateway.service}")
	private String urlApiGatewayService;
	
	
	public Mono<TransactionDTO> updateTransaction(String numacc, Double balance,  String oper){
		
		WebClient webClient = WebClient.create(urlApiGatewayService);
	    return  webClient.put()
	    		.uri("/api/transaction-service/transaction/update-balance/{numacc}/{balance}/{oper}",numacc,balance,oper)
	    		.retrieve()
	    		.bodyToMono(TransactionDTO.class);
		
	}
	
	public Flux<ResumeDTO> updateBalanceAccountsByCardDebitDet(DebitMovementDTO debitMov){
		WebClient webClient = WebClient.create(urlApiGatewayService);
	    return  webClient.post()
	    		.uri("/api/transaction-service/transaction/make-pay-debit-det")
	    		.body( BodyInserters.fromValue(debitMov) )
	    		.retrieve()
	    		.bodyToFlux(ResumeDTO.class);
	    		
	}
	
	public Mono<CustomerDTO> getCustomerByPhone(String phone){
		WebClient webClient = WebClient.create(urlApiGatewayService);
		return  webClient.get()
	    		.uri("/api/customer-service/customer/find-by-phone/{phone}",phone)
	    		.retrieve()
	    		.bodyToMono(CustomerDTO.class);
	}
	
	public Mono<DebitAssociationDTO> getAccountMainByCardDebit(String cardDebit){
		WebClient webClient = WebClient.create(urlApiGatewayService);
		return  webClient.get()
	    		.uri("/api/transaction-service/debit-assoc/find-account-main/{cardDebit}",cardDebit)
	    		.retrieve()
	    		.bodyToMono(DebitAssociationDTO.class);
	}
	
}
