package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.EstatusDeCompra;
import pe.com.iquitos.app.repository.EstatusDeCompraRepository;
import pe.com.iquitos.app.repository.search.EstatusDeCompraSearchRepository;
import pe.com.iquitos.app.service.EstatusDeCompraService;
import pe.com.iquitos.app.service.dto.EstatusDeCompraDTO;
import pe.com.iquitos.app.service.mapper.EstatusDeCompraMapper;
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
 * Test class for the EstatusDeCompraResource REST controller.
 *
 * @see EstatusDeCompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class EstatusDeCompraResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private EstatusDeCompraRepository estatusDeCompraRepository;

    @Autowired
    private EstatusDeCompraMapper estatusDeCompraMapper;

    @Autowired
    private EstatusDeCompraService estatusDeCompraService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.EstatusDeCompraSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstatusDeCompraSearchRepository mockEstatusDeCompraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstatusDeCompraMockMvc;

    private EstatusDeCompra estatusDeCompra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusDeCompraResource estatusDeCompraResource = new EstatusDeCompraResource(estatusDeCompraService);
        this.restEstatusDeCompraMockMvc = MockMvcBuilders.standaloneSetup(estatusDeCompraResource)
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
    public static EstatusDeCompra createEntity(EntityManager em) {
        EstatusDeCompra estatusDeCompra = new EstatusDeCompra()
            .nombre(DEFAULT_NOMBRE);
        return estatusDeCompra;
    }

    @Before
    public void initTest() {
        estatusDeCompra = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusDeCompra() throws Exception {
        int databaseSizeBeforeCreate = estatusDeCompraRepository.findAll().size();

        // Create the EstatusDeCompra
        EstatusDeCompraDTO estatusDeCompraDTO = estatusDeCompraMapper.toDto(estatusDeCompra);
        restEstatusDeCompraMockMvc.perform(post("/api/estatus-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeCompraDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusDeCompra in the database
        List<EstatusDeCompra> estatusDeCompraList = estatusDeCompraRepository.findAll();
        assertThat(estatusDeCompraList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusDeCompra testEstatusDeCompra = estatusDeCompraList.get(estatusDeCompraList.size() - 1);
        assertThat(testEstatusDeCompra.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the EstatusDeCompra in Elasticsearch
        verify(mockEstatusDeCompraSearchRepository, times(1)).save(testEstatusDeCompra);
    }

    @Test
    @Transactional
    public void createEstatusDeCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusDeCompraRepository.findAll().size();

        // Create the EstatusDeCompra with an existing ID
        estatusDeCompra.setId(1L);
        EstatusDeCompraDTO estatusDeCompraDTO = estatusDeCompraMapper.toDto(estatusDeCompra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusDeCompraMockMvc.perform(post("/api/estatus-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusDeCompra in the database
        List<EstatusDeCompra> estatusDeCompraList = estatusDeCompraRepository.findAll();
        assertThat(estatusDeCompraList).hasSize(databaseSizeBeforeCreate);

        // Validate the EstatusDeCompra in Elasticsearch
        verify(mockEstatusDeCompraSearchRepository, times(0)).save(estatusDeCompra);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = estatusDeCompraRepository.findAll().size();
        // set the field null
        estatusDeCompra.setNombre(null);

        // Create the EstatusDeCompra, which fails.
        EstatusDeCompraDTO estatusDeCompraDTO = estatusDeCompraMapper.toDto(estatusDeCompra);

        restEstatusDeCompraMockMvc.perform(post("/api/estatus-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeCompraDTO)))
            .andExpect(status().isBadRequest());

        List<EstatusDeCompra> estatusDeCompraList = estatusDeCompraRepository.findAll();
        assertThat(estatusDeCompraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstatusDeCompras() throws Exception {
        // Initialize the database
        estatusDeCompraRepository.saveAndFlush(estatusDeCompra);

        // Get all the estatusDeCompraList
        restEstatusDeCompraMockMvc.perform(get("/api/estatus-de-compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusDeCompra() throws Exception {
        // Initialize the database
        estatusDeCompraRepository.saveAndFlush(estatusDeCompra);

        // Get the estatusDeCompra
        restEstatusDeCompraMockMvc.perform(get("/api/estatus-de-compras/{id}", estatusDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusDeCompra.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstatusDeCompra() throws Exception {
        // Get the estatusDeCompra
        restEstatusDeCompraMockMvc.perform(get("/api/estatus-de-compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusDeCompra() throws Exception {
        // Initialize the database
        estatusDeCompraRepository.saveAndFlush(estatusDeCompra);

        int databaseSizeBeforeUpdate = estatusDeCompraRepository.findAll().size();

        // Update the estatusDeCompra
        EstatusDeCompra updatedEstatusDeCompra = estatusDeCompraRepository.findById(estatusDeCompra.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusDeCompra are not directly saved in db
        em.detach(updatedEstatusDeCompra);
        updatedEstatusDeCompra
            .nombre(UPDATED_NOMBRE);
        EstatusDeCompraDTO estatusDeCompraDTO = estatusDeCompraMapper.toDto(updatedEstatusDeCompra);

        restEstatusDeCompraMockMvc.perform(put("/api/estatus-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeCompraDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusDeCompra in the database
        List<EstatusDeCompra> estatusDeCompraList = estatusDeCompraRepository.findAll();
        assertThat(estatusDeCompraList).hasSize(databaseSizeBeforeUpdate);
        EstatusDeCompra testEstatusDeCompra = estatusDeCompraList.get(estatusDeCompraList.size() - 1);
        assertThat(testEstatusDeCompra.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the EstatusDeCompra in Elasticsearch
        verify(mockEstatusDeCompraSearchRepository, times(1)).save(testEstatusDeCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusDeCompra() throws Exception {
        int databaseSizeBeforeUpdate = estatusDeCompraRepository.findAll().size();

        // Create the EstatusDeCompra
        EstatusDeCompraDTO estatusDeCompraDTO = estatusDeCompraMapper.toDto(estatusDeCompra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusDeCompraMockMvc.perform(put("/api/estatus-de-compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeCompraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusDeCompra in the database
        List<EstatusDeCompra> estatusDeCompraList = estatusDeCompraRepository.findAll();
        assertThat(estatusDeCompraList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EstatusDeCompra in Elasticsearch
        verify(mockEstatusDeCompraSearchRepository, times(0)).save(estatusDeCompra);
    }

    @Test
    @Transactional
    public void deleteEstatusDeCompra() throws Exception {
        // Initialize the database
        estatusDeCompraRepository.saveAndFlush(estatusDeCompra);

        int databaseSizeBeforeDelete = estatusDeCompraRepository.findAll().size();

        // Get the estatusDeCompra
        restEstatusDeCompraMockMvc.perform(delete("/api/estatus-de-compras/{id}", estatusDeCompra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EstatusDeCompra> estatusDeCompraList = estatusDeCompraRepository.findAll();
        assertThat(estatusDeCompraList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EstatusDeCompra in Elasticsearch
        verify(mockEstatusDeCompraSearchRepository, times(1)).deleteById(estatusDeCompra.getId());
    }

    @Test
    @Transactional
    public void searchEstatusDeCompra() throws Exception {
        // Initialize the database
        estatusDeCompraRepository.saveAndFlush(estatusDeCompra);
        when(mockEstatusDeCompraSearchRepository.search(queryStringQuery("id:" + estatusDeCompra.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(estatusDeCompra), PageRequest.of(0, 1), 1));
        // Search the estatusDeCompra
        restEstatusDeCompraMockMvc.perform(get("/api/_search/estatus-de-compras?query=id:" + estatusDeCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusDeCompra.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusDeCompra.class);
        EstatusDeCompra estatusDeCompra1 = new EstatusDeCompra();
        estatusDeCompra1.setId(1L);
        EstatusDeCompra estatusDeCompra2 = new EstatusDeCompra();
        estatusDeCompra2.setId(estatusDeCompra1.getId());
        assertThat(estatusDeCompra1).isEqualTo(estatusDeCompra2);
        estatusDeCompra2.setId(2L);
        assertThat(estatusDeCompra1).isNotEqualTo(estatusDeCompra2);
        estatusDeCompra1.setId(null);
        assertThat(estatusDeCompra1).isNotEqualTo(estatusDeCompra2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusDeCompraDTO.class);
        EstatusDeCompraDTO estatusDeCompraDTO1 = new EstatusDeCompraDTO();
        estatusDeCompraDTO1.setId(1L);
        EstatusDeCompraDTO estatusDeCompraDTO2 = new EstatusDeCompraDTO();
        assertThat(estatusDeCompraDTO1).isNotEqualTo(estatusDeCompraDTO2);
        estatusDeCompraDTO2.setId(estatusDeCompraDTO1.getId());
        assertThat(estatusDeCompraDTO1).isEqualTo(estatusDeCompraDTO2);
        estatusDeCompraDTO2.setId(2L);
        assertThat(estatusDeCompraDTO1).isNotEqualTo(estatusDeCompraDTO2);
        estatusDeCompraDTO1.setId(null);
        assertThat(estatusDeCompraDTO1).isNotEqualTo(estatusDeCompraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusDeCompraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusDeCompraMapper.fromId(null)).isNull();
    }
}
