package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.PurchaseHasProduct;
import pe.com.iquitos.app.repository.PurchaseHasProductRepository;
import pe.com.iquitos.app.repository.search.PurchaseHasProductSearchRepository;
import pe.com.iquitos.app.service.PurchaseHasProductService;
import pe.com.iquitos.app.service.dto.PurchaseHasProductDTO;
import pe.com.iquitos.app.service.mapper.PurchaseHasProductMapper;
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
 * Test class for the PurchaseHasProductResource REST controller.
 *
 * @see PurchaseHasProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class PurchaseHasProductResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_TAX = 1D;
    private static final Double UPDATED_TAX = 2D;

    private static final LocalDate DEFAULT_DATE_PURCHASE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PURCHASE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PurchaseHasProductRepository purchaseHasProductRepository;

    @Autowired
    private PurchaseHasProductMapper purchaseHasProductMapper;
    
    @Autowired
    private PurchaseHasProductService purchaseHasProductService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.PurchaseHasProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseHasProductSearchRepository mockPurchaseHasProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchaseHasProductMockMvc;

    private PurchaseHasProduct purchaseHasProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseHasProductResource purchaseHasProductResource = new PurchaseHasProductResource(purchaseHasProductService);
        this.restPurchaseHasProductMockMvc = MockMvcBuilders.standaloneSetup(purchaseHasProductResource)
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
    public static PurchaseHasProduct createEntity(EntityManager em) {
        PurchaseHasProduct purchaseHasProduct = new PurchaseHasProduct()
            .quantity(DEFAULT_QUANTITY)
            .tax(DEFAULT_TAX)
            .datePurchase(DEFAULT_DATE_PURCHASE);
        return purchaseHasProduct;
    }

    @Before
    public void initTest() {
        purchaseHasProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseHasProduct() throws Exception {
        int databaseSizeBeforeCreate = purchaseHasProductRepository.findAll().size();

        // Create the PurchaseHasProduct
        PurchaseHasProductDTO purchaseHasProductDTO = purchaseHasProductMapper.toDto(purchaseHasProduct);
        restPurchaseHasProductMockMvc.perform(post("/api/purchase-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHasProductDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseHasProduct in the database
        List<PurchaseHasProduct> purchaseHasProductList = purchaseHasProductRepository.findAll();
        assertThat(purchaseHasProductList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseHasProduct testPurchaseHasProduct = purchaseHasProductList.get(purchaseHasProductList.size() - 1);
        assertThat(testPurchaseHasProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchaseHasProduct.getTax()).isEqualTo(DEFAULT_TAX);
        assertThat(testPurchaseHasProduct.getDatePurchase()).isEqualTo(DEFAULT_DATE_PURCHASE);

        // Validate the PurchaseHasProduct in Elasticsearch
        verify(mockPurchaseHasProductSearchRepository, times(1)).save(testPurchaseHasProduct);
    }

    @Test
    @Transactional
    public void createPurchaseHasProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseHasProductRepository.findAll().size();

        // Create the PurchaseHasProduct with an existing ID
        purchaseHasProduct.setId(1L);
        PurchaseHasProductDTO purchaseHasProductDTO = purchaseHasProductMapper.toDto(purchaseHasProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseHasProductMockMvc.perform(post("/api/purchase-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHasProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHasProduct in the database
        List<PurchaseHasProduct> purchaseHasProductList = purchaseHasProductRepository.findAll();
        assertThat(purchaseHasProductList).hasSize(databaseSizeBeforeCreate);

        // Validate the PurchaseHasProduct in Elasticsearch
        verify(mockPurchaseHasProductSearchRepository, times(0)).save(purchaseHasProduct);
    }

    @Test
    @Transactional
    public void getAllPurchaseHasProducts() throws Exception {
        // Initialize the database
        purchaseHasProductRepository.saveAndFlush(purchaseHasProduct);

        // Get all the purchaseHasProductList
        restPurchaseHasProductMockMvc.perform(get("/api/purchase-has-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseHasProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].datePurchase").value(hasItem(DEFAULT_DATE_PURCHASE.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseHasProduct() throws Exception {
        // Initialize the database
        purchaseHasProductRepository.saveAndFlush(purchaseHasProduct);

        // Get the purchaseHasProduct
        restPurchaseHasProductMockMvc.perform(get("/api/purchase-has-products/{id}", purchaseHasProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseHasProduct.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.tax").value(DEFAULT_TAX.doubleValue()))
            .andExpect(jsonPath("$.datePurchase").value(DEFAULT_DATE_PURCHASE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseHasProduct() throws Exception {
        // Get the purchaseHasProduct
        restPurchaseHasProductMockMvc.perform(get("/api/purchase-has-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseHasProduct() throws Exception {
        // Initialize the database
        purchaseHasProductRepository.saveAndFlush(purchaseHasProduct);

        int databaseSizeBeforeUpdate = purchaseHasProductRepository.findAll().size();

        // Update the purchaseHasProduct
        PurchaseHasProduct updatedPurchaseHasProduct = purchaseHasProductRepository.findById(purchaseHasProduct.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseHasProduct are not directly saved in db
        em.detach(updatedPurchaseHasProduct);
        updatedPurchaseHasProduct
            .quantity(UPDATED_QUANTITY)
            .tax(UPDATED_TAX)
            .datePurchase(UPDATED_DATE_PURCHASE);
        PurchaseHasProductDTO purchaseHasProductDTO = purchaseHasProductMapper.toDto(updatedPurchaseHasProduct);

        restPurchaseHasProductMockMvc.perform(put("/api/purchase-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHasProductDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseHasProduct in the database
        List<PurchaseHasProduct> purchaseHasProductList = purchaseHasProductRepository.findAll();
        assertThat(purchaseHasProductList).hasSize(databaseSizeBeforeUpdate);
        PurchaseHasProduct testPurchaseHasProduct = purchaseHasProductList.get(purchaseHasProductList.size() - 1);
        assertThat(testPurchaseHasProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchaseHasProduct.getTax()).isEqualTo(UPDATED_TAX);
        assertThat(testPurchaseHasProduct.getDatePurchase()).isEqualTo(UPDATED_DATE_PURCHASE);

        // Validate the PurchaseHasProduct in Elasticsearch
        verify(mockPurchaseHasProductSearchRepository, times(1)).save(testPurchaseHasProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseHasProduct() throws Exception {
        int databaseSizeBeforeUpdate = purchaseHasProductRepository.findAll().size();

        // Create the PurchaseHasProduct
        PurchaseHasProductDTO purchaseHasProductDTO = purchaseHasProductMapper.toDto(purchaseHasProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseHasProductMockMvc.perform(put("/api/purchase-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseHasProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseHasProduct in the database
        List<PurchaseHasProduct> purchaseHasProductList = purchaseHasProductRepository.findAll();
        assertThat(purchaseHasProductList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PurchaseHasProduct in Elasticsearch
        verify(mockPurchaseHasProductSearchRepository, times(0)).save(purchaseHasProduct);
    }

    @Test
    @Transactional
    public void deletePurchaseHasProduct() throws Exception {
        // Initialize the database
        purchaseHasProductRepository.saveAndFlush(purchaseHasProduct);

        int databaseSizeBeforeDelete = purchaseHasProductRepository.findAll().size();

        // Get the purchaseHasProduct
        restPurchaseHasProductMockMvc.perform(delete("/api/purchase-has-products/{id}", purchaseHasProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchaseHasProduct> purchaseHasProductList = purchaseHasProductRepository.findAll();
        assertThat(purchaseHasProductList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PurchaseHasProduct in Elasticsearch
        verify(mockPurchaseHasProductSearchRepository, times(1)).deleteById(purchaseHasProduct.getId());
    }

    @Test
    @Transactional
    public void searchPurchaseHasProduct() throws Exception {
        // Initialize the database
        purchaseHasProductRepository.saveAndFlush(purchaseHasProduct);
        when(mockPurchaseHasProductSearchRepository.search(queryStringQuery("id:" + purchaseHasProduct.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(purchaseHasProduct), PageRequest.of(0, 1), 1));
        // Search the purchaseHasProduct
        restPurchaseHasProductMockMvc.perform(get("/api/_search/purchase-has-products?query=id:" + purchaseHasProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseHasProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].tax").value(hasItem(DEFAULT_TAX.doubleValue())))
            .andExpect(jsonPath("$.[*].datePurchase").value(hasItem(DEFAULT_DATE_PURCHASE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseHasProduct.class);
        PurchaseHasProduct purchaseHasProduct1 = new PurchaseHasProduct();
        purchaseHasProduct1.setId(1L);
        PurchaseHasProduct purchaseHasProduct2 = new PurchaseHasProduct();
        purchaseHasProduct2.setId(purchaseHasProduct1.getId());
        assertThat(purchaseHasProduct1).isEqualTo(purchaseHasProduct2);
        purchaseHasProduct2.setId(2L);
        assertThat(purchaseHasProduct1).isNotEqualTo(purchaseHasProduct2);
        purchaseHasProduct1.setId(null);
        assertThat(purchaseHasProduct1).isNotEqualTo(purchaseHasProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseHasProductDTO.class);
        PurchaseHasProductDTO purchaseHasProductDTO1 = new PurchaseHasProductDTO();
        purchaseHasProductDTO1.setId(1L);
        PurchaseHasProductDTO purchaseHasProductDTO2 = new PurchaseHasProductDTO();
        assertThat(purchaseHasProductDTO1).isNotEqualTo(purchaseHasProductDTO2);
        purchaseHasProductDTO2.setId(purchaseHasProductDTO1.getId());
        assertThat(purchaseHasProductDTO1).isEqualTo(purchaseHasProductDTO2);
        purchaseHasProductDTO2.setId(2L);
        assertThat(purchaseHasProductDTO1).isNotEqualTo(purchaseHasProductDTO2);
        purchaseHasProductDTO1.setId(null);
        assertThat(purchaseHasProductDTO1).isNotEqualTo(purchaseHasProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseHasProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseHasProductMapper.fromId(null)).isNull();
    }
}
