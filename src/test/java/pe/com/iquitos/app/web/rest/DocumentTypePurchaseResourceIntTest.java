package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.DocumentTypePurchase;
import pe.com.iquitos.app.repository.DocumentTypePurchaseRepository;
import pe.com.iquitos.app.repository.search.DocumentTypePurchaseSearchRepository;
import pe.com.iquitos.app.service.DocumentTypePurchaseService;
import pe.com.iquitos.app.service.dto.DocumentTypePurchaseDTO;
import pe.com.iquitos.app.service.mapper.DocumentTypePurchaseMapper;
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
 * Test class for the DocumentTypePurchaseResource REST controller.
 *
 * @see DocumentTypePurchaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class DocumentTypePurchaseResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private DocumentTypePurchaseRepository documentTypePurchaseRepository;

    @Autowired
    private DocumentTypePurchaseMapper documentTypePurchaseMapper;
    
    @Autowired
    private DocumentTypePurchaseService documentTypePurchaseService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.DocumentTypePurchaseSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentTypePurchaseSearchRepository mockDocumentTypePurchaseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentTypePurchaseMockMvc;

    private DocumentTypePurchase documentTypePurchase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentTypePurchaseResource documentTypePurchaseResource = new DocumentTypePurchaseResource(documentTypePurchaseService);
        this.restDocumentTypePurchaseMockMvc = MockMvcBuilders.standaloneSetup(documentTypePurchaseResource)
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
    public static DocumentTypePurchase createEntity(EntityManager em) {
        DocumentTypePurchase documentTypePurchase = new DocumentTypePurchase()
            .value(DEFAULT_VALUE)
            .metaData(DEFAULT_META_DATA);
        return documentTypePurchase;
    }

    @Before
    public void initTest() {
        documentTypePurchase = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentTypePurchase() throws Exception {
        int databaseSizeBeforeCreate = documentTypePurchaseRepository.findAll().size();

        // Create the DocumentTypePurchase
        DocumentTypePurchaseDTO documentTypePurchaseDTO = documentTypePurchaseMapper.toDto(documentTypePurchase);
        restDocumentTypePurchaseMockMvc.perform(post("/api/document-type-purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypePurchaseDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentTypePurchase in the database
        List<DocumentTypePurchase> documentTypePurchaseList = documentTypePurchaseRepository.findAll();
        assertThat(documentTypePurchaseList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTypePurchase testDocumentTypePurchase = documentTypePurchaseList.get(documentTypePurchaseList.size() - 1);
        assertThat(testDocumentTypePurchase.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDocumentTypePurchase.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the DocumentTypePurchase in Elasticsearch
        verify(mockDocumentTypePurchaseSearchRepository, times(1)).save(testDocumentTypePurchase);
    }

    @Test
    @Transactional
    public void createDocumentTypePurchaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTypePurchaseRepository.findAll().size();

        // Create the DocumentTypePurchase with an existing ID
        documentTypePurchase.setId(1L);
        DocumentTypePurchaseDTO documentTypePurchaseDTO = documentTypePurchaseMapper.toDto(documentTypePurchase);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTypePurchaseMockMvc.perform(post("/api/document-type-purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypePurchaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypePurchase in the database
        List<DocumentTypePurchase> documentTypePurchaseList = documentTypePurchaseRepository.findAll();
        assertThat(documentTypePurchaseList).hasSize(databaseSizeBeforeCreate);

        // Validate the DocumentTypePurchase in Elasticsearch
        verify(mockDocumentTypePurchaseSearchRepository, times(0)).save(documentTypePurchase);
    }

    @Test
    @Transactional
    public void getAllDocumentTypePurchases() throws Exception {
        // Initialize the database
        documentTypePurchaseRepository.saveAndFlush(documentTypePurchase);

        // Get all the documentTypePurchaseList
        restDocumentTypePurchaseMockMvc.perform(get("/api/document-type-purchases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTypePurchase.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumentTypePurchase() throws Exception {
        // Initialize the database
        documentTypePurchaseRepository.saveAndFlush(documentTypePurchase);

        // Get the documentTypePurchase
        restDocumentTypePurchaseMockMvc.perform(get("/api/document-type-purchases/{id}", documentTypePurchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentTypePurchase.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentTypePurchase() throws Exception {
        // Get the documentTypePurchase
        restDocumentTypePurchaseMockMvc.perform(get("/api/document-type-purchases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTypePurchase() throws Exception {
        // Initialize the database
        documentTypePurchaseRepository.saveAndFlush(documentTypePurchase);

        int databaseSizeBeforeUpdate = documentTypePurchaseRepository.findAll().size();

        // Update the documentTypePurchase
        DocumentTypePurchase updatedDocumentTypePurchase = documentTypePurchaseRepository.findById(documentTypePurchase.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentTypePurchase are not directly saved in db
        em.detach(updatedDocumentTypePurchase);
        updatedDocumentTypePurchase
            .value(UPDATED_VALUE)
            .metaData(UPDATED_META_DATA);
        DocumentTypePurchaseDTO documentTypePurchaseDTO = documentTypePurchaseMapper.toDto(updatedDocumentTypePurchase);

        restDocumentTypePurchaseMockMvc.perform(put("/api/document-type-purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypePurchaseDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentTypePurchase in the database
        List<DocumentTypePurchase> documentTypePurchaseList = documentTypePurchaseRepository.findAll();
        assertThat(documentTypePurchaseList).hasSize(databaseSizeBeforeUpdate);
        DocumentTypePurchase testDocumentTypePurchase = documentTypePurchaseList.get(documentTypePurchaseList.size() - 1);
        assertThat(testDocumentTypePurchase.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDocumentTypePurchase.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the DocumentTypePurchase in Elasticsearch
        verify(mockDocumentTypePurchaseSearchRepository, times(1)).save(testDocumentTypePurchase);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentTypePurchase() throws Exception {
        int databaseSizeBeforeUpdate = documentTypePurchaseRepository.findAll().size();

        // Create the DocumentTypePurchase
        DocumentTypePurchaseDTO documentTypePurchaseDTO = documentTypePurchaseMapper.toDto(documentTypePurchase);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypePurchaseMockMvc.perform(put("/api/document-type-purchases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypePurchaseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypePurchase in the database
        List<DocumentTypePurchase> documentTypePurchaseList = documentTypePurchaseRepository.findAll();
        assertThat(documentTypePurchaseList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DocumentTypePurchase in Elasticsearch
        verify(mockDocumentTypePurchaseSearchRepository, times(0)).save(documentTypePurchase);
    }

    @Test
    @Transactional
    public void deleteDocumentTypePurchase() throws Exception {
        // Initialize the database
        documentTypePurchaseRepository.saveAndFlush(documentTypePurchase);

        int databaseSizeBeforeDelete = documentTypePurchaseRepository.findAll().size();

        // Get the documentTypePurchase
        restDocumentTypePurchaseMockMvc.perform(delete("/api/document-type-purchases/{id}", documentTypePurchase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentTypePurchase> documentTypePurchaseList = documentTypePurchaseRepository.findAll();
        assertThat(documentTypePurchaseList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DocumentTypePurchase in Elasticsearch
        verify(mockDocumentTypePurchaseSearchRepository, times(1)).deleteById(documentTypePurchase.getId());
    }

    @Test
    @Transactional
    public void searchDocumentTypePurchase() throws Exception {
        // Initialize the database
        documentTypePurchaseRepository.saveAndFlush(documentTypePurchase);
        when(mockDocumentTypePurchaseSearchRepository.search(queryStringQuery("id:" + documentTypePurchase.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentTypePurchase), PageRequest.of(0, 1), 1));
        // Search the documentTypePurchase
        restDocumentTypePurchaseMockMvc.perform(get("/api/_search/document-type-purchases?query=id:" + documentTypePurchase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTypePurchase.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTypePurchase.class);
        DocumentTypePurchase documentTypePurchase1 = new DocumentTypePurchase();
        documentTypePurchase1.setId(1L);
        DocumentTypePurchase documentTypePurchase2 = new DocumentTypePurchase();
        documentTypePurchase2.setId(documentTypePurchase1.getId());
        assertThat(documentTypePurchase1).isEqualTo(documentTypePurchase2);
        documentTypePurchase2.setId(2L);
        assertThat(documentTypePurchase1).isNotEqualTo(documentTypePurchase2);
        documentTypePurchase1.setId(null);
        assertThat(documentTypePurchase1).isNotEqualTo(documentTypePurchase2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTypePurchaseDTO.class);
        DocumentTypePurchaseDTO documentTypePurchaseDTO1 = new DocumentTypePurchaseDTO();
        documentTypePurchaseDTO1.setId(1L);
        DocumentTypePurchaseDTO documentTypePurchaseDTO2 = new DocumentTypePurchaseDTO();
        assertThat(documentTypePurchaseDTO1).isNotEqualTo(documentTypePurchaseDTO2);
        documentTypePurchaseDTO2.setId(documentTypePurchaseDTO1.getId());
        assertThat(documentTypePurchaseDTO1).isEqualTo(documentTypePurchaseDTO2);
        documentTypePurchaseDTO2.setId(2L);
        assertThat(documentTypePurchaseDTO1).isNotEqualTo(documentTypePurchaseDTO2);
        documentTypePurchaseDTO1.setId(null);
        assertThat(documentTypePurchaseDTO1).isNotEqualTo(documentTypePurchaseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentTypePurchaseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentTypePurchaseMapper.fromId(null)).isNull();
    }
}
