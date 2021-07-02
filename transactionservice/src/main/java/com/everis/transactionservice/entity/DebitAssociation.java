package com.everis.transactionservice.entity;

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
@Document(collection = "debit_association")
public class DebitAssociation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2465819240512823291L;
	@Id
	private String id;
	@Field(name = "card_num_debit")
    private String cardNumDebit;
	@Field(name = "num_acc_assoc")
    private String numAccAsoc;
	@Field(name = "acc_main")
    private boolean accMain;
	@Field(name = "date_assoc")
    private Date dateAssoc;
	private long status;
 
}
