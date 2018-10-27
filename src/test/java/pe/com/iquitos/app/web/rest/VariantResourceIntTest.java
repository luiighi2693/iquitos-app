package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Variant;
import pe.com.iquitos.app.repository.VariantRepository;
import pe.com.iquitos.app.repository.search.VariantSearchRepository;
import pe.com.iquitos.app.service.VariantService;
import pe.com.iquitos.app.service.dto.VariantDTO;
import pe.com.iquitos.app.service.mapper.VariantMapper;
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

/**
 * Test class for the VariantResource REST controller.
 *
 * @see VariantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class VariantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_SELL = 1D;
    private static final Double UPDATED_PRICE_SELL = 2D;

    private static final Double DEFAULT_PRICE_PURCHASE = 1D;
    private static final Double UPDATED_PRICE_PURCHASE = 2D;

    @Autowired
    private VariantRepository variantRepository;

    @Mock
    private VariantRepository variantRepositoryMock;

    @Autowired
    private VariantMapper variantMapper;
    

    @Mock
    private VariantService variantServiceMock;

    @Autowired
    private VariantService variantService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.VariantSearchRepositoryMockConfiguration
     */
    @Autowired
    private VariantSearchRepository mockVariantSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVariantMockMvc;

    private Variant variant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VariantResource variantResource = new VariantResource(variantService);
        this.restVariantMockMvc = MockMvcBuilders.standaloneSetup(variantResource)
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
    public static Variant createEntity(EntityManager em) {
        Variant variant = new Variant()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .priceSell(DEFAULT_PRICE_SELL)
            .pricePurchase(DEFAULT_PRICE_PURCHASE);
        return variant;
    }

    @Before
    public void initTest() {
        variant = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariant() throws Exception {
        int databaseSizeBeforeCreate = variantRepository.findAll().size();

        // Create the Variant
        VariantDTO variantDTO = variantMapper.toDto(variant);
        restVariantMockMvc.perform(post("/api/variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isCreated());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeCreate + 1);
        Variant testVariant = variantList.get(variantList.size() - 1);
        assertThat(testVariant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVariant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVariant.getPriceSell()).isEqualTo(DEFAULT_PRICE_SELL);
        assertThat(testVariant.getPricePurchase()).isEqualTo(DEFAULT_PRICE_PURCHASE);

        // Validate the Variant in Elasticsearch
        verify(mockVariantSearchRepository, times(1)).save(testVariant);
    }

    @Test
    @Transactional
    public void createVariantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = variantRepository.findAll().size();

        // Create the Variant with an existing ID
        variant.setId(1L);
        VariantDTO variantDTO = variantMapper.toDto(variant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVariantMockMvc.perform(post("/api/variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeCreate);

        // Validate the Variant in Elasticsearch
        verify(mockVariantSearchRepository, times(0)).save(variant);
    }

    @Test
    @Transactional
    public void getAllVariants() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get all the variantList
        restVariantMockMvc.perform(get("/api/variants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].priceSell").value(hasItem(DEFAULT_PRICE_SELL.doubleValue())))
            .andExpect(jsonPath("$.[*].pricePurchase").value(hasItem(DEFAULT_PRICE_PURCHASE.doubleValue())));
    }
    
    public void getAllVariantsWithEagerRelationshipsIsEnabled() throws Exception {
        VariantResource variantResource = new VariantResource(variantServiceMock);
        when(variantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVariantMockMvc = MockMvcBuilders.standaloneSetup(variantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVariantMockMvc.perform(get("/api/variants?eagerload=true"))
        .andExpect(status().isOk());

        verify(variantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllVariantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        VariantResource variantResource = new VariantResource(variantServiceMock);
            when(variantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVariantMockMvc = MockMvcBuilders.standaloneSetup(variantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVariantMockMvc.perform(get("/api/variants?eagerload=true"))
        .andExpect(status().isOk());

            verify(variantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        // Get the variant
        restVariantMockMvc.perform(get("/api/variants/{id}", variant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.priceSell").value(DEFAULT_PRICE_SELL.doubleValue()))
            .andExpect(jsonPath("$.pricePurchase").value(DEFAULT_PRICE_PURCHASE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVariant() throws Exception {
        // Get the variant
        restVariantMockMvc.perform(get("/api/variants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        int databaseSizeBeforeUpdate = variantRepository.findAll().size();

        // Update the variant
        Variant updatedVariant = variantRepository.findById(variant.getId()).get();
        // Disconnect from session so that the updates on updatedVariant are not directly saved in db
        em.detach(updatedVariant);
        updatedVariant
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .priceSell(UPDATED_PRICE_SELL)
            .pricePurchase(UPDATED_PRICE_PURCHASE);
        VariantDTO variantDTO = variantMapper.toDto(updatedVariant);

        restVariantMockMvc.perform(put("/api/variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isOk());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeUpdate);
        Variant testVariant = variantList.get(variantList.size() - 1);
        assertThat(testVariant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVariant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVariant.getPriceSell()).isEqualTo(UPDATED_PRICE_SELL);
        assertThat(testVariant.getPricePurchase()).isEqualTo(UPDATED_PRICE_PURCHASE);

        // Validate the Variant in Elasticsearch
        verify(mockVariantSearchRepository, times(1)).save(testVariant);
    }

    @Test
    @Transactional
    public void updateNonExistingVariant() throws Exception {
        int databaseSizeBeforeUpdate = variantRepository.findAll().size();

        // Create the Variant
        VariantDTO variantDTO = variantMapper.toDto(variant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariantMockMvc.perform(put("/api/variants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(variantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variant in the database
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Variant in Elasticsearch
        verify(mockVariantSearchRepository, times(0)).save(variant);
    }

    @Test
    @Transactional
    public void deleteVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);

        int databaseSizeBeforeDelete = variantRepository.findAll().size();

        // Get the variant
        restVariantMockMvc.perform(delete("/api/variants/{id}", variant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Variant> variantList = variantRepository.findAll();
        assertThat(variantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Variant in Elasticsearch
        verify(mockVariantSearchRepository, times(1)).deleteById(variant.getId());
    }

    @Test
    @Transactional
    public void searchVariant() throws Exception {
        // Initialize the database
        variantRepository.saveAndFlush(variant);
        when(mockVariantSearchRepository.search(queryStringQuery("id:" + variant.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(variant), PageRequest.of(0, 1), 1));
        // Search the variant
        restVariantMockMvc.perform(get("/api/_search/variants?query=id:" + variant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].priceSell").value(hasItem(DEFAULT_PRICE_SELL.doubleValue())))
            .andExpect(jsonPath("$.[*].pricePurchase").value(hasItem(DEFAULT_PRICE_PURCHASE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Variant.class);
        Variant variant1 = new Variant();
        variant1.setId(1L);
        Variant variant2 = new Variant();
        variant2.setId(variant1.getId());
        assertThat(variant1).isEqualTo(variant2);
        variant2.setId(2L);
        assertThat(variant1).isNotEqualTo(variant2);
        variant1.setId(null);
        assertThat(variant1).isNotEqualTo(variant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariantDTO.class);
        VariantDTO variantDTO1 = new VariantDTO();
        variantDTO1.setId(1L);
        VariantDTO variantDTO2 = new VariantDTO();
        assertThat(variantDTO1).isNotEqualTo(variantDTO2);
        variantDTO2.setId(variantDTO1.getId());
        assertThat(variantDTO1).isEqualTo(variantDTO2);
        variantDTO2.setId(2L);
        assertThat(variantDTO1).isNotEqualTo(variantDTO2);
        variantDTO1.setId(null);
        assertThat(variantDTO1).isNotEqualTo(variantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(variantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(variantMapper.fromId(null)).isNull();
    }
}
