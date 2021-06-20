package com.everis.creditcardservice.entity;

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
    private String description;
    private long status;
    @Field(name = "amount_ope")
    private long amountOpe;
    @Field(name = "num_card")
    private String numCard;
    @Field(name = "type_ope")
    private String typeOpe;
    @Field(name = "date_ope")
    private Date dateOpe;
}
