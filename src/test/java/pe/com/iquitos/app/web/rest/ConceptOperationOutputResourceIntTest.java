package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ConceptOperationOutput;
import pe.com.iquitos.app.repository.ConceptOperationOutputRepository;
import pe.com.iquitos.app.repository.search.ConceptOperationOutputSearchRepository;
import pe.com.iquitos.app.service.ConceptOperationOutputService;
import pe.com.iquitos.app.service.dto.ConceptOperationOutputDTO;
import pe.com.iquitos.app.service.mapper.ConceptOperationOutputMapper;
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
 * Test class for the ConceptOperationOutputResource REST controller.
 *
 * @see ConceptOperationOutputResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ConceptOperationOutputResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private ConceptOperationOutputRepository conceptOperationOutputRepository;

    @Autowired
    private ConceptOperationOutputMapper conceptOperationOutputMapper;
    
    @Autowired
    private ConceptOperationOutputService conceptOperationOutputService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ConceptOperationOutputSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConceptOperationOutputSearchRepository mockConceptOperationOutputSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConceptOperationOutputMockMvc;

    private ConceptOperationOutput conceptOperationOutput;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConceptOperationOutputResource conceptOperationOutputResource = new ConceptOperationOutputResource(conceptOperationOutputService);
        this.restConceptOperationOutputMockMvc = MockMvcBuilders.standaloneSetup(conceptOperationOutputResource)
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
    public static ConceptOperationOutput createEntity(EntityManager em) {
        ConceptOperationOutput conceptOperationOutput = new ConceptOperationOutput()
            .value(DEFAULT_VALUE)
            .metaData(DEFAULT_META_DATA);
        return conceptOperationOutput;
    }

    @Before
    public void initTest() {
        conceptOperationOutput = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptOperationOutput() throws Exception {
        int databaseSizeBeforeCreate = conceptOperationOutputRepository.findAll().size();

        // Create the ConceptOperationOutput
        ConceptOperationOutputDTO conceptOperationOutputDTO = conceptOperationOutputMapper.toDto(conceptOperationOutput);
        restConceptOperationOutputMockMvc.perform(post("/api/concept-operation-outputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationOutputDTO)))
            .andExpect(status().isCreated());

        // Validate the ConceptOperationOutput in the database
        List<ConceptOperationOutput> conceptOperationOutputList = conceptOperationOutputRepository.findAll();
        assertThat(conceptOperationOutputList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptOperationOutput testConceptOperationOutput = conceptOperationOutputList.get(conceptOperationOutputList.size() - 1);
        assertThat(testConceptOperationOutput.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testConceptOperationOutput.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the ConceptOperationOutput in Elasticsearch
        verify(mockConceptOperationOutputSearchRepository, times(1)).save(testConceptOperationOutput);
    }

    @Test
    @Transactional
    public void createConceptOperationOutputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptOperationOutputRepository.findAll().size();

        // Create the ConceptOperationOutput with an existing ID
        conceptOperationOutput.setId(1L);
        ConceptOperationOutputDTO conceptOperationOutputDTO = conceptOperationOutputMapper.toDto(conceptOperationOutput);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptOperationOutputMockMvc.perform(post("/api/concept-operation-outputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationOutputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptOperationOutput in the database
        List<ConceptOperationOutput> conceptOperationOutputList = conceptOperationOutputRepository.findAll();
        assertThat(conceptOperationOutputList).hasSize(databaseSizeBeforeCreate);

        // Validate the ConceptOperationOutput in Elasticsearch
        verify(mockConceptOperationOutputSearchRepository, times(0)).save(conceptOperationOutput);
    }

    @Test
    @Transactional
    public void getAllConceptOperationOutputs() throws Exception {
        // Initialize the database
        conceptOperationOutputRepository.saveAndFlush(conceptOperationOutput);

        // Get all the conceptOperationOutputList
        restConceptOperationOutputMockMvc.perform(get("/api/concept-operation-outputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptOperationOutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getConceptOperationOutput() throws Exception {
        // Initialize the database
        conceptOperationOutputRepository.saveAndFlush(conceptOperationOutput);

        // Get the conceptOperationOutput
        restConceptOperationOutputMockMvc.perform(get("/api/concept-operation-outputs/{id}", conceptOperationOutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conceptOperationOutput.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConceptOperationOutput() throws Exception {
        // Get the conceptOperationOutput
        restConceptOperationOutputMockMvc.perform(get("/api/concept-operation-outputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptOperationOutput() throws Exception {
        // Initialize the database
        conceptOperationOutputRepository.saveAndFlush(conceptOperationOutput);

        int databaseSizeBeforeUpdate = conceptOperationOutputRepository.findAll().size();

        // Update the conceptOperationOutput
        ConceptOperationOutput updatedConceptOperationOutput = conceptOperationOutputRepository.findById(conceptOperationOutput.getId()).get();
        // Disconnect from session so that the updates on updatedConceptOperationOutput are not directly saved in db
        em.detach(updatedConceptOperationOutput);
        updatedConceptOperationOutput
            .value(UPDATED_VALUE)
            .metaData(UPDATED_META_DATA);
        ConceptOperationOutputDTO conceptOperationOutputDTO = conceptOperationOutputMapper.toDto(updatedConceptOperationOutput);

        restConceptOperationOutputMockMvc.perform(put("/api/concept-operation-outputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationOutputDTO)))
            .andExpect(status().isOk());

        // Validate the ConceptOperationOutput in the database
        List<ConceptOperationOutput> conceptOperationOutputList = conceptOperationOutputRepository.findAll();
        assertThat(conceptOperationOutputList).hasSize(databaseSizeBeforeUpdate);
        ConceptOperationOutput testConceptOperationOutput = conceptOperationOutputList.get(conceptOperationOutputList.size() - 1);
        assertThat(testConceptOperationOutput.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConceptOperationOutput.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the ConceptOperationOutput in Elasticsearch
        verify(mockConceptOperationOutputSearchRepository, times(1)).save(testConceptOperationOutput);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptOperationOutput() throws Exception {
        int databaseSizeBeforeUpdate = conceptOperationOutputRepository.findAll().size();

        // Create the ConceptOperationOutput
        ConceptOperationOutputDTO conceptOperationOutputDTO = conceptOperationOutputMapper.toDto(conceptOperationOutput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptOperationOutputMockMvc.perform(put("/api/concept-operation-outputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationOutputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptOperationOutput in the database
        List<ConceptOperationOutput> conceptOperationOutputList = conceptOperationOutputRepository.findAll();
        assertThat(conceptOperationOutputList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ConceptOperationOutput in Elasticsearch
        verify(mockConceptOperationOutputSearchRepository, times(0)).save(conceptOperationOutput);
    }

    @Test
    @Transactional
    public void deleteConceptOperationOutput() throws Exception {
        // Initialize the database
        conceptOperationOutputRepository.saveAndFlush(conceptOperationOutput);

        int databaseSizeBeforeDelete = conceptOperationOutputRepository.findAll().size();

        // Get the conceptOperationOutput
        restConceptOperationOutputMockMvc.perform(delete("/api/concept-operation-outputs/{id}", conceptOperationOutput.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConceptOperationOutput> conceptOperationOutputList = conceptOperationOutputRepository.findAll();
        assertThat(conceptOperationOutputList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ConceptOperationOutput in Elasticsearch
        verify(mockConceptOperationOutputSearchRepository, times(1)).deleteById(conceptOperationOutput.getId());
    }

    @Test
    @Transactional
    public void searchConceptOperationOutput() throws Exception {
        // Initialize the database
        conceptOperationOutputRepository.saveAndFlush(conceptOperationOutput);
        when(mockConceptOperationOutputSearchRepository.search(queryStringQuery("id:" + conceptOperationOutput.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(conceptOperationOutput), PageRequest.of(0, 1), 1));
        // Search the conceptOperationOutput
        restConceptOperationOutputMockMvc.perform(get("/api/_search/concept-operation-outputs?query=id:" + conceptOperationOutput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptOperationOutput.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptOperationOutput.class);
        ConceptOperationOutput conceptOperationOutput1 = new ConceptOperationOutput();
        conceptOperationOutput1.setId(1L);
        ConceptOperationOutput conceptOperationOutput2 = new ConceptOperationOutput();
        conceptOperationOutput2.setId(conceptOperationOutput1.getId());
        assertThat(conceptOperationOutput1).isEqualTo(conceptOperationOutput2);
        conceptOperationOutput2.setId(2L);
        assertThat(conceptOperationOutput1).isNotEqualTo(conceptOperationOutput2);
        conceptOperationOutput1.setId(null);
        assertThat(conceptOperationOutput1).isNotEqualTo(conceptOperationOutput2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptOperationOutputDTO.class);
        ConceptOperationOutputDTO conceptOperationOutputDTO1 = new ConceptOperationOutputDTO();
        conceptOperationOutputDTO1.setId(1L);
        ConceptOperationOutputDTO conceptOperationOutputDTO2 = new ConceptOperationOutputDTO();
        assertThat(conceptOperationOutputDTO1).isNotEqualTo(conceptOperationOutputDTO2);
        conceptOperationOutputDTO2.setId(conceptOperationOutputDTO1.getId());
        assertThat(conceptOperationOutputDTO1).isEqualTo(conceptOperationOutputDTO2);
        conceptOperationOutputDTO2.setId(2L);
        assertThat(conceptOperationOutputDTO1).isNotEqualTo(conceptOperationOutputDTO2);
        conceptOperationOutputDTO1.setId(null);
        assertThat(conceptOperationOutputDTO1).isNotEqualTo(conceptOperationOutputDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conceptOperationOutputMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conceptOperationOutputMapper.fromId(null)).isNull();
    }
}
