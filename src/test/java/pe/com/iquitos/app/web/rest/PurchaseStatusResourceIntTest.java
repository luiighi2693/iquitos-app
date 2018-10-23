package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.PurchaseStatus;
import pe.com.iquitos.app.repository.PurchaseStatusRepository;
import pe.com.iquitos.app.repository.search.PurchaseStatusSearchRepository;
import pe.com.iquitos.app.service.PurchaseStatusService;
import pe.com.iquitos.app.service.dto.PurchaseStatusDTO;
import pe.com.iquitos.app.service.mapper.PurchaseStatusMapper;
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
 * Test class for the PurchaseStatusResource REST controller.
 *
 * @see PurchaseStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class PurchaseStatusResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private PurchaseStatusRepository purchaseStatusRepository;

    @Autowired
    private PurchaseStatusMapper purchaseStatusMapper;
    
    @Autowired
    private PurchaseStatusService purchaseStatusService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.PurchaseStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseStatusSearchRepository mockPurchaseStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchaseStatusMockMvc;

    private PurchaseStatus purchaseStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseStatusResource purchaseStatusResource = new PurchaseStatusResource(purchaseStatusService);
        this.restPurchaseStatusMockMvc = MockMvcBuilders.standaloneSetup(purchaseStatusResource)
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
    public static PurchaseStatus createEntity(EntityManager em) {
        PurchaseStatus purchaseStatus = new PurchaseStatus()
            .value(DEFAULT_VALUE)
            .metaData(DEFAULT_META_DATA);
        return purchaseStatus;
    }

    @Before
    public void initTest() {
        purchaseStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseStatus() throws Exception {
        int databaseSizeBeforeCreate = purchaseStatusRepository.findAll().size();

        // Create the PurchaseStatus
        PurchaseStatusDTO purchaseStatusDTO = purchaseStatusMapper.toDto(purchaseStatus);
        restPurchaseStatusMockMvc.perform(post("/api/purchase-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseStatus in the database
        List<PurchaseStatus> purchaseStatusList = purchaseStatusRepository.findAll();
        assertThat(purchaseStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseStatus testPurchaseStatus = purchaseStatusList.get(purchaseStatusList.size() - 1);
        assertThat(testPurchaseStatus.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testPurchaseStatus.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the PurchaseStatus in Elasticsearch
        verify(mockPurchaseStatusSearchRepository, times(1)).save(testPurchaseStatus);
    }

    @Test
    @Transactional
    public void createPurchaseStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseStatusRepository.findAll().size();

        // Create the PurchaseStatus with an existing ID
        purchaseStatus.setId(1L);
        PurchaseStatusDTO purchaseStatusDTO = purchaseStatusMapper.toDto(purchaseStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseStatusMockMvc.perform(post("/api/purchase-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseStatus in the database
        List<PurchaseStatus> purchaseStatusList = purchaseStatusRepository.findAll();
        assertThat(purchaseStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the PurchaseStatus in Elasticsearch
        verify(mockPurchaseStatusSearchRepository, times(0)).save(purchaseStatus);
    }

    @Test
    @Transactional
    public void getAllPurchaseStatuses() throws Exception {
        // Initialize the database
        purchaseStatusRepository.saveAndFlush(purchaseStatus);

        // Get all the purchaseStatusList
        restPurchaseStatusMockMvc.perform(get("/api/purchase-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseStatus() throws Exception {
        // Initialize the database
        purchaseStatusRepository.saveAndFlush(purchaseStatus);

        // Get the purchaseStatus
        restPurchaseStatusMockMvc.perform(get("/api/purchase-statuses/{id}", purchaseStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseStatus.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseStatus() throws Exception {
        // Get the purchaseStatus
        restPurchaseStatusMockMvc.perform(get("/api/purchase-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseStatus() throws Exception {
        // Initialize the database
        purchaseStatusRepository.saveAndFlush(purchaseStatus);

        int databaseSizeBeforeUpdate = purchaseStatusRepository.findAll().size();

        // Update the purchaseStatus
        PurchaseStatus updatedPurchaseStatus = purchaseStatusRepository.findById(purchaseStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseStatus are not directly saved in db
        em.detach(updatedPurchaseStatus);
        updatedPurchaseStatus
            .value(UPDATED_VALUE)
            .metaData(UPDATED_META_DATA);
        PurchaseStatusDTO purchaseStatusDTO = purchaseStatusMapper.toDto(updatedPurchaseStatus);

        restPurchaseStatusMockMvc.perform(put("/api/purchase-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseStatusDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseStatus in the database
        List<PurchaseStatus> purchaseStatusList = purchaseStatusRepository.findAll();
        assertThat(purchaseStatusList).hasSize(databaseSizeBeforeUpdate);
        PurchaseStatus testPurchaseStatus = purchaseStatusList.get(purchaseStatusList.size() - 1);
        assertThat(testPurchaseStatus.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testPurchaseStatus.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the PurchaseStatus in Elasticsearch
        verify(mockPurchaseStatusSearchRepository, times(1)).save(testPurchaseStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseStatus() throws Exception {
        int databaseSizeBeforeUpdate = purchaseStatusRepository.findAll().size();

        // Create the PurchaseStatus
        PurchaseStatusDTO purchaseStatusDTO = purchaseStatusMapper.toDto(purchaseStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseStatusMockMvc.perform(put("/api/purchase-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseStatus in the database
        List<PurchaseStatus> purchaseStatusList = purchaseStatusRepository.findAll();
        assertThat(purchaseStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PurchaseStatus in Elasticsearch
        verify(mockPurchaseStatusSearchRepository, times(0)).save(purchaseStatus);
    }

    @Test
    @Transactional
    public void deletePurchaseStatus() throws Exception {
        // Initialize the database
        purchaseStatusRepository.saveAndFlush(purchaseStatus);

        int databaseSizeBeforeDelete = purchaseStatusRepository.findAll().size();

        // Get the purchaseStatus
        restPurchaseStatusMockMvc.perform(delete("/api/purchase-statuses/{id}", purchaseStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseStatus> purchaseStatusList = purchaseStatusRepository.findAll();
        assertThat(purchaseStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PurchaseStatus in Elasticsearch
        verify(mockPurchaseStatusSearchRepository, times(1)).deleteById(purchaseStatus.getId());
    }

    @Test
    @Transactional
    public void searchPurchaseStatus() throws Exception {
        // Initialize the database
        purchaseStatusRepository.saveAndFlush(purchaseStatus);
        when(mockPurchaseStatusSearchRepository.search(queryStringQuery("id:" + purchaseStatus.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(purchaseStatus), PageRequest.of(0, 1), 1));
        // Search the purchaseStatus
        restPurchaseStatusMockMvc.perform(get("/api/_search/purchase-statuses?query=id:" + purchaseStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseStatus.class);
        PurchaseStatus purchaseStatus1 = new PurchaseStatus();
        purchaseStatus1.setId(1L);
        PurchaseStatus purchaseStatus2 = new PurchaseStatus();
        purchaseStatus2.setId(purchaseStatus1.getId());
        assertThat(purchaseStatus1).isEqualTo(purchaseStatus2);
        purchaseStatus2.setId(2L);
        assertThat(purchaseStatus1).isNotEqualTo(purchaseStatus2);
        purchaseStatus1.setId(null);
        assertThat(purchaseStatus1).isNotEqualTo(purchaseStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseStatusDTO.class);
        PurchaseStatusDTO purchaseStatusDTO1 = new PurchaseStatusDTO();
        purchaseStatusDTO1.setId(1L);
        PurchaseStatusDTO purchaseStatusDTO2 = new PurchaseStatusDTO();
        assertThat(purchaseStatusDTO1).isNotEqualTo(purchaseStatusDTO2);
        purchaseStatusDTO2.setId(purchaseStatusDTO1.getId());
        assertThat(purchaseStatusDTO1).isEqualTo(purchaseStatusDTO2);
        purchaseStatusDTO2.setId(2L);
        assertThat(purchaseStatusDTO1).isNotEqualTo(purchaseStatusDTO2);
        purchaseStatusDTO1.setId(null);
        assertThat(purchaseStatusDTO1).isNotEqualTo(purchaseStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseStatusMapper.fromId(null)).isNull();
    }
}
