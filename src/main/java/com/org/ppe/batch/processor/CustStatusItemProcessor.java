package com.org.ppe.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.org.ppe.domain.CUSTSTATUS;
import com.org.ppe.service.dto.CUSTSTATUSDTO;


public class CustStatusItemProcessor implements ItemProcessor<CUSTSTATUSDTO, CUSTSTATUS> {
	

	@Override
	public  CUSTSTATUS process(CUSTSTATUSDTO custStatusDto) {
		//copy to entity from dto
		CUSTSTATUS cUSTSTATUS = new CUSTSTATUS();
		cUSTSTATUS.setCustId(custStatusDto.getCustId());
		cUSTSTATUS.setChangeDt(custStatusDto.getChangeDt());
		cUSTSTATUS.setUserId(custStatusDto.getUserId());
		cUSTSTATUS.setPpeStatus(custStatusDto.getPpeStatus());
		
		return cUSTSTATUS;
				
	}

}
