package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Producto;
import pe.com.iquitos.app.repository.ProductoRepository;
import pe.com.iquitos.app.repository.search.ProductoSearchRepository;
import pe.com.iquitos.app.service.ProductoService;
import pe.com.iquitos.app.service.dto.ProductoDTO;
import pe.com.iquitos.app.service.mapper.ProductoMapper;
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
import org.springframework.util.Base64Utils;

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

import pe.com.iquitos.app.domain.enumeration.ProductType;
/**
 * Test class for the ProductoResource REST controller.
 *
 * @see ProductoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ProductoResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_EXPIRACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EXPIRACION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ES_FAVORITO = false;
    private static final Boolean UPDATED_ES_FAVORITO = true;

    private static final Boolean DEFAULT_VISIBLE_PARA_LA_VENTA = false;
    private static final Boolean UPDATED_VISIBLE_PARA_LA_VENTA = true;

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;

    private static final Integer DEFAULT_NOTIFICACION_DE_LIMITE_DE_STOCK = 1;
    private static final Integer UPDATED_NOTIFICACION_DE_LIMITE_DE_STOCK = 2;

    private static final ProductType DEFAULT_TIPO_DE_PRODUCTO = ProductType.BIENES;
    private static final ProductType UPDATED_TIPO_DE_PRODUCTO = ProductType.SERVICIOS;

    @Autowired
    private ProductoRepository productoRepository;

    @Mock
    private ProductoRepository productoRepositoryMock;

    @Autowired
    private ProductoMapper productoMapper;

    @Mock
    private ProductoService productoServiceMock;

    @Autowired
    private ProductoService productoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ProductoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductoSearchRepository mockProductoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductoMockMvc;

    private Producto producto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoResource productoResource = new ProductoResource(productoService);
        this.restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
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
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaExpiracion(DEFAULT_FECHA_EXPIRACION)
            .esFavorito(DEFAULT_ES_FAVORITO)
            .visibleParaLaVenta(DEFAULT_VISIBLE_PARA_LA_VENTA)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .stock(DEFAULT_STOCK)
            .notificacionDeLimiteDeStock(DEFAULT_NOTIFICACION_DE_LIMITE_DE_STOCK)
            .tipoDeProducto(DEFAULT_TIPO_DE_PRODUCTO);
        return producto;
    }

    @Before
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getFechaExpiracion()).isEqualTo(DEFAULT_FECHA_EXPIRACION);
        assertThat(testProducto.isEsFavorito()).isEqualTo(DEFAULT_ES_FAVORITO);
        assertThat(testProducto.isVisibleParaLaVenta()).isEqualTo(DEFAULT_VISIBLE_PARA_LA_VENTA);
        assertThat(testProducto.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testProducto.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testProducto.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testProducto.getNotificacionDeLimiteDeStock()).isEqualTo(DEFAULT_NOTIFICACION_DE_LIMITE_DE_STOCK);
        assertThat(testProducto.getTipoDeProducto()).isEqualTo(DEFAULT_TIPO_DE_PRODUCTO);

        // Validate the Producto in Elasticsearch
        verify(mockProductoSearchRepository, times(1)).save(testProducto);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Producto in Elasticsearch
        verify(mockProductoSearchRepository, times(0)).save(producto);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setCodigo(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setStock(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaExpiracion").value(hasItem(DEFAULT_FECHA_EXPIRACION.toString())))
            .andExpect(jsonPath("$.[*].esFavorito").value(hasItem(DEFAULT_ES_FAVORITO.booleanValue())))
            .andExpect(jsonPath("$.[*].visibleParaLaVenta").value(hasItem(DEFAULT_VISIBLE_PARA_LA_VENTA.booleanValue())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].notificacionDeLimiteDeStock").value(hasItem(DEFAULT_NOTIFICACION_DE_LIMITE_DE_STOCK)))
            .andExpect(jsonPath("$.[*].tipoDeProducto").value(hasItem(DEFAULT_TIPO_DE_PRODUCTO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProductosWithEagerRelationshipsIsEnabled() throws Exception {
        ProductoResource productoResource = new ProductoResource(productoServiceMock);
        when(productoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoMockMvc.perform(get("/api/productos?eagerload=true"))
        .andExpect(status().isOk());

        verify(productoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProductosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProductoResource productoResource = new ProductoResource(productoServiceMock);
            when(productoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoMockMvc.perform(get("/api/productos?eagerload=true"))
        .andExpect(status().isOk());

            verify(productoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fechaExpiracion").value(DEFAULT_FECHA_EXPIRACION.toString()))
            .andExpect(jsonPath("$.esFavorito").value(DEFAULT_ES_FAVORITO.booleanValue()))
            .andExpect(jsonPath("$.visibleParaLaVenta").value(DEFAULT_VISIBLE_PARA_LA_VENTA.booleanValue()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.notificacionDeLimiteDeStock").value(DEFAULT_NOTIFICACION_DE_LIMITE_DE_STOCK))
            .andExpect(jsonPath("$.tipoDeProducto").value(DEFAULT_TIPO_DE_PRODUCTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .esFavorito(UPDATED_ES_FAVORITO)
            .visibleParaLaVenta(UPDATED_VISIBLE_PARA_LA_VENTA)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .stock(UPDATED_STOCK)
            .notificacionDeLimiteDeStock(UPDATED_NOTIFICACION_DE_LIMITE_DE_STOCK)
            .tipoDeProducto(UPDATED_TIPO_DE_PRODUCTO);
        ProductoDTO productoDTO = productoMapper.toDto(updatedProducto);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getFechaExpiracion()).isEqualTo(UPDATED_FECHA_EXPIRACION);
        assertThat(testProducto.isEsFavorito()).isEqualTo(UPDATED_ES_FAVORITO);
        assertThat(testProducto.isVisibleParaLaVenta()).isEqualTo(UPDATED_VISIBLE_PARA_LA_VENTA);
        assertThat(testProducto.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testProducto.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testProducto.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProducto.getNotificacionDeLimiteDeStock()).isEqualTo(UPDATED_NOTIFICACION_DE_LIMITE_DE_STOCK);
        assertThat(testProducto.getTipoDeProducto()).isEqualTo(UPDATED_TIPO_DE_PRODUCTO);

        // Validate the Producto in Elasticsearch
        verify(mockProductoSearchRepository, times(1)).save(testProducto);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Producto in Elasticsearch
        verify(mockProductoSearchRepository, times(0)).save(producto);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Get the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Producto in Elasticsearch
        verify(mockProductoSearchRepository, times(1)).deleteById(producto.getId());
    }

    @Test
    @Transactional
    public void searchProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);
        when(mockProductoSearchRepository.search(queryStringQuery("id:" + producto.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(producto), PageRequest.of(0, 1), 1));
        // Search the producto
        restProductoMockMvc.perform(get("/api/_search/productos?query=id:" + producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].fechaExpiracion").value(hasItem(DEFAULT_FECHA_EXPIRACION.toString())))
            .andExpect(jsonPath("$.[*].esFavorito").value(hasItem(DEFAULT_ES_FAVORITO.booleanValue())))
            .andExpect(jsonPath("$.[*].visibleParaLaVenta").value(hasItem(DEFAULT_VISIBLE_PARA_LA_VENTA.booleanValue())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].notificacionDeLimiteDeStock").value(hasItem(DEFAULT_NOTIFICACION_DE_LIMITE_DE_STOCK)))
            .andExpect(jsonPath("$.[*].tipoDeProducto").value(hasItem(DEFAULT_TIPO_DE_PRODUCTO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producto.class);
        Producto producto1 = new Producto();
        producto1.setId(1L);
        Producto producto2 = new Producto();
        producto2.setId(producto1.getId());
        assertThat(producto1).isEqualTo(producto2);
        producto2.setId(2L);
        assertThat(producto1).isNotEqualTo(producto2);
        producto1.setId(null);
        assertThat(producto1).isNotEqualTo(producto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoDTO.class);
        ProductoDTO productoDTO1 = new ProductoDTO();
        productoDTO1.setId(1L);
        ProductoDTO productoDTO2 = new ProductoDTO();
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
        productoDTO2.setId(productoDTO1.getId());
        assertThat(productoDTO1).isEqualTo(productoDTO2);
        productoDTO2.setId(2L);
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
        productoDTO1.setId(null);
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoMapper.fromId(null)).isNull();
    }
}
