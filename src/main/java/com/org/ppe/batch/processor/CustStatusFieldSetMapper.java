package com.org.ppe.batch.processor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.boot.context.properties.bind.BindException;

import com.org.ppe.service.dto.CUSTSTATUSDTO;
public class CustStatusFieldSetMapper implements FieldSetMapper<CUSTSTATUSDTO> {
//custom field set mapper to convert Date.
	@Override
	public CUSTSTATUSDTO mapFieldSet( FieldSet fieldSet) throws BindException {
		CUSTSTATUSDTO dto = new CUSTSTATUSDTO();
		dto.setCustId(fieldSet.readInt("custId"));
		dto.setPpeStatus(fieldSet.readString("ppeStatus"));
		dto.setUserId(fieldSet.readString("userId"));
		
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	     LocalDate localDate = LocalDate.parse(fieldSet.readString("changeDt"),formatter);	
		
	     dto.setChangeDt(localDate);

		return dto;
	}

}