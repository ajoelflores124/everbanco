package com.everis.movementservice.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movement")
public class Movement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1167884508515638931L;
	@Id
	private String id;
	@Field(name = "num_doc")
	private String numDoc;
    private String description;
    @Field(name = "num_account")
    private String numAccount;
    private Double amount;
    private Date date;
    @Field(name = "type_mov")
    private String typeMov;
    private long status;
    
    @Field(name = "debit_card_pay")
    private String debitCardPay;
    
}
