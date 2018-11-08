package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Operacion;
import pe.com.iquitos.app.repository.OperacionRepository;
import pe.com.iquitos.app.repository.search.OperacionSearchRepository;
import pe.com.iquitos.app.service.OperacionService;
import pe.com.iquitos.app.service.dto.OperacionDTO;
import pe.com.iquitos.app.service.mapper.OperacionMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static pe.com.iquitos.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pe.com.iquitos.app.domain.enumeration.OperationType;
/**
 * Test class for the OperacionResource REST controller.
 *
 * @see OperacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class OperacionResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GLOSA = "AAAAAAAAAA";
    private static final String UPDATED_GLOSA = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTO = 1D;
    private static final Double UPDATED_MONTO = 2D;

    private static final OperationType DEFAULT_TIPO_DE_OPERACION = OperationType.INGRESO;
    private static final OperationType UPDATED_TIPO_DE_OPERACION = OperationType.EGRESO;

    @Autowired
    private OperacionRepository operacionRepository;

    @Autowired
    private OperacionMapper operacionMapper;

    @Autowired
    private OperacionService operacionService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.OperacionSearchRepositoryMockConfiguration
     */
    @Autowired
    private OperacionSearchRepository mockOperacionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperacionMockMvc;

    private Operacion operacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OperacionResource operacionResource = new OperacionResource(operacionService);
        this.restOperacionMockMvc = MockMvcBuilders.standaloneSetup(operacionResource)
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
    public static Operacion createEntity(EntityManager em) {
        Operacion operacion = new Operacion()
            .fecha(DEFAULT_FECHA)
            .glosa(DEFAULT_GLOSA)
            .monto(DEFAULT_MONTO)
            .tipoDeOperacion(DEFAULT_TIPO_DE_OPERACION);
        return operacion;
    }

    @Before
    public void initTest() {
        operacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperacion() throws Exception {
        int databaseSizeBeforeCreate = operacionRepository.findAll().size();

        // Create the Operacion
        OperacionDTO operacionDTO = operacionMapper.toDto(operacion);
        restOperacionMockMvc.perform(post("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeCreate + 1);
        Operacion testOperacion = operacionList.get(operacionList.size() - 1);
        assertThat(testOperacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testOperacion.getGlosa()).isEqualTo(DEFAULT_GLOSA);
        assertThat(testOperacion.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testOperacion.getTipoDeOperacion()).isEqualTo(DEFAULT_TIPO_DE_OPERACION);

        // Validate the Operacion in Elasticsearch
        verify(mockOperacionSearchRepository, times(1)).save(testOperacion);
    }

    @Test
    @Transactional
    public void createOperacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operacionRepository.findAll().size();

        // Create the Operacion with an existing ID
        operacion.setId(1L);
        OperacionDTO operacionDTO = operacionMapper.toDto(operacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperacionMockMvc.perform(post("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Operacion in Elasticsearch
        verify(mockOperacionSearchRepository, times(0)).save(operacion);
    }

    @Test
    @Transactional
    public void checkMontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = operacionRepository.findAll().size();
        // set the field null
        operacion.setMonto(null);

        // Create the Operacion, which fails.
        OperacionDTO operacionDTO = operacionMapper.toDto(operacion);

        restOperacionMockMvc.perform(post("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacionDTO)))
            .andExpect(status().isBadRequest());

        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperacions() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        // Get all the operacionList
        restOperacionMockMvc.perform(get("/api/operacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].tipoDeOperacion").value(hasItem(DEFAULT_TIPO_DE_OPERACION.toString())));
    }
    
    @Test
    @Transactional
    public void getOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        // Get the operacion
        restOperacionMockMvc.perform(get("/api/operacions/{id}", operacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.glosa").value(DEFAULT_GLOSA.toString()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()))
            .andExpect(jsonPath("$.tipoDeOperacion").value(DEFAULT_TIPO_DE_OPERACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOperacion() throws Exception {
        // Get the operacion
        restOperacionMockMvc.perform(get("/api/operacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        int databaseSizeBeforeUpdate = operacionRepository.findAll().size();

        // Update the operacion
        Operacion updatedOperacion = operacionRepository.findById(operacion.getId()).get();
        // Disconnect from session so that the updates on updatedOperacion are not directly saved in db
        em.detach(updatedOperacion);
        updatedOperacion
            .fecha(UPDATED_FECHA)
            .glosa(UPDATED_GLOSA)
            .monto(UPDATED_MONTO)
            .tipoDeOperacion(UPDATED_TIPO_DE_OPERACION);
        OperacionDTO operacionDTO = operacionMapper.toDto(updatedOperacion);

        restOperacionMockMvc.perform(put("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacionDTO)))
            .andExpect(status().isOk());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeUpdate);
        Operacion testOperacion = operacionList.get(operacionList.size() - 1);
        assertThat(testOperacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testOperacion.getGlosa()).isEqualTo(UPDATED_GLOSA);
        assertThat(testOperacion.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testOperacion.getTipoDeOperacion()).isEqualTo(UPDATED_TIPO_DE_OPERACION);

        // Validate the Operacion in Elasticsearch
        verify(mockOperacionSearchRepository, times(1)).save(testOperacion);
    }

    @Test
    @Transactional
    public void updateNonExistingOperacion() throws Exception {
        int databaseSizeBeforeUpdate = operacionRepository.findAll().size();

        // Create the Operacion
        OperacionDTO operacionDTO = operacionMapper.toDto(operacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperacionMockMvc.perform(put("/api/operacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Operacion in the database
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Operacion in Elasticsearch
        verify(mockOperacionSearchRepository, times(0)).save(operacion);
    }

    @Test
    @Transactional
    public void deleteOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);

        int databaseSizeBeforeDelete = operacionRepository.findAll().size();

        // Get the operacion
        restOperacionMockMvc.perform(delete("/api/operacions/{id}", operacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Operacion> operacionList = operacionRepository.findAll();
        assertThat(operacionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Operacion in Elasticsearch
        verify(mockOperacionSearchRepository, times(1)).deleteById(operacion.getId());
    }

    @Test
    @Transactional
    public void searchOperacion() throws Exception {
        // Initialize the database
        operacionRepository.saveAndFlush(operacion);
        when(mockOperacionSearchRepository.search(queryStringQuery("id:" + operacion.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(operacion), PageRequest.of(0, 1), 1));
        // Search the operacion
        restOperacionMockMvc.perform(get("/api/_search/operacions?query=id:" + operacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA)))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].tipoDeOperacion").value(hasItem(DEFAULT_TIPO_DE_OPERACION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operacion.class);
        Operacion operacion1 = new Operacion();
        operacion1.setId(1L);
        Operacion operacion2 = new Operacion();
        operacion2.setId(operacion1.getId());
        assertThat(operacion1).isEqualTo(operacion2);
        operacion2.setId(2L);
        assertThat(operacion1).isNotEqualTo(operacion2);
        operacion1.setId(null);
        assertThat(operacion1).isNotEqualTo(operacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OperacionDTO.class);
        OperacionDTO operacionDTO1 = new OperacionDTO();
        operacionDTO1.setId(1L);
        OperacionDTO operacionDTO2 = new OperacionDTO();
        assertThat(operacionDTO1).isNotEqualTo(operacionDTO2);
        operacionDTO2.setId(operacionDTO1.getId());
        assertThat(operacionDTO1).isEqualTo(operacionDTO2);
        operacionDTO2.setId(2L);
        assertThat(operacionDTO1).isNotEqualTo(operacionDTO2);
        operacionDTO1.setId(null);
        assertThat(operacionDTO1).isNotEqualTo(operacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(operacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(operacionMapper.fromId(null)).isNull();
    }
}
