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
@Document(collection = "transaction")
public class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2465819240512823291L;
	@Id
	private String id;
	@Field(name = "date_tra")
    private Date dateTra;
	@Field(name = "num_account")
    private String numAccount;
	@Field(name = "type_tra")
    private String typeTra;
	@Field(name = "limit_credit")
    private double limitCredit;
    private double balance;//saldo
    @Field(name = "out_credit")
    private double outCredit;
    private Customer customer;
    private Representative[] representatives;
    private Product product;
    @Field(name = "desc_tra")
    private String descTra;
    private long status;
    @Field(name = "amount_loan")
    private double amountLoan;
    @Field(name = "date_pay_next")
    private Date datePayNext;
    @Field(name = "closed_account")
    private boolean closedAccount;
    @Field(name = "debt_status")
    private String debtStatus;
    
    @Field(name = "card_num")
    private String cardNum;
    
    @Field(name = "credit_cod")
    private String creditCod;
}
