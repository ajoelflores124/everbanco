package com.everis.creditservice.entity;

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
@Document(collection = "credit")

public class Credit implements Serializable  {/**
	 * 
	 */
	private static final long serialVersionUID = -2792938633961361851L;
	
	@Id
	private String id;
	@Field(name = "num_doc")
	private String numDoc;
	@Field(name = "commission_free")
    private boolean commissionFree;
    private long commission;
    private String description;
    @Field(name = "limit_mov")
    private long limitMOV;
    private String numAccount;
	
}
