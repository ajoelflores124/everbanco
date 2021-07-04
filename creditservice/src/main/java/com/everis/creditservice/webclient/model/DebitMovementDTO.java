package com.everis.creditservice.webclient.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebitMovementDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2465819240512823291L;
	@Id
	private String id;
	@Field(name = "card_num_debit")
    private String cardNumDebit;
	@Field(name = "des_mov")
    private String desMov;
	@Field(name = "type_oper")
    private String typeOper;
	@Field(name = "amount_mov")
    private Double amountMov;
	@Field(name = "date_mov")
    private Date dateMov;
	private long status;
 
}
