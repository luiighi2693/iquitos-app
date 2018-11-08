package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Amortizacion;
import pe.com.iquitos.app.repository.AmortizacionRepository;
import pe.com.iquitos.app.repository.search.AmortizacionSearchRepository;
import pe.com.iquitos.app.service.AmortizacionService;
import pe.com.iquitos.app.service.dto.AmortizacionDTO;
import pe.com.iquitos.app.service.mapper.AmortizacionMapper;
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
 * Test class for the AmortizacionResource REST controller.
 *
 * @see AmortizacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class AmortizacionResourceIntTest {

    private static final Double DEFAULT_MONTO = 1D;
    private static final Double UPDATED_MONTO = 2D;

    private static final Double DEFAULT_MONTO_PAGADO = 1D;
    private static final Double UPDATED_MONTO_PAGADO = 2D;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODIGO_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_GLOSA = "AAAAAAAAAA";
    private static final String UPDATED_GLOSA = "BBBBBBBBBB";

    @Autowired
    private AmortizacionRepository amortizacionRepository;

    @Autowired
    private AmortizacionMapper amortizacionMapper;
    
    @Autowired
    private AmortizacionService amortizacionService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.AmortizacionSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmortizacionSearchRepository mockAmortizacionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmortizacionMockMvc;

    private Amortizacion amortizacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizacionResource amortizacionResource = new AmortizacionResource(amortizacionService);
        this.restAmortizacionMockMvc = MockMvcBuilders.standaloneSetup(amortizacionResource)
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
    public static Amortizacion createEntity(EntityManager em) {
        Amortizacion amortizacion = new Amortizacion()
            .monto(DEFAULT_MONTO)
            .montoPagado(DEFAULT_MONTO_PAGADO)
            .fecha(DEFAULT_FECHA)
            .codigoDocumento(DEFAULT_CODIGO_DOCUMENTO)
            .glosa(DEFAULT_GLOSA);
        return amortizacion;
    }

    @Before
    public void initTest() {
        amortizacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizacion() throws Exception {
        int databaseSizeBeforeCreate = amortizacionRepository.findAll().size();

        // Create the Amortizacion
        AmortizacionDTO amortizacionDTO = amortizacionMapper.toDto(amortizacion);
        restAmortizacionMockMvc.perform(post("/api/amortizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Amortizacion in the database
        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeCreate + 1);
        Amortizacion testAmortizacion = amortizacionList.get(amortizacionList.size() - 1);
        assertThat(testAmortizacion.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testAmortizacion.getMontoPagado()).isEqualTo(DEFAULT_MONTO_PAGADO);
        assertThat(testAmortizacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testAmortizacion.getCodigoDocumento()).isEqualTo(DEFAULT_CODIGO_DOCUMENTO);
        assertThat(testAmortizacion.getGlosa()).isEqualTo(DEFAULT_GLOSA);

        // Validate the Amortizacion in Elasticsearch
        verify(mockAmortizacionSearchRepository, times(1)).save(testAmortizacion);
    }

    @Test
    @Transactional
    public void createAmortizacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizacionRepository.findAll().size();

        // Create the Amortizacion with an existing ID
        amortizacion.setId(1L);
        AmortizacionDTO amortizacionDTO = amortizacionMapper.toDto(amortizacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizacionMockMvc.perform(post("/api/amortizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amortizacion in the database
        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Amortizacion in Elasticsearch
        verify(mockAmortizacionSearchRepository, times(0)).save(amortizacion);
    }

    @Test
    @Transactional
    public void checkMontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizacionRepository.findAll().size();
        // set the field null
        amortizacion.setMonto(null);

        // Create the Amortizacion, which fails.
        AmortizacionDTO amortizacionDTO = amortizacionMapper.toDto(amortizacion);

        restAmortizacionMockMvc.perform(post("/api/amortizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizacionDTO)))
            .andExpect(status().isBadRequest());

        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoPagadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizacionRepository.findAll().size();
        // set the field null
        amortizacion.setMontoPagado(null);

        // Create the Amortizacion, which fails.
        AmortizacionDTO amortizacionDTO = amortizacionMapper.toDto(amortizacion);

        restAmortizacionMockMvc.perform(post("/api/amortizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizacionDTO)))
            .andExpect(status().isBadRequest());

        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizacionRepository.findAll().size();
        // set the field null
        amortizacion.setCodigoDocumento(null);

        // Create the Amortizacion, which fails.
        AmortizacionDTO amortizacionDTO = amortizacionMapper.toDto(amortizacion);

        restAmortizacionMockMvc.perform(post("/api/amortizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizacionDTO)))
            .andExpect(status().isBadRequest());

        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmortizacions() throws Exception {
        // Initialize the database
        amortizacionRepository.saveAndFlush(amortizacion);

        // Get all the amortizacionList
        restAmortizacionMockMvc.perform(get("/api/amortizacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoPagado").value(hasItem(DEFAULT_MONTO_PAGADO.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].codigoDocumento").value(hasItem(DEFAULT_CODIGO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())));
    }
    
    @Test
    @Transactional
    public void getAmortizacion() throws Exception {
        // Initialize the database
        amortizacionRepository.saveAndFlush(amortizacion);

        // Get the amortizacion
        restAmortizacionMockMvc.perform(get("/api/amortizacions/{id}", amortizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizacion.getId().intValue()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()))
            .andExpect(jsonPath("$.montoPagado").value(DEFAULT_MONTO_PAGADO.doubleValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.codigoDocumento").value(DEFAULT_CODIGO_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.glosa").value(DEFAULT_GLOSA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAmortizacion() throws Exception {
        // Get the amortizacion
        restAmortizacionMockMvc.perform(get("/api/amortizacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizacion() throws Exception {
        // Initialize the database
        amortizacionRepository.saveAndFlush(amortizacion);

        int databaseSizeBeforeUpdate = amortizacionRepository.findAll().size();

        // Update the amortizacion
        Amortizacion updatedAmortizacion = amortizacionRepository.findById(amortizacion.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizacion are not directly saved in db
        em.detach(updatedAmortizacion);
        updatedAmortizacion
            .monto(UPDATED_MONTO)
            .montoPagado(UPDATED_MONTO_PAGADO)
            .fecha(UPDATED_FECHA)
            .codigoDocumento(UPDATED_CODIGO_DOCUMENTO)
            .glosa(UPDATED_GLOSA);
        AmortizacionDTO amortizacionDTO = amortizacionMapper.toDto(updatedAmortizacion);

        restAmortizacionMockMvc.perform(put("/api/amortizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizacionDTO)))
            .andExpect(status().isOk());

        // Validate the Amortizacion in the database
        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeUpdate);
        Amortizacion testAmortizacion = amortizacionList.get(amortizacionList.size() - 1);
        assertThat(testAmortizacion.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testAmortizacion.getMontoPagado()).isEqualTo(UPDATED_MONTO_PAGADO);
        assertThat(testAmortizacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testAmortizacion.getCodigoDocumento()).isEqualTo(UPDATED_CODIGO_DOCUMENTO);
        assertThat(testAmortizacion.getGlosa()).isEqualTo(UPDATED_GLOSA);

        // Validate the Amortizacion in Elasticsearch
        verify(mockAmortizacionSearchRepository, times(1)).save(testAmortizacion);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizacion() throws Exception {
        int databaseSizeBeforeUpdate = amortizacionRepository.findAll().size();

        // Create the Amortizacion
        AmortizacionDTO amortizacionDTO = amortizacionMapper.toDto(amortizacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizacionMockMvc.perform(put("/api/amortizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amortizacion in the database
        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Amortizacion in Elasticsearch
        verify(mockAmortizacionSearchRepository, times(0)).save(amortizacion);
    }

    @Test
    @Transactional
    public void deleteAmortizacion() throws Exception {
        // Initialize the database
        amortizacionRepository.saveAndFlush(amortizacion);

        int databaseSizeBeforeDelete = amortizacionRepository.findAll().size();

        // Get the amortizacion
        restAmortizacionMockMvc.perform(delete("/api/amortizacions/{id}", amortizacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Amortizacion> amortizacionList = amortizacionRepository.findAll();
        assertThat(amortizacionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Amortizacion in Elasticsearch
        verify(mockAmortizacionSearchRepository, times(1)).deleteById(amortizacion.getId());
    }

    @Test
    @Transactional
    public void searchAmortizacion() throws Exception {
        // Initialize the database
        amortizacionRepository.saveAndFlush(amortizacion);
        when(mockAmortizacionSearchRepository.search(queryStringQuery("id:" + amortizacion.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amortizacion), PageRequest.of(0, 1), 1));
        // Search the amortizacion
        restAmortizacionMockMvc.perform(get("/api/_search/amortizacions?query=id:" + amortizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoPagado").value(hasItem(DEFAULT_MONTO_PAGADO.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].codigoDocumento").value(hasItem(DEFAULT_CODIGO_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amortizacion.class);
        Amortizacion amortizacion1 = new Amortizacion();
        amortizacion1.setId(1L);
        Amortizacion amortizacion2 = new Amortizacion();
        amortizacion2.setId(amortizacion1.getId());
        assertThat(amortizacion1).isEqualTo(amortizacion2);
        amortizacion2.setId(2L);
        assertThat(amortizacion1).isNotEqualTo(amortizacion2);
        amortizacion1.setId(null);
        assertThat(amortizacion1).isNotEqualTo(amortizacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizacionDTO.class);
        AmortizacionDTO amortizacionDTO1 = new AmortizacionDTO();
        amortizacionDTO1.setId(1L);
        AmortizacionDTO amortizacionDTO2 = new AmortizacionDTO();
        assertThat(amortizacionDTO1).isNotEqualTo(amortizacionDTO2);
        amortizacionDTO2.setId(amortizacionDTO1.getId());
        assertThat(amortizacionDTO1).isEqualTo(amortizacionDTO2);
        amortizacionDTO2.setId(2L);
        assertThat(amortizacionDTO1).isNotEqualTo(amortizacionDTO2);
        amortizacionDTO1.setId(null);
        assertThat(amortizacionDTO1).isNotEqualTo(amortizacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizacionMapper.fromId(null)).isNull();
    }
}
