package com.everis.transactionservice.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7414292834369263485L;
	@Field(name = "num_doc")
	private String numDoc;
	@Field(name = "name")
    private String name;
	@Field(name = "lastname")
    private String lastname;
    @Field(name = "type_customer")
    private String typeCustomer;

}
