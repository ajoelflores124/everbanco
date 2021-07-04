package com.everis.creditservice.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.creditservice.webclient.model.DebitMovementDTO;
import com.everis.creditservice.webclient.model.ResumeDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TransactionServiceClient {

	@Value("${url.apigateway.service}")
	private String urlApiGatewayService;
	
	public Flux<ResumeDTO> updateBalanceAccountsByCardDebitDet(DebitMovementDTO debitMov){
		WebClient webClient = WebClient.create(urlApiGatewayService);
	    return  webClient.post()
	    		.uri("/api/transaction-service/transaction/make-pay-debit-det")
	    		.body( BodyInserters.fromValue(debitMov) )
	    		.retrieve()
	    		.bodyToFlux(ResumeDTO.class);
	    		
	}
	
	
	
}
