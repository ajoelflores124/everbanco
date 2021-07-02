package com.everis.transactionservice.service;

import java.util.Date;

import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.dto.ReportProductDTO;
import com.everis.transactionservice.entity.Transaction;

import reactor.core.publisher.Flux;

public interface IResumeService {
	
	/**
	 * Lista el resumen de un cliente con todo sus productos
	 * @param numdoc
	 * @return Flux<ResumeDTO>
	 */
	public Flux<ResumeDTO> resumeByCustomer(String numdoc);
	
	/**
	 * Lista los productos del banco en un intervalo de tiempo
	 * @param startDate
	 * @param endDate
	 * @return 
	 */
	public Flux<ReportProductDTO> reportProductoByTime(String startDate, String endDate);
}
