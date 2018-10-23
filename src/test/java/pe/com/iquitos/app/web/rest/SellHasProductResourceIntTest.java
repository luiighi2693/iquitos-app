package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.SellHasProduct;
import pe.com.iquitos.app.repository.SellHasProductRepository;
import pe.com.iquitos.app.repository.search.SellHasProductSearchRepository;
import pe.com.iquitos.app.service.SellHasProductService;
import pe.com.iquitos.app.service.dto.SellHasProductDTO;
import pe.com.iquitos.app.service.mapper.SellHasProductMapper;
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
 * Test class for the SellHasProductResource REST controller.
 *
 * @see SellHasProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class SellHasProductResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    @Autowired
    private SellHasProductRepository sellHasProductRepository;

    @Autowired
    private SellHasProductMapper sellHasProductMapper;
    
    @Autowired
    private SellHasProductService sellHasProductService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.SellHasProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private SellHasProductSearchRepository mockSellHasProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSellHasProductMockMvc;

    private SellHasProduct sellHasProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SellHasProductResource sellHasProductResource = new SellHasProductResource(sellHasProductService);
        this.restSellHasProductMockMvc = MockMvcBuilders.standaloneSetup(sellHasProductResource)
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
    public static SellHasProduct createEntity(EntityManager em) {
        SellHasProduct sellHasProduct = new SellHasProduct()
            .quantity(DEFAULT_QUANTITY)
            .discount(DEFAULT_DISCOUNT);
        return sellHasProduct;
    }

    @Before
    public void initTest() {
        sellHasProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createSellHasProduct() throws Exception {
        int databaseSizeBeforeCreate = sellHasProductRepository.findAll().size();

        // Create the SellHasProduct
        SellHasProductDTO sellHasProductDTO = sellHasProductMapper.toDto(sellHasProduct);
        restSellHasProductMockMvc.perform(post("/api/sell-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellHasProductDTO)))
            .andExpect(status().isCreated());

        // Validate the SellHasProduct in the database
        List<SellHasProduct> sellHasProductList = sellHasProductRepository.findAll();
        assertThat(sellHasProductList).hasSize(databaseSizeBeforeCreate + 1);
        SellHasProduct testSellHasProduct = sellHasProductList.get(sellHasProductList.size() - 1);
        assertThat(testSellHasProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testSellHasProduct.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);

        // Validate the SellHasProduct in Elasticsearch
        verify(mockSellHasProductSearchRepository, times(1)).save(testSellHasProduct);
    }

    @Test
    @Transactional
    public void createSellHasProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sellHasProductRepository.findAll().size();

        // Create the SellHasProduct with an existing ID
        sellHasProduct.setId(1L);
        SellHasProductDTO sellHasProductDTO = sellHasProductMapper.toDto(sellHasProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSellHasProductMockMvc.perform(post("/api/sell-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellHasProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SellHasProduct in the database
        List<SellHasProduct> sellHasProductList = sellHasProductRepository.findAll();
        assertThat(sellHasProductList).hasSize(databaseSizeBeforeCreate);

        // Validate the SellHasProduct in Elasticsearch
        verify(mockSellHasProductSearchRepository, times(0)).save(sellHasProduct);
    }

    @Test
    @Transactional
    public void getAllSellHasProducts() throws Exception {
        // Initialize the database
        sellHasProductRepository.saveAndFlush(sellHasProduct);

        // Get all the sellHasProductList
        restSellHasProductMockMvc.perform(get("/api/sell-has-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sellHasProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSellHasProduct() throws Exception {
        // Initialize the database
        sellHasProductRepository.saveAndFlush(sellHasProduct);

        // Get the sellHasProduct
        restSellHasProductMockMvc.perform(get("/api/sell-has-products/{id}", sellHasProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sellHasProduct.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSellHasProduct() throws Exception {
        // Get the sellHasProduct
        restSellHasProductMockMvc.perform(get("/api/sell-has-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSellHasProduct() throws Exception {
        // Initialize the database
        sellHasProductRepository.saveAndFlush(sellHasProduct);

        int databaseSizeBeforeUpdate = sellHasProductRepository.findAll().size();

        // Update the sellHasProduct
        SellHasProduct updatedSellHasProduct = sellHasProductRepository.findById(sellHasProduct.getId()).get();
        // Disconnect from session so that the updates on updatedSellHasProduct are not directly saved in db
        em.detach(updatedSellHasProduct);
        updatedSellHasProduct
            .quantity(UPDATED_QUANTITY)
            .discount(UPDATED_DISCOUNT);
        SellHasProductDTO sellHasProductDTO = sellHasProductMapper.toDto(updatedSellHasProduct);

        restSellHasProductMockMvc.perform(put("/api/sell-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellHasProductDTO)))
            .andExpect(status().isOk());

        // Validate the SellHasProduct in the database
        List<SellHasProduct> sellHasProductList = sellHasProductRepository.findAll();
        assertThat(sellHasProductList).hasSize(databaseSizeBeforeUpdate);
        SellHasProduct testSellHasProduct = sellHasProductList.get(sellHasProductList.size() - 1);
        assertThat(testSellHasProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testSellHasProduct.getDiscount()).isEqualTo(UPDATED_DISCOUNT);

        // Validate the SellHasProduct in Elasticsearch
        verify(mockSellHasProductSearchRepository, times(1)).save(testSellHasProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingSellHasProduct() throws Exception {
        int databaseSizeBeforeUpdate = sellHasProductRepository.findAll().size();

        // Create the SellHasProduct
        SellHasProductDTO sellHasProductDTO = sellHasProductMapper.toDto(sellHasProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellHasProductMockMvc.perform(put("/api/sell-has-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellHasProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SellHasProduct in the database
        List<SellHasProduct> sellHasProductList = sellHasProductRepository.findAll();
        assertThat(sellHasProductList).hasSize(databaseSizeBeforeUpdate);

        // Validate the SellHasProduct in Elasticsearch
        verify(mockSellHasProductSearchRepository, times(0)).save(sellHasProduct);
    }

    @Test
    @Transactional
    public void deleteSellHasProduct() throws Exception {
        // Initialize the database
        sellHasProductRepository.saveAndFlush(sellHasProduct);

        int databaseSizeBeforeDelete = sellHasProductRepository.findAll().size();

        // Get the sellHasProduct
        restSellHasProductMockMvc.perform(delete("/api/sell-has-products/{id}", sellHasProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SellHasProduct> sellHasProductList = sellHasProductRepository.findAll();
        assertThat(sellHasProductList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the SellHasProduct in Elasticsearch
        verify(mockSellHasProductSearchRepository, times(1)).deleteById(sellHasProduct.getId());
    }

    @Test
    @Transactional
    public void searchSellHasProduct() throws Exception {
        // Initialize the database
        sellHasProductRepository.saveAndFlush(sellHasProduct);
        when(mockSellHasProductSearchRepository.search(queryStringQuery("id:" + sellHasProduct.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sellHasProduct), PageRequest.of(0, 1), 1));
        // Search the sellHasProduct
        restSellHasProductMockMvc.perform(get("/api/_search/sell-has-products?query=id:" + sellHasProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sellHasProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SellHasProduct.class);
        SellHasProduct sellHasProduct1 = new SellHasProduct();
        sellHasProduct1.setId(1L);
        SellHasProduct sellHasProduct2 = new SellHasProduct();
        sellHasProduct2.setId(sellHasProduct1.getId());
        assertThat(sellHasProduct1).isEqualTo(sellHasProduct2);
        sellHasProduct2.setId(2L);
        assertThat(sellHasProduct1).isNotEqualTo(sellHasProduct2);
        sellHasProduct1.setId(null);
        assertThat(sellHasProduct1).isNotEqualTo(sellHasProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SellHasProductDTO.class);
        SellHasProductDTO sellHasProductDTO1 = new SellHasProductDTO();
        sellHasProductDTO1.setId(1L);
        SellHasProductDTO sellHasProductDTO2 = new SellHasProductDTO();
        assertThat(sellHasProductDTO1).isNotEqualTo(sellHasProductDTO2);
        sellHasProductDTO2.setId(sellHasProductDTO1.getId());
        assertThat(sellHasProductDTO1).isEqualTo(sellHasProductDTO2);
        sellHasProductDTO2.setId(2L);
        assertThat(sellHasProductDTO1).isNotEqualTo(sellHasProductDTO2);
        sellHasProductDTO1.setId(null);
        assertThat(sellHasProductDTO1).isNotEqualTo(sellHasProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sellHasProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sellHasProductMapper.fromId(null)).isNull();
    }
}
