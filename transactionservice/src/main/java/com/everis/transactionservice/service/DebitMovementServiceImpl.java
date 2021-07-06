package com.everis.transactionservice.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.everis.transactionservice.dto.ReportProductDTO;
import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.entity.DebitMovement;
import com.everis.transactionservice.exception.EntityNotFoundException;
import com.everis.transactionservice.repository.IDebitAssociationRepository;
import com.everis.transactionservice.repository.IDebitMovementRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DebitMovementServiceImpl implements IDebitMovementService {

	
	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Autowired
	private IDebitMovementRepository debitMovementRep;
	
	public DebitMovementServiceImpl(IDebitMovementRepository debitMovementRep) {
		this.debitMovementRep = debitMovementRep;
	}

	@Override
	public Flux<DebitMovement> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<DebitMovement> findEntityById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<DebitMovement> createEntity(DebitMovement entity) throws Exception {
		
		
		entity.setDateMov( new Date() );
		entity.setStatus(1);
		return debitMovementRep.insert(entity);
	}

	@Override
	public Mono<DebitMovement> updateEntity(DebitMovement entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<DebitMovement> listTop10DebitMovement(String numDebit) {
		
		return debitMovementRep.findTop10ByCardNumDebitOrderByDateMovDesc(numDebit);
	}
	
	

     

	 
	
	

}
