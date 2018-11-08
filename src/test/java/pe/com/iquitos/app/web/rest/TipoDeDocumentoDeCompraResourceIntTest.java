package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.TipoDeDocumentoDeCompra;
import pe.com.iquitos.app.repository.TipoDeDocumentoDeCompraRepository;
import pe.com.iquitos.app.repository.search.TipoDeDocumentoDeCompraSearchRepository;
import pe.com.iquitos.app.service.TipoDeDocumentoDeCompraService;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeCompraDTO;
import pe.com.iquitos.app.service.mapper.TipoDeDocumentoDeCompraMapper;
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
 * Test class for the TipoDeDocumentoDeCompraResource REST controller.
 *
 * @see TipoDeDocumentoDeCompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class TipoDeDocumentoDeCompraResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoDeDocumentoDeCompraRepository tipoDeDocumentoDeCompraRepository;

    @Autowired
    private TipoDeDocumentoDeCompraMapper tipoDeDocumentoDeCompraMapper;
    
    @Autowired
    private TipoDeDocumentoDeCompraService tipoDeDocumentoDeCompraService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.TipoDeDocumentoDeCompraSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDeDocumentoDeCompraSearchRepository mockTipoDeDocumentoDeCompraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoDeDocumentoDeCompraMockMvc;

    private TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDeDocumentoDeCompraResource tipoDeDocumentoDeCompraResource = new TipoDeDocumentoDeCompraResource(tipoDeDocumentoDeCompraService);
        this.restTipoDeDocumentoDeCompraMockMvc = MockMvcBuilders.standaloneSetup(tipoDeDocumentoDeCompraResource)
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
    public static TipoDeDocumentoDeCompra createEntity(EntityManager em) {
        TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra = new TipoDeDocumentoDeCompra()
            .nombre(DEFAULT_NOMBRE);
        return tipoDeDocumentoDeCompra;
    }

    @Before
    public void initTest() {
        tipoDeDocumentoDeCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeDocumentoDeCompra() throws Exception {
        int databaseSizeBeforeCreate = tipoDeDocumentoDeCompraRepository.findAll().size();

        // Create the TipoDeDocumentoDeCompra
        TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO = tipoDeDocumentoDeCompraMapper.toDto(tipoDeDocumentoDeCompra);
        restTipoDeDocumentoDeCompraMockMvc.perform(post("/api/tipo-de-documento-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeCompraDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDeDocumentoDeCompra in the database
        List<TipoDeDocumentoDeCompra> tipoDeDocumentoDeCompraList = tipoDeDocumentoDeCompraRepository.findAll();
        assertThat(tipoDeDocumentoDeCompraList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeDocumentoDeCompra testTipoDeDocumentoDeCompra = tipoDeDocumentoDeCompraList.get(tipoDeDocumentoDeCompraList.size() - 1);
        assertThat(testTipoDeDocumentoDeCompra.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the TipoDeDocumentoDeCompra in Elasticsearch
        verify(mockTipoDeDocumentoDeCompraSearchRepository, times(1)).save(testTipoDeDocumentoDeCompra);
    }

    @Test
    @Transactional
    public void createTipoDeDocumentoDeCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDeDocumentoDeCompraRepository.findAll().size();

        // Create the TipoDeDocumentoDeCompra with an existing ID
        tipoDeDocumentoDeCompra.setId(1L);
        TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO = tipoDeDocumentoDeCompraMapper.toDto(tipoDeDocumentoDeCompra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeDocumentoDeCompraMockMvc.perform(post("/api/tipo-de-documento-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeDocumentoDeCompra in the database
        List<TipoDeDocumentoDeCompra> tipoDeDocumentoDeCompraList = tipoDeDocumentoDeCompraRepository.findAll();
        assertThat(tipoDeDocumentoDeCompraList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDeDocumentoDeCompra in Elasticsearch
        verify(mockTipoDeDocumentoDeCompraSearchRepository, times(0)).save(tipoDeDocumentoDeCompra);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeDocumentoDeCompraRepository.findAll().size();
        // set the field null
        tipoDeDocumentoDeCompra.setNombre(null);

        // Create the TipoDeDocumentoDeCompra, which fails.
        TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO = tipoDeDocumentoDeCompraMapper.toDto(tipoDeDocumentoDeCompra);

        restTipoDeDocumentoDeCompraMockMvc.perform(post("/api/tipo-de-documento-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeCompraDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeDocumentoDeCompra> tipoDeDocumentoDeCompraList = tipoDeDocumentoDeCompraRepository.findAll();
        assertThat(tipoDeDocumentoDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDeDocumentoDeCompras() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeCompraRepository.saveAndFlush(tipoDeDocumentoDeCompra);

        // Get all the tipoDeDocumentoDeCompraList
        restTipoDeDocumentoDeCompraMockMvc.perform(get("/api/tipo-de-documento-de-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeDocumentoDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoDeDocumentoDeCompra() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeCompraRepository.saveAndFlush(tipoDeDocumentoDeCompra);

        // Get the tipoDeDocumentoDeCompra
        restTipoDeDocumentoDeCompraMockMvc.perform(get("/api/tipo-de-documento-de-compras/{id}", tipoDeDocumentoDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeDocumentoDeCompra.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDeDocumentoDeCompra() throws Exception {
        // Get the tipoDeDocumentoDeCompra
        restTipoDeDocumentoDeCompraMockMvc.perform(get("/api/tipo-de-documento-de-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeDocumentoDeCompra() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeCompraRepository.saveAndFlush(tipoDeDocumentoDeCompra);

        int databaseSizeBeforeUpdate = tipoDeDocumentoDeCompraRepository.findAll().size();

        // Update the tipoDeDocumentoDeCompra
        TipoDeDocumentoDeCompra updatedTipoDeDocumentoDeCompra = tipoDeDocumentoDeCompraRepository.findById(tipoDeDocumentoDeCompra.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeDocumentoDeCompra are not directly saved in db
        em.detach(updatedTipoDeDocumentoDeCompra);
        updatedTipoDeDocumentoDeCompra
            .nombre(UPDATED_NOMBRE);
        TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO = tipoDeDocumentoDeCompraMapper.toDto(updatedTipoDeDocumentoDeCompra);

        restTipoDeDocumentoDeCompraMockMvc.perform(put("/api/tipo-de-documento-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeCompraDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDeDocumentoDeCompra in the database
        List<TipoDeDocumentoDeCompra> tipoDeDocumentoDeCompraList = tipoDeDocumentoDeCompraRepository.findAll();
        assertThat(tipoDeDocumentoDeCompraList).hasSize(databaseSizeBeforeUpdate);
        TipoDeDocumentoDeCompra testTipoDeDocumentoDeCompra = tipoDeDocumentoDeCompraList.get(tipoDeDocumentoDeCompraList.size() - 1);
        assertThat(testTipoDeDocumentoDeCompra.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the TipoDeDocumentoDeCompra in Elasticsearch
        verify(mockTipoDeDocumentoDeCompraSearchRepository, times(1)).save(testTipoDeDocumentoDeCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeDocumentoDeCompra() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeDocumentoDeCompraRepository.findAll().size();

        // Create the TipoDeDocumentoDeCompra
        TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO = tipoDeDocumentoDeCompraMapper.toDto(tipoDeDocumentoDeCompra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeDocumentoDeCompraMockMvc.perform(put("/api/tipo-de-documento-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeDocumentoDeCompra in the database
        List<TipoDeDocumentoDeCompra> tipoDeDocumentoDeCompraList = tipoDeDocumentoDeCompraRepository.findAll();
        assertThat(tipoDeDocumentoDeCompraList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDeDocumentoDeCompra in Elasticsearch
        verify(mockTipoDeDocumentoDeCompraSearchRepository, times(0)).save(tipoDeDocumentoDeCompra);
    }

    @Test
    @Transactional
    public void deleteTipoDeDocumentoDeCompra() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeCompraRepository.saveAndFlush(tipoDeDocumentoDeCompra);

        int databaseSizeBeforeDelete = tipoDeDocumentoDeCompraRepository.findAll().size();

        // Get the tipoDeDocumentoDeCompra
        restTipoDeDocumentoDeCompraMockMvc.perform(delete("/api/tipo-de-documento-de-compras/{id}", tipoDeDocumentoDeCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoDeDocumentoDeCompra> tipoDeDocumentoDeCompraList = tipoDeDocumentoDeCompraRepository.findAll();
        assertThat(tipoDeDocumentoDeCompraList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDeDocumentoDeCompra in Elasticsearch
        verify(mockTipoDeDocumentoDeCompraSearchRepository, times(1)).deleteById(tipoDeDocumentoDeCompra.getId());
    }

    @Test
    @Transactional
    public void searchTipoDeDocumentoDeCompra() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeCompraRepository.saveAndFlush(tipoDeDocumentoDeCompra);
        when(mockTipoDeDocumentoDeCompraSearchRepository.search(queryStringQuery("id:" + tipoDeDocumentoDeCompra.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDeDocumentoDeCompra), PageRequest.of(0, 1), 1));
        // Search the tipoDeDocumentoDeCompra
        restTipoDeDocumentoDeCompraMockMvc.perform(get("/api/_search/tipo-de-documento-de-compras?query=id:" + tipoDeDocumentoDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeDocumentoDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeDocumentoDeCompra.class);
        TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra1 = new TipoDeDocumentoDeCompra();
        tipoDeDocumentoDeCompra1.setId(1L);
        TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra2 = new TipoDeDocumentoDeCompra();
        tipoDeDocumentoDeCompra2.setId(tipoDeDocumentoDeCompra1.getId());
        assertThat(tipoDeDocumentoDeCompra1).isEqualTo(tipoDeDocumentoDeCompra2);
        tipoDeDocumentoDeCompra2.setId(2L);
        assertThat(tipoDeDocumentoDeCompra1).isNotEqualTo(tipoDeDocumentoDeCompra2);
        tipoDeDocumentoDeCompra1.setId(null);
        assertThat(tipoDeDocumentoDeCompra1).isNotEqualTo(tipoDeDocumentoDeCompra2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeDocumentoDeCompraDTO.class);
        TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO1 = new TipoDeDocumentoDeCompraDTO();
        tipoDeDocumentoDeCompraDTO1.setId(1L);
        TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO2 = new TipoDeDocumentoDeCompraDTO();
        assertThat(tipoDeDocumentoDeCompraDTO1).isNotEqualTo(tipoDeDocumentoDeCompraDTO2);
        tipoDeDocumentoDeCompraDTO2.setId(tipoDeDocumentoDeCompraDTO1.getId());
        assertThat(tipoDeDocumentoDeCompraDTO1).isEqualTo(tipoDeDocumentoDeCompraDTO2);
        tipoDeDocumentoDeCompraDTO2.setId(2L);
        assertThat(tipoDeDocumentoDeCompraDTO1).isNotEqualTo(tipoDeDocumentoDeCompraDTO2);
        tipoDeDocumentoDeCompraDTO1.setId(null);
        assertThat(tipoDeDocumentoDeCompraDTO1).isNotEqualTo(tipoDeDocumentoDeCompraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDeDocumentoDeCompraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDeDocumentoDeCompraMapper.fromId(null)).isNull();
    }
}
