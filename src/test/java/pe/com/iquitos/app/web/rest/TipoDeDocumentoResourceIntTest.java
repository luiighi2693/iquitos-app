package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.TipoDeDocumento;
import pe.com.iquitos.app.repository.TipoDeDocumentoRepository;
import pe.com.iquitos.app.repository.search.TipoDeDocumentoSearchRepository;
import pe.com.iquitos.app.service.TipoDeDocumentoService;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDTO;
import pe.com.iquitos.app.service.mapper.TipoDeDocumentoMapper;
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
 * Test class for the TipoDeDocumentoResource REST controller.
 *
 * @see TipoDeDocumentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class TipoDeDocumentoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Double DEFAULT_CANTIDAD_DIGITOS = 1D;
    private static final Double UPDATED_CANTIDAD_DIGITOS = 2D;

    @Autowired
    private TipoDeDocumentoRepository tipoDeDocumentoRepository;

    @Autowired
    private TipoDeDocumentoMapper tipoDeDocumentoMapper;

    @Autowired
    private TipoDeDocumentoService tipoDeDocumentoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.TipoDeDocumentoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TipoDeDocumentoSearchRepository mockTipoDeDocumentoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoDeDocumentoMockMvc;

    private TipoDeDocumento tipoDeDocumento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoDeDocumentoResource tipoDeDocumentoResource = new TipoDeDocumentoResource(tipoDeDocumentoService);
        this.restTipoDeDocumentoMockMvc = MockMvcBuilders.standaloneSetup(tipoDeDocumentoResource)
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
    public static TipoDeDocumento createEntity(EntityManager em) {
        TipoDeDocumento tipoDeDocumento = new TipoDeDocumento()
            .nombre(DEFAULT_NOMBRE)
            .cantidadDigitos(DEFAULT_CANTIDAD_DIGITOS);
        return tipoDeDocumento;
    }

    @Before
    public void initTest() {
        tipoDeDocumento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDeDocumento() throws Exception {
        int databaseSizeBeforeCreate = tipoDeDocumentoRepository.findAll().size();

        // Create the TipoDeDocumento
        TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoMapper.toDto(tipoDeDocumento);
        restTipoDeDocumentoMockMvc.perform(post("/api/tipo-de-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoDeDocumento in the database
        List<TipoDeDocumento> tipoDeDocumentoList = tipoDeDocumentoRepository.findAll();
        assertThat(tipoDeDocumentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDeDocumento testTipoDeDocumento = tipoDeDocumentoList.get(tipoDeDocumentoList.size() - 1);
        assertThat(testTipoDeDocumento.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoDeDocumento.getCantidadDigitos()).isEqualTo(DEFAULT_CANTIDAD_DIGITOS);

        // Validate the TipoDeDocumento in Elasticsearch
        verify(mockTipoDeDocumentoSearchRepository, times(1)).save(testTipoDeDocumento);
    }

    @Test
    @Transactional
    public void createTipoDeDocumentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDeDocumentoRepository.findAll().size();

        // Create the TipoDeDocumento with an existing ID
        tipoDeDocumento.setId(1L);
        TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoMapper.toDto(tipoDeDocumento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDeDocumentoMockMvc.perform(post("/api/tipo-de-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeDocumento in the database
        List<TipoDeDocumento> tipoDeDocumentoList = tipoDeDocumentoRepository.findAll();
        assertThat(tipoDeDocumentoList).hasSize(databaseSizeBeforeCreate);

        // Validate the TipoDeDocumento in Elasticsearch
        verify(mockTipoDeDocumentoSearchRepository, times(0)).save(tipoDeDocumento);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDeDocumentoRepository.findAll().size();
        // set the field null
        tipoDeDocumento.setNombre(null);

        // Create the TipoDeDocumento, which fails.
        TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoMapper.toDto(tipoDeDocumento);

        restTipoDeDocumentoMockMvc.perform(post("/api/tipo-de-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoDeDocumento> tipoDeDocumentoList = tipoDeDocumentoRepository.findAll();
        assertThat(tipoDeDocumentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDeDocumentos() throws Exception {
        // Initialize the database
        tipoDeDocumentoRepository.saveAndFlush(tipoDeDocumento);

        // Get all the tipoDeDocumentoList
        restTipoDeDocumentoMockMvc.perform(get("/api/tipo-de-documentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].cantidadDigitos").value(hasItem(DEFAULT_CANTIDAD_DIGITOS.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTipoDeDocumento() throws Exception {
        // Initialize the database
        tipoDeDocumentoRepository.saveAndFlush(tipoDeDocumento);

        // Get the tipoDeDocumento
        restTipoDeDocumentoMockMvc.perform(get("/api/tipo-de-documentos/{id}", tipoDeDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDeDocumento.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.cantidadDigitos").value(DEFAULT_CANTIDAD_DIGITOS.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDeDocumento() throws Exception {
        // Get the tipoDeDocumento
        restTipoDeDocumentoMockMvc.perform(get("/api/tipo-de-documentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDeDocumento() throws Exception {
        // Initialize the database
        tipoDeDocumentoRepository.saveAndFlush(tipoDeDocumento);

        int databaseSizeBeforeUpdate = tipoDeDocumentoRepository.findAll().size();

        // Update the tipoDeDocumento
        TipoDeDocumento updatedTipoDeDocumento = tipoDeDocumentoRepository.findById(tipoDeDocumento.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDeDocumento are not directly saved in db
        em.detach(updatedTipoDeDocumento);
        updatedTipoDeDocumento
            .nombre(UPDATED_NOMBRE)
            .cantidadDigitos(UPDATED_CANTIDAD_DIGITOS);
        TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoMapper.toDto(updatedTipoDeDocumento);

        restTipoDeDocumentoMockMvc.perform(put("/api/tipo-de-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoDeDocumento in the database
        List<TipoDeDocumento> tipoDeDocumentoList = tipoDeDocumentoRepository.findAll();
        assertThat(tipoDeDocumentoList).hasSize(databaseSizeBeforeUpdate);
        TipoDeDocumento testTipoDeDocumento = tipoDeDocumentoList.get(tipoDeDocumentoList.size() - 1);
        assertThat(testTipoDeDocumento.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoDeDocumento.getCantidadDigitos()).isEqualTo(UPDATED_CANTIDAD_DIGITOS);

        // Validate the TipoDeDocumento in Elasticsearch
        verify(mockTipoDeDocumentoSearchRepository, times(1)).save(testTipoDeDocumento);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDeDocumento() throws Exception {
        int databaseSizeBeforeUpdate = tipoDeDocumentoRepository.findAll().size();

        // Create the TipoDeDocumento
        TipoDeDocumentoDTO tipoDeDocumentoDTO = tipoDeDocumentoMapper.toDto(tipoDeDocumento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDeDocumentoMockMvc.perform(put("/api/tipo-de-documentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoDeDocumentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDeDocumento in the database
        List<TipoDeDocumento> tipoDeDocumentoList = tipoDeDocumentoRepository.findAll();
        assertThat(tipoDeDocumentoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TipoDeDocumento in Elasticsearch
        verify(mockTipoDeDocumentoSearchRepository, times(0)).save(tipoDeDocumento);
    }

    @Test
    @Transactional
    public void deleteTipoDeDocumento() throws Exception {
        // Initialize the database
        tipoDeDocumentoRepository.saveAndFlush(tipoDeDocumento);

        int databaseSizeBeforeDelete = tipoDeDocumentoRepository.findAll().size();

        // Get the tipoDeDocumento
        restTipoDeDocumentoMockMvc.perform(delete("/api/tipo-de-documentos/{id}", tipoDeDocumento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoDeDocumento> tipoDeDocumentoList = tipoDeDocumentoRepository.findAll();
        assertThat(tipoDeDocumentoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TipoDeDocumento in Elasticsearch
        verify(mockTipoDeDocumentoSearchRepository, times(1)).deleteById(tipoDeDocumento.getId());
    }

    @Test
    @Transactional
    public void searchTipoDeDocumento() throws Exception {
        // Initialize the database
        tipoDeDocumentoRepository.saveAndFlush(tipoDeDocumento);
        when(mockTipoDeDocumentoSearchRepository.search(queryStringQuery("id:" + tipoDeDocumento.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tipoDeDocumento), PageRequest.of(0, 1), 1));
        // Search the tipoDeDocumento
        restTipoDeDocumentoMockMvc.perform(get("/api/_search/tipo-de-documentos?query=id:" + tipoDeDocumento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDeDocumento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].cantidadDigitos").value(hasItem(DEFAULT_CANTIDAD_DIGITOS.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeDocumento.class);
        TipoDeDocumento tipoDeDocumento1 = new TipoDeDocumento();
        tipoDeDocumento1.setId(1L);
        TipoDeDocumento tipoDeDocumento2 = new TipoDeDocumento();
        tipoDeDocumento2.setId(tipoDeDocumento1.getId());
        assertThat(tipoDeDocumento1).isEqualTo(tipoDeDocumento2);
        tipoDeDocumento2.setId(2L);
        assertThat(tipoDeDocumento1).isNotEqualTo(tipoDeDocumento2);
        tipoDeDocumento1.setId(null);
        assertThat(tipoDeDocumento1).isNotEqualTo(tipoDeDocumento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDeDocumentoDTO.class);
        TipoDeDocumentoDTO tipoDeDocumentoDTO1 = new TipoDeDocumentoDTO();
        tipoDeDocumentoDTO1.setId(1L);
        TipoDeDocumentoDTO tipoDeDocumentoDTO2 = new TipoDeDocumentoDTO();
        assertThat(tipoDeDocumentoDTO1).isNotEqualTo(tipoDeDocumentoDTO2);
        tipoDeDocumentoDTO2.setId(tipoDeDocumentoDTO1.getId());
        assertThat(tipoDeDocumentoDTO1).isEqualTo(tipoDeDocumentoDTO2);
        tipoDeDocumentoDTO2.setId(2L);
        assertThat(tipoDeDocumentoDTO1).isNotEqualTo(tipoDeDocumentoDTO2);
        tipoDeDocumentoDTO1.setId(null);
        assertThat(tipoDeDocumentoDTO1).isNotEqualTo(tipoDeDocumentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoDeDocumentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoDeDocumentoMapper.fromId(null)).isNull();
    }
}
