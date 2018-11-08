package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.TipoDePago;
import pe.com.iquitos.app.repository.TipoDePagoRepository;
import pe.com.iquitos.app.repository.search.TipoDePagoSearchRepository;
import pe.com.iquitos.app.service.TipoDePagoService;
import pe.com.iquitos.app.service.dto.TipoDePagoDTO;
import pe.com.iquitos.app.service.mapper.TipoDePagoMapper;
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
 * Test class for the TipoDePagoResource REST controller.
 *
 * @see TipoDePagoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class TipoDePagoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoDePagoRepository tipoDePagoRepository;

    @Autowired
    private TipoDePagoMapper tipoDePagoMapper;
    
    @Autowired
    private TipoDePagoService tipoDePagoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.TipoDePagoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDePagoSearchRepository mockTipoDePagoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoDePagoMockMvc;

    private TipoDePago tipoDePago;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDePagoResource tipoDePagoResource = new TipoDePagoResource(tipoDePagoService);
        this.restTipoDePagoMockMvc = MockMvcBuilders.standaloneSetup(tipoDePagoResource)
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
    public static TipoDePago createEntity(EntityManager em) {
        TipoDePago tipoDePago = new TipoDePago()
            .nombre(DEFAULT_NOMBRE);
        return tipoDePago;
    }

    @Before
    public void initTest() {
        tipoDePago = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDePago() throws Exception {
        int databaseSizeBeforeCreate = tipoDePagoRepository.findAll().size();

        // Create the TipoDePago
        TipoDePagoDTO tipoDePagoDTO = tipoDePagoMapper.toDto(tipoDePago);
        restTipoDePagoMockMvc.perform(post("/api/tipo-de-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDePagoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDePago in the database
        List<TipoDePago> tipoDePagoList = tipoDePagoRepository.findAll();
        assertThat(tipoDePagoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDePago testTipoDePago = tipoDePagoList.get(tipoDePagoList.size() - 1);
        assertThat(testTipoDePago.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the TipoDePago in Elasticsearch
        verify(mockTipoDePagoSearchRepository, times(1)).save(testTipoDePago);
    }

    @Test
    @Transactional
    public void createTipoDePagoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDePagoRepository.findAll().size();

        // Create the TipoDePago with an existing ID
        tipoDePago.setId(1L);
        TipoDePagoDTO tipoDePagoDTO = tipoDePagoMapper.toDto(tipoDePago);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDePagoMockMvc.perform(post("/api/tipo-de-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDePagoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDePago in the database
        List<TipoDePago> tipoDePagoList = tipoDePagoRepository.findAll();
        assertThat(tipoDePagoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDePago in Elasticsearch
        verify(mockTipoDePagoSearchRepository, times(0)).save(tipoDePago);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDePagoRepository.findAll().size();
        // set the field null
        tipoDePago.setNombre(null);

        // Create the TipoDePago, which fails.
        TipoDePagoDTO tipoDePagoDTO = tipoDePagoMapper.toDto(tipoDePago);

        restTipoDePagoMockMvc.perform(post("/api/tipo-de-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDePagoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDePago> tipoDePagoList = tipoDePagoRepository.findAll();
        assertThat(tipoDePagoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDePagos() throws Exception {
        // Initialize the database
        tipoDePagoRepository.saveAndFlush(tipoDePago);

        // Get all the tipoDePagoList
        restTipoDePagoMockMvc.perform(get("/api/tipo-de-pagos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDePago.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoDePago() throws Exception {
        // Initialize the database
        tipoDePagoRepository.saveAndFlush(tipoDePago);

        // Get the tipoDePago
        restTipoDePagoMockMvc.perform(get("/api/tipo-de-pagos/{id}", tipoDePago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDePago.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDePago() throws Exception {
        // Get the tipoDePago
        restTipoDePagoMockMvc.perform(get("/api/tipo-de-pagos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDePago() throws Exception {
        // Initialize the database
        tipoDePagoRepository.saveAndFlush(tipoDePago);

        int databaseSizeBeforeUpdate = tipoDePagoRepository.findAll().size();

        // Update the tipoDePago
        TipoDePago updatedTipoDePago = tipoDePagoRepository.findById(tipoDePago.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDePago are not directly saved in db
        em.detach(updatedTipoDePago);
        updatedTipoDePago
            .nombre(UPDATED_NOMBRE);
        TipoDePagoDTO tipoDePagoDTO = tipoDePagoMapper.toDto(updatedTipoDePago);

        restTipoDePagoMockMvc.perform(put("/api/tipo-de-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDePagoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDePago in the database
        List<TipoDePago> tipoDePagoList = tipoDePagoRepository.findAll();
        assertThat(tipoDePagoList).hasSize(databaseSizeBeforeUpdate);
        TipoDePago testTipoDePago = tipoDePagoList.get(tipoDePagoList.size() - 1);
        assertThat(testTipoDePago.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the TipoDePago in Elasticsearch
        verify(mockTipoDePagoSearchRepository, times(1)).save(testTipoDePago);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDePago() throws Exception {
        int databaseSizeBeforeUpdate = tipoDePagoRepository.findAll().size();

        // Create the TipoDePago
        TipoDePagoDTO tipoDePagoDTO = tipoDePagoMapper.toDto(tipoDePago);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDePagoMockMvc.perform(put("/api/tipo-de-pagos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDePagoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDePago in the database
        List<TipoDePago> tipoDePagoList = tipoDePagoRepository.findAll();
        assertThat(tipoDePagoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDePago in Elasticsearch
        verify(mockTipoDePagoSearchRepository, times(0)).save(tipoDePago);
    }

    @Test
    @Transactional
    public void deleteTipoDePago() throws Exception {
        // Initialize the database
        tipoDePagoRepository.saveAndFlush(tipoDePago);

        int databaseSizeBeforeDelete = tipoDePagoRepository.findAll().size();

        // Get the tipoDePago
        restTipoDePagoMockMvc.perform(delete("/api/tipo-de-pagos/{id}", tipoDePago.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoDePago> tipoDePagoList = tipoDePagoRepository.findAll();
        assertThat(tipoDePagoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDePago in Elasticsearch
        verify(mockTipoDePagoSearchRepository, times(1)).deleteById(tipoDePago.getId());
    }

    @Test
    @Transactional
    public void searchTipoDePago() throws Exception {
        // Initialize the database
        tipoDePagoRepository.saveAndFlush(tipoDePago);
        when(mockTipoDePagoSearchRepository.search(queryStringQuery("id:" + tipoDePago.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDePago), PageRequest.of(0, 1), 1));
        // Search the tipoDePago
        restTipoDePagoMockMvc.perform(get("/api/_search/tipo-de-pagos?query=id:" + tipoDePago.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDePago.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDePago.class);
        TipoDePago tipoDePago1 = new TipoDePago();
        tipoDePago1.setId(1L);
        TipoDePago tipoDePago2 = new TipoDePago();
        tipoDePago2.setId(tipoDePago1.getId());
        assertThat(tipoDePago1).isEqualTo(tipoDePago2);
        tipoDePago2.setId(2L);
        assertThat(tipoDePago1).isNotEqualTo(tipoDePago2);
        tipoDePago1.setId(null);
        assertThat(tipoDePago1).isNotEqualTo(tipoDePago2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDePagoDTO.class);
        TipoDePagoDTO tipoDePagoDTO1 = new TipoDePagoDTO();
        tipoDePagoDTO1.setId(1L);
        TipoDePagoDTO tipoDePagoDTO2 = new TipoDePagoDTO();
        assertThat(tipoDePagoDTO1).isNotEqualTo(tipoDePagoDTO2);
        tipoDePagoDTO2.setId(tipoDePagoDTO1.getId());
        assertThat(tipoDePagoDTO1).isEqualTo(tipoDePagoDTO2);
        tipoDePagoDTO2.setId(2L);
        assertThat(tipoDePagoDTO1).isNotEqualTo(tipoDePagoDTO2);
        tipoDePagoDTO1.setId(null);
        assertThat(tipoDePagoDTO1).isNotEqualTo(tipoDePagoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDePagoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDePagoMapper.fromId(null)).isNull();
    }
}
