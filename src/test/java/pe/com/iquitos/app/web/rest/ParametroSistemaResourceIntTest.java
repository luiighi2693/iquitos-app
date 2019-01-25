package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ParametroSistema;
import pe.com.iquitos.app.repository.ParametroSistemaRepository;
import pe.com.iquitos.app.repository.search.ParametroSistemaSearchRepository;
import pe.com.iquitos.app.service.ParametroSistemaService;
import pe.com.iquitos.app.service.dto.ParametroSistemaDTO;
import pe.com.iquitos.app.service.mapper.ParametroSistemaMapper;
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
 * Test class for the ParametroSistemaResource REST controller.
 *
 * @see ParametroSistemaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ParametroSistemaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ParametroSistemaRepository parametroSistemaRepository;

    @Autowired
    private ParametroSistemaMapper parametroSistemaMapper;

    @Autowired
    private ParametroSistemaService parametroSistemaService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ParametroSistemaSearchRepositoryMockConfiguration
     */
    @Autowired
    private ParametroSistemaSearchRepository mockParametroSistemaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParametroSistemaMockMvc;

    private ParametroSistema parametroSistema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParametroSistemaResource parametroSistemaResource = new ParametroSistemaResource(parametroSistemaService);
        this.restParametroSistemaMockMvc = MockMvcBuilders.standaloneSetup(parametroSistemaResource)
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
    public static ParametroSistema createEntity(EntityManager em) {
        ParametroSistema parametroSistema = new ParametroSistema()
            .nombre(DEFAULT_NOMBRE);
        return parametroSistema;
    }

    @Before
    public void initTest() {
        parametroSistema = createEntity(em);
    }

    @Test
    @Transactional
    public void createParametroSistema() throws Exception {
        int databaseSizeBeforeCreate = parametroSistemaRepository.findAll().size();

        // Create the ParametroSistema
        ParametroSistemaDTO parametroSistemaDTO = parametroSistemaMapper.toDto(parametroSistema);
        restParametroSistemaMockMvc.perform(post("/api/parametro-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroSistemaDTO)))
            .andExpect(status().isCreated());

        // Validate the ParametroSistema in the database
        List<ParametroSistema> parametroSistemaList = parametroSistemaRepository.findAll();
        assertThat(parametroSistemaList).hasSize(databaseSizeBeforeCreate + 1);
        ParametroSistema testParametroSistema = parametroSistemaList.get(parametroSistemaList.size() - 1);
        assertThat(testParametroSistema.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the ParametroSistema in Elasticsearch
        verify(mockParametroSistemaSearchRepository, times(1)).save(testParametroSistema);
    }

    @Test
    @Transactional
    public void createParametroSistemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parametroSistemaRepository.findAll().size();

        // Create the ParametroSistema with an existing ID
        parametroSistema.setId(1L);
        ParametroSistemaDTO parametroSistemaDTO = parametroSistemaMapper.toDto(parametroSistema);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParametroSistemaMockMvc.perform(post("/api/parametro-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroSistemaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParametroSistema in the database
        List<ParametroSistema> parametroSistemaList = parametroSistemaRepository.findAll();
        assertThat(parametroSistemaList).hasSize(databaseSizeBeforeCreate);

        // Validate the ParametroSistema in Elasticsearch
        verify(mockParametroSistemaSearchRepository, times(0)).save(parametroSistema);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = parametroSistemaRepository.findAll().size();
        // set the field null
        parametroSistema.setNombre(null);

        // Create the ParametroSistema, which fails.
        ParametroSistemaDTO parametroSistemaDTO = parametroSistemaMapper.toDto(parametroSistema);

        restParametroSistemaMockMvc.perform(post("/api/parametro-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroSistemaDTO)))
            .andExpect(status().isBadRequest());

        List<ParametroSistema> parametroSistemaList = parametroSistemaRepository.findAll();
        assertThat(parametroSistemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParametroSistemas() throws Exception {
        // Initialize the database
        parametroSistemaRepository.saveAndFlush(parametroSistema);

        // Get all the parametroSistemaList
        restParametroSistemaMockMvc.perform(get("/api/parametro-sistemas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametroSistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getParametroSistema() throws Exception {
        // Initialize the database
        parametroSistemaRepository.saveAndFlush(parametroSistema);

        // Get the parametroSistema
        restParametroSistemaMockMvc.perform(get("/api/parametro-sistemas/{id}", parametroSistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parametroSistema.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParametroSistema() throws Exception {
        // Get the parametroSistema
        restParametroSistemaMockMvc.perform(get("/api/parametro-sistemas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParametroSistema() throws Exception {
        // Initialize the database
        parametroSistemaRepository.saveAndFlush(parametroSistema);

        int databaseSizeBeforeUpdate = parametroSistemaRepository.findAll().size();

        // Update the parametroSistema
        ParametroSistema updatedParametroSistema = parametroSistemaRepository.findById(parametroSistema.getId()).get();
        // Disconnect from session so that the updates on updatedParametroSistema are not directly saved in db
        em.detach(updatedParametroSistema);
        updatedParametroSistema
            .nombre(UPDATED_NOMBRE);
        ParametroSistemaDTO parametroSistemaDTO = parametroSistemaMapper.toDto(updatedParametroSistema);

        restParametroSistemaMockMvc.perform(put("/api/parametro-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroSistemaDTO)))
            .andExpect(status().isOk());

        // Validate the ParametroSistema in the database
        List<ParametroSistema> parametroSistemaList = parametroSistemaRepository.findAll();
        assertThat(parametroSistemaList).hasSize(databaseSizeBeforeUpdate);
        ParametroSistema testParametroSistema = parametroSistemaList.get(parametroSistemaList.size() - 1);
        assertThat(testParametroSistema.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the ParametroSistema in Elasticsearch
        verify(mockParametroSistemaSearchRepository, times(1)).save(testParametroSistema);
    }

    @Test
    @Transactional
    public void updateNonExistingParametroSistema() throws Exception {
        int databaseSizeBeforeUpdate = parametroSistemaRepository.findAll().size();

        // Create the ParametroSistema
        ParametroSistemaDTO parametroSistemaDTO = parametroSistemaMapper.toDto(parametroSistema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParametroSistemaMockMvc.perform(put("/api/parametro-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parametroSistemaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ParametroSistema in the database
        List<ParametroSistema> parametroSistemaList = parametroSistemaRepository.findAll();
        assertThat(parametroSistemaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ParametroSistema in Elasticsearch
        verify(mockParametroSistemaSearchRepository, times(0)).save(parametroSistema);
    }

    @Test
    @Transactional
    public void deleteParametroSistema() throws Exception {
        // Initialize the database
        parametroSistemaRepository.saveAndFlush(parametroSistema);

        int databaseSizeBeforeDelete = parametroSistemaRepository.findAll().size();

        // Get the parametroSistema
        restParametroSistemaMockMvc.perform(delete("/api/parametro-sistemas/{id}", parametroSistema.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParametroSistema> parametroSistemaList = parametroSistemaRepository.findAll();
        assertThat(parametroSistemaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ParametroSistema in Elasticsearch
        verify(mockParametroSistemaSearchRepository, times(1)).deleteById(parametroSistema.getId());
    }

    @Test
    @Transactional
    public void searchParametroSistema() throws Exception {
        // Initialize the database
        parametroSistemaRepository.saveAndFlush(parametroSistema);
        when(mockParametroSistemaSearchRepository.search(queryStringQuery("id:" + parametroSistema.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(parametroSistema), PageRequest.of(0, 1), 1));
        // Search the parametroSistema
        restParametroSistemaMockMvc.perform(get("/api/_search/parametro-sistemas?query=id:" + parametroSistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parametroSistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametroSistema.class);
        ParametroSistema parametroSistema1 = new ParametroSistema();
        parametroSistema1.setId(1L);
        ParametroSistema parametroSistema2 = new ParametroSistema();
        parametroSistema2.setId(parametroSistema1.getId());
        assertThat(parametroSistema1).isEqualTo(parametroSistema2);
        parametroSistema2.setId(2L);
        assertThat(parametroSistema1).isNotEqualTo(parametroSistema2);
        parametroSistema1.setId(null);
        assertThat(parametroSistema1).isNotEqualTo(parametroSistema2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParametroSistemaDTO.class);
        ParametroSistemaDTO parametroSistemaDTO1 = new ParametroSistemaDTO();
        parametroSistemaDTO1.setId(1L);
        ParametroSistemaDTO parametroSistemaDTO2 = new ParametroSistemaDTO();
        assertThat(parametroSistemaDTO1).isNotEqualTo(parametroSistemaDTO2);
        parametroSistemaDTO2.setId(parametroSistemaDTO1.getId());
        assertThat(parametroSistemaDTO1).isEqualTo(parametroSistemaDTO2);
        parametroSistemaDTO2.setId(2L);
        assertThat(parametroSistemaDTO1).isNotEqualTo(parametroSistemaDTO2);
        parametroSistemaDTO1.setId(null);
        assertThat(parametroSistemaDTO1).isNotEqualTo(parametroSistemaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(parametroSistemaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(parametroSistemaMapper.fromId(null)).isNull();
    }
}
