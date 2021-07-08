package com.everis.transactionservice.topic.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;

@Slf4j
@Component
public class TransactionServiceConsumer {

	private final static String CUSTOMER_TOPIC = "customerservice-topic";
	private final static String GROUP_ID = "transaction-group";
	
	@KafkaListener( topics = CUSTOMER_TOPIC, groupId = GROUP_ID)
	public String retrieveSavedCustomer(String data) {
		
		log.info("data desde kafka listener=>"+data);
		
		return data;
				
	}
	
}
