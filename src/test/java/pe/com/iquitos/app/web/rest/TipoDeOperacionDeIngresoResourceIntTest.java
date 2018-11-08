package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.TipoDeOperacionDeIngreso;
import pe.com.iquitos.app.repository.TipoDeOperacionDeIngresoRepository;
import pe.com.iquitos.app.repository.search.TipoDeOperacionDeIngresoSearchRepository;
import pe.com.iquitos.app.service.TipoDeOperacionDeIngresoService;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeIngresoDTO;
import pe.com.iquitos.app.service.mapper.TipoDeOperacionDeIngresoMapper;
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
 * Test class for the TipoDeOperacionDeIngresoResource REST controller.
 *
 * @see TipoDeOperacionDeIngresoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class TipoDeOperacionDeIngresoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoDeOperacionDeIngresoRepository tipoDeOperacionDeIngresoRepository;

    @Autowired
    private TipoDeOperacionDeIngresoMapper tipoDeOperacionDeIngresoMapper;

    @Autowired
    private TipoDeOperacionDeIngresoService tipoDeOperacionDeIngresoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.TipoDeOperacionDeIngresoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDeOperacionDeIngresoSearchRepository mockTipoDeOperacionDeIngresoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoDeOperacionDeIngresoMockMvc;

    private TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDeOperacionDeIngresoResource tipoDeOperacionDeIngresoResource = new TipoDeOperacionDeIngresoResource(tipoDeOperacionDeIngresoService);
        this.restTipoDeOperacionDeIngresoMockMvc = MockMvcBuilders.standaloneSetup(tipoDeOperacionDeIngresoResource)
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
    public static TipoDeOperacionDeIngreso createEntity(EntityManager em) {
        TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso = new TipoDeOperacionDeIngreso()
            .nombre(DEFAULT_NOMBRE);
        return tipoDeOperacionDeIngreso;
    }

    @Before
    public void initTest() {
        tipoDeOperacionDeIngreso = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeOperacionDeIngreso() throws Exception {
        int databaseSizeBeforeCreate = tipoDeOperacionDeIngresoRepository.findAll().size();

        // Create the TipoDeOperacionDeIngreso
        TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO = tipoDeOperacionDeIngresoMapper.toDto(tipoDeOperacionDeIngreso);
        restTipoDeOperacionDeIngresoMockMvc.perform(post("/api/tipo-de-operacion-de-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeIngresoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDeOperacionDeIngreso in the database
        List<TipoDeOperacionDeIngreso> tipoDeOperacionDeIngresoList = tipoDeOperacionDeIngresoRepository.findAll();
        assertThat(tipoDeOperacionDeIngresoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeOperacionDeIngreso testTipoDeOperacionDeIngreso = tipoDeOperacionDeIngresoList.get(tipoDeOperacionDeIngresoList.size() - 1);
        assertThat(testTipoDeOperacionDeIngreso.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the TipoDeOperacionDeIngreso in Elasticsearch
        verify(mockTipoDeOperacionDeIngresoSearchRepository, times(1)).save(testTipoDeOperacionDeIngreso);
    }

    @Test
    @Transactional
    public void createTipoDeOperacionDeIngresoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDeOperacionDeIngresoRepository.findAll().size();

        // Create the TipoDeOperacionDeIngreso with an existing ID
        tipoDeOperacionDeIngreso.setId(1L);
        TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO = tipoDeOperacionDeIngresoMapper.toDto(tipoDeOperacionDeIngreso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeOperacionDeIngresoMockMvc.perform(post("/api/tipo-de-operacion-de-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeIngresoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeOperacionDeIngreso in the database
        List<TipoDeOperacionDeIngreso> tipoDeOperacionDeIngresoList = tipoDeOperacionDeIngresoRepository.findAll();
        assertThat(tipoDeOperacionDeIngresoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDeOperacionDeIngreso in Elasticsearch
        verify(mockTipoDeOperacionDeIngresoSearchRepository, times(0)).save(tipoDeOperacionDeIngreso);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeOperacionDeIngresoRepository.findAll().size();
        // set the field null
        tipoDeOperacionDeIngreso.setNombre(null);

        // Create the TipoDeOperacionDeIngreso, which fails.
        TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO = tipoDeOperacionDeIngresoMapper.toDto(tipoDeOperacionDeIngreso);

        restTipoDeOperacionDeIngresoMockMvc.perform(post("/api/tipo-de-operacion-de-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeIngresoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeOperacionDeIngreso> tipoDeOperacionDeIngresoList = tipoDeOperacionDeIngresoRepository.findAll();
        assertThat(tipoDeOperacionDeIngresoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDeOperacionDeIngresos() throws Exception {
        // Initialize the database
        tipoDeOperacionDeIngresoRepository.saveAndFlush(tipoDeOperacionDeIngreso);

        // Get all the tipoDeOperacionDeIngresoList
        restTipoDeOperacionDeIngresoMockMvc.perform(get("/api/tipo-de-operacion-de-ingresos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeOperacionDeIngreso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoDeOperacionDeIngreso() throws Exception {
        // Initialize the database
        tipoDeOperacionDeIngresoRepository.saveAndFlush(tipoDeOperacionDeIngreso);

        // Get the tipoDeOperacionDeIngreso
        restTipoDeOperacionDeIngresoMockMvc.perform(get("/api/tipo-de-operacion-de-ingresos/{id}", tipoDeOperacionDeIngreso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeOperacionDeIngreso.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDeOperacionDeIngreso() throws Exception {
        // Get the tipoDeOperacionDeIngreso
        restTipoDeOperacionDeIngresoMockMvc.perform(get("/api/tipo-de-operacion-de-ingresos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeOperacionDeIngreso() throws Exception {
        // Initialize the database
        tipoDeOperacionDeIngresoRepository.saveAndFlush(tipoDeOperacionDeIngreso);

        int databaseSizeBeforeUpdate = tipoDeOperacionDeIngresoRepository.findAll().size();

        // Update the tipoDeOperacionDeIngreso
        TipoDeOperacionDeIngreso updatedTipoDeOperacionDeIngreso = tipoDeOperacionDeIngresoRepository.findById(tipoDeOperacionDeIngreso.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeOperacionDeIngreso are not directly saved in db
        em.detach(updatedTipoDeOperacionDeIngreso);
        updatedTipoDeOperacionDeIngreso
            .nombre(UPDATED_NOMBRE);
        TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO = tipoDeOperacionDeIngresoMapper.toDto(updatedTipoDeOperacionDeIngreso);

        restTipoDeOperacionDeIngresoMockMvc.perform(put("/api/tipo-de-operacion-de-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeIngresoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDeOperacionDeIngreso in the database
        List<TipoDeOperacionDeIngreso> tipoDeOperacionDeIngresoList = tipoDeOperacionDeIngresoRepository.findAll();
        assertThat(tipoDeOperacionDeIngresoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeOperacionDeIngreso testTipoDeOperacionDeIngreso = tipoDeOperacionDeIngresoList.get(tipoDeOperacionDeIngresoList.size() - 1);
        assertThat(testTipoDeOperacionDeIngreso.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the TipoDeOperacionDeIngreso in Elasticsearch
        verify(mockTipoDeOperacionDeIngresoSearchRepository, times(1)).save(testTipoDeOperacionDeIngreso);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeOperacionDeIngreso() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeOperacionDeIngresoRepository.findAll().size();

        // Create the TipoDeOperacionDeIngreso
        TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO = tipoDeOperacionDeIngresoMapper.toDto(tipoDeOperacionDeIngreso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeOperacionDeIngresoMockMvc.perform(put("/api/tipo-de-operacion-de-ingresos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeOperacionDeIngresoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeOperacionDeIngreso in the database
        List<TipoDeOperacionDeIngreso> tipoDeOperacionDeIngresoList = tipoDeOperacionDeIngresoRepository.findAll();
        assertThat(tipoDeOperacionDeIngresoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDeOperacionDeIngreso in Elasticsearch
        verify(mockTipoDeOperacionDeIngresoSearchRepository, times(0)).save(tipoDeOperacionDeIngreso);
    }

    @Test
    @Transactional
    public void deleteTipoDeOperacionDeIngreso() throws Exception {
        // Initialize the database
        tipoDeOperacionDeIngresoRepository.saveAndFlush(tipoDeOperacionDeIngreso);

        int databaseSizeBeforeDelete = tipoDeOperacionDeIngresoRepository.findAll().size();

        // Get the tipoDeOperacionDeIngreso
        restTipoDeOperacionDeIngresoMockMvc.perform(delete("/api/tipo-de-operacion-de-ingresos/{id}", tipoDeOperacionDeIngreso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoDeOperacionDeIngreso> tipoDeOperacionDeIngresoList = tipoDeOperacionDeIngresoRepository.findAll();
        assertThat(tipoDeOperacionDeIngresoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDeOperacionDeIngreso in Elasticsearch
        verify(mockTipoDeOperacionDeIngresoSearchRepository, times(1)).deleteById(tipoDeOperacionDeIngreso.getId());
    }

    @Test
    @Transactional
    public void searchTipoDeOperacionDeIngreso() throws Exception {
        // Initialize the database
        tipoDeOperacionDeIngresoRepository.saveAndFlush(tipoDeOperacionDeIngreso);
        when(mockTipoDeOperacionDeIngresoSearchRepository.search(queryStringQuery("id:" + tipoDeOperacionDeIngreso.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDeOperacionDeIngreso), PageRequest.of(0, 1), 1));
        // Search the tipoDeOperacionDeIngreso
        restTipoDeOperacionDeIngresoMockMvc.perform(get("/api/_search/tipo-de-operacion-de-ingresos?query=id:" + tipoDeOperacionDeIngreso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeOperacionDeIngreso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeOperacionDeIngreso.class);
        TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso1 = new TipoDeOperacionDeIngreso();
        tipoDeOperacionDeIngreso1.setId(1L);
        TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso2 = new TipoDeOperacionDeIngreso();
        tipoDeOperacionDeIngreso2.setId(tipoDeOperacionDeIngreso1.getId());
        assertThat(tipoDeOperacionDeIngreso1).isEqualTo(tipoDeOperacionDeIngreso2);
        tipoDeOperacionDeIngreso2.setId(2L);
        assertThat(tipoDeOperacionDeIngreso1).isNotEqualTo(tipoDeOperacionDeIngreso2);
        tipoDeOperacionDeIngreso1.setId(null);
        assertThat(tipoDeOperacionDeIngreso1).isNotEqualTo(tipoDeOperacionDeIngreso2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeOperacionDeIngresoDTO.class);
        TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO1 = new TipoDeOperacionDeIngresoDTO();
        tipoDeOperacionDeIngresoDTO1.setId(1L);
        TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO2 = new TipoDeOperacionDeIngresoDTO();
        assertThat(tipoDeOperacionDeIngresoDTO1).isNotEqualTo(tipoDeOperacionDeIngresoDTO2);
        tipoDeOperacionDeIngresoDTO2.setId(tipoDeOperacionDeIngresoDTO1.getId());
        assertThat(tipoDeOperacionDeIngresoDTO1).isEqualTo(tipoDeOperacionDeIngresoDTO2);
        tipoDeOperacionDeIngresoDTO2.setId(2L);
        assertThat(tipoDeOperacionDeIngresoDTO1).isNotEqualTo(tipoDeOperacionDeIngresoDTO2);
        tipoDeOperacionDeIngresoDTO1.setId(null);
        assertThat(tipoDeOperacionDeIngresoDTO1).isNotEqualTo(tipoDeOperacionDeIngresoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDeOperacionDeIngresoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDeOperacionDeIngresoMapper.fromId(null)).isNull();
    }
}
