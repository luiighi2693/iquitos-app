package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.UnidadDeMedida;
import pe.com.iquitos.app.repository.UnidadDeMedidaRepository;
import pe.com.iquitos.app.repository.search.UnidadDeMedidaSearchRepository;
import pe.com.iquitos.app.service.UnidadDeMedidaService;
import pe.com.iquitos.app.service.dto.UnidadDeMedidaDTO;
import pe.com.iquitos.app.service.mapper.UnidadDeMedidaMapper;
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
 * Test class for the UnidadDeMedidaResource REST controller.
 *
 * @see UnidadDeMedidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class UnidadDeMedidaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private UnidadDeMedidaRepository unidadDeMedidaRepository;

    @Autowired
    private UnidadDeMedidaMapper unidadDeMedidaMapper;
    
    @Autowired
    private UnidadDeMedidaService unidadDeMedidaService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.UnidadDeMedidaSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadDeMedidaSearchRepository mockUnidadDeMedidaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnidadDeMedidaMockMvc;

    private UnidadDeMedida unidadDeMedida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadDeMedidaResource unidadDeMedidaResource = new UnidadDeMedidaResource(unidadDeMedidaService);
        this.restUnidadDeMedidaMockMvc = MockMvcBuilders.standaloneSetup(unidadDeMedidaResource)
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
    public static UnidadDeMedida createEntity(EntityManager em) {
        UnidadDeMedida unidadDeMedida = new UnidadDeMedida()
            .nombre(DEFAULT_NOMBRE);
        return unidadDeMedida;
    }

    @Before
    public void initTest() {
        unidadDeMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadDeMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadDeMedidaRepository.findAll().size();

        // Create the UnidadDeMedida
        UnidadDeMedidaDTO unidadDeMedidaDTO = unidadDeMedidaMapper.toDto(unidadDeMedida);
        restUnidadDeMedidaMockMvc.perform(post("/api/unidad-de-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadDeMedidaDTO)))
            .andExpect(status().isCreated());

        // Validate the UnidadDeMedida in the database
        List<UnidadDeMedida> unidadDeMedidaList = unidadDeMedidaRepository.findAll();
        assertThat(unidadDeMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadDeMedida testUnidadDeMedida = unidadDeMedidaList.get(unidadDeMedidaList.size() - 1);
        assertThat(testUnidadDeMedida.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the UnidadDeMedida in Elasticsearch
        verify(mockUnidadDeMedidaSearchRepository, times(1)).save(testUnidadDeMedida);
    }

    @Test
    @Transactional
    public void createUnidadDeMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadDeMedidaRepository.findAll().size();

        // Create the UnidadDeMedida with an existing ID
        unidadDeMedida.setId(1L);
        UnidadDeMedidaDTO unidadDeMedidaDTO = unidadDeMedidaMapper.toDto(unidadDeMedida);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadDeMedidaMockMvc.perform(post("/api/unidad-de-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadDeMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadDeMedida in the database
        List<UnidadDeMedida> unidadDeMedidaList = unidadDeMedidaRepository.findAll();
        assertThat(unidadDeMedidaList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnidadDeMedida in Elasticsearch
        verify(mockUnidadDeMedidaSearchRepository, times(0)).save(unidadDeMedida);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadDeMedidaRepository.findAll().size();
        // set the field null
        unidadDeMedida.setNombre(null);

        // Create the UnidadDeMedida, which fails.
        UnidadDeMedidaDTO unidadDeMedidaDTO = unidadDeMedidaMapper.toDto(unidadDeMedida);

        restUnidadDeMedidaMockMvc.perform(post("/api/unidad-de-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadDeMedidaDTO)))
            .andExpect(status().isBadRequest());

        List<UnidadDeMedida> unidadDeMedidaList = unidadDeMedidaRepository.findAll();
        assertThat(unidadDeMedidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadDeMedidas() throws Exception {
        // Initialize the database
        unidadDeMedidaRepository.saveAndFlush(unidadDeMedida);

        // Get all the unidadDeMedidaList
        restUnidadDeMedidaMockMvc.perform(get("/api/unidad-de-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadDeMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getUnidadDeMedida() throws Exception {
        // Initialize the database
        unidadDeMedidaRepository.saveAndFlush(unidadDeMedida);

        // Get the unidadDeMedida
        restUnidadDeMedidaMockMvc.perform(get("/api/unidad-de-medidas/{id}", unidadDeMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadDeMedida.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadDeMedida() throws Exception {
        // Get the unidadDeMedida
        restUnidadDeMedidaMockMvc.perform(get("/api/unidad-de-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadDeMedida() throws Exception {
        // Initialize the database
        unidadDeMedidaRepository.saveAndFlush(unidadDeMedida);

        int databaseSizeBeforeUpdate = unidadDeMedidaRepository.findAll().size();

        // Update the unidadDeMedida
        UnidadDeMedida updatedUnidadDeMedida = unidadDeMedidaRepository.findById(unidadDeMedida.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadDeMedida are not directly saved in db
        em.detach(updatedUnidadDeMedida);
        updatedUnidadDeMedida
            .nombre(UPDATED_NOMBRE);
        UnidadDeMedidaDTO unidadDeMedidaDTO = unidadDeMedidaMapper.toDto(updatedUnidadDeMedida);

        restUnidadDeMedidaMockMvc.perform(put("/api/unidad-de-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadDeMedidaDTO)))
            .andExpect(status().isOk());

        // Validate the UnidadDeMedida in the database
        List<UnidadDeMedida> unidadDeMedidaList = unidadDeMedidaRepository.findAll();
        assertThat(unidadDeMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadDeMedida testUnidadDeMedida = unidadDeMedidaList.get(unidadDeMedidaList.size() - 1);
        assertThat(testUnidadDeMedida.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the UnidadDeMedida in Elasticsearch
        verify(mockUnidadDeMedidaSearchRepository, times(1)).save(testUnidadDeMedida);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadDeMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadDeMedidaRepository.findAll().size();

        // Create the UnidadDeMedida
        UnidadDeMedidaDTO unidadDeMedidaDTO = unidadDeMedidaMapper.toDto(unidadDeMedida);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadDeMedidaMockMvc.perform(put("/api/unidad-de-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadDeMedidaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadDeMedida in the database
        List<UnidadDeMedida> unidadDeMedidaList = unidadDeMedidaRepository.findAll();
        assertThat(unidadDeMedidaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnidadDeMedida in Elasticsearch
        verify(mockUnidadDeMedidaSearchRepository, times(0)).save(unidadDeMedida);
    }

    @Test
    @Transactional
    public void deleteUnidadDeMedida() throws Exception {
        // Initialize the database
        unidadDeMedidaRepository.saveAndFlush(unidadDeMedida);

        int databaseSizeBeforeDelete = unidadDeMedidaRepository.findAll().size();

        // Get the unidadDeMedida
        restUnidadDeMedidaMockMvc.perform(delete("/api/unidad-de-medidas/{id}", unidadDeMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UnidadDeMedida> unidadDeMedidaList = unidadDeMedidaRepository.findAll();
        assertThat(unidadDeMedidaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnidadDeMedida in Elasticsearch
        verify(mockUnidadDeMedidaSearchRepository, times(1)).deleteById(unidadDeMedida.getId());
    }

    @Test
    @Transactional
    public void searchUnidadDeMedida() throws Exception {
        // Initialize the database
        unidadDeMedidaRepository.saveAndFlush(unidadDeMedida);
        when(mockUnidadDeMedidaSearchRepository.search(queryStringQuery("id:" + unidadDeMedida.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unidadDeMedida), PageRequest.of(0, 1), 1));
        // Search the unidadDeMedida
        restUnidadDeMedidaMockMvc.perform(get("/api/_search/unidad-de-medidas?query=id:" + unidadDeMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadDeMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadDeMedida.class);
        UnidadDeMedida unidadDeMedida1 = new UnidadDeMedida();
        unidadDeMedida1.setId(1L);
        UnidadDeMedida unidadDeMedida2 = new UnidadDeMedida();
        unidadDeMedida2.setId(unidadDeMedida1.getId());
        assertThat(unidadDeMedida1).isEqualTo(unidadDeMedida2);
        unidadDeMedida2.setId(2L);
        assertThat(unidadDeMedida1).isNotEqualTo(unidadDeMedida2);
        unidadDeMedida1.setId(null);
        assertThat(unidadDeMedida1).isNotEqualTo(unidadDeMedida2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadDeMedidaDTO.class);
        UnidadDeMedidaDTO unidadDeMedidaDTO1 = new UnidadDeMedidaDTO();
        unidadDeMedidaDTO1.setId(1L);
        UnidadDeMedidaDTO unidadDeMedidaDTO2 = new UnidadDeMedidaDTO();
        assertThat(unidadDeMedidaDTO1).isNotEqualTo(unidadDeMedidaDTO2);
        unidadDeMedidaDTO2.setId(unidadDeMedidaDTO1.getId());
        assertThat(unidadDeMedidaDTO1).isEqualTo(unidadDeMedidaDTO2);
        unidadDeMedidaDTO2.setId(2L);
        assertThat(unidadDeMedidaDTO1).isNotEqualTo(unidadDeMedidaDTO2);
        unidadDeMedidaDTO1.setId(null);
        assertThat(unidadDeMedidaDTO1).isNotEqualTo(unidadDeMedidaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unidadDeMedidaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unidadDeMedidaMapper.fromId(null)).isNull();
    }
}
