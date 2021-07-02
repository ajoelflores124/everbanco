package com.everis.transactionservice.controller;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.transactionservice.dto.ReportProductDTO;
import com.everis.transactionservice.dto.ResumeDTO;
import com.everis.transactionservice.entity.Transaction;
import com.everis.transactionservice.service.IResumeService;

import reactor.core.publisher.Flux;

/**
 * @author Angel
 *
 */
@RestController
@RequestMapping(path = "/resume")
public class ResumeController {
	
	@Autowired
	private IResumeService resumenService;

	@GetMapping("/consolid/{numdoc}")
	public Flux<ResumeDTO> getTransactions(@PathVariable String numdoc){
		return resumenService.resumeByCustomer(numdoc);
	}
	
	@GetMapping("/report-product/{starDate}/{endDate}")
	public Flux<ReportProductDTO> getReportProduct(@PathVariable String starDate, @PathVariable String endDate ){
		return resumenService.reportProductoByTime(starDate, endDate);
	}
	
}
