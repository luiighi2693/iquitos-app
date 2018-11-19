package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ProductosRelacionadosTags;
import pe.com.iquitos.app.repository.ProductosRelacionadosTagsRepository;
import pe.com.iquitos.app.repository.search.ProductosRelacionadosTagsSearchRepository;
import pe.com.iquitos.app.service.ProductosRelacionadosTagsService;
import pe.com.iquitos.app.service.dto.ProductosRelacionadosTagsDTO;
import pe.com.iquitos.app.service.mapper.ProductosRelacionadosTagsMapper;
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
 * Test class for the ProductosRelacionadosTagsResource REST controller.
 *
 * @see ProductosRelacionadosTagsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ProductosRelacionadosTagsResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private ProductosRelacionadosTagsRepository productosRelacionadosTagsRepository;

    @Autowired
    private ProductosRelacionadosTagsMapper productosRelacionadosTagsMapper;

    @Autowired
    private ProductosRelacionadosTagsService productosRelacionadosTagsService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ProductosRelacionadosTagsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProductosRelacionadosTagsSearchRepository mockProductosRelacionadosTagsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductosRelacionadosTagsMockMvc;

    private ProductosRelacionadosTags productosRelacionadosTags;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductosRelacionadosTagsResource productosRelacionadosTagsResource = new ProductosRelacionadosTagsResource(productosRelacionadosTagsService);
        this.restProductosRelacionadosTagsMockMvc = MockMvcBuilders.standaloneSetup(productosRelacionadosTagsResource)
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
    public static ProductosRelacionadosTags createEntity(EntityManager em) {
        ProductosRelacionadosTags productosRelacionadosTags = new ProductosRelacionadosTags()
            .nombre(DEFAULT_NOMBRE);
        return productosRelacionadosTags;
    }

    @Before
    public void initTest() {
        productosRelacionadosTags = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductosRelacionadosTags() throws Exception {
        int databaseSizeBeforeCreate = productosRelacionadosTagsRepository.findAll().size();

        // Create the ProductosRelacionadosTags
        ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO = productosRelacionadosTagsMapper.toDto(productosRelacionadosTags);
        restProductosRelacionadosTagsMockMvc.perform(post("/api/productos-relacionados-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosRelacionadosTagsDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductosRelacionadosTags in the database
        List<ProductosRelacionadosTags> productosRelacionadosTagsList = productosRelacionadosTagsRepository.findAll();
        assertThat(productosRelacionadosTagsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductosRelacionadosTags testProductosRelacionadosTags = productosRelacionadosTagsList.get(productosRelacionadosTagsList.size() - 1);
        assertThat(testProductosRelacionadosTags.getNombre()).isEqualTo(DEFAULT_NOMBRE);

        // Validate the ProductosRelacionadosTags in Elasticsearch
        verify(mockProductosRelacionadosTagsSearchRepository, times(1)).save(testProductosRelacionadosTags);
    }

    @Test
    @Transactional
    public void createProductosRelacionadosTagsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productosRelacionadosTagsRepository.findAll().size();

        // Create the ProductosRelacionadosTags with an existing ID
        productosRelacionadosTags.setId(1L);
        ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO = productosRelacionadosTagsMapper.toDto(productosRelacionadosTags);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductosRelacionadosTagsMockMvc.perform(post("/api/productos-relacionados-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosRelacionadosTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductosRelacionadosTags in the database
        List<ProductosRelacionadosTags> productosRelacionadosTagsList = productosRelacionadosTagsRepository.findAll();
        assertThat(productosRelacionadosTagsList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProductosRelacionadosTags in Elasticsearch
        verify(mockProductosRelacionadosTagsSearchRepository, times(0)).save(productosRelacionadosTags);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = productosRelacionadosTagsRepository.findAll().size();
        // set the field null
        productosRelacionadosTags.setNombre(null);

        // Create the ProductosRelacionadosTags, which fails.
        ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO = productosRelacionadosTagsMapper.toDto(productosRelacionadosTags);

        restProductosRelacionadosTagsMockMvc.perform(post("/api/productos-relacionados-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosRelacionadosTagsDTO)))
            .andExpect(status().isBadRequest());

        List<ProductosRelacionadosTags> productosRelacionadosTagsList = productosRelacionadosTagsRepository.findAll();
        assertThat(productosRelacionadosTagsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductosRelacionadosTags() throws Exception {
        // Initialize the database
        productosRelacionadosTagsRepository.saveAndFlush(productosRelacionadosTags);

        // Get all the productosRelacionadosTagsList
        restProductosRelacionadosTagsMockMvc.perform(get("/api/productos-relacionados-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productosRelacionadosTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductosRelacionadosTags() throws Exception {
        // Initialize the database
        productosRelacionadosTagsRepository.saveAndFlush(productosRelacionadosTags);

        // Get the productosRelacionadosTags
        restProductosRelacionadosTagsMockMvc.perform(get("/api/productos-relacionados-tags/{id}", productosRelacionadosTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productosRelacionadosTags.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductosRelacionadosTags() throws Exception {
        // Get the productosRelacionadosTags
        restProductosRelacionadosTagsMockMvc.perform(get("/api/productos-relacionados-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductosRelacionadosTags() throws Exception {
        // Initialize the database
        productosRelacionadosTagsRepository.saveAndFlush(productosRelacionadosTags);

        int databaseSizeBeforeUpdate = productosRelacionadosTagsRepository.findAll().size();

        // Update the productosRelacionadosTags
        ProductosRelacionadosTags updatedProductosRelacionadosTags = productosRelacionadosTagsRepository.findById(productosRelacionadosTags.getId()).get();
        // Disconnect from session so that the updates on updatedProductosRelacionadosTags are not directly saved in db
        em.detach(updatedProductosRelacionadosTags);
        updatedProductosRelacionadosTags
            .nombre(UPDATED_NOMBRE);
        ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO = productosRelacionadosTagsMapper.toDto(updatedProductosRelacionadosTags);

        restProductosRelacionadosTagsMockMvc.perform(put("/api/productos-relacionados-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosRelacionadosTagsDTO)))
            .andExpect(status().isOk());

        // Validate the ProductosRelacionadosTags in the database
        List<ProductosRelacionadosTags> productosRelacionadosTagsList = productosRelacionadosTagsRepository.findAll();
        assertThat(productosRelacionadosTagsList).hasSize(databaseSizeBeforeUpdate);
        ProductosRelacionadosTags testProductosRelacionadosTags = productosRelacionadosTagsList.get(productosRelacionadosTagsList.size() - 1);
        assertThat(testProductosRelacionadosTags.getNombre()).isEqualTo(UPDATED_NOMBRE);

        // Validate the ProductosRelacionadosTags in Elasticsearch
        verify(mockProductosRelacionadosTagsSearchRepository, times(1)).save(testProductosRelacionadosTags);
    }

    @Test
    @Transactional
    public void updateNonExistingProductosRelacionadosTags() throws Exception {
        int databaseSizeBeforeUpdate = productosRelacionadosTagsRepository.findAll().size();

        // Create the ProductosRelacionadosTags
        ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO = productosRelacionadosTagsMapper.toDto(productosRelacionadosTags);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductosRelacionadosTagsMockMvc.perform(put("/api/productos-relacionados-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productosRelacionadosTagsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductosRelacionadosTags in the database
        List<ProductosRelacionadosTags> productosRelacionadosTagsList = productosRelacionadosTagsRepository.findAll();
        assertThat(productosRelacionadosTagsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProductosRelacionadosTags in Elasticsearch
        verify(mockProductosRelacionadosTagsSearchRepository, times(0)).save(productosRelacionadosTags);
    }

    @Test
    @Transactional
    public void deleteProductosRelacionadosTags() throws Exception {
        // Initialize the database
        productosRelacionadosTagsRepository.saveAndFlush(productosRelacionadosTags);

        int databaseSizeBeforeDelete = productosRelacionadosTagsRepository.findAll().size();

        // Get the productosRelacionadosTags
        restProductosRelacionadosTagsMockMvc.perform(delete("/api/productos-relacionados-tags/{id}", productosRelacionadosTags.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductosRelacionadosTags> productosRelacionadosTagsList = productosRelacionadosTagsRepository.findAll();
        assertThat(productosRelacionadosTagsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProductosRelacionadosTags in Elasticsearch
        verify(mockProductosRelacionadosTagsSearchRepository, times(1)).deleteById(productosRelacionadosTags.getId());
    }

    @Test
    @Transactional
    public void searchProductosRelacionadosTags() throws Exception {
        // Initialize the database
        productosRelacionadosTagsRepository.saveAndFlush(productosRelacionadosTags);
        when(mockProductosRelacionadosTagsSearchRepository.search(queryStringQuery("id:" + productosRelacionadosTags.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(productosRelacionadosTags), PageRequest.of(0, 1), 1));
        // Search the productosRelacionadosTags
        restProductosRelacionadosTagsMockMvc.perform(get("/api/_search/productos-relacionados-tags?query=id:" + productosRelacionadosTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productosRelacionadosTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductosRelacionadosTags.class);
        ProductosRelacionadosTags productosRelacionadosTags1 = new ProductosRelacionadosTags();
        productosRelacionadosTags1.setId(1L);
        ProductosRelacionadosTags productosRelacionadosTags2 = new ProductosRelacionadosTags();
        productosRelacionadosTags2.setId(productosRelacionadosTags1.getId());
        assertThat(productosRelacionadosTags1).isEqualTo(productosRelacionadosTags2);
        productosRelacionadosTags2.setId(2L);
        assertThat(productosRelacionadosTags1).isNotEqualTo(productosRelacionadosTags2);
        productosRelacionadosTags1.setId(null);
        assertThat(productosRelacionadosTags1).isNotEqualTo(productosRelacionadosTags2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductosRelacionadosTagsDTO.class);
        ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO1 = new ProductosRelacionadosTagsDTO();
        productosRelacionadosTagsDTO1.setId(1L);
        ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO2 = new ProductosRelacionadosTagsDTO();
        assertThat(productosRelacionadosTagsDTO1).isNotEqualTo(productosRelacionadosTagsDTO2);
        productosRelacionadosTagsDTO2.setId(productosRelacionadosTagsDTO1.getId());
        assertThat(productosRelacionadosTagsDTO1).isEqualTo(productosRelacionadosTagsDTO2);
        productosRelacionadosTagsDTO2.setId(2L);
        assertThat(productosRelacionadosTagsDTO1).isNotEqualTo(productosRelacionadosTagsDTO2);
        productosRelacionadosTagsDTO1.setId(null);
        assertThat(productosRelacionadosTagsDTO1).isNotEqualTo(productosRelacionadosTagsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productosRelacionadosTagsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productosRelacionadosTagsMapper.fromId(null)).isNull();
    }
}
