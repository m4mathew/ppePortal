package com.org.ppe.web.rest;

import com.org.ppe.PpeportalApp;

import com.org.ppe.domain.CUSTSTATUS;
import com.org.ppe.repository.CUSTSTATUSRepository;
import com.org.ppe.service.CUSTSTATUSService;
import com.org.ppe.service.dto.CUSTSTATUSDTO;
import com.org.ppe.service.mapper.CUSTSTATUSMapper;
import com.org.ppe.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.org.ppe.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CUSTSTATUSResource REST controller.
 *
 * @see CUSTSTATUSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PpeportalApp.class)
public class CUSTSTATUSResourceIntTest {

    private static final Integer DEFAULT_CUST_ID = 1;
    private static final Integer UPDATED_CUST_ID = 2;

    private static final String DEFAULT_PPE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PPE_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CHANGE_DT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHANGE_DT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private CUSTSTATUSRepository cUSTSTATUSRepository;


    @Autowired
    private CUSTSTATUSMapper cUSTSTATUSMapper;
    

    @Autowired
    private CUSTSTATUSService cUSTSTATUSService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCUSTSTATUSMockMvc;

    private CUSTSTATUS cUSTSTATUS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CUSTSTATUSResource cUSTSTATUSResource = new CUSTSTATUSResource(cUSTSTATUSService);
        this.restCUSTSTATUSMockMvc = MockMvcBuilders.standaloneSetup(cUSTSTATUSResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CUSTSTATUS createEntity(EntityManager em) {
        CUSTSTATUS cUSTSTATUS = new CUSTSTATUS()
            .custId(DEFAULT_CUST_ID)
            .ppeStatus(DEFAULT_PPE_STATUS)
            .changeDt(DEFAULT_CHANGE_DT)
            .userId(DEFAULT_USER_ID);
        return cUSTSTATUS;
    }

    @Before
    public void initTest() {
        cUSTSTATUS = createEntity(em);
    }

    @Test
    @Transactional
    public void createCUSTSTATUS() throws Exception {
        int databaseSizeBeforeCreate = cUSTSTATUSRepository.findAll().size();

        // Create the CUSTSTATUS
        CUSTSTATUSDTO cUSTSTATUSDTO = cUSTSTATUSMapper.toDto(cUSTSTATUS);
        restCUSTSTATUSMockMvc.perform(post("/api/custstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTSTATUSDTO)))
            .andExpect(status().isCreated());

        // Validate the CUSTSTATUS in the database
        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeCreate + 1);
        CUSTSTATUS testCUSTSTATUS = cUSTSTATUSList.get(cUSTSTATUSList.size() - 1);
        assertThat(testCUSTSTATUS.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testCUSTSTATUS.getPpeStatus()).isEqualTo(DEFAULT_PPE_STATUS);
        assertThat(testCUSTSTATUS.getChangeDt()).isEqualTo(DEFAULT_CHANGE_DT);
        assertThat(testCUSTSTATUS.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createCUSTSTATUSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cUSTSTATUSRepository.findAll().size();

        // Create the CUSTSTATUS with an existing ID
        cUSTSTATUS.setId(1L);
        CUSTSTATUSDTO cUSTSTATUSDTO = cUSTSTATUSMapper.toDto(cUSTSTATUS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCUSTSTATUSMockMvc.perform(post("/api/custstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTSTATUSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CUSTSTATUS in the database
        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCustIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cUSTSTATUSRepository.findAll().size();
        // set the field null
        cUSTSTATUS.setCustId(null);

        // Create the CUSTSTATUS, which fails.
        CUSTSTATUSDTO cUSTSTATUSDTO = cUSTSTATUSMapper.toDto(cUSTSTATUS);

        restCUSTSTATUSMockMvc.perform(post("/api/custstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTSTATUSDTO)))
            .andExpect(status().isBadRequest());

        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPpeStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = cUSTSTATUSRepository.findAll().size();
        // set the field null
        cUSTSTATUS.setPpeStatus(null);

        // Create the CUSTSTATUS, which fails.
        CUSTSTATUSDTO cUSTSTATUSDTO = cUSTSTATUSMapper.toDto(cUSTSTATUS);

        restCUSTSTATUSMockMvc.perform(post("/api/custstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTSTATUSDTO)))
            .andExpect(status().isBadRequest());

        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = cUSTSTATUSRepository.findAll().size();
        // set the field null
        cUSTSTATUS.setUserId(null);

        // Create the CUSTSTATUS, which fails.
        CUSTSTATUSDTO cUSTSTATUSDTO = cUSTSTATUSMapper.toDto(cUSTSTATUS);

        restCUSTSTATUSMockMvc.perform(post("/api/custstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTSTATUSDTO)))
            .andExpect(status().isBadRequest());

        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCUSTSTATUSES() throws Exception {
        // Initialize the database
        cUSTSTATUSRepository.saveAndFlush(cUSTSTATUS);

        // Get all the cUSTSTATUSList
        restCUSTSTATUSMockMvc.perform(get("/api/custstatuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cUSTSTATUS.getId().intValue())))
            .andExpect(jsonPath("$.[*].custId").value(hasItem(DEFAULT_CUST_ID)))
            .andExpect(jsonPath("$.[*].ppeStatus").value(hasItem(DEFAULT_PPE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].changeDt").value(hasItem(DEFAULT_CHANGE_DT.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }
    

    @Test
    @Transactional
    public void getCUSTSTATUS() throws Exception {
        // Initialize the database
        cUSTSTATUSRepository.saveAndFlush(cUSTSTATUS);

        // Get the cUSTSTATUS
        restCUSTSTATUSMockMvc.perform(get("/api/custstatuses/{id}", cUSTSTATUS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cUSTSTATUS.getId().intValue()))
            .andExpect(jsonPath("$.custId").value(DEFAULT_CUST_ID))
            .andExpect(jsonPath("$.ppeStatus").value(DEFAULT_PPE_STATUS.toString()))
            .andExpect(jsonPath("$.changeDt").value(DEFAULT_CHANGE_DT.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCUSTSTATUS() throws Exception {
        // Get the cUSTSTATUS
        restCUSTSTATUSMockMvc.perform(get("/api/custstatuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCUSTSTATUS() throws Exception {
        // Initialize the database
        cUSTSTATUSRepository.saveAndFlush(cUSTSTATUS);

        int databaseSizeBeforeUpdate = cUSTSTATUSRepository.findAll().size();

        // Update the cUSTSTATUS
        CUSTSTATUS updatedCUSTSTATUS = cUSTSTATUSRepository.findById(cUSTSTATUS.getId()).get();
        // Disconnect from session so that the updates on updatedCUSTSTATUS are not directly saved in db
        em.detach(updatedCUSTSTATUS);
        updatedCUSTSTATUS
            .custId(UPDATED_CUST_ID)
            .ppeStatus(UPDATED_PPE_STATUS)
            .changeDt(UPDATED_CHANGE_DT)
            .userId(UPDATED_USER_ID);
        CUSTSTATUSDTO cUSTSTATUSDTO = cUSTSTATUSMapper.toDto(updatedCUSTSTATUS);

        restCUSTSTATUSMockMvc.perform(put("/api/custstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTSTATUSDTO)))
            .andExpect(status().isOk());

        // Validate the CUSTSTATUS in the database
        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeUpdate);
        CUSTSTATUS testCUSTSTATUS = cUSTSTATUSList.get(cUSTSTATUSList.size() - 1);
        assertThat(testCUSTSTATUS.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testCUSTSTATUS.getPpeStatus()).isEqualTo(UPDATED_PPE_STATUS);
        assertThat(testCUSTSTATUS.getChangeDt()).isEqualTo(UPDATED_CHANGE_DT);
        assertThat(testCUSTSTATUS.getUserId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingCUSTSTATUS() throws Exception {
        int databaseSizeBeforeUpdate = cUSTSTATUSRepository.findAll().size();

        // Create the CUSTSTATUS
        CUSTSTATUSDTO cUSTSTATUSDTO = cUSTSTATUSMapper.toDto(cUSTSTATUS);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCUSTSTATUSMockMvc.perform(put("/api/custstatuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cUSTSTATUSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CUSTSTATUS in the database
        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCUSTSTATUS() throws Exception {
        // Initialize the database
        cUSTSTATUSRepository.saveAndFlush(cUSTSTATUS);

        int databaseSizeBeforeDelete = cUSTSTATUSRepository.findAll().size();

        // Get the cUSTSTATUS
        restCUSTSTATUSMockMvc.perform(delete("/api/custstatuses/{id}", cUSTSTATUS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CUSTSTATUS> cUSTSTATUSList = cUSTSTATUSRepository.findAll();
        assertThat(cUSTSTATUSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CUSTSTATUS.class);
        CUSTSTATUS cUSTSTATUS1 = new CUSTSTATUS();
        cUSTSTATUS1.setId(1L);
        CUSTSTATUS cUSTSTATUS2 = new CUSTSTATUS();
        cUSTSTATUS2.setId(cUSTSTATUS1.getId());
        assertThat(cUSTSTATUS1).isEqualTo(cUSTSTATUS2);
        cUSTSTATUS2.setId(2L);
        assertThat(cUSTSTATUS1).isNotEqualTo(cUSTSTATUS2);
        cUSTSTATUS1.setId(null);
        assertThat(cUSTSTATUS1).isNotEqualTo(cUSTSTATUS2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CUSTSTATUSDTO.class);
        CUSTSTATUSDTO cUSTSTATUSDTO1 = new CUSTSTATUSDTO();
        cUSTSTATUSDTO1.setId(1L);
        CUSTSTATUSDTO cUSTSTATUSDTO2 = new CUSTSTATUSDTO();
        assertThat(cUSTSTATUSDTO1).isNotEqualTo(cUSTSTATUSDTO2);
        cUSTSTATUSDTO2.setId(cUSTSTATUSDTO1.getId());
        assertThat(cUSTSTATUSDTO1).isEqualTo(cUSTSTATUSDTO2);
        cUSTSTATUSDTO2.setId(2L);
        assertThat(cUSTSTATUSDTO1).isNotEqualTo(cUSTSTATUSDTO2);
        cUSTSTATUSDTO1.setId(null);
        assertThat(cUSTSTATUSDTO1).isNotEqualTo(cUSTSTATUSDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cUSTSTATUSMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cUSTSTATUSMapper.fromId(null)).isNull();
    }
}
