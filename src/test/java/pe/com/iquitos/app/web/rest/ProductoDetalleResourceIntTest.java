package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ProductoDetalle;
import pe.com.iquitos.app.repository.ProductoDetalleRepository;
import pe.com.iquitos.app.repository.search.ProductoDetalleSearchRepository;
import pe.com.iquitos.app.service.ProductoDetalleService;
import pe.com.iquitos.app.service.dto.ProductoDetalleDTO;
import pe.com.iquitos.app.service.mapper.ProductoDetalleMapper;
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
 * Test class for the ProductoDetalleResource REST controller.
 *
 * @see ProductoDetalleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ProductoDetalleResourceIntTest {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final String DEFAULT_PRODUCTO_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTO_LABEL = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO_VENTA = 1D;
    private static final Double UPDATED_PRECIO_VENTA = 2D;

    @Autowired
    private ProductoDetalleRepository productoDetalleRepository;

    @Mock
    private ProductoDetalleRepository productoDetalleRepositoryMock;

    @Autowired
    private ProductoDetalleMapper productoDetalleMapper;

    @Mock
    private ProductoDetalleService productoDetalleServiceMock;

    @Autowired
    private ProductoDetalleService productoDetalleService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ProductoDetalleSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductoDetalleSearchRepository mockProductoDetalleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductoDetalleMockMvc;

    private ProductoDetalle productoDetalle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoDetalleResource productoDetalleResource = new ProductoDetalleResource(productoDetalleService);
        this.restProductoDetalleMockMvc = MockMvcBuilders.standaloneSetup(productoDetalleResource)
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
    public static ProductoDetalle createEntity(EntityManager em) {
        ProductoDetalle productoDetalle = new ProductoDetalle()
            .cantidad(DEFAULT_CANTIDAD)
            .productoLabel(DEFAULT_PRODUCTO_LABEL)
            .precioVenta(DEFAULT_PRECIO_VENTA);
        return productoDetalle;
    }

    @Before
    public void initTest() {
        productoDetalle = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductoDetalle() throws Exception {
        int databaseSizeBeforeCreate = productoDetalleRepository.findAll().size();

        // Create the ProductoDetalle
        ProductoDetalleDTO productoDetalleDTO = productoDetalleMapper.toDto(productoDetalle);
        restProductoDetalleMockMvc.perform(post("/api/producto-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDetalleDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductoDetalle in the database
        List<ProductoDetalle> productoDetalleList = productoDetalleRepository.findAll();
        assertThat(productoDetalleList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoDetalle testProductoDetalle = productoDetalleList.get(productoDetalleList.size() - 1);
        assertThat(testProductoDetalle.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoDetalle.getProductoLabel()).isEqualTo(DEFAULT_PRODUCTO_LABEL);
        assertThat(testProductoDetalle.getPrecioVenta()).isEqualTo(DEFAULT_PRECIO_VENTA);

        // Validate the ProductoDetalle in Elasticsearch
        verify(mockProductoDetalleSearchRepository, times(1)).save(testProductoDetalle);
    }

    @Test
    @Transactional
    public void createProductoDetalleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoDetalleRepository.findAll().size();

        // Create the ProductoDetalle with an existing ID
        productoDetalle.setId(1L);
        ProductoDetalleDTO productoDetalleDTO = productoDetalleMapper.toDto(productoDetalle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoDetalleMockMvc.perform(post("/api/producto-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoDetalle in the database
        List<ProductoDetalle> productoDetalleList = productoDetalleRepository.findAll();
        assertThat(productoDetalleList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductoDetalle in Elasticsearch
        verify(mockProductoDetalleSearchRepository, times(0)).save(productoDetalle);
    }

    @Test
    @Transactional
    public void getAllProductoDetalles() throws Exception {
        // Initialize the database
        productoDetalleRepository.saveAndFlush(productoDetalle);

        // Get all the productoDetalleList
        restProductoDetalleMockMvc.perform(get("/api/producto-detalles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].productoLabel").value(hasItem(DEFAULT_PRODUCTO_LABEL.toString())))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(DEFAULT_PRECIO_VENTA.doubleValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProductoDetallesWithEagerRelationshipsIsEnabled() throws Exception {
        ProductoDetalleResource productoDetalleResource = new ProductoDetalleResource(productoDetalleServiceMock);
        when(productoDetalleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProductoDetalleMockMvc = MockMvcBuilders.standaloneSetup(productoDetalleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoDetalleMockMvc.perform(get("/api/producto-detalles?eagerload=true"))
        .andExpect(status().isOk());

        verify(productoDetalleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProductoDetallesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProductoDetalleResource productoDetalleResource = new ProductoDetalleResource(productoDetalleServiceMock);
            when(productoDetalleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProductoDetalleMockMvc = MockMvcBuilders.standaloneSetup(productoDetalleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoDetalleMockMvc.perform(get("/api/producto-detalles?eagerload=true"))
        .andExpect(status().isOk());

            verify(productoDetalleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProductoDetalle() throws Exception {
        // Initialize the database
        productoDetalleRepository.saveAndFlush(productoDetalle);

        // Get the productoDetalle
        restProductoDetalleMockMvc.perform(get("/api/producto-detalles/{id}", productoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productoDetalle.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.productoLabel").value(DEFAULT_PRODUCTO_LABEL.toString()))
            .andExpect(jsonPath("$.precioVenta").value(DEFAULT_PRECIO_VENTA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProductoDetalle() throws Exception {
        // Get the productoDetalle
        restProductoDetalleMockMvc.perform(get("/api/producto-detalles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductoDetalle() throws Exception {
        // Initialize the database
        productoDetalleRepository.saveAndFlush(productoDetalle);

        int databaseSizeBeforeUpdate = productoDetalleRepository.findAll().size();

        // Update the productoDetalle
        ProductoDetalle updatedProductoDetalle = productoDetalleRepository.findById(productoDetalle.getId()).get();
        // Disconnect from session so that the updates on updatedProductoDetalle are not directly saved in db
        em.detach(updatedProductoDetalle);
        updatedProductoDetalle
            .cantidad(UPDATED_CANTIDAD)
            .productoLabel(UPDATED_PRODUCTO_LABEL)
            .precioVenta(UPDATED_PRECIO_VENTA);
        ProductoDetalleDTO productoDetalleDTO = productoDetalleMapper.toDto(updatedProductoDetalle);

        restProductoDetalleMockMvc.perform(put("/api/producto-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDetalleDTO)))
            .andExpect(status().isOk());

        // Validate the ProductoDetalle in the database
        List<ProductoDetalle> productoDetalleList = productoDetalleRepository.findAll();
        assertThat(productoDetalleList).hasSize(databaseSizeBeforeUpdate);
        ProductoDetalle testProductoDetalle = productoDetalleList.get(productoDetalleList.size() - 1);
        assertThat(testProductoDetalle.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoDetalle.getProductoLabel()).isEqualTo(UPDATED_PRODUCTO_LABEL);
        assertThat(testProductoDetalle.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);

        // Validate the ProductoDetalle in Elasticsearch
        verify(mockProductoDetalleSearchRepository, times(1)).save(testProductoDetalle);
    }

    @Test
    @Transactional
    public void updateNonExistingProductoDetalle() throws Exception {
        int databaseSizeBeforeUpdate = productoDetalleRepository.findAll().size();

        // Create the ProductoDetalle
        ProductoDetalleDTO productoDetalleDTO = productoDetalleMapper.toDto(productoDetalle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoDetalleMockMvc.perform(put("/api/producto-detalles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDetalleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoDetalle in the database
        List<ProductoDetalle> productoDetalleList = productoDetalleRepository.findAll();
        assertThat(productoDetalleList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductoDetalle in Elasticsearch
        verify(mockProductoDetalleSearchRepository, times(0)).save(productoDetalle);
    }

    @Test
    @Transactional
    public void deleteProductoDetalle() throws Exception {
        // Initialize the database
        productoDetalleRepository.saveAndFlush(productoDetalle);

        int databaseSizeBeforeDelete = productoDetalleRepository.findAll().size();

        // Get the productoDetalle
        restProductoDetalleMockMvc.perform(delete("/api/producto-detalles/{id}", productoDetalle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductoDetalle> productoDetalleList = productoDetalleRepository.findAll();
        assertThat(productoDetalleList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductoDetalle in Elasticsearch
        verify(mockProductoDetalleSearchRepository, times(1)).deleteById(productoDetalle.getId());
    }

    @Test
    @Transactional
    public void searchProductoDetalle() throws Exception {
        // Initialize the database
        productoDetalleRepository.saveAndFlush(productoDetalle);
        when(mockProductoDetalleSearchRepository.search(queryStringQuery("id:" + productoDetalle.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productoDetalle), PageRequest.of(0, 1), 1));
        // Search the productoDetalle
        restProductoDetalleMockMvc.perform(get("/api/_search/producto-detalles?query=id:" + productoDetalle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoDetalle.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].productoLabel").value(hasItem(DEFAULT_PRODUCTO_LABEL)))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(DEFAULT_PRECIO_VENTA.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoDetalle.class);
        ProductoDetalle productoDetalle1 = new ProductoDetalle();
        productoDetalle1.setId(1L);
        ProductoDetalle productoDetalle2 = new ProductoDetalle();
        productoDetalle2.setId(productoDetalle1.getId());
        assertThat(productoDetalle1).isEqualTo(productoDetalle2);
        productoDetalle2.setId(2L);
        assertThat(productoDetalle1).isNotEqualTo(productoDetalle2);
        productoDetalle1.setId(null);
        assertThat(productoDetalle1).isNotEqualTo(productoDetalle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoDetalleDTO.class);
        ProductoDetalleDTO productoDetalleDTO1 = new ProductoDetalleDTO();
        productoDetalleDTO1.setId(1L);
        ProductoDetalleDTO productoDetalleDTO2 = new ProductoDetalleDTO();
        assertThat(productoDetalleDTO1).isNotEqualTo(productoDetalleDTO2);
        productoDetalleDTO2.setId(productoDetalleDTO1.getId());
        assertThat(productoDetalleDTO1).isEqualTo(productoDetalleDTO2);
        productoDetalleDTO2.setId(2L);
        assertThat(productoDetalleDTO1).isNotEqualTo(productoDetalleDTO2);
        productoDetalleDTO1.setId(null);
        assertThat(productoDetalleDTO1).isNotEqualTo(productoDetalleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoDetalleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoDetalleMapper.fromId(null)).isNull();
    }
}
