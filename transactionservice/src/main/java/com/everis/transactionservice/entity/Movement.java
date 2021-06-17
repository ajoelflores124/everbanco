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
public class Movement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4019062661363850099L;
	
	@Field(name = "type_mov")
	private String typeMov;
	@Field(name = "date_mov")
    private Date dateMov;
	@Field(name = "amount_mov")
    private Double amountMov;

}
