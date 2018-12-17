package com.org.ppe.service.impl;

import com.org.ppe.domain.CUSTSTATUS;
import com.org.ppe.repository.CUSTSTATUSRepository;
import com.org.ppe.service.CUSTSTATUSService;
import com.org.ppe.service.dto.CUSTSTATUSDTO;
import com.org.ppe.service.dto.SUMMARYDTO;
import com.org.ppe.service.mapper.CUSTSTATUSMapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Service Implementation for managing CUSTSTATUS.
 */
@Service
@Transactional
public class CUSTSTATUSServiceImpl implements CUSTSTATUSService {

    private final Logger log = LoggerFactory.getLogger(CUSTSTATUSServiceImpl.class);

    private final CUSTSTATUSRepository cUSTSTATUSRepository;

    private final CUSTSTATUSMapper cUSTSTATUSMapper;
    


    public CUSTSTATUSServiceImpl(CUSTSTATUSRepository cUSTSTATUSRepository, CUSTSTATUSMapper cUSTSTATUSMapper) {
        this.cUSTSTATUSRepository = cUSTSTATUSRepository;
        this.cUSTSTATUSMapper = cUSTSTATUSMapper;

    }

    /**
     * Save a cUSTSTATUS.
     *
     * @param cUSTSTATUSDTO the entity to save
     * @return 
     * @return the persisted entity
     */
    @Override
    public void saveAll(List<? extends CUSTSTATUSDTO> list) {
        Iterator<CUSTSTATUSDTO> iter = (Iterator<CUSTSTATUSDTO>) list.iterator();
    	while(iter.hasNext())
    	 cUSTSTATUSRepository.save(cUSTSTATUSMapper.toEntity(iter.next()));
         
    }

    /**
     * Get all the cUSTSTATUSES.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CUSTSTATUSDTO> findAll() {
        log.debug("Request to get all CUSTSTATUSES");
        return cUSTSTATUSRepository.findAll().stream()
            .map(cUSTSTATUSMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one cUSTSTATUS by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CUSTSTATUSDTO> findOne(Long id) {
        log.debug("Request to get CUSTSTATUS : {}", id);
        return cUSTSTATUSRepository.findById(id)
            .map(cUSTSTATUSMapper::toDto);
    }

    /**
     * Delete the cUSTSTATUS by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CUSTSTATUS : {}", id);
        cUSTSTATUSRepository.deleteById(id);
    }
    /**
     * Save a cUSTSTATUS.
     *
     * @param cUSTSTATUSDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CUSTSTATUSDTO save(CUSTSTATUSDTO cUSTSTATUSDTO) {
        log.debug("Request to save CUSTSTATUS : {}", cUSTSTATUSDTO);
        CUSTSTATUS cUSTSTATUS = cUSTSTATUSMapper.toEntity(cUSTSTATUSDTO);
        cUSTSTATUS = cUSTSTATUSRepository.save(cUSTSTATUS);
        return cUSTSTATUSMapper.toDto(cUSTSTATUS);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CUSTSTATUSDTO> findOnlyLatest() {
        log.debug("Request to get all CUSTSTATUSES");
        return cUSTSTATUSRepository.findOnlyLatest().stream()
            .map(cUSTSTATUSMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    @Override
    @Transactional(readOnly = true)
    public List<SUMMARYDTO> findSummary() {
        log.debug("Summary");
      return cUSTSTATUSRepository.findSumary();
    }
    

}
