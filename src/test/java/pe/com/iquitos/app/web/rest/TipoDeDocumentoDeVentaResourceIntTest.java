package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.TipoDeDocumentoDeVenta;
import pe.com.iquitos.app.repository.TipoDeDocumentoDeVentaRepository;
import pe.com.iquitos.app.repository.search.TipoDeDocumentoDeVentaSearchRepository;
import pe.com.iquitos.app.service.TipoDeDocumentoDeVentaService;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeVentaDTO;
import pe.com.iquitos.app.service.mapper.TipoDeDocumentoDeVentaMapper;
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
 * Test class for the TipoDeDocumentoDeVentaResource REST controller.
 *
 * @see TipoDeDocumentoDeVentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class TipoDeDocumentoDeVentaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoDeDocumentoDeVentaRepository tipoDeDocumentoDeVentaRepository;

    @Autowired
    private TipoDeDocumentoDeVentaMapper tipoDeDocumentoDeVentaMapper;

    @Autowired
    private TipoDeDocumentoDeVentaService tipoDeDocumentoDeVentaService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.TipoDeDocumentoDeVentaSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDeDocumentoDeVentaSearchRepository mockTipoDeDocumentoDeVentaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoDeDocumentoDeVentaMockMvc;

    private TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDeDocumentoDeVentaResource tipoDeDocumentoDeVentaResource = new TipoDeDocumentoDeVentaResource(tipoDeDocumentoDeVentaService);
        this.restTipoDeDocumentoDeVentaMockMvc = MockMvcBuilders.standaloneSetup(tipoDeDocumentoDeVentaResource)
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
    public static TipoDeDocumentoDeVenta createEntity(EntityManager em) {
        TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta = new TipoDeDocumentoDeVenta()
            .nombre(DEFAULT_NOMBRE);
        return tipoDeDocumentoDeVenta;
    }

    @Before
    public void initTest() {
        tipoDeDocumentoDeVenta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeDocumentoDeVenta() throws Exception {
        int databaseSizeBeforeCreate = tipoDeDocumentoDeVentaRepository.findAll().size();

        // Create the TipoDeDocumentoDeVenta
        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO = tipoDeDocumentoDeVentaMapper.toDto(tipoDeDocumentoDeVenta);
        restTipoDeDocumentoDeVentaMockMvc.perform(post("/api/tipo-de-documento-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeVentaDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDeDocumentoDeVenta in the database
        List<TipoDeDocumentoDeVenta> tipoDeDocumentoDeVentaList = tipoDeDocumentoDeVentaRepository.findAll();
        assertThat(tipoDeDocumentoDeVentaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeDocumentoDeVenta testTipoDeDocumentoDeVenta = tipoDeDocumentoDeVentaList.get(tipoDeDocumentoDeVentaList.size() - 1);
        assertThat(testTipoDeDocumentoDeVenta.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the TipoDeDocumentoDeVenta in Elasticsearch
        verify(mockTipoDeDocumentoDeVentaSearchRepository, times(1)).save(testTipoDeDocumentoDeVenta);
    }

    @Test
    @Transactional
    public void createTipoDeDocumentoDeVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDeDocumentoDeVentaRepository.findAll().size();

        // Create the TipoDeDocumentoDeVenta with an existing ID
        tipoDeDocumentoDeVenta.setId(1L);
        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO = tipoDeDocumentoDeVentaMapper.toDto(tipoDeDocumentoDeVenta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeDocumentoDeVentaMockMvc.perform(post("/api/tipo-de-documento-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeDocumentoDeVenta in the database
        List<TipoDeDocumentoDeVenta> tipoDeDocumentoDeVentaList = tipoDeDocumentoDeVentaRepository.findAll();
        assertThat(tipoDeDocumentoDeVentaList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDeDocumentoDeVenta in Elasticsearch
        verify(mockTipoDeDocumentoDeVentaSearchRepository, times(0)).save(tipoDeDocumentoDeVenta);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeDocumentoDeVentaRepository.findAll().size();
        // set the field null
        tipoDeDocumentoDeVenta.setNombre(null);

        // Create the TipoDeDocumentoDeVenta, which fails.
        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO = tipoDeDocumentoDeVentaMapper.toDto(tipoDeDocumentoDeVenta);

        restTipoDeDocumentoDeVentaMockMvc.perform(post("/api/tipo-de-documento-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeDocumentoDeVenta> tipoDeDocumentoDeVentaList = tipoDeDocumentoDeVentaRepository.findAll();
        assertThat(tipoDeDocumentoDeVentaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDeDocumentoDeVentas() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeVentaRepository.saveAndFlush(tipoDeDocumentoDeVenta);

        // Get all the tipoDeDocumentoDeVentaList
        restTipoDeDocumentoDeVentaMockMvc.perform(get("/api/tipo-de-documento-de-ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeDocumentoDeVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoDeDocumentoDeVenta() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeVentaRepository.saveAndFlush(tipoDeDocumentoDeVenta);

        // Get the tipoDeDocumentoDeVenta
        restTipoDeDocumentoDeVentaMockMvc.perform(get("/api/tipo-de-documento-de-ventas/{id}", tipoDeDocumentoDeVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeDocumentoDeVenta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDeDocumentoDeVenta() throws Exception {
        // Get the tipoDeDocumentoDeVenta
        restTipoDeDocumentoDeVentaMockMvc.perform(get("/api/tipo-de-documento-de-ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeDocumentoDeVenta() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeVentaRepository.saveAndFlush(tipoDeDocumentoDeVenta);

        int databaseSizeBeforeUpdate = tipoDeDocumentoDeVentaRepository.findAll().size();

        // Update the tipoDeDocumentoDeVenta
        TipoDeDocumentoDeVenta updatedTipoDeDocumentoDeVenta = tipoDeDocumentoDeVentaRepository.findById(tipoDeDocumentoDeVenta.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeDocumentoDeVenta are not directly saved in db
        em.detach(updatedTipoDeDocumentoDeVenta);
        updatedTipoDeDocumentoDeVenta
            .nombre(UPDATED_NOMBRE);
        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO = tipoDeDocumentoDeVentaMapper.toDto(updatedTipoDeDocumentoDeVenta);

        restTipoDeDocumentoDeVentaMockMvc.perform(put("/api/tipo-de-documento-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeVentaDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDeDocumentoDeVenta in the database
        List<TipoDeDocumentoDeVenta> tipoDeDocumentoDeVentaList = tipoDeDocumentoDeVentaRepository.findAll();
        assertThat(tipoDeDocumentoDeVentaList).hasSize(databaseSizeBeforeUpdate);
        TipoDeDocumentoDeVenta testTipoDeDocumentoDeVenta = tipoDeDocumentoDeVentaList.get(tipoDeDocumentoDeVentaList.size() - 1);
        assertThat(testTipoDeDocumentoDeVenta.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the TipoDeDocumentoDeVenta in Elasticsearch
        verify(mockTipoDeDocumentoDeVentaSearchRepository, times(1)).save(testTipoDeDocumentoDeVenta);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeDocumentoDeVenta() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeDocumentoDeVentaRepository.findAll().size();

        // Create the TipoDeDocumentoDeVenta
        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO = tipoDeDocumentoDeVentaMapper.toDto(tipoDeDocumentoDeVenta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeDocumentoDeVentaMockMvc.perform(put("/api/tipo-de-documento-de-ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDeVentaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeDocumentoDeVenta in the database
        List<TipoDeDocumentoDeVenta> tipoDeDocumentoDeVentaList = tipoDeDocumentoDeVentaRepository.findAll();
        assertThat(tipoDeDocumentoDeVentaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDeDocumentoDeVenta in Elasticsearch
        verify(mockTipoDeDocumentoDeVentaSearchRepository, times(0)).save(tipoDeDocumentoDeVenta);
    }

    @Test
    @Transactional
    public void deleteTipoDeDocumentoDeVenta() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeVentaRepository.saveAndFlush(tipoDeDocumentoDeVenta);

        int databaseSizeBeforeDelete = tipoDeDocumentoDeVentaRepository.findAll().size();

        // Get the tipoDeDocumentoDeVenta
        restTipoDeDocumentoDeVentaMockMvc.perform(delete("/api/tipo-de-documento-de-ventas/{id}", tipoDeDocumentoDeVenta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoDeDocumentoDeVenta> tipoDeDocumentoDeVentaList = tipoDeDocumentoDeVentaRepository.findAll();
        assertThat(tipoDeDocumentoDeVentaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDeDocumentoDeVenta in Elasticsearch
        verify(mockTipoDeDocumentoDeVentaSearchRepository, times(1)).deleteById(tipoDeDocumentoDeVenta.getId());
    }

    @Test
    @Transactional
    public void searchTipoDeDocumentoDeVenta() throws Exception {
        // Initialize the database
        tipoDeDocumentoDeVentaRepository.saveAndFlush(tipoDeDocumentoDeVenta);
        when(mockTipoDeDocumentoDeVentaSearchRepository.search(queryStringQuery("id:" + tipoDeDocumentoDeVenta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDeDocumentoDeVenta), PageRequest.of(0, 1), 1));
        // Search the tipoDeDocumentoDeVenta
        restTipoDeDocumentoDeVentaMockMvc.perform(get("/api/_search/tipo-de-documento-de-ventas?query=id:" + tipoDeDocumentoDeVenta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeDocumentoDeVenta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeDocumentoDeVenta.class);
        TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta1 = new TipoDeDocumentoDeVenta();
        tipoDeDocumentoDeVenta1.setId(1L);
        TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta2 = new TipoDeDocumentoDeVenta();
        tipoDeDocumentoDeVenta2.setId(tipoDeDocumentoDeVenta1.getId());
        assertThat(tipoDeDocumentoDeVenta1).isEqualTo(tipoDeDocumentoDeVenta2);
        tipoDeDocumentoDeVenta2.setId(2L);
        assertThat(tipoDeDocumentoDeVenta1).isNotEqualTo(tipoDeDocumentoDeVenta2);
        tipoDeDocumentoDeVenta1.setId(null);
        assertThat(tipoDeDocumentoDeVenta1).isNotEqualTo(tipoDeDocumentoDeVenta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeDocumentoDeVentaDTO.class);
        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO1 = new TipoDeDocumentoDeVentaDTO();
        tipoDeDocumentoDeVentaDTO1.setId(1L);
        TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO2 = new TipoDeDocumentoDeVentaDTO();
        assertThat(tipoDeDocumentoDeVentaDTO1).isNotEqualTo(tipoDeDocumentoDeVentaDTO2);
        tipoDeDocumentoDeVentaDTO2.setId(tipoDeDocumentoDeVentaDTO1.getId());
        assertThat(tipoDeDocumentoDeVentaDTO1).isEqualTo(tipoDeDocumentoDeVentaDTO2);
        tipoDeDocumentoDeVentaDTO2.setId(2L);
        assertThat(tipoDeDocumentoDeVentaDTO1).isNotEqualTo(tipoDeDocumentoDeVentaDTO2);
        tipoDeDocumentoDeVentaDTO1.setId(null);
        assertThat(tipoDeDocumentoDeVentaDTO1).isNotEqualTo(tipoDeDocumentoDeVentaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDeDocumentoDeVentaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDeDocumentoDeVentaMapper.fromId(null)).isNull();
    }
}
