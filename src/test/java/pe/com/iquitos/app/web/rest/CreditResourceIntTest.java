package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Credit;
import pe.com.iquitos.app.repository.CreditRepository;
import pe.com.iquitos.app.repository.search.CreditSearchRepository;
import pe.com.iquitos.app.service.CreditService;
import pe.com.iquitos.app.service.dto.CreditDTO;
import pe.com.iquitos.app.service.mapper.CreditMapper;
import pe.com.iquitos.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.Collections;
import java.util.List;


import static pe.com.iquitos.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CreditResource REST controller.
 *
 * @see CreditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class CreditResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final LocalDate DEFAULT_EMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_PAYMENT_MODE = 1D;
    private static final Double UPDATED_PAYMENT_MODE = 2D;

    private static final Integer DEFAULT_CREDIT_NUMBER = 1;
    private static final Integer UPDATED_CREDIT_NUMBER = 2;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final LocalDate DEFAULT_LIMIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIMIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREDIT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_NOTE = "BBBBBBBBBB";

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private CreditMapper creditMapper;
    
    @Autowired
    private CreditService creditService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.CreditSearchRepositoryMockConfiguration
     */
    @Autowired
    private CreditSearchRepository mockCreditSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCreditMockMvc;

    private Credit credit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditResource creditResource = new CreditResource(creditService);
        this.restCreditMockMvc = MockMvcBuilders.standaloneSetup(creditResource)
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
    public static Credit createEntity(EntityManager em) {
        Credit credit = new Credit()
            .amount(DEFAULT_AMOUNT)
            .emissionDate(DEFAULT_EMISSION_DATE)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .creditNumber(DEFAULT_CREDIT_NUMBER)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .limitDate(DEFAULT_LIMIT_DATE)
            .creditNote(DEFAULT_CREDIT_NOTE);
        return credit;
    }

    @Before
    public void initTest() {
        credit = createEntity(em);
    }

    @Test
    @Transactional
    public void createCredit() throws Exception {
        int databaseSizeBeforeCreate = creditRepository.findAll().size();

        // Create the Credit
        CreditDTO creditDTO = creditMapper.toDto(credit);
        restCreditMockMvc.perform(post("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isCreated());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeCreate + 1);
        Credit testCredit = creditList.get(creditList.size() - 1);
        assertThat(testCredit.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCredit.getEmissionDate()).isEqualTo(DEFAULT_EMISSION_DATE);
        assertThat(testCredit.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testCredit.getCreditNumber()).isEqualTo(DEFAULT_CREDIT_NUMBER);
        assertThat(testCredit.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testCredit.getLimitDate()).isEqualTo(DEFAULT_LIMIT_DATE);
        assertThat(testCredit.getCreditNote()).isEqualTo(DEFAULT_CREDIT_NOTE);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(1)).save(testCredit);
    }

    @Test
    @Transactional
    public void createCreditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditRepository.findAll().size();

        // Create the Credit with an existing ID
        credit.setId(1L);
        CreditDTO creditDTO = creditMapper.toDto(credit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditMockMvc.perform(post("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeCreate);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(0)).save(credit);
    }

    @Test
    @Transactional
    public void getAllCredits() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList
        restCreditMockMvc.perform(get("/api/credits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credit.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.doubleValue())))
            .andExpect(jsonPath("$.[*].creditNumber").value(hasItem(DEFAULT_CREDIT_NUMBER)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].limitDate").value(hasItem(DEFAULT_LIMIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].creditNote").value(hasItem(DEFAULT_CREDIT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get the credit
        restCreditMockMvc.perform(get("/api/credits/{id}", credit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(credit.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.emissionDate").value(DEFAULT_EMISSION_DATE.toString()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.doubleValue()))
            .andExpect(jsonPath("$.creditNumber").value(DEFAULT_CREDIT_NUMBER))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.limitDate").value(DEFAULT_LIMIT_DATE.toString()))
            .andExpect(jsonPath("$.creditNote").value(DEFAULT_CREDIT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCredit() throws Exception {
        // Get the credit
        restCreditMockMvc.perform(get("/api/credits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        int databaseSizeBeforeUpdate = creditRepository.findAll().size();

        // Update the credit
        Credit updatedCredit = creditRepository.findById(credit.getId()).get();
        // Disconnect from session so that the updates on updatedCredit are not directly saved in db
        em.detach(updatedCredit);
        updatedCredit
            .amount(UPDATED_AMOUNT)
            .emissionDate(UPDATED_EMISSION_DATE)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .creditNumber(UPDATED_CREDIT_NUMBER)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .limitDate(UPDATED_LIMIT_DATE)
            .creditNote(UPDATED_CREDIT_NOTE);
        CreditDTO creditDTO = creditMapper.toDto(updatedCredit);

        restCreditMockMvc.perform(put("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isOk());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeUpdate);
        Credit testCredit = creditList.get(creditList.size() - 1);
        assertThat(testCredit.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCredit.getEmissionDate()).isEqualTo(UPDATED_EMISSION_DATE);
        assertThat(testCredit.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testCredit.getCreditNumber()).isEqualTo(UPDATED_CREDIT_NUMBER);
        assertThat(testCredit.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testCredit.getLimitDate()).isEqualTo(UPDATED_LIMIT_DATE);
        assertThat(testCredit.getCreditNote()).isEqualTo(UPDATED_CREDIT_NOTE);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(1)).save(testCredit);
    }

    @Test
    @Transactional
    public void updateNonExistingCredit() throws Exception {
        int databaseSizeBeforeUpdate = creditRepository.findAll().size();

        // Create the Credit
        CreditDTO creditDTO = creditMapper.toDto(credit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditMockMvc.perform(put("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(0)).save(credit);
    }

    @Test
    @Transactional
    public void deleteCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        int databaseSizeBeforeDelete = creditRepository.findAll().size();

        // Get the credit
        restCreditMockMvc.perform(delete("/api/credits/{id}", credit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Credit in Elasticsearch
        verify(mockCreditSearchRepository, times(1)).deleteById(credit.getId());
    }

    @Test
    @Transactional
    public void searchCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);
        when(mockCreditSearchRepository.search(queryStringQuery("id:" + credit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(credit), PageRequest.of(0, 1), 1));
        // Search the credit
        restCreditMockMvc.perform(get("/api/_search/credits?query=id:" + credit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credit.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.doubleValue())))
            .andExpect(jsonPath("$.[*].creditNumber").value(hasItem(DEFAULT_CREDIT_NUMBER)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].limitDate").value(hasItem(DEFAULT_LIMIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].creditNote").value(hasItem(DEFAULT_CREDIT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Credit.class);
        Credit credit1 = new Credit();
        credit1.setId(1L);
        Credit credit2 = new Credit();
        credit2.setId(credit1.getId());
        assertThat(credit1).isEqualTo(credit2);
        credit2.setId(2L);
        assertThat(credit1).isNotEqualTo(credit2);
        credit1.setId(null);
        assertThat(credit1).isNotEqualTo(credit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditDTO.class);
        CreditDTO creditDTO1 = new CreditDTO();
        creditDTO1.setId(1L);
        CreditDTO creditDTO2 = new CreditDTO();
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
        creditDTO2.setId(creditDTO1.getId());
        assertThat(creditDTO1).isEqualTo(creditDTO2);
        creditDTO2.setId(2L);
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
        creditDTO1.setId(null);
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditMapper.fromId(null)).isNull();
    }
}
