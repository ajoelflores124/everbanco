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
public class Consumption implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8135005057889829680L;
	@Field(name = "date_con")
	private Date dateCon;
	@Field(name = "amount_con")
    private String amountCon;
	@Field(name = "desc_con")
    private String descCon;

}
