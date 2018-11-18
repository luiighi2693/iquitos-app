package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Compra;
import pe.com.iquitos.app.repository.CompraRepository;
import pe.com.iquitos.app.repository.search.CompraSearchRepository;
import pe.com.iquitos.app.service.CompraService;
import pe.com.iquitos.app.service.dto.CompraDTO;
import pe.com.iquitos.app.service.mapper.CompraMapper;
import pe.com.iquitos.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static pe.com.iquitos.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pe.com.iquitos.app.domain.enumeration.PurchaseLocation;
import pe.com.iquitos.app.domain.enumeration.PaymentPurchaseType;
/**
 * Test class for the CompraResource REST controller.
 *
 * @see CompraResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class CompraResourceIntTest {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_GUIA_REMISION = "AAAAAAAAAA";
    private static final String UPDATED_GUIA_REMISION = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_DE_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DE_DOCUMENTO = "BBBBBBBBBB";

    private static final PurchaseLocation DEFAULT_UBICACION = PurchaseLocation.TIENDA;
    private static final PurchaseLocation UPDATED_UBICACION = PurchaseLocation.TIENDA;

    private static final Double DEFAULT_MONTO_TOTAL = 1D;
    private static final Double UPDATED_MONTO_TOTAL = 2D;

    private static final String DEFAULT_CORRELATIVO = "AAAAAAAAAA";
    private static final String UPDATED_CORRELATIVO = "BBBBBBBBBB";

    private static final PaymentPurchaseType DEFAULT_TIPO_DE_PAGO_DE_COMPRA = PaymentPurchaseType.CONTADO;
    private static final PaymentPurchaseType UPDATED_TIPO_DE_PAGO_DE_COMPRA = PaymentPurchaseType.CREDITO;

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private CompraRepository compraRepository;

    @Mock
    private CompraRepository compraRepositoryMock;

    @Autowired
    private CompraMapper compraMapper;

    @Mock
    private CompraService compraServiceMock;

    @Autowired
    private CompraService compraService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.CompraSearchRepositoryMockConfiguration
     */
    @Autowired
    private CompraSearchRepository mockCompraSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompraMockMvc;

    private Compra compra;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompraResource compraResource = new CompraResource(compraService);
        this.restCompraMockMvc = MockMvcBuilders.standaloneSetup(compraResource)
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
    public static Compra createEntity(EntityManager em) {
        Compra compra = new Compra()
            .fecha(DEFAULT_FECHA)
            .guiaRemision(DEFAULT_GUIA_REMISION)
            .numeroDeDocumento(DEFAULT_NUMERO_DE_DOCUMENTO)
            .ubicacion(DEFAULT_UBICACION)
            .montoTotal(DEFAULT_MONTO_TOTAL)
            .correlativo(DEFAULT_CORRELATIVO)
            .tipoDePagoDeCompra(DEFAULT_TIPO_DE_PAGO_DE_COMPRA)
            .metaData(DEFAULT_META_DATA);
        return compra;
    }

    @Before
    public void initTest() {
        compra = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompra() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isCreated());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate + 1);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCompra.getGuiaRemision()).isEqualTo(DEFAULT_GUIA_REMISION);
        assertThat(testCompra.getNumeroDeDocumento()).isEqualTo(DEFAULT_NUMERO_DE_DOCUMENTO);
        assertThat(testCompra.getUbicacion()).isEqualTo(DEFAULT_UBICACION);
        assertThat(testCompra.getMontoTotal()).isEqualTo(DEFAULT_MONTO_TOTAL);
        assertThat(testCompra.getCorrelativo()).isEqualTo(DEFAULT_CORRELATIVO);
        assertThat(testCompra.getTipoDePagoDeCompra()).isEqualTo(DEFAULT_TIPO_DE_PAGO_DE_COMPRA);
        assertThat(testCompra.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(1)).save(testCompra);
    }

    @Test
    @Transactional
    public void createCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra with an existing ID
        compra.setId(1L);
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(0)).save(compra);
    }

    @Test
    @Transactional
    public void checkGuiaRemisionIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setGuiaRemision(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);

        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroDeDocumentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setNumeroDeDocumento(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);

        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setMontoTotal(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);

        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorrelativoIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setCorrelativo(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);

        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMetaDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setMetaData(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);

        restCompraMockMvc.perform(post("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompras() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList
        restCompraMockMvc.perform(get("/api/compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].guiaRemision").value(hasItem(DEFAULT_GUIA_REMISION.toString())))
            .andExpect(jsonPath("$.[*].numeroDeDocumento").value(hasItem(DEFAULT_NUMERO_DE_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION.toString())))
            .andExpect(jsonPath("$.[*].montoTotal").value(hasItem(DEFAULT_MONTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].correlativo").value(hasItem(DEFAULT_CORRELATIVO.toString())))
            .andExpect(jsonPath("$.[*].tipoDePagoDeCompra").value(hasItem(DEFAULT_TIPO_DE_PAGO_DE_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllComprasWithEagerRelationshipsIsEnabled() throws Exception {
        CompraResource compraResource = new CompraResource(compraServiceMock);
        when(compraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCompraMockMvc = MockMvcBuilders.standaloneSetup(compraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCompraMockMvc.perform(get("/api/compras?eagerload=true"))
        .andExpect(status().isOk());

        verify(compraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllComprasWithEagerRelationshipsIsNotEnabled() throws Exception {
        CompraResource compraResource = new CompraResource(compraServiceMock);
            when(compraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCompraMockMvc = MockMvcBuilders.standaloneSetup(compraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCompraMockMvc.perform(get("/api/compras?eagerload=true"))
        .andExpect(status().isOk());

            verify(compraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", compra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compra.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.guiaRemision").value(DEFAULT_GUIA_REMISION.toString()))
            .andExpect(jsonPath("$.numeroDeDocumento").value(DEFAULT_NUMERO_DE_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION.toString()))
            .andExpect(jsonPath("$.montoTotal").value(DEFAULT_MONTO_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.correlativo").value(DEFAULT_CORRELATIVO.toString()))
            .andExpect(jsonPath("$.tipoDePagoDeCompra").value(DEFAULT_TIPO_DE_PAGO_DE_COMPRA.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompra() throws Exception {
        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Update the compra
        Compra updatedCompra = compraRepository.findById(compra.getId()).get();
        // Disconnect from session so that the updates on updatedCompra are not directly saved in db
        em.detach(updatedCompra);
        updatedCompra
            .fecha(UPDATED_FECHA)
            .guiaRemision(UPDATED_GUIA_REMISION)
            .numeroDeDocumento(UPDATED_NUMERO_DE_DOCUMENTO)
            .ubicacion(UPDATED_UBICACION)
            .montoTotal(UPDATED_MONTO_TOTAL)
            .correlativo(UPDATED_CORRELATIVO)
            .tipoDePagoDeCompra(UPDATED_TIPO_DE_PAGO_DE_COMPRA)
            .metaData(UPDATED_META_DATA);
        CompraDTO compraDTO = compraMapper.toDto(updatedCompra);

        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isOk());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCompra.getGuiaRemision()).isEqualTo(UPDATED_GUIA_REMISION);
        assertThat(testCompra.getNumeroDeDocumento()).isEqualTo(UPDATED_NUMERO_DE_DOCUMENTO);
        assertThat(testCompra.getUbicacion()).isEqualTo(UPDATED_UBICACION);
        assertThat(testCompra.getMontoTotal()).isEqualTo(UPDATED_MONTO_TOTAL);
        assertThat(testCompra.getCorrelativo()).isEqualTo(UPDATED_CORRELATIVO);
        assertThat(testCompra.getTipoDePagoDeCompra()).isEqualTo(UPDATED_TIPO_DE_PAGO_DE_COMPRA);
        assertThat(testCompra.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(1)).save(testCompra);
    }

    @Test
    @Transactional
    public void updateNonExistingCompra() throws Exception {
        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraMockMvc.perform(put("/api/compras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(0)).save(compra);
    }

    @Test
    @Transactional
    public void deleteCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeDelete = compraRepository.findAll().size();

        // Get the compra
        restCompraMockMvc.perform(delete("/api/compras/{id}", compra.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Compra in Elasticsearch
        verify(mockCompraSearchRepository, times(1)).deleteById(compra.getId());
    }

    @Test
    @Transactional
    public void searchCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);
        when(mockCompraSearchRepository.search(queryStringQuery("id:" + compra.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(compra), PageRequest.of(0, 1), 1));
        // Search the compra
        restCompraMockMvc.perform(get("/api/_search/compras?query=id:" + compra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].guiaRemision").value(hasItem(DEFAULT_GUIA_REMISION)))
            .andExpect(jsonPath("$.[*].numeroDeDocumento").value(hasItem(DEFAULT_NUMERO_DE_DOCUMENTO)))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION.toString())))
            .andExpect(jsonPath("$.[*].montoTotal").value(hasItem(DEFAULT_MONTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].correlativo").value(hasItem(DEFAULT_CORRELATIVO)))
            .andExpect(jsonPath("$.[*].tipoDePagoDeCompra").value(hasItem(DEFAULT_TIPO_DE_PAGO_DE_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compra.class);
        Compra compra1 = new Compra();
        compra1.setId(1L);
        Compra compra2 = new Compra();
        compra2.setId(compra1.getId());
        assertThat(compra1).isEqualTo(compra2);
        compra2.setId(2L);
        assertThat(compra1).isNotEqualTo(compra2);
        compra1.setId(null);
        assertThat(compra1).isNotEqualTo(compra2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompraDTO.class);
        CompraDTO compraDTO1 = new CompraDTO();
        compraDTO1.setId(1L);
        CompraDTO compraDTO2 = new CompraDTO();
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
        compraDTO2.setId(compraDTO1.getId());
        assertThat(compraDTO1).isEqualTo(compraDTO2);
        compraDTO2.setId(2L);
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
        compraDTO1.setId(null);
        assertThat(compraDTO1).isNotEqualTo(compraDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(compraMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(compraMapper.fromId(null)).isNull();
    }
}
