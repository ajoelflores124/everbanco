package com.everis.transactionservice.service;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.everis.transactionservice.dto.ReportProductDTO;
import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.repository.ITransactionRepository;

import reactor.core.publisher.Flux;

/**
 * @author Angel
 *
 */
@Service
public class ResumeServiceImpl implements IResumeService {

	@Autowired
	private ITransactionRepository transactionRep;
	
	private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public ResumeServiceImpl(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
	
	@Override
	public Flux<ResumeDTO> resumeByCustomer(String numdoc) {
		Query query= new Query(Criteria.where("customer.numDoc").is(numdoc));
		return mongoTemplate.find(query,ResumeDTO.class);
	}

	@Override
	public Flux<ReportProductDTO> reportProductoByTime(String startDate, String endDate) {
		
		System.out.println("date: "+ LocalDate.parse(startDate));
		Query query= new Query().addCriteria( Criteria.where("dateTra").ne(null).andOperator(
				Criteria.where("dateTra").gte(LocalDate.parse(startDate)),
				Criteria.where("dateTra").lte(LocalDate.parse(endDate))
			));
		return mongoTemplate.find(query,ReportProductDTO.class);
	}
	
	

}
