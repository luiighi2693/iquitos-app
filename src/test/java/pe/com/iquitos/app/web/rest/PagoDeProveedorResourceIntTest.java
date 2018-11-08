package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.PagoDeProveedor;
import pe.com.iquitos.app.repository.PagoDeProveedorRepository;
import pe.com.iquitos.app.repository.search.PagoDeProveedorSearchRepository;
import pe.com.iquitos.app.service.PagoDeProveedorService;
import pe.com.iquitos.app.service.dto.PagoDeProveedorDTO;
import pe.com.iquitos.app.service.mapper.PagoDeProveedorMapper;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the PagoDeProveedorResource REST controller.
 *
 * @see PagoDeProveedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class PagoDeProveedorResourceIntTest {

    private static final Double DEFAULT_MONTO = 1D;
    private static final Double UPDATED_MONTO = 2D;

    private static final Double DEFAULT_MONTO_PAGADO = 1D;
    private static final Double UPDATED_MONTO_PAGADO = 2D;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODIGO_DE_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DE_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_GLOSA = "AAAAAAAAAA";
    private static final String UPDATED_GLOSA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    @Autowired
    private PagoDeProveedorRepository pagoDeProveedorRepository;

    @Autowired
    private PagoDeProveedorMapper pagoDeProveedorMapper;

    @Autowired
    private PagoDeProveedorService pagoDeProveedorService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.PagoDeProveedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private PagoDeProveedorSearchRepository mockPagoDeProveedorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPagoDeProveedorMockMvc;

    private PagoDeProveedor pagoDeProveedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PagoDeProveedorResource pagoDeProveedorResource = new PagoDeProveedorResource(pagoDeProveedorService);
        this.restPagoDeProveedorMockMvc = MockMvcBuilders.standaloneSetup(pagoDeProveedorResource)
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
    public static PagoDeProveedor createEntity(EntityManager em) {
        PagoDeProveedor pagoDeProveedor = new PagoDeProveedor()
            .monto(DEFAULT_MONTO)
            .montoPagado(DEFAULT_MONTO_PAGADO)
            .fecha(DEFAULT_FECHA)
            .codigoDeDocumento(DEFAULT_CODIGO_DE_DOCUMENTO)
            .glosa(DEFAULT_GLOSA)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE);
        return pagoDeProveedor;
    }

    @Before
    public void initTest() {
        pagoDeProveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagoDeProveedor() throws Exception {
        int databaseSizeBeforeCreate = pagoDeProveedorRepository.findAll().size();

        // Create the PagoDeProveedor
        PagoDeProveedorDTO pagoDeProveedorDTO = pagoDeProveedorMapper.toDto(pagoDeProveedor);
        restPagoDeProveedorMockMvc.perform(post("/api/pago-de-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDeProveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the PagoDeProveedor in the database
        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeCreate + 1);
        PagoDeProveedor testPagoDeProveedor = pagoDeProveedorList.get(pagoDeProveedorList.size() - 1);
        assertThat(testPagoDeProveedor.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testPagoDeProveedor.getMontoPagado()).isEqualTo(DEFAULT_MONTO_PAGADO);
        assertThat(testPagoDeProveedor.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPagoDeProveedor.getCodigoDeDocumento()).isEqualTo(DEFAULT_CODIGO_DE_DOCUMENTO);
        assertThat(testPagoDeProveedor.getGlosa()).isEqualTo(DEFAULT_GLOSA);
        assertThat(testPagoDeProveedor.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testPagoDeProveedor.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);

        // Validate the PagoDeProveedor in Elasticsearch
        verify(mockPagoDeProveedorSearchRepository, times(1)).save(testPagoDeProveedor);
    }

    @Test
    @Transactional
    public void createPagoDeProveedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pagoDeProveedorRepository.findAll().size();

        // Create the PagoDeProveedor with an existing ID
        pagoDeProveedor.setId(1L);
        PagoDeProveedorDTO pagoDeProveedorDTO = pagoDeProveedorMapper.toDto(pagoDeProveedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPagoDeProveedorMockMvc.perform(post("/api/pago-de-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDeProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PagoDeProveedor in the database
        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the PagoDeProveedor in Elasticsearch
        verify(mockPagoDeProveedorSearchRepository, times(0)).save(pagoDeProveedor);
    }

    @Test
    @Transactional
    public void checkMontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoDeProveedorRepository.findAll().size();
        // set the field null
        pagoDeProveedor.setMonto(null);

        // Create the PagoDeProveedor, which fails.
        PagoDeProveedorDTO pagoDeProveedorDTO = pagoDeProveedorMapper.toDto(pagoDeProveedor);

        restPagoDeProveedorMockMvc.perform(post("/api/pago-de-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDeProveedorDTO)))
            .andExpect(status().isBadRequest());

        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoPagadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoDeProveedorRepository.findAll().size();
        // set the field null
        pagoDeProveedor.setMontoPagado(null);

        // Create the PagoDeProveedor, which fails.
        PagoDeProveedorDTO pagoDeProveedorDTO = pagoDeProveedorMapper.toDto(pagoDeProveedor);

        restPagoDeProveedorMockMvc.perform(post("/api/pago-de-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDeProveedorDTO)))
            .andExpect(status().isBadRequest());

        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodigoDeDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pagoDeProveedorRepository.findAll().size();
        // set the field null
        pagoDeProveedor.setCodigoDeDocumento(null);

        // Create the PagoDeProveedor, which fails.
        PagoDeProveedorDTO pagoDeProveedorDTO = pagoDeProveedorMapper.toDto(pagoDeProveedor);

        restPagoDeProveedorMockMvc.perform(post("/api/pago-de-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDeProveedorDTO)))
            .andExpect(status().isBadRequest());

        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPagoDeProveedors() throws Exception {
        // Initialize the database
        pagoDeProveedorRepository.saveAndFlush(pagoDeProveedor);

        // Get all the pagoDeProveedorList
        restPagoDeProveedorMockMvc.perform(get("/api/pago-de-proveedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagoDeProveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoPagado").value(hasItem(DEFAULT_MONTO_PAGADO.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].codigoDeDocumento").value(hasItem(DEFAULT_CODIGO_DE_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }
    
    @Test
    @Transactional
    public void getPagoDeProveedor() throws Exception {
        // Initialize the database
        pagoDeProveedorRepository.saveAndFlush(pagoDeProveedor);

        // Get the pagoDeProveedor
        restPagoDeProveedorMockMvc.perform(get("/api/pago-de-proveedors/{id}", pagoDeProveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pagoDeProveedor.getId().intValue()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()))
            .andExpect(jsonPath("$.montoPagado").value(DEFAULT_MONTO_PAGADO.doubleValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.codigoDeDocumento").value(DEFAULT_CODIGO_DE_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.glosa").value(DEFAULT_GLOSA.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)));
    }

    @Test
    @Transactional
    public void getNonExistingPagoDeProveedor() throws Exception {
        // Get the pagoDeProveedor
        restPagoDeProveedorMockMvc.perform(get("/api/pago-de-proveedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagoDeProveedor() throws Exception {
        // Initialize the database
        pagoDeProveedorRepository.saveAndFlush(pagoDeProveedor);

        int databaseSizeBeforeUpdate = pagoDeProveedorRepository.findAll().size();

        // Update the pagoDeProveedor
        PagoDeProveedor updatedPagoDeProveedor = pagoDeProveedorRepository.findById(pagoDeProveedor.getId()).get();
        // Disconnect from session so that the updates on updatedPagoDeProveedor are not directly saved in db
        em.detach(updatedPagoDeProveedor);
        updatedPagoDeProveedor
            .monto(UPDATED_MONTO)
            .montoPagado(UPDATED_MONTO_PAGADO)
            .fecha(UPDATED_FECHA)
            .codigoDeDocumento(UPDATED_CODIGO_DE_DOCUMENTO)
            .glosa(UPDATED_GLOSA)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE);
        PagoDeProveedorDTO pagoDeProveedorDTO = pagoDeProveedorMapper.toDto(updatedPagoDeProveedor);

        restPagoDeProveedorMockMvc.perform(put("/api/pago-de-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDeProveedorDTO)))
            .andExpect(status().isOk());

        // Validate the PagoDeProveedor in the database
        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeUpdate);
        PagoDeProveedor testPagoDeProveedor = pagoDeProveedorList.get(pagoDeProveedorList.size() - 1);
        assertThat(testPagoDeProveedor.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testPagoDeProveedor.getMontoPagado()).isEqualTo(UPDATED_MONTO_PAGADO);
        assertThat(testPagoDeProveedor.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPagoDeProveedor.getCodigoDeDocumento()).isEqualTo(UPDATED_CODIGO_DE_DOCUMENTO);
        assertThat(testPagoDeProveedor.getGlosa()).isEqualTo(UPDATED_GLOSA);
        assertThat(testPagoDeProveedor.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testPagoDeProveedor.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);

        // Validate the PagoDeProveedor in Elasticsearch
        verify(mockPagoDeProveedorSearchRepository, times(1)).save(testPagoDeProveedor);
    }

    @Test
    @Transactional
    public void updateNonExistingPagoDeProveedor() throws Exception {
        int databaseSizeBeforeUpdate = pagoDeProveedorRepository.findAll().size();

        // Create the PagoDeProveedor
        PagoDeProveedorDTO pagoDeProveedorDTO = pagoDeProveedorMapper.toDto(pagoDeProveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPagoDeProveedorMockMvc.perform(put("/api/pago-de-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pagoDeProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PagoDeProveedor in the database
        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PagoDeProveedor in Elasticsearch
        verify(mockPagoDeProveedorSearchRepository, times(0)).save(pagoDeProveedor);
    }

    @Test
    @Transactional
    public void deletePagoDeProveedor() throws Exception {
        // Initialize the database
        pagoDeProveedorRepository.saveAndFlush(pagoDeProveedor);

        int databaseSizeBeforeDelete = pagoDeProveedorRepository.findAll().size();

        // Get the pagoDeProveedor
        restPagoDeProveedorMockMvc.perform(delete("/api/pago-de-proveedors/{id}", pagoDeProveedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PagoDeProveedor> pagoDeProveedorList = pagoDeProveedorRepository.findAll();
        assertThat(pagoDeProveedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PagoDeProveedor in Elasticsearch
        verify(mockPagoDeProveedorSearchRepository, times(1)).deleteById(pagoDeProveedor.getId());
    }

    @Test
    @Transactional
    public void searchPagoDeProveedor() throws Exception {
        // Initialize the database
        pagoDeProveedorRepository.saveAndFlush(pagoDeProveedor);
        when(mockPagoDeProveedorSearchRepository.search(queryStringQuery("id:" + pagoDeProveedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pagoDeProveedor), PageRequest.of(0, 1), 1));
        // Search the pagoDeProveedor
        restPagoDeProveedorMockMvc.perform(get("/api/_search/pago-de-proveedors?query=id:" + pagoDeProveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagoDeProveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoPagado").value(hasItem(DEFAULT_MONTO_PAGADO.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].codigoDeDocumento").value(hasItem(DEFAULT_CODIGO_DE_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagoDeProveedor.class);
        PagoDeProveedor pagoDeProveedor1 = new PagoDeProveedor();
        pagoDeProveedor1.setId(1L);
        PagoDeProveedor pagoDeProveedor2 = new PagoDeProveedor();
        pagoDeProveedor2.setId(pagoDeProveedor1.getId());
        assertThat(pagoDeProveedor1).isEqualTo(pagoDeProveedor2);
        pagoDeProveedor2.setId(2L);
        assertThat(pagoDeProveedor1).isNotEqualTo(pagoDeProveedor2);
        pagoDeProveedor1.setId(null);
        assertThat(pagoDeProveedor1).isNotEqualTo(pagoDeProveedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PagoDeProveedorDTO.class);
        PagoDeProveedorDTO pagoDeProveedorDTO1 = new PagoDeProveedorDTO();
        pagoDeProveedorDTO1.setId(1L);
        PagoDeProveedorDTO pagoDeProveedorDTO2 = new PagoDeProveedorDTO();
        assertThat(pagoDeProveedorDTO1).isNotEqualTo(pagoDeProveedorDTO2);
        pagoDeProveedorDTO2.setId(pagoDeProveedorDTO1.getId());
        assertThat(pagoDeProveedorDTO1).isEqualTo(pagoDeProveedorDTO2);
        pagoDeProveedorDTO2.setId(2L);
        assertThat(pagoDeProveedorDTO1).isNotEqualTo(pagoDeProveedorDTO2);
        pagoDeProveedorDTO1.setId(null);
        assertThat(pagoDeProveedorDTO1).isNotEqualTo(pagoDeProveedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pagoDeProveedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pagoDeProveedorMapper.fromId(null)).isNull();
    }
}
