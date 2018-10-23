package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.DocumentTypeSell;
import pe.com.iquitos.app.repository.DocumentTypeSellRepository;
import pe.com.iquitos.app.repository.search.DocumentTypeSellSearchRepository;
import pe.com.iquitos.app.service.DocumentTypeSellService;
import pe.com.iquitos.app.service.dto.DocumentTypeSellDTO;
import pe.com.iquitos.app.service.mapper.DocumentTypeSellMapper;
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
 * Test class for the DocumentTypeSellResource REST controller.
 *
 * @see DocumentTypeSellResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class DocumentTypeSellResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private DocumentTypeSellRepository documentTypeSellRepository;

    @Autowired
    private DocumentTypeSellMapper documentTypeSellMapper;
    
    @Autowired
    private DocumentTypeSellService documentTypeSellService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.DocumentTypeSellSearchRepositoryMockConfiguration
     */
    @Autowired
    private DocumentTypeSellSearchRepository mockDocumentTypeSellSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDocumentTypeSellMockMvc;

    private DocumentTypeSell documentTypeSell;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentTypeSellResource documentTypeSellResource = new DocumentTypeSellResource(documentTypeSellService);
        this.restDocumentTypeSellMockMvc = MockMvcBuilders.standaloneSetup(documentTypeSellResource)
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
    public static DocumentTypeSell createEntity(EntityManager em) {
        DocumentTypeSell documentTypeSell = new DocumentTypeSell()
            .value(DEFAULT_VALUE)
            .metaData(DEFAULT_META_DATA);
        return documentTypeSell;
    }

    @Before
    public void initTest() {
        documentTypeSell = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentTypeSell() throws Exception {
        int databaseSizeBeforeCreate = documentTypeSellRepository.findAll().size();

        // Create the DocumentTypeSell
        DocumentTypeSellDTO documentTypeSellDTO = documentTypeSellMapper.toDto(documentTypeSell);
        restDocumentTypeSellMockMvc.perform(post("/api/document-type-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeSellDTO)))
            .andExpect(status().isCreated());

        // Validate the DocumentTypeSell in the database
        List<DocumentTypeSell> documentTypeSellList = documentTypeSellRepository.findAll();
        assertThat(documentTypeSellList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentTypeSell testDocumentTypeSell = documentTypeSellList.get(documentTypeSellList.size() - 1);
        assertThat(testDocumentTypeSell.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDocumentTypeSell.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the DocumentTypeSell in Elasticsearch
        verify(mockDocumentTypeSellSearchRepository, times(1)).save(testDocumentTypeSell);
    }

    @Test
    @Transactional
    public void createDocumentTypeSellWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentTypeSellRepository.findAll().size();

        // Create the DocumentTypeSell with an existing ID
        documentTypeSell.setId(1L);
        DocumentTypeSellDTO documentTypeSellDTO = documentTypeSellMapper.toDto(documentTypeSell);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentTypeSellMockMvc.perform(post("/api/document-type-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeSellDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeSell in the database
        List<DocumentTypeSell> documentTypeSellList = documentTypeSellRepository.findAll();
        assertThat(documentTypeSellList).hasSize(databaseSizeBeforeCreate);

        // Validate the DocumentTypeSell in Elasticsearch
        verify(mockDocumentTypeSellSearchRepository, times(0)).save(documentTypeSell);
    }

    @Test
    @Transactional
    public void getAllDocumentTypeSells() throws Exception {
        // Initialize the database
        documentTypeSellRepository.saveAndFlush(documentTypeSell);

        // Get all the documentTypeSellList
        restDocumentTypeSellMockMvc.perform(get("/api/document-type-sells?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTypeSell.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumentTypeSell() throws Exception {
        // Initialize the database
        documentTypeSellRepository.saveAndFlush(documentTypeSell);

        // Get the documentTypeSell
        restDocumentTypeSellMockMvc.perform(get("/api/document-type-sells/{id}", documentTypeSell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentTypeSell.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentTypeSell() throws Exception {
        // Get the documentTypeSell
        restDocumentTypeSellMockMvc.perform(get("/api/document-type-sells/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentTypeSell() throws Exception {
        // Initialize the database
        documentTypeSellRepository.saveAndFlush(documentTypeSell);

        int databaseSizeBeforeUpdate = documentTypeSellRepository.findAll().size();

        // Update the documentTypeSell
        DocumentTypeSell updatedDocumentTypeSell = documentTypeSellRepository.findById(documentTypeSell.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentTypeSell are not directly saved in db
        em.detach(updatedDocumentTypeSell);
        updatedDocumentTypeSell
            .value(UPDATED_VALUE)
            .metaData(UPDATED_META_DATA);
        DocumentTypeSellDTO documentTypeSellDTO = documentTypeSellMapper.toDto(updatedDocumentTypeSell);

        restDocumentTypeSellMockMvc.perform(put("/api/document-type-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeSellDTO)))
            .andExpect(status().isOk());

        // Validate the DocumentTypeSell in the database
        List<DocumentTypeSell> documentTypeSellList = documentTypeSellRepository.findAll();
        assertThat(documentTypeSellList).hasSize(databaseSizeBeforeUpdate);
        DocumentTypeSell testDocumentTypeSell = documentTypeSellList.get(documentTypeSellList.size() - 1);
        assertThat(testDocumentTypeSell.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDocumentTypeSell.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the DocumentTypeSell in Elasticsearch
        verify(mockDocumentTypeSellSearchRepository, times(1)).save(testDocumentTypeSell);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentTypeSell() throws Exception {
        int databaseSizeBeforeUpdate = documentTypeSellRepository.findAll().size();

        // Create the DocumentTypeSell
        DocumentTypeSellDTO documentTypeSellDTO = documentTypeSellMapper.toDto(documentTypeSell);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentTypeSellMockMvc.perform(put("/api/document-type-sells")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentTypeSellDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentTypeSell in the database
        List<DocumentTypeSell> documentTypeSellList = documentTypeSellRepository.findAll();
        assertThat(documentTypeSellList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DocumentTypeSell in Elasticsearch
        verify(mockDocumentTypeSellSearchRepository, times(0)).save(documentTypeSell);
    }

    @Test
    @Transactional
    public void deleteDocumentTypeSell() throws Exception {
        // Initialize the database
        documentTypeSellRepository.saveAndFlush(documentTypeSell);

        int databaseSizeBeforeDelete = documentTypeSellRepository.findAll().size();

        // Get the documentTypeSell
        restDocumentTypeSellMockMvc.perform(delete("/api/document-type-sells/{id}", documentTypeSell.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocumentTypeSell> documentTypeSellList = documentTypeSellRepository.findAll();
        assertThat(documentTypeSellList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DocumentTypeSell in Elasticsearch
        verify(mockDocumentTypeSellSearchRepository, times(1)).deleteById(documentTypeSell.getId());
    }

    @Test
    @Transactional
    public void searchDocumentTypeSell() throws Exception {
        // Initialize the database
        documentTypeSellRepository.saveAndFlush(documentTypeSell);
        when(mockDocumentTypeSellSearchRepository.search(queryStringQuery("id:" + documentTypeSell.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(documentTypeSell), PageRequest.of(0, 1), 1));
        // Search the documentTypeSell
        restDocumentTypeSellMockMvc.perform(get("/api/_search/document-type-sells?query=id:" + documentTypeSell.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentTypeSell.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTypeSell.class);
        DocumentTypeSell documentTypeSell1 = new DocumentTypeSell();
        documentTypeSell1.setId(1L);
        DocumentTypeSell documentTypeSell2 = new DocumentTypeSell();
        documentTypeSell2.setId(documentTypeSell1.getId());
        assertThat(documentTypeSell1).isEqualTo(documentTypeSell2);
        documentTypeSell2.setId(2L);
        assertThat(documentTypeSell1).isNotEqualTo(documentTypeSell2);
        documentTypeSell1.setId(null);
        assertThat(documentTypeSell1).isNotEqualTo(documentTypeSell2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentTypeSellDTO.class);
        DocumentTypeSellDTO documentTypeSellDTO1 = new DocumentTypeSellDTO();
        documentTypeSellDTO1.setId(1L);
        DocumentTypeSellDTO documentTypeSellDTO2 = new DocumentTypeSellDTO();
        assertThat(documentTypeSellDTO1).isNotEqualTo(documentTypeSellDTO2);
        documentTypeSellDTO2.setId(documentTypeSellDTO1.getId());
        assertThat(documentTypeSellDTO1).isEqualTo(documentTypeSellDTO2);
        documentTypeSellDTO2.setId(2L);
        assertThat(documentTypeSellDTO1).isNotEqualTo(documentTypeSellDTO2);
        documentTypeSellDTO1.setId(null);
        assertThat(documentTypeSellDTO1).isNotEqualTo(documentTypeSellDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(documentTypeSellMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(documentTypeSellMapper.fromId(null)).isNull();
    }
}
