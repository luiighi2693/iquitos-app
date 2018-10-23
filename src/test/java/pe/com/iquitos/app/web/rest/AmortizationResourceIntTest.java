package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Amortization;
import pe.com.iquitos.app.repository.AmortizationRepository;
import pe.com.iquitos.app.repository.search.AmortizationSearchRepository;
import pe.com.iquitos.app.service.AmortizationService;
import pe.com.iquitos.app.service.dto.AmortizationDTO;
import pe.com.iquitos.app.service.mapper.AmortizationMapper;
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
 * Test class for the AmortizationResource REST controller.
 *
 * @see AmortizationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class AmortizationResourceIntTest {

    private static final Double DEFAULT_AMOUNT_TO_PAY = 1D;
    private static final Double UPDATED_AMOUNT_TO_PAY = 2D;

    private static final Double DEFAULT_AMOUNT_PAYED = 1D;
    private static final Double UPDATED_AMOUNT_PAYED = 2D;

    private static final LocalDate DEFAULT_EMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOCUMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GLOSS = "AAAAAAAAAA";
    private static final String UPDATED_GLOSS = "BBBBBBBBBB";

    @Autowired
    private AmortizationRepository amortizationRepository;

    @Autowired
    private AmortizationMapper amortizationMapper;
    
    @Autowired
    private AmortizationService amortizationService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.AmortizationSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmortizationSearchRepository mockAmortizationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmortizationMockMvc;

    private Amortization amortization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationResource amortizationResource = new AmortizationResource(amortizationService);
        this.restAmortizationMockMvc = MockMvcBuilders.standaloneSetup(amortizationResource)
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
    public static Amortization createEntity(EntityManager em) {
        Amortization amortization = new Amortization()
            .amountToPay(DEFAULT_AMOUNT_TO_PAY)
            .amountPayed(DEFAULT_AMOUNT_PAYED)
            .emissionDate(DEFAULT_EMISSION_DATE)
            .documentCode(DEFAULT_DOCUMENT_CODE)
            .gloss(DEFAULT_GLOSS);
        return amortization;
    }

    @Before
    public void initTest() {
        amortization = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortization() throws Exception {
        int databaseSizeBeforeCreate = amortizationRepository.findAll().size();

        // Create the Amortization
        AmortizationDTO amortizationDTO = amortizationMapper.toDto(amortization);
        restAmortizationMockMvc.perform(post("/api/amortizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDTO)))
            .andExpect(status().isCreated());

        // Validate the Amortization in the database
        List<Amortization> amortizationList = amortizationRepository.findAll();
        assertThat(amortizationList).hasSize(databaseSizeBeforeCreate + 1);
        Amortization testAmortization = amortizationList.get(amortizationList.size() - 1);
        assertThat(testAmortization.getAmountToPay()).isEqualTo(DEFAULT_AMOUNT_TO_PAY);
        assertThat(testAmortization.getAmountPayed()).isEqualTo(DEFAULT_AMOUNT_PAYED);
        assertThat(testAmortization.getEmissionDate()).isEqualTo(DEFAULT_EMISSION_DATE);
        assertThat(testAmortization.getDocumentCode()).isEqualTo(DEFAULT_DOCUMENT_CODE);
        assertThat(testAmortization.getGloss()).isEqualTo(DEFAULT_GLOSS);

        // Validate the Amortization in Elasticsearch
        verify(mockAmortizationSearchRepository, times(1)).save(testAmortization);
    }

    @Test
    @Transactional
    public void createAmortizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationRepository.findAll().size();

        // Create the Amortization with an existing ID
        amortization.setId(1L);
        AmortizationDTO amortizationDTO = amortizationMapper.toDto(amortization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationMockMvc.perform(post("/api/amortizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amortization in the database
        List<Amortization> amortizationList = amortizationRepository.findAll();
        assertThat(amortizationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Amortization in Elasticsearch
        verify(mockAmortizationSearchRepository, times(0)).save(amortization);
    }

    @Test
    @Transactional
    public void getAllAmortizations() throws Exception {
        // Initialize the database
        amortizationRepository.saveAndFlush(amortization);

        // Get all the amortizationList
        restAmortizationMockMvc.perform(get("/api/amortizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortization.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountToPay").value(hasItem(DEFAULT_AMOUNT_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPayed").value(hasItem(DEFAULT_AMOUNT_PAYED.doubleValue())))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].gloss").value(hasItem(DEFAULT_GLOSS.toString())));
    }
    
    @Test
    @Transactional
    public void getAmortization() throws Exception {
        // Initialize the database
        amortizationRepository.saveAndFlush(amortization);

        // Get the amortization
        restAmortizationMockMvc.perform(get("/api/amortizations/{id}", amortization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortization.getId().intValue()))
            .andExpect(jsonPath("$.amountToPay").value(DEFAULT_AMOUNT_TO_PAY.doubleValue()))
            .andExpect(jsonPath("$.amountPayed").value(DEFAULT_AMOUNT_PAYED.doubleValue()))
            .andExpect(jsonPath("$.emissionDate").value(DEFAULT_EMISSION_DATE.toString()))
            .andExpect(jsonPath("$.documentCode").value(DEFAULT_DOCUMENT_CODE.toString()))
            .andExpect(jsonPath("$.gloss").value(DEFAULT_GLOSS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmortization() throws Exception {
        // Get the amortization
        restAmortizationMockMvc.perform(get("/api/amortizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortization() throws Exception {
        // Initialize the database
        amortizationRepository.saveAndFlush(amortization);

        int databaseSizeBeforeUpdate = amortizationRepository.findAll().size();

        // Update the amortization
        Amortization updatedAmortization = amortizationRepository.findById(amortization.getId()).get();
        // Disconnect from session so that the updates on updatedAmortization are not directly saved in db
        em.detach(updatedAmortization);
        updatedAmortization
            .amountToPay(UPDATED_AMOUNT_TO_PAY)
            .amountPayed(UPDATED_AMOUNT_PAYED)
            .emissionDate(UPDATED_EMISSION_DATE)
            .documentCode(UPDATED_DOCUMENT_CODE)
            .gloss(UPDATED_GLOSS);
        AmortizationDTO amortizationDTO = amortizationMapper.toDto(updatedAmortization);

        restAmortizationMockMvc.perform(put("/api/amortizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDTO)))
            .andExpect(status().isOk());

        // Validate the Amortization in the database
        List<Amortization> amortizationList = amortizationRepository.findAll();
        assertThat(amortizationList).hasSize(databaseSizeBeforeUpdate);
        Amortization testAmortization = amortizationList.get(amortizationList.size() - 1);
        assertThat(testAmortization.getAmountToPay()).isEqualTo(UPDATED_AMOUNT_TO_PAY);
        assertThat(testAmortization.getAmountPayed()).isEqualTo(UPDATED_AMOUNT_PAYED);
        assertThat(testAmortization.getEmissionDate()).isEqualTo(UPDATED_EMISSION_DATE);
        assertThat(testAmortization.getDocumentCode()).isEqualTo(UPDATED_DOCUMENT_CODE);
        assertThat(testAmortization.getGloss()).isEqualTo(UPDATED_GLOSS);

        // Validate the Amortization in Elasticsearch
        verify(mockAmortizationSearchRepository, times(1)).save(testAmortization);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortization() throws Exception {
        int databaseSizeBeforeUpdate = amortizationRepository.findAll().size();

        // Create the Amortization
        AmortizationDTO amortizationDTO = amortizationMapper.toDto(amortization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationMockMvc.perform(put("/api/amortizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amortization in the database
        List<Amortization> amortizationList = amortizationRepository.findAll();
        assertThat(amortizationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Amortization in Elasticsearch
        verify(mockAmortizationSearchRepository, times(0)).save(amortization);
    }

    @Test
    @Transactional
    public void deleteAmortization() throws Exception {
        // Initialize the database
        amortizationRepository.saveAndFlush(amortization);

        int databaseSizeBeforeDelete = amortizationRepository.findAll().size();

        // Get the amortization
        restAmortizationMockMvc.perform(delete("/api/amortizations/{id}", amortization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Amortization> amortizationList = amortizationRepository.findAll();
        assertThat(amortizationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Amortization in Elasticsearch
        verify(mockAmortizationSearchRepository, times(1)).deleteById(amortization.getId());
    }

    @Test
    @Transactional
    public void searchAmortization() throws Exception {
        // Initialize the database
        amortizationRepository.saveAndFlush(amortization);
        when(mockAmortizationSearchRepository.search(queryStringQuery("id:" + amortization.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amortization), PageRequest.of(0, 1), 1));
        // Search the amortization
        restAmortizationMockMvc.perform(get("/api/_search/amortizations?query=id:" + amortization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortization.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountToPay").value(hasItem(DEFAULT_AMOUNT_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPayed").value(hasItem(DEFAULT_AMOUNT_PAYED.doubleValue())))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].gloss").value(hasItem(DEFAULT_GLOSS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amortization.class);
        Amortization amortization1 = new Amortization();
        amortization1.setId(1L);
        Amortization amortization2 = new Amortization();
        amortization2.setId(amortization1.getId());
        assertThat(amortization1).isEqualTo(amortization2);
        amortization2.setId(2L);
        assertThat(amortization1).isNotEqualTo(amortization2);
        amortization1.setId(null);
        assertThat(amortization1).isNotEqualTo(amortization2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationDTO.class);
        AmortizationDTO amortizationDTO1 = new AmortizationDTO();
        amortizationDTO1.setId(1L);
        AmortizationDTO amortizationDTO2 = new AmortizationDTO();
        assertThat(amortizationDTO1).isNotEqualTo(amortizationDTO2);
        amortizationDTO2.setId(amortizationDTO1.getId());
        assertThat(amortizationDTO1).isEqualTo(amortizationDTO2);
        amortizationDTO2.setId(2L);
        assertThat(amortizationDTO1).isNotEqualTo(amortizationDTO2);
        amortizationDTO1.setId(null);
        assertThat(amortizationDTO1).isNotEqualTo(amortizationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationMapper.fromId(null)).isNull();
    }
}
