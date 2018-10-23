package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ProviderPayment;
import pe.com.iquitos.app.repository.ProviderPaymentRepository;
import pe.com.iquitos.app.repository.search.ProviderPaymentSearchRepository;
import pe.com.iquitos.app.service.ProviderPaymentService;
import pe.com.iquitos.app.service.dto.ProviderPaymentDTO;
import pe.com.iquitos.app.service.mapper.ProviderPaymentMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the ProviderPaymentResource REST controller.
 *
 * @see ProviderPaymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ProviderPaymentResourceIntTest {

    private static final Double DEFAULT_AMOUNT_TO_PAY = 1D;
    private static final Double UPDATED_AMOUNT_TO_PAY = 2D;

    private static final Double DEFAULT_AMOUNT_PAYED = 1D;
    private static final Double UPDATED_AMOUNT_PAYED = 2D;

    private static final LocalDate DEFAULT_EMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DOCUMENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GLOSA = "AAAAAAAAAA";
    private static final String UPDATED_GLOSA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ProviderPaymentRepository providerPaymentRepository;

    @Autowired
    private ProviderPaymentMapper providerPaymentMapper;
    
    @Autowired
    private ProviderPaymentService providerPaymentService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ProviderPaymentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProviderPaymentSearchRepository mockProviderPaymentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderPaymentMockMvc;

    private ProviderPayment providerPayment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProviderPaymentResource providerPaymentResource = new ProviderPaymentResource(providerPaymentService);
        this.restProviderPaymentMockMvc = MockMvcBuilders.standaloneSetup(providerPaymentResource)
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
    public static ProviderPayment createEntity(EntityManager em) {
        ProviderPayment providerPayment = new ProviderPayment()
            .amountToPay(DEFAULT_AMOUNT_TO_PAY)
            .amountPayed(DEFAULT_AMOUNT_PAYED)
            .emissionDate(DEFAULT_EMISSION_DATE)
            .documentCode(DEFAULT_DOCUMENT_CODE)
            .glosa(DEFAULT_GLOSA)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return providerPayment;
    }

    @Before
    public void initTest() {
        providerPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderPayment() throws Exception {
        int databaseSizeBeforeCreate = providerPaymentRepository.findAll().size();

        // Create the ProviderPayment
        ProviderPaymentDTO providerPaymentDTO = providerPaymentMapper.toDto(providerPayment);
        restProviderPaymentMockMvc.perform(post("/api/provider-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the ProviderPayment in the database
        List<ProviderPayment> providerPaymentList = providerPaymentRepository.findAll();
        assertThat(providerPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderPayment testProviderPayment = providerPaymentList.get(providerPaymentList.size() - 1);
        assertThat(testProviderPayment.getAmountToPay()).isEqualTo(DEFAULT_AMOUNT_TO_PAY);
        assertThat(testProviderPayment.getAmountPayed()).isEqualTo(DEFAULT_AMOUNT_PAYED);
        assertThat(testProviderPayment.getEmissionDate()).isEqualTo(DEFAULT_EMISSION_DATE);
        assertThat(testProviderPayment.getDocumentCode()).isEqualTo(DEFAULT_DOCUMENT_CODE);
        assertThat(testProviderPayment.getGlosa()).isEqualTo(DEFAULT_GLOSA);
        assertThat(testProviderPayment.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProviderPayment.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);

        // Validate the ProviderPayment in Elasticsearch
        verify(mockProviderPaymentSearchRepository, times(1)).save(testProviderPayment);
    }

    @Test
    @Transactional
    public void createProviderPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerPaymentRepository.findAll().size();

        // Create the ProviderPayment with an existing ID
        providerPayment.setId(1L);
        ProviderPaymentDTO providerPaymentDTO = providerPaymentMapper.toDto(providerPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderPaymentMockMvc.perform(post("/api/provider-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProviderPayment in the database
        List<ProviderPayment> providerPaymentList = providerPaymentRepository.findAll();
        assertThat(providerPaymentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProviderPayment in Elasticsearch
        verify(mockProviderPaymentSearchRepository, times(0)).save(providerPayment);
    }

    @Test
    @Transactional
    public void getAllProviderPayments() throws Exception {
        // Initialize the database
        providerPaymentRepository.saveAndFlush(providerPayment);

        // Get all the providerPaymentList
        restProviderPaymentMockMvc.perform(get("/api/provider-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountToPay").value(hasItem(DEFAULT_AMOUNT_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPayed").value(hasItem(DEFAULT_AMOUNT_PAYED.doubleValue())))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getProviderPayment() throws Exception {
        // Initialize the database
        providerPaymentRepository.saveAndFlush(providerPayment);

        // Get the providerPayment
        restProviderPaymentMockMvc.perform(get("/api/provider-payments/{id}", providerPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerPayment.getId().intValue()))
            .andExpect(jsonPath("$.amountToPay").value(DEFAULT_AMOUNT_TO_PAY.doubleValue()))
            .andExpect(jsonPath("$.amountPayed").value(DEFAULT_AMOUNT_PAYED.doubleValue()))
            .andExpect(jsonPath("$.emissionDate").value(DEFAULT_EMISSION_DATE.toString()))
            .andExpect(jsonPath("$.documentCode").value(DEFAULT_DOCUMENT_CODE.toString()))
            .andExpect(jsonPath("$.glosa").value(DEFAULT_GLOSA.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingProviderPayment() throws Exception {
        // Get the providerPayment
        restProviderPaymentMockMvc.perform(get("/api/provider-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderPayment() throws Exception {
        // Initialize the database
        providerPaymentRepository.saveAndFlush(providerPayment);

        int databaseSizeBeforeUpdate = providerPaymentRepository.findAll().size();

        // Update the providerPayment
        ProviderPayment updatedProviderPayment = providerPaymentRepository.findById(providerPayment.getId()).get();
        // Disconnect from session so that the updates on updatedProviderPayment are not directly saved in db
        em.detach(updatedProviderPayment);
        updatedProviderPayment
            .amountToPay(UPDATED_AMOUNT_TO_PAY)
            .amountPayed(UPDATED_AMOUNT_PAYED)
            .emissionDate(UPDATED_EMISSION_DATE)
            .documentCode(UPDATED_DOCUMENT_CODE)
            .glosa(UPDATED_GLOSA)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ProviderPaymentDTO providerPaymentDTO = providerPaymentMapper.toDto(updatedProviderPayment);

        restProviderPaymentMockMvc.perform(put("/api/provider-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the ProviderPayment in the database
        List<ProviderPayment> providerPaymentList = providerPaymentRepository.findAll();
        assertThat(providerPaymentList).hasSize(databaseSizeBeforeUpdate);
        ProviderPayment testProviderPayment = providerPaymentList.get(providerPaymentList.size() - 1);
        assertThat(testProviderPayment.getAmountToPay()).isEqualTo(UPDATED_AMOUNT_TO_PAY);
        assertThat(testProviderPayment.getAmountPayed()).isEqualTo(UPDATED_AMOUNT_PAYED);
        assertThat(testProviderPayment.getEmissionDate()).isEqualTo(UPDATED_EMISSION_DATE);
        assertThat(testProviderPayment.getDocumentCode()).isEqualTo(UPDATED_DOCUMENT_CODE);
        assertThat(testProviderPayment.getGlosa()).isEqualTo(UPDATED_GLOSA);
        assertThat(testProviderPayment.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProviderPayment.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);

        // Validate the ProviderPayment in Elasticsearch
        verify(mockProviderPaymentSearchRepository, times(1)).save(testProviderPayment);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderPayment() throws Exception {
        int databaseSizeBeforeUpdate = providerPaymentRepository.findAll().size();

        // Create the ProviderPayment
        ProviderPaymentDTO providerPaymentDTO = providerPaymentMapper.toDto(providerPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProviderPaymentMockMvc.perform(put("/api/provider-payments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProviderPayment in the database
        List<ProviderPayment> providerPaymentList = providerPaymentRepository.findAll();
        assertThat(providerPaymentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProviderPayment in Elasticsearch
        verify(mockProviderPaymentSearchRepository, times(0)).save(providerPayment);
    }

    @Test
    @Transactional
    public void deleteProviderPayment() throws Exception {
        // Initialize the database
        providerPaymentRepository.saveAndFlush(providerPayment);

        int databaseSizeBeforeDelete = providerPaymentRepository.findAll().size();

        // Get the providerPayment
        restProviderPaymentMockMvc.perform(delete("/api/provider-payments/{id}", providerPayment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProviderPayment> providerPaymentList = providerPaymentRepository.findAll();
        assertThat(providerPaymentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProviderPayment in Elasticsearch
        verify(mockProviderPaymentSearchRepository, times(1)).deleteById(providerPayment.getId());
    }

    @Test
    @Transactional
    public void searchProviderPayment() throws Exception {
        // Initialize the database
        providerPaymentRepository.saveAndFlush(providerPayment);
        when(mockProviderPaymentSearchRepository.search(queryStringQuery("id:" + providerPayment.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(providerPayment), PageRequest.of(0, 1), 1));
        // Search the providerPayment
        restProviderPaymentMockMvc.perform(get("/api/_search/provider-payments?query=id:" + providerPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountToPay").value(hasItem(DEFAULT_AMOUNT_TO_PAY.doubleValue())))
            .andExpect(jsonPath("$.[*].amountPayed").value(hasItem(DEFAULT_AMOUNT_PAYED.doubleValue())))
            .andExpect(jsonPath("$.[*].emissionDate").value(hasItem(DEFAULT_EMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentCode").value(hasItem(DEFAULT_DOCUMENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderPayment.class);
        ProviderPayment providerPayment1 = new ProviderPayment();
        providerPayment1.setId(1L);
        ProviderPayment providerPayment2 = new ProviderPayment();
        providerPayment2.setId(providerPayment1.getId());
        assertThat(providerPayment1).isEqualTo(providerPayment2);
        providerPayment2.setId(2L);
        assertThat(providerPayment1).isNotEqualTo(providerPayment2);
        providerPayment1.setId(null);
        assertThat(providerPayment1).isNotEqualTo(providerPayment2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderPaymentDTO.class);
        ProviderPaymentDTO providerPaymentDTO1 = new ProviderPaymentDTO();
        providerPaymentDTO1.setId(1L);
        ProviderPaymentDTO providerPaymentDTO2 = new ProviderPaymentDTO();
        assertThat(providerPaymentDTO1).isNotEqualTo(providerPaymentDTO2);
        providerPaymentDTO2.setId(providerPaymentDTO1.getId());
        assertThat(providerPaymentDTO1).isEqualTo(providerPaymentDTO2);
        providerPaymentDTO2.setId(2L);
        assertThat(providerPaymentDTO1).isNotEqualTo(providerPaymentDTO2);
        providerPaymentDTO1.setId(null);
        assertThat(providerPaymentDTO1).isNotEqualTo(providerPaymentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(providerPaymentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(providerPaymentMapper.fromId(null)).isNull();
    }
}
