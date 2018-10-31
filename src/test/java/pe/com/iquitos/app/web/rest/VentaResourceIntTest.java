package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Venta;
import pe.com.iquitos.app.repository.VentaRepository;
import pe.com.iquitos.app.repository.search.VentaSearchRepository;
import pe.com.iquitos.app.service.VentaService;
import pe.com.iquitos.app.service.dto.VentaDTO;
import pe.com.iquitos.app.service.mapper.VentaMapper;
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

import pe.com.iquitos.app.domain.enumeration.SellStatus;
/**
 * Test class for the VentaResource REST controller.
 *
 * @see VentaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class VentaResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final Double DEFAULT_SUB_TOTAL = 1D;
    private static final Double UPDATED_SUB_TOTAL = 2D;

    private static final Double DEFAULT_IMPUESTO = 1D;
    private static final Double UPDATED_IMPUESTO = 2D;

    private static final Double DEFAULT_MONTO_TOTAL = 1D;
    private static final Double UPDATED_MONTO_TOTAL = 2D;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final SellStatus DEFAULT_ESTATUS = SellStatus.PENDIENTE;
    private static final SellStatus UPDATED_ESTATUS = SellStatus.COMPLETADO;

    private static final String DEFAULT_GLOSA = "AAAAAAAAAA";
    private static final String UPDATED_GLOSA = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private VentaRepository ventaRepository;

    @Mock
    private VentaRepository ventaRepositoryMock;

    @Autowired
    private VentaMapper ventaMapper;
    

    @Mock
    private VentaService ventaServiceMock;

    @Autowired
    private VentaService ventaService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.VentaSearchRepositoryMockConfiguration
     */
    @Autowired
    private VentaSearchRepository mockVentaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVentaMockMvc;

    private Venta venta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VentaResource ventaResource = new VentaResource(ventaService);
        this.restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
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
    public static Venta createEntity(EntityManager em) {
        Venta venta = new Venta()
            .codigo(DEFAULT_CODIGO)
            .subTotal(DEFAULT_SUB_TOTAL)
            .impuesto(DEFAULT_IMPUESTO)
            .montoTotal(DEFAULT_MONTO_TOTAL)
            .fecha(DEFAULT_FECHA)
            .estatus(DEFAULT_ESTATUS)
            .glosa(DEFAULT_GLOSA)
            .metaData(DEFAULT_META_DATA);
        return venta;
    }

    @Before
    public void initTest() {
        venta = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenta() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate + 1);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testVenta.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
        assertThat(testVenta.getImpuesto()).isEqualTo(DEFAULT_IMPUESTO);
        assertThat(testVenta.getMontoTotal()).isEqualTo(DEFAULT_MONTO_TOTAL);
        assertThat(testVenta.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testVenta.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testVenta.getGlosa()).isEqualTo(DEFAULT_GLOSA);
        assertThat(testVenta.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the Venta in Elasticsearch
        verify(mockVentaSearchRepository, times(1)).save(testVenta);
    }

    @Test
    @Transactional
    public void createVentaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ventaRepository.findAll().size();

        // Create the Venta with an existing ID
        venta.setId(1L);
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Venta in Elasticsearch
        verify(mockVentaSearchRepository, times(0)).save(venta);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaRepository.findAll().size();
        // set the field null
        venta.setCodigo(null);

        // Create the Venta, which fails.
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaRepository.findAll().size();
        // set the field null
        venta.setSubTotal(null);

        // Create the Venta, which fails.
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImpuestoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaRepository.findAll().size();
        // set the field null
        venta.setImpuesto(null);

        // Create the Venta, which fails.
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaRepository.findAll().size();
        // set the field null
        venta.setMontoTotal(null);

        // Create the Venta, which fails.
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMetaDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventaRepository.findAll().size();
        // set the field null
        venta.setMetaData(null);

        // Create the Venta, which fails.
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        restVentaMockMvc.perform(post("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVentas() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get all the ventaList
        restVentaMockMvc.perform(get("/api/ventas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venta.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoTotal").value(hasItem(DEFAULT_MONTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    public void getAllVentasWithEagerRelationshipsIsEnabled() throws Exception {
        VentaResource ventaResource = new VentaResource(ventaServiceMock);
        when(ventaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVentaMockMvc.perform(get("/api/ventas?eagerload=true"))
        .andExpect(status().isOk());

        verify(ventaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllVentasWithEagerRelationshipsIsNotEnabled() throws Exception {
        VentaResource ventaResource = new VentaResource(ventaServiceMock);
            when(ventaServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVentaMockMvc = MockMvcBuilders.standaloneSetup(ventaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVentaMockMvc.perform(get("/api/ventas?eagerload=true"))
        .andExpect(status().isOk());

            verify(ventaServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", venta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(venta.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.impuesto").value(DEFAULT_IMPUESTO.doubleValue()))
            .andExpect(jsonPath("$.montoTotal").value(DEFAULT_MONTO_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.glosa").value(DEFAULT_GLOSA.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVenta() throws Exception {
        // Get the venta
        restVentaMockMvc.perform(get("/api/ventas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Update the venta
        Venta updatedVenta = ventaRepository.findById(venta.getId()).get();
        // Disconnect from session so that the updates on updatedVenta are not directly saved in db
        em.detach(updatedVenta);
        updatedVenta
            .codigo(UPDATED_CODIGO)
            .subTotal(UPDATED_SUB_TOTAL)
            .impuesto(UPDATED_IMPUESTO)
            .montoTotal(UPDATED_MONTO_TOTAL)
            .fecha(UPDATED_FECHA)
            .estatus(UPDATED_ESTATUS)
            .glosa(UPDATED_GLOSA)
            .metaData(UPDATED_META_DATA);
        VentaDTO ventaDTO = ventaMapper.toDto(updatedVenta);

        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isOk());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);
        Venta testVenta = ventaList.get(ventaList.size() - 1);
        assertThat(testVenta.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testVenta.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
        assertThat(testVenta.getImpuesto()).isEqualTo(UPDATED_IMPUESTO);
        assertThat(testVenta.getMontoTotal()).isEqualTo(UPDATED_MONTO_TOTAL);
        assertThat(testVenta.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testVenta.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testVenta.getGlosa()).isEqualTo(UPDATED_GLOSA);
        assertThat(testVenta.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the Venta in Elasticsearch
        verify(mockVentaSearchRepository, times(1)).save(testVenta);
    }

    @Test
    @Transactional
    public void updateNonExistingVenta() throws Exception {
        int databaseSizeBeforeUpdate = ventaRepository.findAll().size();

        // Create the Venta
        VentaDTO ventaDTO = ventaMapper.toDto(venta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentaMockMvc.perform(put("/api/ventas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ventaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venta in the database
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Venta in Elasticsearch
        verify(mockVentaSearchRepository, times(0)).save(venta);
    }

    @Test
    @Transactional
    public void deleteVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);

        int databaseSizeBeforeDelete = ventaRepository.findAll().size();

        // Get the venta
        restVentaMockMvc.perform(delete("/api/ventas/{id}", venta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Venta> ventaList = ventaRepository.findAll();
        assertThat(ventaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Venta in Elasticsearch
        verify(mockVentaSearchRepository, times(1)).deleteById(venta.getId());
    }

    @Test
    @Transactional
    public void searchVenta() throws Exception {
        // Initialize the database
        ventaRepository.saveAndFlush(venta);
        when(mockVentaSearchRepository.search(queryStringQuery("id:" + venta.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(venta), PageRequest.of(0, 1), 1));
        // Search the venta
        restVentaMockMvc.perform(get("/api/_search/ventas?query=id:" + venta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venta.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].impuesto").value(hasItem(DEFAULT_IMPUESTO.doubleValue())))
            .andExpect(jsonPath("$.[*].montoTotal").value(hasItem(DEFAULT_MONTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].glosa").value(hasItem(DEFAULT_GLOSA.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Venta.class);
        Venta venta1 = new Venta();
        venta1.setId(1L);
        Venta venta2 = new Venta();
        venta2.setId(venta1.getId());
        assertThat(venta1).isEqualTo(venta2);
        venta2.setId(2L);
        assertThat(venta1).isNotEqualTo(venta2);
        venta1.setId(null);
        assertThat(venta1).isNotEqualTo(venta2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VentaDTO.class);
        VentaDTO ventaDTO1 = new VentaDTO();
        ventaDTO1.setId(1L);
        VentaDTO ventaDTO2 = new VentaDTO();
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO2.setId(ventaDTO1.getId());
        assertThat(ventaDTO1).isEqualTo(ventaDTO2);
        ventaDTO2.setId(2L);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
        ventaDTO1.setId(null);
        assertThat(ventaDTO1).isNotEqualTo(ventaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ventaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ventaMapper.fromId(null)).isNull();
    }
}
