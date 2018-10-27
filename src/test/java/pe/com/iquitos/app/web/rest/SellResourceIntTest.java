package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Sell;
import pe.com.iquitos.app.repository.SellRepository;
import pe.com.iquitos.app.repository.search.SellSearchRepository;
import pe.com.iquitos.app.service.SellService;
import pe.com.iquitos.app.service.dto.SellDTO;
import pe.com.iquitos.app.service.mapper.SellMapper;
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

import pe.com.iquitos.app.domain.enumeration.SellStatus;
/**
 * Test class for the SellResource REST controller.
 *
 * @see SellResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class SellResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_SUB_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_SUB_TOTAL_AMOUNT = 2D;

    private static final Double DEFAULT_TAX_AMOUNT = 1D;
    private static final Double UPDATED_TAX_AMOUNT = 2D;

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final SellStatus DEFAULT_STATUS = SellStatus.PENDIENTE;
    private static final SellStatus UPDATED_STATUS = SellStatus.COMPLETADO;

    private static final String DEFAULT_GLOSS = "AAAAAAAAAA";
    private static final String UPDATED_GLOSS = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private SellRepository sellRepository;

    @Mock
    private SellRepository sellRepositoryMock;

    @Autowired
    private SellMapper sellMapper;
    

    @Mock
    private SellService sellServiceMock;

    @Autowired
    private SellService sellService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.SellSearchRepositoryMockConfiguration
     */
    @Autowired
    private SellSearchRepository mockSellSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSellMockMvc;

    private Sell sell;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SellResource sellResource = new SellResource(sellService);
        this.restSellMockMvc = MockMvcBuilders.standaloneSetup(sellResource)
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
    public static Sell createEntity(EntityManager em) {
        Sell sell = new Sell()
            .code(DEFAULT_CODE)
            .subTotalAmount(DEFAULT_SUB_TOTAL_AMOUNT)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .totalAmount(DEFAULT_TOTAL_AMOUNT)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .gloss(DEFAULT_GLOSS)
            .metaData(DEFAULT_META_DATA);
        return sell;
    }

    @Before
    public void initTest() {
        sell = createEntity(em);
    }

    @Test
    @Transactional
    public void createSell() throws Exception {
        int databaseSizeBeforeCreate = sellRepository.findAll().size();

        // Create the Sell
        SellDTO sellDTO = sellMapper.toDto(sell);
        restSellMockMvc.perform(post("/api/sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellDTO)))
            .andExpect(status().isCreated());

        // Validate the Sell in the database
        List<Sell> sellList = sellRepository.findAll();
        assertThat(sellList).hasSize(databaseSizeBeforeCreate + 1);
        Sell testSell = sellList.get(sellList.size() - 1);
        assertThat(testSell.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSell.getSubTotalAmount()).isEqualTo(DEFAULT_SUB_TOTAL_AMOUNT);
        assertThat(testSell.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testSell.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testSell.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSell.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSell.getGloss()).isEqualTo(DEFAULT_GLOSS);
        assertThat(testSell.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the Sell in Elasticsearch
        verify(mockSellSearchRepository, times(1)).save(testSell);
    }

    @Test
    @Transactional
    public void createSellWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sellRepository.findAll().size();

        // Create the Sell with an existing ID
        sell.setId(1L);
        SellDTO sellDTO = sellMapper.toDto(sell);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSellMockMvc.perform(post("/api/sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sell in the database
        List<Sell> sellList = sellRepository.findAll();
        assertThat(sellList).hasSize(databaseSizeBeforeCreate);

        // Validate the Sell in Elasticsearch
        verify(mockSellSearchRepository, times(0)).save(sell);
    }

    @Test
    @Transactional
    public void getAllSells() throws Exception {
        // Initialize the database
        sellRepository.saveAndFlush(sell);

        // Get all the sellList
        restSellMockMvc.perform(get("/api/sells?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sell.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].subTotalAmount").value(hasItem(DEFAULT_SUB_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gloss").value(hasItem(DEFAULT_GLOSS.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    public void getAllSellsWithEagerRelationshipsIsEnabled() throws Exception {
        SellResource sellResource = new SellResource(sellServiceMock);
        when(sellServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restSellMockMvc = MockMvcBuilders.standaloneSetup(sellResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSellMockMvc.perform(get("/api/sells?eagerload=true"))
        .andExpect(status().isOk());

        verify(sellServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllSellsWithEagerRelationshipsIsNotEnabled() throws Exception {
        SellResource sellResource = new SellResource(sellServiceMock);
            when(sellServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restSellMockMvc = MockMvcBuilders.standaloneSetup(sellResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restSellMockMvc.perform(get("/api/sells?eagerload=true"))
        .andExpect(status().isOk());

            verify(sellServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSell() throws Exception {
        // Initialize the database
        sellRepository.saveAndFlush(sell);

        // Get the sell
        restSellMockMvc.perform(get("/api/sells/{id}", sell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sell.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.subTotalAmount").value(DEFAULT_SUB_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.gloss").value(DEFAULT_GLOSS.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSell() throws Exception {
        // Get the sell
        restSellMockMvc.perform(get("/api/sells/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSell() throws Exception {
        // Initialize the database
        sellRepository.saveAndFlush(sell);

        int databaseSizeBeforeUpdate = sellRepository.findAll().size();

        // Update the sell
        Sell updatedSell = sellRepository.findById(sell.getId()).get();
        // Disconnect from session so that the updates on updatedSell are not directly saved in db
        em.detach(updatedSell);
        updatedSell
            .code(UPDATED_CODE)
            .subTotalAmount(UPDATED_SUB_TOTAL_AMOUNT)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .totalAmount(UPDATED_TOTAL_AMOUNT)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .gloss(UPDATED_GLOSS)
            .metaData(UPDATED_META_DATA);
        SellDTO sellDTO = sellMapper.toDto(updatedSell);

        restSellMockMvc.perform(put("/api/sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellDTO)))
            .andExpect(status().isOk());

        // Validate the Sell in the database
        List<Sell> sellList = sellRepository.findAll();
        assertThat(sellList).hasSize(databaseSizeBeforeUpdate);
        Sell testSell = sellList.get(sellList.size() - 1);
        assertThat(testSell.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSell.getSubTotalAmount()).isEqualTo(UPDATED_SUB_TOTAL_AMOUNT);
        assertThat(testSell.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testSell.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testSell.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSell.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSell.getGloss()).isEqualTo(UPDATED_GLOSS);
        assertThat(testSell.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the Sell in Elasticsearch
        verify(mockSellSearchRepository, times(1)).save(testSell);
    }

    @Test
    @Transactional
    public void updateNonExistingSell() throws Exception {
        int databaseSizeBeforeUpdate = sellRepository.findAll().size();

        // Create the Sell
        SellDTO sellDTO = sellMapper.toDto(sell);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSellMockMvc.perform(put("/api/sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sellDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sell in the database
        List<Sell> sellList = sellRepository.findAll();
        assertThat(sellList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Sell in Elasticsearch
        verify(mockSellSearchRepository, times(0)).save(sell);
    }

    @Test
    @Transactional
    public void deleteSell() throws Exception {
        // Initialize the database
        sellRepository.saveAndFlush(sell);

        int databaseSizeBeforeDelete = sellRepository.findAll().size();

        // Get the sell
        restSellMockMvc.perform(delete("/api/sells/{id}", sell.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sell> sellList = sellRepository.findAll();
        assertThat(sellList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Sell in Elasticsearch
        verify(mockSellSearchRepository, times(1)).deleteById(sell.getId());
    }

    @Test
    @Transactional
    public void searchSell() throws Exception {
        // Initialize the database
        sellRepository.saveAndFlush(sell);
        when(mockSellSearchRepository.search(queryStringQuery("id:" + sell.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(sell), PageRequest.of(0, 1), 1));
        // Search the sell
        restSellMockMvc.perform(get("/api/_search/sells?query=id:" + sell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sell.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].subTotalAmount").value(hasItem(DEFAULT_SUB_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].gloss").value(hasItem(DEFAULT_GLOSS.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sell.class);
        Sell sell1 = new Sell();
        sell1.setId(1L);
        Sell sell2 = new Sell();
        sell2.setId(sell1.getId());
        assertThat(sell1).isEqualTo(sell2);
        sell2.setId(2L);
        assertThat(sell1).isNotEqualTo(sell2);
        sell1.setId(null);
        assertThat(sell1).isNotEqualTo(sell2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SellDTO.class);
        SellDTO sellDTO1 = new SellDTO();
        sellDTO1.setId(1L);
        SellDTO sellDTO2 = new SellDTO();
        assertThat(sellDTO1).isNotEqualTo(sellDTO2);
        sellDTO2.setId(sellDTO1.getId());
        assertThat(sellDTO1).isEqualTo(sellDTO2);
        sellDTO2.setId(2L);
        assertThat(sellDTO1).isNotEqualTo(sellDTO2);
        sellDTO1.setId(null);
        assertThat(sellDTO1).isNotEqualTo(sellDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sellMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sellMapper.fromId(null)).isNull();
    }
}
