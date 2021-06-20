package com.everis.creditservice.entity;

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
@Document(collection = "credit")

public class Credit implements Serializable  {/**
	 * 
	 */
	private static final long serialVersionUID = -2792938633961361851L;
	
	@Id
	private String id;
	@Field(name = "num_doc")
	private String numDoc;
    private String description;
    private long status;
    @Field(name = "amount_pay")
    private long amountPay;
    @Field(name = "date_pay")
    private Date datePay;
    private long numTra;
	
}
