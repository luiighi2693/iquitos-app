package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Variante;
import pe.com.iquitos.app.repository.VarianteRepository;
import pe.com.iquitos.app.repository.search.VarianteSearchRepository;
import pe.com.iquitos.app.service.VarianteService;
import pe.com.iquitos.app.service.dto.VarianteDTO;
import pe.com.iquitos.app.service.mapper.VarianteMapper;
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

/**
 * Test class for the VarianteResource REST controller.
 *
 * @see VarianteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class VarianteResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_VENTA = 1D;
    private static final Double UPDATED_PRECIO_VENTA = 2D;

    private static final Double DEFAULT_PRECIO_COMPRA = 1D;
    private static final Double UPDATED_PRECIO_COMPRA = 2D;

    private static final Double DEFAULT_CANTIDAD = 1D;
    private static final Double UPDATED_CANTIDAD = 2D;

    @Autowired
    private VarianteRepository varianteRepository;

    @Mock
    private VarianteRepository varianteRepositoryMock;

    @Autowired
    private VarianteMapper varianteMapper;

    @Mock
    private VarianteService varianteServiceMock;

    @Autowired
    private VarianteService varianteService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.VarianteSearchRepositoryMockConfiguration
     */
    @Autowired
    private VarianteSearchRepository mockVarianteSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVarianteMockMvc;

    private Variante variante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VarianteResource varianteResource = new VarianteResource(varianteService);
        this.restVarianteMockMvc = MockMvcBuilders.standaloneSetup(varianteResource)
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
    public static Variante createEntity(EntityManager em) {
        Variante variante = new Variante()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .precioVenta(DEFAULT_PRECIO_VENTA)
            .precioCompra(DEFAULT_PRECIO_COMPRA)
            .cantidad(DEFAULT_CANTIDAD);
        return variante;
    }

    @Before
    public void initTest() {
        variante = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariante() throws Exception {
        int databaseSizeBeforeCreate = varianteRepository.findAll().size();

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);
        restVarianteMockMvc.perform(post("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isCreated());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeCreate + 1);
        Variante testVariante = varianteList.get(varianteList.size() - 1);
        assertThat(testVariante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testVariante.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testVariante.getPrecioVenta()).isEqualTo(DEFAULT_PRECIO_VENTA);
        assertThat(testVariante.getPrecioCompra()).isEqualTo(DEFAULT_PRECIO_COMPRA);
        assertThat(testVariante.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);

        // Validate the Variante in Elasticsearch
        verify(mockVarianteSearchRepository, times(1)).save(testVariante);
    }

    @Test
    @Transactional
    public void createVarianteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = varianteRepository.findAll().size();

        // Create the Variante with an existing ID
        variante.setId(1L);
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVarianteMockMvc.perform(post("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Variante in Elasticsearch
        verify(mockVarianteSearchRepository, times(0)).save(variante);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianteRepository.findAll().size();
        // set the field null
        variante.setNombre(null);

        // Create the Variante, which fails.
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        restVarianteMockMvc.perform(post("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioVentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianteRepository.findAll().size();
        // set the field null
        variante.setPrecioVenta(null);

        // Create the Variante, which fails.
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        restVarianteMockMvc.perform(post("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecioCompraIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianteRepository.findAll().size();
        // set the field null
        variante.setPrecioCompra(null);

        // Create the Variante, which fails.
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        restVarianteMockMvc.perform(post("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadIsRequired() throws Exception {
        int databaseSizeBeforeTest = varianteRepository.findAll().size();
        // set the field null
        variante.setCantidad(null);

        // Create the Variante, which fails.
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        restVarianteMockMvc.perform(post("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVariantes() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        // Get all the varianteList
        restVarianteMockMvc.perform(get("/api/variantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(DEFAULT_PRECIO_VENTA.doubleValue())))
            .andExpect(jsonPath("$.[*].precioCompra").value(hasItem(DEFAULT_PRECIO_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllVariantesWithEagerRelationshipsIsEnabled() throws Exception {
        VarianteResource varianteResource = new VarianteResource(varianteServiceMock);
        when(varianteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restVarianteMockMvc = MockMvcBuilders.standaloneSetup(varianteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVarianteMockMvc.perform(get("/api/variantes?eagerload=true"))
        .andExpect(status().isOk());

        verify(varianteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllVariantesWithEagerRelationshipsIsNotEnabled() throws Exception {
        VarianteResource varianteResource = new VarianteResource(varianteServiceMock);
            when(varianteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restVarianteMockMvc = MockMvcBuilders.standaloneSetup(varianteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restVarianteMockMvc.perform(get("/api/variantes?eagerload=true"))
        .andExpect(status().isOk());

            verify(varianteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getVariante() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        // Get the variante
        restVarianteMockMvc.perform(get("/api/variantes/{id}", variante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(variante.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.precioVenta").value(DEFAULT_PRECIO_VENTA.doubleValue()))
            .andExpect(jsonPath("$.precioCompra").value(DEFAULT_PRECIO_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVariante() throws Exception {
        // Get the variante
        restVarianteMockMvc.perform(get("/api/variantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariante() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();

        // Update the variante
        Variante updatedVariante = varianteRepository.findById(variante.getId()).get();
        // Disconnect from session so that the updates on updatedVariante are not directly saved in db
        em.detach(updatedVariante);
        updatedVariante
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .precioVenta(UPDATED_PRECIO_VENTA)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .cantidad(UPDATED_CANTIDAD);
        VarianteDTO varianteDTO = varianteMapper.toDto(updatedVariante);

        restVarianteMockMvc.perform(put("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isOk());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);
        Variante testVariante = varianteList.get(varianteList.size() - 1);
        assertThat(testVariante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVariante.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testVariante.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);
        assertThat(testVariante.getPrecioCompra()).isEqualTo(UPDATED_PRECIO_COMPRA);
        assertThat(testVariante.getCantidad()).isEqualTo(UPDATED_CANTIDAD);

        // Validate the Variante in Elasticsearch
        verify(mockVarianteSearchRepository, times(1)).save(testVariante);
    }

    @Test
    @Transactional
    public void updateNonExistingVariante() throws Exception {
        int databaseSizeBeforeUpdate = varianteRepository.findAll().size();

        // Create the Variante
        VarianteDTO varianteDTO = varianteMapper.toDto(variante);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVarianteMockMvc.perform(put("/api/variantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(varianteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Variante in the database
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Variante in Elasticsearch
        verify(mockVarianteSearchRepository, times(0)).save(variante);
    }

    @Test
    @Transactional
    public void deleteVariante() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);

        int databaseSizeBeforeDelete = varianteRepository.findAll().size();

        // Get the variante
        restVarianteMockMvc.perform(delete("/api/variantes/{id}", variante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Variante> varianteList = varianteRepository.findAll();
        assertThat(varianteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Variante in Elasticsearch
        verify(mockVarianteSearchRepository, times(1)).deleteById(variante.getId());
    }

    @Test
    @Transactional
    public void searchVariante() throws Exception {
        // Initialize the database
        varianteRepository.saveAndFlush(variante);
        when(mockVarianteSearchRepository.search(queryStringQuery("id:" + variante.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(variante), PageRequest.of(0, 1), 1));
        // Search the variante
        restVarianteMockMvc.perform(get("/api/_search/variantes?query=id:" + variante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(DEFAULT_PRECIO_VENTA.doubleValue())))
            .andExpect(jsonPath("$.[*].precioCompra").value(hasItem(DEFAULT_PRECIO_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Variante.class);
        Variante variante1 = new Variante();
        variante1.setId(1L);
        Variante variante2 = new Variante();
        variante2.setId(variante1.getId());
        assertThat(variante1).isEqualTo(variante2);
        variante2.setId(2L);
        assertThat(variante1).isNotEqualTo(variante2);
        variante1.setId(null);
        assertThat(variante1).isNotEqualTo(variante2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VarianteDTO.class);
        VarianteDTO varianteDTO1 = new VarianteDTO();
        varianteDTO1.setId(1L);
        VarianteDTO varianteDTO2 = new VarianteDTO();
        assertThat(varianteDTO1).isNotEqualTo(varianteDTO2);
        varianteDTO2.setId(varianteDTO1.getId());
        assertThat(varianteDTO1).isEqualTo(varianteDTO2);
        varianteDTO2.setId(2L);
        assertThat(varianteDTO1).isNotEqualTo(varianteDTO2);
        varianteDTO1.setId(null);
        assertThat(varianteDTO1).isNotEqualTo(varianteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(varianteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(varianteMapper.fromId(null)).isNull();
    }
}
