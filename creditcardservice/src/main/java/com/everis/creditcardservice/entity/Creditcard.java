package com.everis.creditcardservice.entity;

import java.io.Serializable;

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
@Document(collection = "creditcard")

public class Creditcard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4903805073296348735L;
	
	@Id
	private String id;
	@Field(name = "num_doc")
	private String numDoc;
	@Field(name = "num_card")
    private String numCard;
    private String description;
    @Field(name = "limit_credit")
    private long limitCredit;
}
