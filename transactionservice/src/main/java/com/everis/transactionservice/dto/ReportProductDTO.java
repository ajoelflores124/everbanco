package com.everis.transactionservice.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.everis.transactionservice.entity.Customer;
import com.everis.transactionservice.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "transaction")
public class ReportProductDTO implements Serializable {
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
    private double balance;
    @Field(name = "out_credit")
    private double outCredit;
    private Customer customer;
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
    
}
