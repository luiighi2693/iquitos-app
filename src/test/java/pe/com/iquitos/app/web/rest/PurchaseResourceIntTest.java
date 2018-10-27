package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Purchase;
import pe.com.iquitos.app.repository.PurchaseRepository;
import pe.com.iquitos.app.repository.search.PurchaseSearchRepository;
import pe.com.iquitos.app.service.PurchaseService;
import pe.com.iquitos.app.service.dto.PurchaseDTO;
import pe.com.iquitos.app.service.mapper.PurchaseMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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

import pe.com.iquitos.app.domain.enumeration.PurchaseLocation;
import pe.com.iquitos.app.domain.enumeration.PaymentPurchaseType;
/**
 * Test class for the PurchaseResource REST controller.
 *
 * @see PurchaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class PurchaseResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REMISION_GUIDE = "AAAAAAAAAA";
    private static final String UPDATED_REMISION_GUIDE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NUMBER = "BBBBBBBBBB";

    private static final PurchaseLocation DEFAULT_LOCATION = PurchaseLocation.TIENDA;
    private static final PurchaseLocation UPDATED_LOCATION = PurchaseLocation.TIENDA;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final String DEFAULT_CORRELATIVE = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATIVE = "BBBBBBBBBB";

    private static final PaymentPurchaseType DEFAULT_PAYMENT_PURCHASE_TYPE = PaymentPurchaseType.CONTADO;
    private static final PaymentPurchaseType UPDATED_PAYMENT_PURCHASE_TYPE = PaymentPurchaseType.CREDITO;

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Mock
    private PurchaseRepository purchaseRepositoryMock;

    @Autowired
    private PurchaseMapper purchaseMapper;
    

    @Mock
    private PurchaseService purchaseServiceMock;

    @Autowired
    private PurchaseService purchaseService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.PurchaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private PurchaseSearchRepository mockPurchaseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchaseMockMvc;

    private Purchase purchase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseResource purchaseResource = new PurchaseResource(purchaseService);
        this.restPurchaseMockMvc = MockMvcBuilders.standaloneSetup(purchaseResource)
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
    public static Purchase createEntity(EntityManager em) {
        Purchase purchase = new Purchase()
            .date(DEFAULT_DATE)
            .remisionGuide(DEFAULT_REMISION_GUIDE)
            .documentNumber(DEFAULT_DOCUMENT_NUMBER)
            .location(DEFAULT_LOCATION)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .correlative(DEFAULT_CORRELATIVE)
            .paymentPurchaseType(DEFAULT_PAYMENT_PURCHASE_TYPE)
            .metaData(DEFAULT_META_DATA);
        return purchase;
    }

    @Before
    public void initTest() {
        purchase = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchase() throws Exception {
        int databaseSizeBeforeCreate = purchaseRepository.findAll().size();

        // Create the Purchase
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);
        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isCreated());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeCreate + 1);
        Purchase testPurchase = purchaseList.get(purchaseList.size() - 1);
        assertThat(testPurchase.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPurchase.getRemisionGuide()).isEqualTo(DEFAULT_REMISION_GUIDE);
        assertThat(testPurchase.getDocumentNumber()).isEqualTo(DEFAULT_DOCUMENT_NUMBER);
        assertThat(testPurchase.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testPurchase.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testPurchase.getCorrelative()).isEqualTo(DEFAULT_CORRELATIVE);
        assertThat(testPurchase.getPaymentPurchaseType()).isEqualTo(DEFAULT_PAYMENT_PURCHASE_TYPE);
        assertThat(testPurchase.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the Purchase in Elasticsearch
        verify(mockPurchaseSearchRepository, times(1)).save(testPurchase);
    }

    @Test
    @Transactional
    public void createPurchaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseRepository.findAll().size();

        // Create the Purchase with an existing ID
        purchase.setId(1L);
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseMockMvc.perform(post("/api/purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeCreate);

        // Validate the Purchase in Elasticsearch
        verify(mockPurchaseSearchRepository, times(0)).save(purchase);
    }

    @Test
    @Transactional
    public void getAllPurchases() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get all the purchaseList
        restPurchaseMockMvc.perform(get("/api/purchases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchase.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].remisionGuide").value(hasItem(DEFAULT_REMISION_GUIDE.toString())))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].correlative").value(hasItem(DEFAULT_CORRELATIVE.toString())))
            .andExpect(jsonPath("$.[*].paymentPurchaseType").value(hasItem(DEFAULT_PAYMENT_PURCHASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    public void getAllPurchasesWithEagerRelationshipsIsEnabled() throws Exception {
        PurchaseResource purchaseResource = new PurchaseResource(purchaseServiceMock);
        when(purchaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPurchaseMockMvc = MockMvcBuilders.standaloneSetup(purchaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPurchaseMockMvc.perform(get("/api/purchases?eagerload=true"))
        .andExpect(status().isOk());

        verify(purchaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllPurchasesWithEagerRelationshipsIsNotEnabled() throws Exception {
        PurchaseResource purchaseResource = new PurchaseResource(purchaseServiceMock);
            when(purchaseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPurchaseMockMvc = MockMvcBuilders.standaloneSetup(purchaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPurchaseMockMvc.perform(get("/api/purchases?eagerload=true"))
        .andExpect(status().isOk());

            verify(purchaseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", purchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchase.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.remisionGuide").value(DEFAULT_REMISION_GUIDE.toString()))
            .andExpect(jsonPath("$.documentNumber").value(DEFAULT_DOCUMENT_NUMBER.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.correlative").value(DEFAULT_CORRELATIVE.toString()))
            .andExpect(jsonPath("$.paymentPurchaseType").value(DEFAULT_PAYMENT_PURCHASE_TYPE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPurchase() throws Exception {
        // Get the purchase
        restPurchaseMockMvc.perform(get("/api/purchases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        int databaseSizeBeforeUpdate = purchaseRepository.findAll().size();

        // Update the purchase
        Purchase updatedPurchase = purchaseRepository.findById(purchase.getId()).get();
        // Disconnect from session so that the updates on updatedPurchase are not directly saved in db
        em.detach(updatedPurchase);
        updatedPurchase
            .date(UPDATED_DATE)
            .remisionGuide(UPDATED_REMISION_GUIDE)
            .documentNumber(UPDATED_DOCUMENT_NUMBER)
            .location(UPDATED_LOCATION)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .correlative(UPDATED_CORRELATIVE)
            .paymentPurchaseType(UPDATED_PAYMENT_PURCHASE_TYPE)
            .metaData(UPDATED_META_DATA);
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(updatedPurchase);

        restPurchaseMockMvc.perform(put("/api/purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isOk());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeUpdate);
        Purchase testPurchase = purchaseList.get(purchaseList.size() - 1);
        assertThat(testPurchase.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPurchase.getRemisionGuide()).isEqualTo(UPDATED_REMISION_GUIDE);
        assertThat(testPurchase.getDocumentNumber()).isEqualTo(UPDATED_DOCUMENT_NUMBER);
        assertThat(testPurchase.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testPurchase.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testPurchase.getCorrelative()).isEqualTo(UPDATED_CORRELATIVE);
        assertThat(testPurchase.getPaymentPurchaseType()).isEqualTo(UPDATED_PAYMENT_PURCHASE_TYPE);
        assertThat(testPurchase.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the Purchase in Elasticsearch
        verify(mockPurchaseSearchRepository, times(1)).save(testPurchase);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchase() throws Exception {
        int databaseSizeBeforeUpdate = purchaseRepository.findAll().size();

        // Create the Purchase
        PurchaseDTO purchaseDTO = purchaseMapper.toDto(purchase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseMockMvc.perform(put("/api/purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Purchase in the database
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Purchase in Elasticsearch
        verify(mockPurchaseSearchRepository, times(0)).save(purchase);
    }

    @Test
    @Transactional
    public void deletePurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);

        int databaseSizeBeforeDelete = purchaseRepository.findAll().size();

        // Get the purchase
        restPurchaseMockMvc.perform(delete("/api/purchases/{id}", purchase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Purchase> purchaseList = purchaseRepository.findAll();
        assertThat(purchaseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Purchase in Elasticsearch
        verify(mockPurchaseSearchRepository, times(1)).deleteById(purchase.getId());
    }

    @Test
    @Transactional
    public void searchPurchase() throws Exception {
        // Initialize the database
        purchaseRepository.saveAndFlush(purchase);
        when(mockPurchaseSearchRepository.search(queryStringQuery("id:" + purchase.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(purchase), PageRequest.of(0, 1), 1));
        // Search the purchase
        restPurchaseMockMvc.perform(get("/api/_search/purchases?query=id:" + purchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchase.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].remisionGuide").value(hasItem(DEFAULT_REMISION_GUIDE.toString())))
            .andExpect(jsonPath("$.[*].documentNumber").value(hasItem(DEFAULT_DOCUMENT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].correlative").value(hasItem(DEFAULT_CORRELATIVE.toString())))
            .andExpect(jsonPath("$.[*].paymentPurchaseType").value(hasItem(DEFAULT_PAYMENT_PURCHASE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Purchase.class);
        Purchase purchase1 = new Purchase();
        purchase1.setId(1L);
        Purchase purchase2 = new Purchase();
        purchase2.setId(purchase1.getId());
        assertThat(purchase1).isEqualTo(purchase2);
        purchase2.setId(2L);
        assertThat(purchase1).isNotEqualTo(purchase2);
        purchase1.setId(null);
        assertThat(purchase1).isNotEqualTo(purchase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseDTO.class);
        PurchaseDTO purchaseDTO1 = new PurchaseDTO();
        purchaseDTO1.setId(1L);
        PurchaseDTO purchaseDTO2 = new PurchaseDTO();
        assertThat(purchaseDTO1).isNotEqualTo(purchaseDTO2);
        purchaseDTO2.setId(purchaseDTO1.getId());
        assertThat(purchaseDTO1).isEqualTo(purchaseDTO2);
        purchaseDTO2.setId(2L);
        assertThat(purchaseDTO1).isNotEqualTo(purchaseDTO2);
        purchaseDTO1.setId(null);
        assertThat(purchaseDTO1).isNotEqualTo(purchaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseMapper.fromId(null)).isNull();
    }
}
