package com.everis.transactionservice.entity;

import java.io.Serializable;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Representative implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4784554276376346978L;
	

	@Field(name = "num_doc_rep")
	private String numDocRep;
	@Field(name = "name_rep")
    private String nameRep;
	@Field(name = "lastname_rep")
    private String lastnameRep;
	@Field(name = "type_rep")
    private String typeRep;

}
