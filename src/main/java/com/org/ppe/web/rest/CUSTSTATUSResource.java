package com.org.ppe.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.org.ppe.service.CUSTSTATUSService;
import com.org.ppe.web.rest.errors.BadRequestAlertException;
import com.org.ppe.web.rest.util.HeaderUtil;
import com.org.ppe.service.dto.CUSTSTATUSDTO;
import com.org.ppe.service.dto.SUMMARYDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CUSTSTATUS.
 */
@RestController
@RequestMapping("/api")
public class CUSTSTATUSResource {

    private final Logger log = LoggerFactory.getLogger(CUSTSTATUSResource.class);

    private static final String ENTITY_NAME = "cUSTSTATUS";

    private final CUSTSTATUSService cUSTSTATUSService;

    public CUSTSTATUSResource(CUSTSTATUSService cUSTSTATUSService) {
        this.cUSTSTATUSService = cUSTSTATUSService;
    }

    /**
     * POST  /custstatuses : Create a new cUSTSTATUS.
     *
     * @param cUSTSTATUSDTO the cUSTSTATUSDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cUSTSTATUSDTO, or with status 400 (Bad Request) if the cUSTSTATUS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/custstatuses")
    @Timed
    public ResponseEntity<CUSTSTATUSDTO> createCUSTSTATUS(@Valid @RequestBody CUSTSTATUSDTO cUSTSTATUSDTO) throws URISyntaxException {
        log.debug("REST request to save CUSTSTATUS : {}", cUSTSTATUSDTO);
        if (cUSTSTATUSDTO.getId() != null) {
            throw new BadRequestAlertException("A new cUSTSTATUS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CUSTSTATUSDTO result = cUSTSTATUSService.save(cUSTSTATUSDTO);
        return ResponseEntity.created(new URI("/api/custstatuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /custstatuses : Updates an existing cUSTSTATUS.
     *
     * @param cUSTSTATUSDTO the cUSTSTATUSDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cUSTSTATUSDTO,
     * or with status 400 (Bad Request) if the cUSTSTATUSDTO is not valid,
     * or with status 500 (Internal Server Error) if the cUSTSTATUSDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/custstatuses")
    @Timed
    public ResponseEntity<CUSTSTATUSDTO> updateCUSTSTATUS(@Valid @RequestBody CUSTSTATUSDTO cUSTSTATUSDTO) throws URISyntaxException {
        log.debug("REST request to update CUSTSTATUS : {}", cUSTSTATUSDTO);
        if (cUSTSTATUSDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CUSTSTATUSDTO result = cUSTSTATUSService.save(cUSTSTATUSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cUSTSTATUSDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /custstatuses : get all the cUSTSTATUSES.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cUSTSTATUSES in body
     */
    @GetMapping("/custstatuses")
    @Timed
    public List<CUSTSTATUSDTO> getAllCUSTSTATUSES() {
        log.debug("REST request to get all CUSTSTATUSES");
        return cUSTSTATUSService.findAll();
    }

    /**
     * GET  /custstatuses/:id : get the "id" cUSTSTATUS.
     *
     * @param id the id of the cUSTSTATUSDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cUSTSTATUSDTO, or with status 404 (Not Found)
     */
    @GetMapping("/custstatuses/{id}")
    @Timed
    public ResponseEntity<CUSTSTATUSDTO> getCUSTSTATUS(@PathVariable Long id) {
        log.debug("REST request to get CUSTSTATUS : {}", id);
        Optional<CUSTSTATUSDTO> cUSTSTATUSDTO = cUSTSTATUSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cUSTSTATUSDTO);
    }

    /**
     * DELETE  /custstatuses/:id : delete the "id" cUSTSTATUS.
     *
     * @param id the id of the cUSTSTATUSDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/custstatuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteCUSTSTATUS(@PathVariable Long id) {
        log.debug("REST request to delete CUSTSTATUS : {}", id);
        cUSTSTATUSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/custstatuses/latest")
    @Timed
    public List<CUSTSTATUSDTO> findOnlyLatest() {
        log.debug("REST request to get all CUSTSTATUSES");
        return cUSTSTATUSService.findOnlyLatest();
    }
    
    @GetMapping("/custstatuses/summary")
    @Timed
    public List<SUMMARYDTO> findSummary() {
        log.debug("REST request to get all summary");
        return cUSTSTATUSService.findSummary();
    }
    
}
