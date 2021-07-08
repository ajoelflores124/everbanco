package com.everis.yankiservice.topic.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.everis.yankiservice.model.Customer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class YankiServiceProducer {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	private final static String YANKI_TOPIC = "yankiservice-topic";
	
	public void sendSaveCustomerService(Customer customer) {
		log.info("enviando el objeto cliente yanki");
		kafkaTemplate.send(YANKI_TOPIC, customer );
	}
}
