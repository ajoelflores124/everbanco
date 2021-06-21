package com.everis.representativeservice.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "representative")
public class Representative implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7437142667967979787L;
	
	private String id;
	@Field(name = "num_doc_rep")
    private String numDocRep;
	@Field(name = "name_rep")
    private String nameRep;
	@Field(name = "lastname_rep")
    private String lastnameRep;
	@Field(name = "email_rep")
    private String emailRep;
	@Field(name = "address_rep")
    private String addressRep;
	@Field(name = "phone_rep")
	private String phoneRep;
	/*@Field(name = "type_rep")
    private String typeRep;*/
	@Field(name = "status")
    private long status;
    

}
