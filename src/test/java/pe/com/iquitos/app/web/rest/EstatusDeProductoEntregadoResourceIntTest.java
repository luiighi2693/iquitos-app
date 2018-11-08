package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.EstatusDeProductoEntregado;
import pe.com.iquitos.app.repository.EstatusDeProductoEntregadoRepository;
import pe.com.iquitos.app.repository.search.EstatusDeProductoEntregadoSearchRepository;
import pe.com.iquitos.app.service.EstatusDeProductoEntregadoService;
import pe.com.iquitos.app.service.dto.EstatusDeProductoEntregadoDTO;
import pe.com.iquitos.app.service.mapper.EstatusDeProductoEntregadoMapper;
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
 * Test class for the EstatusDeProductoEntregadoResource REST controller.
 *
 * @see EstatusDeProductoEntregadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class EstatusDeProductoEntregadoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private EstatusDeProductoEntregadoRepository estatusDeProductoEntregadoRepository;

    @Autowired
    private EstatusDeProductoEntregadoMapper estatusDeProductoEntregadoMapper;

    @Autowired
    private EstatusDeProductoEntregadoService estatusDeProductoEntregadoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.EstatusDeProductoEntregadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EstatusDeProductoEntregadoSearchRepository mockEstatusDeProductoEntregadoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEstatusDeProductoEntregadoMockMvc;

    private EstatusDeProductoEntregado estatusDeProductoEntregado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EstatusDeProductoEntregadoResource estatusDeProductoEntregadoResource = new EstatusDeProductoEntregadoResource(estatusDeProductoEntregadoService);
        this.restEstatusDeProductoEntregadoMockMvc = MockMvcBuilders.standaloneSetup(estatusDeProductoEntregadoResource)
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
    public static EstatusDeProductoEntregado createEntity(EntityManager em) {
        EstatusDeProductoEntregado estatusDeProductoEntregado = new EstatusDeProductoEntregado()
            .nombre(DEFAULT_NOMBRE);
        return estatusDeProductoEntregado;
    }

    @Before
    public void initTest() {
        estatusDeProductoEntregado = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstatusDeProductoEntregado() throws Exception {
        int databaseSizeBeforeCreate = estatusDeProductoEntregadoRepository.findAll().size();

        // Create the EstatusDeProductoEntregado
        EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO = estatusDeProductoEntregadoMapper.toDto(estatusDeProductoEntregado);
        restEstatusDeProductoEntregadoMockMvc.perform(post("/api/estatus-de-producto-entregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeProductoEntregadoDTO)))
            .andExpect(status().isCreated());

        // Validate the EstatusDeProductoEntregado in the database
        List<EstatusDeProductoEntregado> estatusDeProductoEntregadoList = estatusDeProductoEntregadoRepository.findAll();
        assertThat(estatusDeProductoEntregadoList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusDeProductoEntregado testEstatusDeProductoEntregado = estatusDeProductoEntregadoList.get(estatusDeProductoEntregadoList.size() - 1);
        assertThat(testEstatusDeProductoEntregado.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the EstatusDeProductoEntregado in Elasticsearch
        verify(mockEstatusDeProductoEntregadoSearchRepository, times(1)).save(testEstatusDeProductoEntregado);
    }

    @Test
    @Transactional
    public void createEstatusDeProductoEntregadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estatusDeProductoEntregadoRepository.findAll().size();

        // Create the EstatusDeProductoEntregado with an existing ID
        estatusDeProductoEntregado.setId(1L);
        EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO = estatusDeProductoEntregadoMapper.toDto(estatusDeProductoEntregado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusDeProductoEntregadoMockMvc.perform(post("/api/estatus-de-producto-entregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeProductoEntregadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusDeProductoEntregado in the database
        List<EstatusDeProductoEntregado> estatusDeProductoEntregadoList = estatusDeProductoEntregadoRepository.findAll();
        assertThat(estatusDeProductoEntregadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the EstatusDeProductoEntregado in Elasticsearch
        verify(mockEstatusDeProductoEntregadoSearchRepository, times(0)).save(estatusDeProductoEntregado);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = estatusDeProductoEntregadoRepository.findAll().size();
        // set the field null
        estatusDeProductoEntregado.setNombre(null);

        // Create the EstatusDeProductoEntregado, which fails.
        EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO = estatusDeProductoEntregadoMapper.toDto(estatusDeProductoEntregado);

        restEstatusDeProductoEntregadoMockMvc.perform(post("/api/estatus-de-producto-entregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeProductoEntregadoDTO)))
            .andExpect(status().isBadRequest());

        List<EstatusDeProductoEntregado> estatusDeProductoEntregadoList = estatusDeProductoEntregadoRepository.findAll();
        assertThat(estatusDeProductoEntregadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstatusDeProductoEntregados() throws Exception {
        // Initialize the database
        estatusDeProductoEntregadoRepository.saveAndFlush(estatusDeProductoEntregado);

        // Get all the estatusDeProductoEntregadoList
        restEstatusDeProductoEntregadoMockMvc.perform(get("/api/estatus-de-producto-entregados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusDeProductoEntregado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getEstatusDeProductoEntregado() throws Exception {
        // Initialize the database
        estatusDeProductoEntregadoRepository.saveAndFlush(estatusDeProductoEntregado);

        // Get the estatusDeProductoEntregado
        restEstatusDeProductoEntregadoMockMvc.perform(get("/api/estatus-de-producto-entregados/{id}", estatusDeProductoEntregado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(estatusDeProductoEntregado.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEstatusDeProductoEntregado() throws Exception {
        // Get the estatusDeProductoEntregado
        restEstatusDeProductoEntregadoMockMvc.perform(get("/api/estatus-de-producto-entregados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstatusDeProductoEntregado() throws Exception {
        // Initialize the database
        estatusDeProductoEntregadoRepository.saveAndFlush(estatusDeProductoEntregado);

        int databaseSizeBeforeUpdate = estatusDeProductoEntregadoRepository.findAll().size();

        // Update the estatusDeProductoEntregado
        EstatusDeProductoEntregado updatedEstatusDeProductoEntregado = estatusDeProductoEntregadoRepository.findById(estatusDeProductoEntregado.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusDeProductoEntregado are not directly saved in db
        em.detach(updatedEstatusDeProductoEntregado);
        updatedEstatusDeProductoEntregado
            .nombre(UPDATED_NOMBRE);
        EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO = estatusDeProductoEntregadoMapper.toDto(updatedEstatusDeProductoEntregado);

        restEstatusDeProductoEntregadoMockMvc.perform(put("/api/estatus-de-producto-entregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeProductoEntregadoDTO)))
            .andExpect(status().isOk());

        // Validate the EstatusDeProductoEntregado in the database
        List<EstatusDeProductoEntregado> estatusDeProductoEntregadoList = estatusDeProductoEntregadoRepository.findAll();
        assertThat(estatusDeProductoEntregadoList).hasSize(databaseSizeBeforeUpdate);
        EstatusDeProductoEntregado testEstatusDeProductoEntregado = estatusDeProductoEntregadoList.get(estatusDeProductoEntregadoList.size() - 1);
        assertThat(testEstatusDeProductoEntregado.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the EstatusDeProductoEntregado in Elasticsearch
        verify(mockEstatusDeProductoEntregadoSearchRepository, times(1)).save(testEstatusDeProductoEntregado);
    }

    @Test
    @Transactional
    public void updateNonExistingEstatusDeProductoEntregado() throws Exception {
        int databaseSizeBeforeUpdate = estatusDeProductoEntregadoRepository.findAll().size();

        // Create the EstatusDeProductoEntregado
        EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO = estatusDeProductoEntregadoMapper.toDto(estatusDeProductoEntregado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusDeProductoEntregadoMockMvc.perform(put("/api/estatus-de-producto-entregados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(estatusDeProductoEntregadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstatusDeProductoEntregado in the database
        List<EstatusDeProductoEntregado> estatusDeProductoEntregadoList = estatusDeProductoEntregadoRepository.findAll();
        assertThat(estatusDeProductoEntregadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the EstatusDeProductoEntregado in Elasticsearch
        verify(mockEstatusDeProductoEntregadoSearchRepository, times(0)).save(estatusDeProductoEntregado);
    }

    @Test
    @Transactional
    public void deleteEstatusDeProductoEntregado() throws Exception {
        // Initialize the database
        estatusDeProductoEntregadoRepository.saveAndFlush(estatusDeProductoEntregado);

        int databaseSizeBeforeDelete = estatusDeProductoEntregadoRepository.findAll().size();

        // Get the estatusDeProductoEntregado
        restEstatusDeProductoEntregadoMockMvc.perform(delete("/api/estatus-de-producto-entregados/{id}", estatusDeProductoEntregado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EstatusDeProductoEntregado> estatusDeProductoEntregadoList = estatusDeProductoEntregadoRepository.findAll();
        assertThat(estatusDeProductoEntregadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the EstatusDeProductoEntregado in Elasticsearch
        verify(mockEstatusDeProductoEntregadoSearchRepository, times(1)).deleteById(estatusDeProductoEntregado.getId());
    }

    @Test
    @Transactional
    public void searchEstatusDeProductoEntregado() throws Exception {
        // Initialize the database
        estatusDeProductoEntregadoRepository.saveAndFlush(estatusDeProductoEntregado);
        when(mockEstatusDeProductoEntregadoSearchRepository.search(queryStringQuery("id:" + estatusDeProductoEntregado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(estatusDeProductoEntregado), PageRequest.of(0, 1), 1));
        // Search the estatusDeProductoEntregado
        restEstatusDeProductoEntregadoMockMvc.perform(get("/api/_search/estatus-de-producto-entregados?query=id:" + estatusDeProductoEntregado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusDeProductoEntregado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusDeProductoEntregado.class);
        EstatusDeProductoEntregado estatusDeProductoEntregado1 = new EstatusDeProductoEntregado();
        estatusDeProductoEntregado1.setId(1L);
        EstatusDeProductoEntregado estatusDeProductoEntregado2 = new EstatusDeProductoEntregado();
        estatusDeProductoEntregado2.setId(estatusDeProductoEntregado1.getId());
        assertThat(estatusDeProductoEntregado1).isEqualTo(estatusDeProductoEntregado2);
        estatusDeProductoEntregado2.setId(2L);
        assertThat(estatusDeProductoEntregado1).isNotEqualTo(estatusDeProductoEntregado2);
        estatusDeProductoEntregado1.setId(null);
        assertThat(estatusDeProductoEntregado1).isNotEqualTo(estatusDeProductoEntregado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstatusDeProductoEntregadoDTO.class);
        EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO1 = new EstatusDeProductoEntregadoDTO();
        estatusDeProductoEntregadoDTO1.setId(1L);
        EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO2 = new EstatusDeProductoEntregadoDTO();
        assertThat(estatusDeProductoEntregadoDTO1).isNotEqualTo(estatusDeProductoEntregadoDTO2);
        estatusDeProductoEntregadoDTO2.setId(estatusDeProductoEntregadoDTO1.getId());
        assertThat(estatusDeProductoEntregadoDTO1).isEqualTo(estatusDeProductoEntregadoDTO2);
        estatusDeProductoEntregadoDTO2.setId(2L);
        assertThat(estatusDeProductoEntregadoDTO1).isNotEqualTo(estatusDeProductoEntregadoDTO2);
        estatusDeProductoEntregadoDTO1.setId(null);
        assertThat(estatusDeProductoEntregadoDTO1).isNotEqualTo(estatusDeProductoEntregadoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(estatusDeProductoEntregadoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(estatusDeProductoEntregadoMapper.fromId(null)).isNull();
    }
}
