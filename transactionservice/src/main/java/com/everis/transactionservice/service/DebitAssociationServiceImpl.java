package com.everis.transactionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.everis.transactionservice.entity.DebitAssociation;
import com.everis.transactionservice.exception.EntityNotFoundException;
import com.everis.transactionservice.repository.IDebitAssociationRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DebitAssociationServiceImpl implements IDebitAssociationService {

	
	/**
	 * 
	 */
	@Value("${msg.error.registro.notfound}")
	private String msgNotFound;
	
	@Autowired
	private IDebitAssociationRepository debitAssociationRepo;
	
	@Override
	public Flux<DebitAssociation> findAll() {
		return debitAssociationRepo.findAll();
	}

	@Override
	public Mono<DebitAssociation> findEntityById(String id) {
		return debitAssociationRepo.findById(id);
	}

	@Override
	public Mono<DebitAssociation> createEntity(DebitAssociation entity) throws Exception {
		return debitAssociationRepo.insert(entity);
	}

	@Override
	public Mono<DebitAssociation> updateEntity(DebitAssociation entity) {
		return  debitAssociationRepo.findById(entity.getId())
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> debitAssociationRepo.save(entity));
	}

	@Override
	public Mono<Void> deleteEntity(String id) {
		return  debitAssociationRepo.findById(id)
				 .switchIfEmpty(Mono.error( new EntityNotFoundException(msgNotFound) ))
				 .flatMap(item-> debitAssociationRepo.deleteById(id));
	}
	
	

}
