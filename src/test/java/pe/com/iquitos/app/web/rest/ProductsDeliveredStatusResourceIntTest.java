package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ProductsDeliveredStatus;
import pe.com.iquitos.app.repository.ProductsDeliveredStatusRepository;
import pe.com.iquitos.app.repository.search.ProductsDeliveredStatusSearchRepository;
import pe.com.iquitos.app.service.ProductsDeliveredStatusService;
import pe.com.iquitos.app.service.dto.ProductsDeliveredStatusDTO;
import pe.com.iquitos.app.service.mapper.ProductsDeliveredStatusMapper;
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
 * Test class for the ProductsDeliveredStatusResource REST controller.
 *
 * @see ProductsDeliveredStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ProductsDeliveredStatusResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private ProductsDeliveredStatusRepository productsDeliveredStatusRepository;

    @Autowired
    private ProductsDeliveredStatusMapper productsDeliveredStatusMapper;
    
    @Autowired
    private ProductsDeliveredStatusService productsDeliveredStatusService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ProductsDeliveredStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductsDeliveredStatusSearchRepository mockProductsDeliveredStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductsDeliveredStatusMockMvc;

    private ProductsDeliveredStatus productsDeliveredStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductsDeliveredStatusResource productsDeliveredStatusResource = new ProductsDeliveredStatusResource(productsDeliveredStatusService);
        this.restProductsDeliveredStatusMockMvc = MockMvcBuilders.standaloneSetup(productsDeliveredStatusResource)
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
    public static ProductsDeliveredStatus createEntity(EntityManager em) {
        ProductsDeliveredStatus productsDeliveredStatus = new ProductsDeliveredStatus()
            .value(DEFAULT_VALUE)
            .metaData(DEFAULT_META_DATA);
        return productsDeliveredStatus;
    }

    @Before
    public void initTest() {
        productsDeliveredStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductsDeliveredStatus() throws Exception {
        int databaseSizeBeforeCreate = productsDeliveredStatusRepository.findAll().size();

        // Create the ProductsDeliveredStatus
        ProductsDeliveredStatusDTO productsDeliveredStatusDTO = productsDeliveredStatusMapper.toDto(productsDeliveredStatus);
        restProductsDeliveredStatusMockMvc.perform(post("/api/products-delivered-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDeliveredStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductsDeliveredStatus in the database
        List<ProductsDeliveredStatus> productsDeliveredStatusList = productsDeliveredStatusRepository.findAll();
        assertThat(productsDeliveredStatusList).hasSize(databaseSizeBeforeCreate + 1);
        ProductsDeliveredStatus testProductsDeliveredStatus = productsDeliveredStatusList.get(productsDeliveredStatusList.size() - 1);
        assertThat(testProductsDeliveredStatus.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testProductsDeliveredStatus.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the ProductsDeliveredStatus in Elasticsearch
        verify(mockProductsDeliveredStatusSearchRepository, times(1)).save(testProductsDeliveredStatus);
    }

    @Test
    @Transactional
    public void createProductsDeliveredStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsDeliveredStatusRepository.findAll().size();

        // Create the ProductsDeliveredStatus with an existing ID
        productsDeliveredStatus.setId(1L);
        ProductsDeliveredStatusDTO productsDeliveredStatusDTO = productsDeliveredStatusMapper.toDto(productsDeliveredStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsDeliveredStatusMockMvc.perform(post("/api/products-delivered-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDeliveredStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsDeliveredStatus in the database
        List<ProductsDeliveredStatus> productsDeliveredStatusList = productsDeliveredStatusRepository.findAll();
        assertThat(productsDeliveredStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductsDeliveredStatus in Elasticsearch
        verify(mockProductsDeliveredStatusSearchRepository, times(0)).save(productsDeliveredStatus);
    }

    @Test
    @Transactional
    public void getAllProductsDeliveredStatuses() throws Exception {
        // Initialize the database
        productsDeliveredStatusRepository.saveAndFlush(productsDeliveredStatus);

        // Get all the productsDeliveredStatusList
        restProductsDeliveredStatusMockMvc.perform(get("/api/products-delivered-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsDeliveredStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getProductsDeliveredStatus() throws Exception {
        // Initialize the database
        productsDeliveredStatusRepository.saveAndFlush(productsDeliveredStatus);

        // Get the productsDeliveredStatus
        restProductsDeliveredStatusMockMvc.perform(get("/api/products-delivered-statuses/{id}", productsDeliveredStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productsDeliveredStatus.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductsDeliveredStatus() throws Exception {
        // Get the productsDeliveredStatus
        restProductsDeliveredStatusMockMvc.perform(get("/api/products-delivered-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductsDeliveredStatus() throws Exception {
        // Initialize the database
        productsDeliveredStatusRepository.saveAndFlush(productsDeliveredStatus);

        int databaseSizeBeforeUpdate = productsDeliveredStatusRepository.findAll().size();

        // Update the productsDeliveredStatus
        ProductsDeliveredStatus updatedProductsDeliveredStatus = productsDeliveredStatusRepository.findById(productsDeliveredStatus.getId()).get();
        // Disconnect from session so that the updates on updatedProductsDeliveredStatus are not directly saved in db
        em.detach(updatedProductsDeliveredStatus);
        updatedProductsDeliveredStatus
            .value(UPDATED_VALUE)
            .metaData(UPDATED_META_DATA);
        ProductsDeliveredStatusDTO productsDeliveredStatusDTO = productsDeliveredStatusMapper.toDto(updatedProductsDeliveredStatus);

        restProductsDeliveredStatusMockMvc.perform(put("/api/products-delivered-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDeliveredStatusDTO)))
            .andExpect(status().isOk());

        // Validate the ProductsDeliveredStatus in the database
        List<ProductsDeliveredStatus> productsDeliveredStatusList = productsDeliveredStatusRepository.findAll();
        assertThat(productsDeliveredStatusList).hasSize(databaseSizeBeforeUpdate);
        ProductsDeliveredStatus testProductsDeliveredStatus = productsDeliveredStatusList.get(productsDeliveredStatusList.size() - 1);
        assertThat(testProductsDeliveredStatus.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testProductsDeliveredStatus.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the ProductsDeliveredStatus in Elasticsearch
        verify(mockProductsDeliveredStatusSearchRepository, times(1)).save(testProductsDeliveredStatus);
    }

    @Test
    @Transactional
    public void updateNonExistingProductsDeliveredStatus() throws Exception {
        int databaseSizeBeforeUpdate = productsDeliveredStatusRepository.findAll().size();

        // Create the ProductsDeliveredStatus
        ProductsDeliveredStatusDTO productsDeliveredStatusDTO = productsDeliveredStatusMapper.toDto(productsDeliveredStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsDeliveredStatusMockMvc.perform(put("/api/products-delivered-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDeliveredStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductsDeliveredStatus in the database
        List<ProductsDeliveredStatus> productsDeliveredStatusList = productsDeliveredStatusRepository.findAll();
        assertThat(productsDeliveredStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductsDeliveredStatus in Elasticsearch
        verify(mockProductsDeliveredStatusSearchRepository, times(0)).save(productsDeliveredStatus);
    }

    @Test
    @Transactional
    public void deleteProductsDeliveredStatus() throws Exception {
        // Initialize the database
        productsDeliveredStatusRepository.saveAndFlush(productsDeliveredStatus);

        int databaseSizeBeforeDelete = productsDeliveredStatusRepository.findAll().size();

        // Get the productsDeliveredStatus
        restProductsDeliveredStatusMockMvc.perform(delete("/api/products-delivered-statuses/{id}", productsDeliveredStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductsDeliveredStatus> productsDeliveredStatusList = productsDeliveredStatusRepository.findAll();
        assertThat(productsDeliveredStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductsDeliveredStatus in Elasticsearch
        verify(mockProductsDeliveredStatusSearchRepository, times(1)).deleteById(productsDeliveredStatus.getId());
    }

    @Test
    @Transactional
    public void searchProductsDeliveredStatus() throws Exception {
        // Initialize the database
        productsDeliveredStatusRepository.saveAndFlush(productsDeliveredStatus);
        when(mockProductsDeliveredStatusSearchRepository.search(queryStringQuery("id:" + productsDeliveredStatus.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productsDeliveredStatus), PageRequest.of(0, 1), 1));
        // Search the productsDeliveredStatus
        restProductsDeliveredStatusMockMvc.perform(get("/api/_search/products-delivered-statuses?query=id:" + productsDeliveredStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productsDeliveredStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsDeliveredStatus.class);
        ProductsDeliveredStatus productsDeliveredStatus1 = new ProductsDeliveredStatus();
        productsDeliveredStatus1.setId(1L);
        ProductsDeliveredStatus productsDeliveredStatus2 = new ProductsDeliveredStatus();
        productsDeliveredStatus2.setId(productsDeliveredStatus1.getId());
        assertThat(productsDeliveredStatus1).isEqualTo(productsDeliveredStatus2);
        productsDeliveredStatus2.setId(2L);
        assertThat(productsDeliveredStatus1).isNotEqualTo(productsDeliveredStatus2);
        productsDeliveredStatus1.setId(null);
        assertThat(productsDeliveredStatus1).isNotEqualTo(productsDeliveredStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsDeliveredStatusDTO.class);
        ProductsDeliveredStatusDTO productsDeliveredStatusDTO1 = new ProductsDeliveredStatusDTO();
        productsDeliveredStatusDTO1.setId(1L);
        ProductsDeliveredStatusDTO productsDeliveredStatusDTO2 = new ProductsDeliveredStatusDTO();
        assertThat(productsDeliveredStatusDTO1).isNotEqualTo(productsDeliveredStatusDTO2);
        productsDeliveredStatusDTO2.setId(productsDeliveredStatusDTO1.getId());
        assertThat(productsDeliveredStatusDTO1).isEqualTo(productsDeliveredStatusDTO2);
        productsDeliveredStatusDTO2.setId(2L);
        assertThat(productsDeliveredStatusDTO1).isNotEqualTo(productsDeliveredStatusDTO2);
        productsDeliveredStatusDTO1.setId(null);
        assertThat(productsDeliveredStatusDTO1).isNotEqualTo(productsDeliveredStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productsDeliveredStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productsDeliveredStatusMapper.fromId(null)).isNull();
    }
}
