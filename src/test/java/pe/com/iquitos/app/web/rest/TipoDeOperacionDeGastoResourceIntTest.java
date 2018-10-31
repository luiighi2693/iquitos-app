package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.TipoDeOperacionDeGasto;
import pe.com.iquitos.app.repository.TipoDeOperacionDeGastoRepository;
import pe.com.iquitos.app.repository.search.TipoDeOperacionDeGastoSearchRepository;
import pe.com.iquitos.app.service.TipoDeOperacionDeGastoService;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeGastoDTO;
import pe.com.iquitos.app.service.mapper.TipoDeOperacionDeGastoMapper;
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
 * Test class for the TipoDeOperacionDeGastoResource REST controller.
 *
 * @see TipoDeOperacionDeGastoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class TipoDeOperacionDeGastoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoDeOperacionDeGastoRepository tipoDeOperacionDeGastoRepository;

    @Autowired
    private TipoDeOperacionDeGastoMapper tipoDeOperacionDeGastoMapper;
    
    @Autowired
    private TipoDeOperacionDeGastoService tipoDeOperacionDeGastoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.TipoDeOperacionDeGastoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDeOperacionDeGastoSearchRepository mockTipoDeOperacionDeGastoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoDeOperacionDeGastoMockMvc;

    private TipoDeOperacionDeGasto tipoDeOperacionDeGasto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDeOperacionDeGastoResource tipoDeOperacionDeGastoResource = new TipoDeOperacionDeGastoResource(tipoDeOperacionDeGastoService);
        this.restTipoDeOperacionDeGastoMockMvc = MockMvcBuilders.standaloneSetup(tipoDeOperacionDeGastoResource)
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
    public static TipoDeOperacionDeGasto createEntity(EntityManager em) {
        TipoDeOperacionDeGasto tipoDeOperacionDeGasto = new TipoDeOperacionDeGasto()
            .nombre(DEFAULT_NOMBRE);
        return tipoDeOperacionDeGasto;
    }

    @Before
    public void initTest() {
        tipoDeOperacionDeGasto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeOperacionDeGasto() throws Exception {
        int databaseSizeBeforeCreate = tipoDeOperacionDeGastoRepository.findAll().size();

        // Create the TipoDeOperacionDeGasto
        TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO = tipoDeOperacionDeGastoMapper.toDto(tipoDeOperacionDeGasto);
        restTipoDeOperacionDeGastoMockMvc.perform(post("/api/tipo-de-operacion-de-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeGastoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDeOperacionDeGasto in the database
        List<TipoDeOperacionDeGasto> tipoDeOperacionDeGastoList = tipoDeOperacionDeGastoRepository.findAll();
        assertThat(tipoDeOperacionDeGastoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeOperacionDeGasto testTipoDeOperacionDeGasto = tipoDeOperacionDeGastoList.get(tipoDeOperacionDeGastoList.size() - 1);
        assertThat(testTipoDeOperacionDeGasto.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the TipoDeOperacionDeGasto in Elasticsearch
        verify(mockTipoDeOperacionDeGastoSearchRepository, times(1)).save(testTipoDeOperacionDeGasto);
    }

    @Test
    @Transactional
    public void createTipoDeOperacionDeGastoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDeOperacionDeGastoRepository.findAll().size();

        // Create the TipoDeOperacionDeGasto with an existing ID
        tipoDeOperacionDeGasto.setId(1L);
        TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO = tipoDeOperacionDeGastoMapper.toDto(tipoDeOperacionDeGasto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeOperacionDeGastoMockMvc.perform(post("/api/tipo-de-operacion-de-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeGastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeOperacionDeGasto in the database
        List<TipoDeOperacionDeGasto> tipoDeOperacionDeGastoList = tipoDeOperacionDeGastoRepository.findAll();
        assertThat(tipoDeOperacionDeGastoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDeOperacionDeGasto in Elasticsearch
        verify(mockTipoDeOperacionDeGastoSearchRepository, times(0)).save(tipoDeOperacionDeGasto);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeOperacionDeGastoRepository.findAll().size();
        // set the field null
        tipoDeOperacionDeGasto.setNombre(null);

        // Create the TipoDeOperacionDeGasto, which fails.
        TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO = tipoDeOperacionDeGastoMapper.toDto(tipoDeOperacionDeGasto);

        restTipoDeOperacionDeGastoMockMvc.perform(post("/api/tipo-de-operacion-de-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeGastoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeOperacionDeGasto> tipoDeOperacionDeGastoList = tipoDeOperacionDeGastoRepository.findAll();
        assertThat(tipoDeOperacionDeGastoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDeOperacionDeGastos() throws Exception {
        // Initialize the database
        tipoDeOperacionDeGastoRepository.saveAndFlush(tipoDeOperacionDeGasto);

        // Get all the tipoDeOperacionDeGastoList
        restTipoDeOperacionDeGastoMockMvc.perform(get("/api/tipo-de-operacion-de-gastos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeOperacionDeGasto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoDeOperacionDeGasto() throws Exception {
        // Initialize the database
        tipoDeOperacionDeGastoRepository.saveAndFlush(tipoDeOperacionDeGasto);

        // Get the tipoDeOperacionDeGasto
        restTipoDeOperacionDeGastoMockMvc.perform(get("/api/tipo-de-operacion-de-gastos/{id}", tipoDeOperacionDeGasto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeOperacionDeGasto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDeOperacionDeGasto() throws Exception {
        // Get the tipoDeOperacionDeGasto
        restTipoDeOperacionDeGastoMockMvc.perform(get("/api/tipo-de-operacion-de-gastos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeOperacionDeGasto() throws Exception {
        // Initialize the database
        tipoDeOperacionDeGastoRepository.saveAndFlush(tipoDeOperacionDeGasto);

        int databaseSizeBeforeUpdate = tipoDeOperacionDeGastoRepository.findAll().size();

        // Update the tipoDeOperacionDeGasto
        TipoDeOperacionDeGasto updatedTipoDeOperacionDeGasto = tipoDeOperacionDeGastoRepository.findById(tipoDeOperacionDeGasto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeOperacionDeGasto are not directly saved in db
        em.detach(updatedTipoDeOperacionDeGasto);
        updatedTipoDeOperacionDeGasto
            .nombre(UPDATED_NOMBRE);
        TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO = tipoDeOperacionDeGastoMapper.toDto(updatedTipoDeOperacionDeGasto);

        restTipoDeOperacionDeGastoMockMvc.perform(put("/api/tipo-de-operacion-de-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeGastoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDeOperacionDeGasto in the database
        List<TipoDeOperacionDeGasto> tipoDeOperacionDeGastoList = tipoDeOperacionDeGastoRepository.findAll();
        assertThat(tipoDeOperacionDeGastoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeOperacionDeGasto testTipoDeOperacionDeGasto = tipoDeOperacionDeGastoList.get(tipoDeOperacionDeGastoList.size() - 1);
        assertThat(testTipoDeOperacionDeGasto.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the TipoDeOperacionDeGasto in Elasticsearch
        verify(mockTipoDeOperacionDeGastoSearchRepository, times(1)).save(testTipoDeOperacionDeGasto);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeOperacionDeGasto() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeOperacionDeGastoRepository.findAll().size();

        // Create the TipoDeOperacionDeGasto
        TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO = tipoDeOperacionDeGastoMapper.toDto(tipoDeOperacionDeGasto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeOperacionDeGastoMockMvc.perform(put("/api/tipo-de-operacion-de-gastos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeGastoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeOperacionDeGasto in the database
        List<TipoDeOperacionDeGasto> tipoDeOperacionDeGastoList = tipoDeOperacionDeGastoRepository.findAll();
        assertThat(tipoDeOperacionDeGastoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDeOperacionDeGasto in Elasticsearch
        verify(mockTipoDeOperacionDeGastoSearchRepository, times(0)).save(tipoDeOperacionDeGasto);
    }

    @Test
    @Transactional
    public void deleteTipoDeOperacionDeGasto() throws Exception {
        // Initialize the database
        tipoDeOperacionDeGastoRepository.saveAndFlush(tipoDeOperacionDeGasto);

        int databaseSizeBeforeDelete = tipoDeOperacionDeGastoRepository.findAll().size();

        // Get the tipoDeOperacionDeGasto
        restTipoDeOperacionDeGastoMockMvc.perform(delete("/api/tipo-de-operacion-de-gastos/{id}", tipoDeOperacionDeGasto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoDeOperacionDeGasto> tipoDeOperacionDeGastoList = tipoDeOperacionDeGastoRepository.findAll();
        assertThat(tipoDeOperacionDeGastoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDeOperacionDeGasto in Elasticsearch
        verify(mockTipoDeOperacionDeGastoSearchRepository, times(1)).deleteById(tipoDeOperacionDeGasto.getId());
    }

    @Test
    @Transactional
    public void searchTipoDeOperacionDeGasto() throws Exception {
        // Initialize the database
        tipoDeOperacionDeGastoRepository.saveAndFlush(tipoDeOperacionDeGasto);
        when(mockTipoDeOperacionDeGastoSearchRepository.search(queryStringQuery("id:" + tipoDeOperacionDeGasto.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDeOperacionDeGasto), PageRequest.of(0, 1), 1));
        // Search the tipoDeOperacionDeGasto
        restTipoDeOperacionDeGastoMockMvc.perform(get("/api/_search/tipo-de-operacion-de-gastos?query=id:" + tipoDeOperacionDeGasto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeOperacionDeGasto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeOperacionDeGasto.class);
        TipoDeOperacionDeGasto tipoDeOperacionDeGasto1 = new TipoDeOperacionDeGasto();
        tipoDeOperacionDeGasto1.setId(1L);
        TipoDeOperacionDeGasto tipoDeOperacionDeGasto2 = new TipoDeOperacionDeGasto();
        tipoDeOperacionDeGasto2.setId(tipoDeOperacionDeGasto1.getId());
        assertThat(tipoDeOperacionDeGasto1).isEqualTo(tipoDeOperacionDeGasto2);
        tipoDeOperacionDeGasto2.setId(2L);
        assertThat(tipoDeOperacionDeGasto1).isNotEqualTo(tipoDeOperacionDeGasto2);
        tipoDeOperacionDeGasto1.setId(null);
        assertThat(tipoDeOperacionDeGasto1).isNotEqualTo(tipoDeOperacionDeGasto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeOperacionDeGastoDTO.class);
        TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO1 = new TipoDeOperacionDeGastoDTO();
        tipoDeOperacionDeGastoDTO1.setId(1L);
        TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO2 = new TipoDeOperacionDeGastoDTO();
        assertThat(tipoDeOperacionDeGastoDTO1).isNotEqualTo(tipoDeOperacionDeGastoDTO2);
        tipoDeOperacionDeGastoDTO2.setId(tipoDeOperacionDeGastoDTO1.getId());
        assertThat(tipoDeOperacionDeGastoDTO1).isEqualTo(tipoDeOperacionDeGastoDTO2);
        tipoDeOperacionDeGastoDTO2.setId(2L);
        assertThat(tipoDeOperacionDeGastoDTO1).isNotEqualTo(tipoDeOperacionDeGastoDTO2);
        tipoDeOperacionDeGastoDTO1.setId(null);
        assertThat(tipoDeOperacionDeGastoDTO1).isNotEqualTo(tipoDeOperacionDeGastoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDeOperacionDeGastoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDeOperacionDeGastoMapper.fromId(null)).isNull();
    }
}
