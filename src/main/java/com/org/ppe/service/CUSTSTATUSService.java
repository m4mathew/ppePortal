package com.org.ppe.service;

import com.org.ppe.service.dto.CUSTSTATUSDTO;
import com.org.ppe.service.dto.SUMMARYDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CUSTSTATUS.
 */
public interface CUSTSTATUSService {

    /**
     * Save a cUSTSTATUS.
     *
     * @param cUSTSTATUSDTO the entity to save
     * @return the persisted entity
     */
    CUSTSTATUSDTO save(CUSTSTATUSDTO cUSTSTATUSDTO);

    /**
     * Get all the cUSTSTATUSES.
     *
     * @return the list of entities
     */
    List<CUSTSTATUSDTO> findAll();


    /**
     * Get the "id" cUSTSTATUS.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CUSTSTATUSDTO> findOne(Long id);

    /**
     * Delete the "id" cUSTSTATUS.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

	void saveAll( List<? extends CUSTSTATUSDTO> list);
	
	 List<CUSTSTATUSDTO> findOnlyLatest();
	 
	 List<SUMMARYDTO> findSummary();

}
