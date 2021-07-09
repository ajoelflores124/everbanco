package com.everis.movementservice.topic.consumer;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.everis.movementservice.entity.Movement;
import com.everis.movementservice.repository.IMovementRepository;
import com.everis.movementservice.webclient.TransactionServiceClient;
import com.everis.movementservice.webclient.model.CustomerDTO;
import com.everis.movementservice.webclient.model.DebitAssociationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class MovementServiceConsumer {
	
	private final static String YANKI_MOVEMENT_TOPIC = "yanki-movement-topic";
	private final static String GROUP_ID = "movement-group";
	
	@Autowired
	private IMovementRepository movementRepo;
	
	@Autowired
	private TransactionServiceClient transactionServiceCliente;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@KafkaListener( topics = YANKI_MOVEMENT_TOPIC, groupId = GROUP_ID)
	public Disposable retrieveSavedCustomer(String data) throws Exception {
		
		log.info(" data desde el listener (movement)=>"+data);
		
		Movement movement= objectMapper.readValue(data, Movement.class);
		movement.setDate(new Date());
	    //Obtenemos el num de tarjeta de debito
		log.info("phone=>"+ movement.getPhone());
		CustomerDTO customer= transactionServiceCliente.getCustomerByPhone(movement.getPhone())
				.share()
				.block();
		//obtener el numero de cuenta principal
		log.info("card num debit=>"+ customer.getCardNumDebit());
		DebitAssociationDTO debitAssoc = transactionServiceCliente.getAccountMainByCardDebit(customer.getCardNumDebit())
				.share()
				.block();
 
		//registrar el movimiento
		Mono.just(movement)
        .log()
        .flatMap(movementRepo::save)
        .subscribe();
		
		//actualizar el monto segun el tipo de movimiento
		return transactionServiceCliente.updateTransaction(debitAssoc.getNumAccAsoc(),
				movement.getAmount(),
				movement.getTypeMov().equalsIgnoreCase("D")?"+":"-"
				).subscribe();
	}

}
