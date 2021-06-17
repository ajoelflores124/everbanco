package com.everis.transactionservice.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2266400239755970885L;
	
	@Field(name = "date_pay")
	private Date datePay;
	@Field(name = "amount_pay")
    private double amountPay;
	@Field(name = "desc_pay")
    private String descPay;
}
