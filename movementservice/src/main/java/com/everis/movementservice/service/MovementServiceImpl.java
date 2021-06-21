package com.everis.movementservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.movementservice.entity.Movement;
import com.everis.movementservice.exception.EntityNotFoundException;
import com.everis.movementservice.repository.IMovementRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@PropertySource("classpath:application.properties")
@Service
public class MovementServiceImpl implements IMovementService {

	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Value("${url.customer.service}")
	private String urlCustomerService;
	
	@Autowired
	private IMovementRepository movementRep;
	private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public MovementServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    //WebClient webClient = WebClient.create(urlCustomerService);
	
	@Override
	public Flux<Movement> findAll() {
		return movementRep.findAll();
	}

	@Override
	public Mono<Movement> findEntityById(String id) {
		return movementRep.findById(id);
	}

	@Override
	public Mono<Movement> createEntity(Movement movement) {
	   return movementRep.insert(movement);
	}

	@Override
	public Mono<Movement> updateEntity(Movement movement) {
		return  movementRep.findById(movement.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> movementRep.save(movement));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return  movementRep.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> movementRep.deleteById(id));
	}

	//Consultar todos los movimientos e un producto bancario que tiene un cliente 
	@Override
	public Flux<Movement> getMovements(String numDoc) {
		return movementRep.findByNumDoc(numDoc);
	}
}
