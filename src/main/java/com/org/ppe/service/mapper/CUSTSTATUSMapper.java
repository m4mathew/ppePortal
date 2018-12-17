package com.org.ppe.service.mapper;

import com.org.ppe.domain.*;
import com.org.ppe.service.dto.CUSTSTATUSDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CUSTSTATUS and its DTO CUSTSTATUSDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CUSTSTATUSMapper extends EntityMapper<CUSTSTATUSDTO, CUSTSTATUS> {



    default CUSTSTATUS fromId(Long id) {
        /*if (id == null) {
            return null;
        }*/
        CUSTSTATUS cUSTSTATUS = new CUSTSTATUS();
        cUSTSTATUS.setId(id);
        return cUSTSTATUS;
    }
}
