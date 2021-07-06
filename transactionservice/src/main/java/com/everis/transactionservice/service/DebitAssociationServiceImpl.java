package com.everis.transactionservice.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.everis.transactionservice.dto.ReportProductDTO;
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
	
	
	private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public DebitAssociationServiceImpl(ReactiveMongoTemplate mongoTemplate,IDebitAssociationRepository debitAssociationRepo) {
        this.mongoTemplate = mongoTemplate;
        this.debitAssociationRepo = debitAssociationRepo;
    }
    
    
    
	
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

	@Override
	public Mono<DebitAssociation> findAccountMainByCardDebit(String cardDebit) {
		Query query= new Query().addCriteria( Criteria.where("cardNumDebit").is(cardDebit).andOperator(
				Criteria.where("accMain").is(true),
				Criteria.where("status").is(1)
				));

		return mongoTemplate.findOne(query,DebitAssociation.class);
	}

	@Override
	public Flux<DebitAssociation> listAccountsByCardDebit(String cardDebit) {
		Query query= new Query().addCriteria( Criteria.where("cardNumDebit").is(cardDebit).andOperator(
				Criteria.where("accMain").is(false),
				Criteria.where("status").is(1)
				));
		return mongoTemplate.find(query,DebitAssociation.class);
	}

	 
	
	

}
