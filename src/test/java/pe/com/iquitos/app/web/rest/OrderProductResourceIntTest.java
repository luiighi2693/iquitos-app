package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.OrderProduct;
import pe.com.iquitos.app.repository.OrderProductRepository;
import pe.com.iquitos.app.repository.search.OrderProductSearchRepository;
import pe.com.iquitos.app.service.OrderProductService;
import pe.com.iquitos.app.service.dto.OrderProductDTO;
import pe.com.iquitos.app.service.mapper.OrderProductMapper;
import pe.com.iquitos.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static pe.com.iquitos.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pe.com.iquitos.app.domain.enumeration.OrderStatus;
/**
 * Test class for the OrderProductResource REST controller.
 *
 * @see OrderProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class OrderProductResourceIntTest {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_GUIDE = "AAAAAAAAAA";
    private static final String UPDATED_GUIDE = "BBBBBBBBBB";

    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.CREADO;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.CANCELADO;

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Mock
    private OrderProductRepository orderProductRepositoryMock;

    @Autowired
    private OrderProductMapper orderProductMapper;
    

    @Mock
    private OrderProductService orderProductServiceMock;

    @Autowired
    private OrderProductService orderProductService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.OrderProductSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrderProductSearchRepository mockOrderProductSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderProductMockMvc;

    private OrderProduct orderProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderProductResource orderProductResource = new OrderProductResource(orderProductService);
        this.restOrderProductMockMvc = MockMvcBuilders.standaloneSetup(orderProductResource)
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
    public static OrderProduct createEntity(EntityManager em) {
        OrderProduct orderProduct = new OrderProduct()
            .note(DEFAULT_NOTE)
            .guide(DEFAULT_GUIDE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .metaData(DEFAULT_META_DATA);
        return orderProduct;
    }

    @Before
    public void initTest() {
        orderProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderProduct() throws Exception {
        int databaseSizeBeforeCreate = orderProductRepository.findAll().size();

        // Create the OrderProduct
        OrderProductDTO orderProductDTO = orderProductMapper.toDto(orderProduct);
        restOrderProductMockMvc.perform(post("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderProduct in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeCreate + 1);
        OrderProduct testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testOrderProduct.getGuide()).isEqualTo(DEFAULT_GUIDE);
        assertThat(testOrderProduct.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrderProduct.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the OrderProduct in Elasticsearch
        verify(mockOrderProductSearchRepository, times(1)).save(testOrderProduct);
    }

    @Test
    @Transactional
    public void createOrderProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderProductRepository.findAll().size();

        // Create the OrderProduct with an existing ID
        orderProduct.setId(1L);
        OrderProductDTO orderProductDTO = orderProductMapper.toDto(orderProduct);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderProductMockMvc.perform(post("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderProduct in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeCreate);

        // Validate the OrderProduct in Elasticsearch
        verify(mockOrderProductSearchRepository, times(0)).save(orderProduct);
    }

    @Test
    @Transactional
    public void getAllOrderProducts() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        // Get all the orderProductList
        restOrderProductMockMvc.perform(get("/api/order-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].guide").value(hasItem(DEFAULT_GUIDE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    public void getAllOrderProductsWithEagerRelationshipsIsEnabled() throws Exception {
        OrderProductResource orderProductResource = new OrderProductResource(orderProductServiceMock);
        when(orderProductServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restOrderProductMockMvc = MockMvcBuilders.standaloneSetup(orderProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrderProductMockMvc.perform(get("/api/order-products?eagerload=true"))
        .andExpect(status().isOk());

        verify(orderProductServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllOrderProductsWithEagerRelationshipsIsNotEnabled() throws Exception {
        OrderProductResource orderProductResource = new OrderProductResource(orderProductServiceMock);
            when(orderProductServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restOrderProductMockMvc = MockMvcBuilders.standaloneSetup(orderProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restOrderProductMockMvc.perform(get("/api/order-products?eagerload=true"))
        .andExpect(status().isOk());

            verify(orderProductServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        // Get the orderProduct
        restOrderProductMockMvc.perform(get("/api/order-products/{id}", orderProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderProduct.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.guide").value(DEFAULT_GUIDE.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderProduct() throws Exception {
        // Get the orderProduct
        restOrderProductMockMvc.perform(get("/api/order-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();

        // Update the orderProduct
        OrderProduct updatedOrderProduct = orderProductRepository.findById(orderProduct.getId()).get();
        // Disconnect from session so that the updates on updatedOrderProduct are not directly saved in db
        em.detach(updatedOrderProduct);
        updatedOrderProduct
            .note(UPDATED_NOTE)
            .guide(UPDATED_GUIDE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .metaData(UPDATED_META_DATA);
        OrderProductDTO orderProductDTO = orderProductMapper.toDto(updatedOrderProduct);

        restOrderProductMockMvc.perform(put("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductDTO)))
            .andExpect(status().isOk());

        // Validate the OrderProduct in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);
        OrderProduct testOrderProduct = orderProductList.get(orderProductList.size() - 1);
        assertThat(testOrderProduct.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testOrderProduct.getGuide()).isEqualTo(UPDATED_GUIDE);
        assertThat(testOrderProduct.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrderProduct.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the OrderProduct in Elasticsearch
        verify(mockOrderProductSearchRepository, times(1)).save(testOrderProduct);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderProduct() throws Exception {
        int databaseSizeBeforeUpdate = orderProductRepository.findAll().size();

        // Create the OrderProduct
        OrderProductDTO orderProductDTO = orderProductMapper.toDto(orderProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderProductMockMvc.perform(put("/api/order-products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderProductDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderProduct in the database
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OrderProduct in Elasticsearch
        verify(mockOrderProductSearchRepository, times(0)).save(orderProduct);
    }

    @Test
    @Transactional
    public void deleteOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);

        int databaseSizeBeforeDelete = orderProductRepository.findAll().size();

        // Get the orderProduct
        restOrderProductMockMvc.perform(delete("/api/order-products/{id}", orderProduct.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderProduct> orderProductList = orderProductRepository.findAll();
        assertThat(orderProductList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OrderProduct in Elasticsearch
        verify(mockOrderProductSearchRepository, times(1)).deleteById(orderProduct.getId());
    }

    @Test
    @Transactional
    public void searchOrderProduct() throws Exception {
        // Initialize the database
        orderProductRepository.saveAndFlush(orderProduct);
        when(mockOrderProductSearchRepository.search(queryStringQuery("id:" + orderProduct.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(orderProduct), PageRequest.of(0, 1), 1));
        // Search the orderProduct
        restOrderProductMockMvc.perform(get("/api/_search/order-products?query=id:" + orderProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].guide").value(hasItem(DEFAULT_GUIDE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProduct.class);
        OrderProduct orderProduct1 = new OrderProduct();
        orderProduct1.setId(1L);
        OrderProduct orderProduct2 = new OrderProduct();
        orderProduct2.setId(orderProduct1.getId());
        assertThat(orderProduct1).isEqualTo(orderProduct2);
        orderProduct2.setId(2L);
        assertThat(orderProduct1).isNotEqualTo(orderProduct2);
        orderProduct1.setId(null);
        assertThat(orderProduct1).isNotEqualTo(orderProduct2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderProductDTO.class);
        OrderProductDTO orderProductDTO1 = new OrderProductDTO();
        orderProductDTO1.setId(1L);
        OrderProductDTO orderProductDTO2 = new OrderProductDTO();
        assertThat(orderProductDTO1).isNotEqualTo(orderProductDTO2);
        orderProductDTO2.setId(orderProductDTO1.getId());
        assertThat(orderProductDTO1).isEqualTo(orderProductDTO2);
        orderProductDTO2.setId(2L);
        assertThat(orderProductDTO1).isNotEqualTo(orderProductDTO2);
        orderProductDTO1.setId(null);
        assertThat(orderProductDTO1).isNotEqualTo(orderProductDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderProductMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderProductMapper.fromId(null)).isNull();
    }
}
