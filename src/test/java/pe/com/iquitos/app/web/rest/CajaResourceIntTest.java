package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Caja;
import pe.com.iquitos.app.repository.CajaRepository;
import pe.com.iquitos.app.repository.search.CajaSearchRepository;
import pe.com.iquitos.app.service.CajaService;
import pe.com.iquitos.app.service.dto.CajaDTO;
import pe.com.iquitos.app.service.mapper.CajaMapper;
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

/**
 * Test class for the CajaResource REST controller.
 *
 * @see CajaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class CajaResourceIntTest {

    private static final Double DEFAULT_MONTO = 1D;
    private static final Double UPDATED_MONTO = 2D;

    private static final Double DEFAULT_MONTO_ACTUAL = 1D;
    private static final Double UPDATED_MONTO_ACTUAL = 2D;

    private static final LocalDate DEFAULT_FECHA_INICIAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIAL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FINAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FINAL = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private CajaMapper cajaMapper;
    
    @Autowired
    private CajaService cajaService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.CajaSearchRepositoryMockConfiguration
     */
    @Autowired
    private CajaSearchRepository mockCajaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCajaMockMvc;

    private Caja caja;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CajaResource cajaResource = new CajaResource(cajaService);
        this.restCajaMockMvc = MockMvcBuilders.standaloneSetup(cajaResource)
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
    public static Caja createEntity(EntityManager em) {
        Caja caja = new Caja()
            .monto(DEFAULT_MONTO)
            .montoActual(DEFAULT_MONTO_ACTUAL)
            .fechaInicial(DEFAULT_FECHA_INICIAL)
            .fechaFinal(DEFAULT_FECHA_FINAL);
        return caja;
    }

    @Before
    public void initTest() {
        caja = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaja() throws Exception {
        int databaseSizeBeforeCreate = cajaRepository.findAll().size();

        // Create the Caja
        CajaDTO cajaDTO = cajaMapper.toDto(caja);
        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isCreated());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeCreate + 1);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testCaja.getMontoActual()).isEqualTo(DEFAULT_MONTO_ACTUAL);
        assertThat(testCaja.getFechaInicial()).isEqualTo(DEFAULT_FECHA_INICIAL);
        assertThat(testCaja.getFechaFinal()).isEqualTo(DEFAULT_FECHA_FINAL);

        // Validate the Caja in Elasticsearch
        verify(mockCajaSearchRepository, times(1)).save(testCaja);
    }

    @Test
    @Transactional
    public void createCajaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cajaRepository.findAll().size();

        // Create the Caja with an existing ID
        caja.setId(1L);
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Caja in Elasticsearch
        verify(mockCajaSearchRepository, times(0)).save(caja);
    }

    @Test
    @Transactional
    public void checkMontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajaRepository.findAll().size();
        // set the field null
        caja.setMonto(null);

        // Create the Caja, which fails.
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoActualIsRequired() throws Exception {
        int databaseSizeBeforeTest = cajaRepository.findAll().size();
        // set the field null
        caja.setMontoActual(null);

        // Create the Caja, which fails.
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        restCajaMockMvc.perform(post("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCajas() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        // Get all the cajaList
        restCajaMockMvc.perform(get("/api/cajas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caja.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoActual").value(hasItem(DEFAULT_MONTO_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaInicial").value(hasItem(DEFAULT_FECHA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].fechaFinal").value(hasItem(DEFAULT_FECHA_FINAL.toString())));
    }
    
    @Test
    @Transactional
    public void getCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        // Get the caja
        restCajaMockMvc.perform(get("/api/cajas/{id}", caja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(caja.getId().intValue()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()))
            .andExpect(jsonPath("$.montoActual").value(DEFAULT_MONTO_ACTUAL.doubleValue()))
            .andExpect(jsonPath("$.fechaInicial").value(DEFAULT_FECHA_INICIAL.toString()))
            .andExpect(jsonPath("$.fechaFinal").value(DEFAULT_FECHA_FINAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCaja() throws Exception {
        // Get the caja
        restCajaMockMvc.perform(get("/api/cajas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();

        // Update the caja
        Caja updatedCaja = cajaRepository.findById(caja.getId()).get();
        // Disconnect from session so that the updates on updatedCaja are not directly saved in db
        em.detach(updatedCaja);
        updatedCaja
            .monto(UPDATED_MONTO)
            .montoActual(UPDATED_MONTO_ACTUAL)
            .fechaInicial(UPDATED_FECHA_INICIAL)
            .fechaFinal(UPDATED_FECHA_FINAL);
        CajaDTO cajaDTO = cajaMapper.toDto(updatedCaja);

        restCajaMockMvc.perform(put("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isOk());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);
        Caja testCaja = cajaList.get(cajaList.size() - 1);
        assertThat(testCaja.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testCaja.getMontoActual()).isEqualTo(UPDATED_MONTO_ACTUAL);
        assertThat(testCaja.getFechaInicial()).isEqualTo(UPDATED_FECHA_INICIAL);
        assertThat(testCaja.getFechaFinal()).isEqualTo(UPDATED_FECHA_FINAL);

        // Validate the Caja in Elasticsearch
        verify(mockCajaSearchRepository, times(1)).save(testCaja);
    }

    @Test
    @Transactional
    public void updateNonExistingCaja() throws Exception {
        int databaseSizeBeforeUpdate = cajaRepository.findAll().size();

        // Create the Caja
        CajaDTO cajaDTO = cajaMapper.toDto(caja);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCajaMockMvc.perform(put("/api/cajas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cajaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Caja in the database
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Caja in Elasticsearch
        verify(mockCajaSearchRepository, times(0)).save(caja);
    }

    @Test
    @Transactional
    public void deleteCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);

        int databaseSizeBeforeDelete = cajaRepository.findAll().size();

        // Get the caja
        restCajaMockMvc.perform(delete("/api/cajas/{id}", caja.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Caja> cajaList = cajaRepository.findAll();
        assertThat(cajaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Caja in Elasticsearch
        verify(mockCajaSearchRepository, times(1)).deleteById(caja.getId());
    }

    @Test
    @Transactional
    public void searchCaja() throws Exception {
        // Initialize the database
        cajaRepository.saveAndFlush(caja);
        when(mockCajaSearchRepository.search(queryStringQuery("id:" + caja.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(caja), PageRequest.of(0, 1), 1));
        // Search the caja
        restCajaMockMvc.perform(get("/api/_search/cajas?query=id:" + caja.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caja.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoActual").value(hasItem(DEFAULT_MONTO_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaInicial").value(hasItem(DEFAULT_FECHA_INICIAL.toString())))
            .andExpect(jsonPath("$.[*].fechaFinal").value(hasItem(DEFAULT_FECHA_FINAL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caja.class);
        Caja caja1 = new Caja();
        caja1.setId(1L);
        Caja caja2 = new Caja();
        caja2.setId(caja1.getId());
        assertThat(caja1).isEqualTo(caja2);
        caja2.setId(2L);
        assertThat(caja1).isNotEqualTo(caja2);
        caja1.setId(null);
        assertThat(caja1).isNotEqualTo(caja2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CajaDTO.class);
        CajaDTO cajaDTO1 = new CajaDTO();
        cajaDTO1.setId(1L);
        CajaDTO cajaDTO2 = new CajaDTO();
        assertThat(cajaDTO1).isNotEqualTo(cajaDTO2);
        cajaDTO2.setId(cajaDTO1.getId());
        assertThat(cajaDTO1).isEqualTo(cajaDTO2);
        cajaDTO2.setId(2L);
        assertThat(cajaDTO1).isNotEqualTo(cajaDTO2);
        cajaDTO1.setId(null);
        assertThat(cajaDTO1).isNotEqualTo(cajaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cajaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cajaMapper.fromId(null)).isNull();
    }
}
