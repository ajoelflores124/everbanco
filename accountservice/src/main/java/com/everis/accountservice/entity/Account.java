package com.everis.accountservice.entity;

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
@Document(collection = "account")

public class Account implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -307530199706637625L;

	@Id
	private String id;
	@Field(name = "num_doc")
	private String numDoc;
	@Field(name = "commission_free")
    private boolean commissionFree;
    private String description;
    @Field(name = "limit_mov")
    private long limitMOV;
    @Field(name = "num_account")
    private String numAccount;
	
}
