package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ConceptOperationInput;
import pe.com.iquitos.app.repository.ConceptOperationInputRepository;
import pe.com.iquitos.app.repository.search.ConceptOperationInputSearchRepository;
import pe.com.iquitos.app.service.ConceptOperationInputService;
import pe.com.iquitos.app.service.dto.ConceptOperationInputDTO;
import pe.com.iquitos.app.service.mapper.ConceptOperationInputMapper;
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
 * Test class for the ConceptOperationInputResource REST controller.
 *
 * @see ConceptOperationInputResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ConceptOperationInputResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private ConceptOperationInputRepository conceptOperationInputRepository;

    @Autowired
    private ConceptOperationInputMapper conceptOperationInputMapper;
    
    @Autowired
    private ConceptOperationInputService conceptOperationInputService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ConceptOperationInputSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConceptOperationInputSearchRepository mockConceptOperationInputSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConceptOperationInputMockMvc;

    private ConceptOperationInput conceptOperationInput;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConceptOperationInputResource conceptOperationInputResource = new ConceptOperationInputResource(conceptOperationInputService);
        this.restConceptOperationInputMockMvc = MockMvcBuilders.standaloneSetup(conceptOperationInputResource)
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
    public static ConceptOperationInput createEntity(EntityManager em) {
        ConceptOperationInput conceptOperationInput = new ConceptOperationInput()
            .value(DEFAULT_VALUE)
            .metaData(DEFAULT_META_DATA);
        return conceptOperationInput;
    }

    @Before
    public void initTest() {
        conceptOperationInput = createEntity(em);
    }

    @Test
    @Transactional
    public void createConceptOperationInput() throws Exception {
        int databaseSizeBeforeCreate = conceptOperationInputRepository.findAll().size();

        // Create the ConceptOperationInput
        ConceptOperationInputDTO conceptOperationInputDTO = conceptOperationInputMapper.toDto(conceptOperationInput);
        restConceptOperationInputMockMvc.perform(post("/api/concept-operation-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationInputDTO)))
            .andExpect(status().isCreated());

        // Validate the ConceptOperationInput in the database
        List<ConceptOperationInput> conceptOperationInputList = conceptOperationInputRepository.findAll();
        assertThat(conceptOperationInputList).hasSize(databaseSizeBeforeCreate + 1);
        ConceptOperationInput testConceptOperationInput = conceptOperationInputList.get(conceptOperationInputList.size() - 1);
        assertThat(testConceptOperationInput.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testConceptOperationInput.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the ConceptOperationInput in Elasticsearch
        verify(mockConceptOperationInputSearchRepository, times(1)).save(testConceptOperationInput);
    }

    @Test
    @Transactional
    public void createConceptOperationInputWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conceptOperationInputRepository.findAll().size();

        // Create the ConceptOperationInput with an existing ID
        conceptOperationInput.setId(1L);
        ConceptOperationInputDTO conceptOperationInputDTO = conceptOperationInputMapper.toDto(conceptOperationInput);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConceptOperationInputMockMvc.perform(post("/api/concept-operation-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationInputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptOperationInput in the database
        List<ConceptOperationInput> conceptOperationInputList = conceptOperationInputRepository.findAll();
        assertThat(conceptOperationInputList).hasSize(databaseSizeBeforeCreate);

        // Validate the ConceptOperationInput in Elasticsearch
        verify(mockConceptOperationInputSearchRepository, times(0)).save(conceptOperationInput);
    }

    @Test
    @Transactional
    public void getAllConceptOperationInputs() throws Exception {
        // Initialize the database
        conceptOperationInputRepository.saveAndFlush(conceptOperationInput);

        // Get all the conceptOperationInputList
        restConceptOperationInputMockMvc.perform(get("/api/concept-operation-inputs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptOperationInput.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getConceptOperationInput() throws Exception {
        // Initialize the database
        conceptOperationInputRepository.saveAndFlush(conceptOperationInput);

        // Get the conceptOperationInput
        restConceptOperationInputMockMvc.perform(get("/api/concept-operation-inputs/{id}", conceptOperationInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conceptOperationInput.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConceptOperationInput() throws Exception {
        // Get the conceptOperationInput
        restConceptOperationInputMockMvc.perform(get("/api/concept-operation-inputs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConceptOperationInput() throws Exception {
        // Initialize the database
        conceptOperationInputRepository.saveAndFlush(conceptOperationInput);

        int databaseSizeBeforeUpdate = conceptOperationInputRepository.findAll().size();

        // Update the conceptOperationInput
        ConceptOperationInput updatedConceptOperationInput = conceptOperationInputRepository.findById(conceptOperationInput.getId()).get();
        // Disconnect from session so that the updates on updatedConceptOperationInput are not directly saved in db
        em.detach(updatedConceptOperationInput);
        updatedConceptOperationInput
            .value(UPDATED_VALUE)
            .metaData(UPDATED_META_DATA);
        ConceptOperationInputDTO conceptOperationInputDTO = conceptOperationInputMapper.toDto(updatedConceptOperationInput);

        restConceptOperationInputMockMvc.perform(put("/api/concept-operation-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationInputDTO)))
            .andExpect(status().isOk());

        // Validate the ConceptOperationInput in the database
        List<ConceptOperationInput> conceptOperationInputList = conceptOperationInputRepository.findAll();
        assertThat(conceptOperationInputList).hasSize(databaseSizeBeforeUpdate);
        ConceptOperationInput testConceptOperationInput = conceptOperationInputList.get(conceptOperationInputList.size() - 1);
        assertThat(testConceptOperationInput.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testConceptOperationInput.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the ConceptOperationInput in Elasticsearch
        verify(mockConceptOperationInputSearchRepository, times(1)).save(testConceptOperationInput);
    }

    @Test
    @Transactional
    public void updateNonExistingConceptOperationInput() throws Exception {
        int databaseSizeBeforeUpdate = conceptOperationInputRepository.findAll().size();

        // Create the ConceptOperationInput
        ConceptOperationInputDTO conceptOperationInputDTO = conceptOperationInputMapper.toDto(conceptOperationInput);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConceptOperationInputMockMvc.perform(put("/api/concept-operation-inputs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conceptOperationInputDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConceptOperationInput in the database
        List<ConceptOperationInput> conceptOperationInputList = conceptOperationInputRepository.findAll();
        assertThat(conceptOperationInputList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ConceptOperationInput in Elasticsearch
        verify(mockConceptOperationInputSearchRepository, times(0)).save(conceptOperationInput);
    }

    @Test
    @Transactional
    public void deleteConceptOperationInput() throws Exception {
        // Initialize the database
        conceptOperationInputRepository.saveAndFlush(conceptOperationInput);

        int databaseSizeBeforeDelete = conceptOperationInputRepository.findAll().size();

        // Get the conceptOperationInput
        restConceptOperationInputMockMvc.perform(delete("/api/concept-operation-inputs/{id}", conceptOperationInput.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConceptOperationInput> conceptOperationInputList = conceptOperationInputRepository.findAll();
        assertThat(conceptOperationInputList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ConceptOperationInput in Elasticsearch
        verify(mockConceptOperationInputSearchRepository, times(1)).deleteById(conceptOperationInput.getId());
    }

    @Test
    @Transactional
    public void searchConceptOperationInput() throws Exception {
        // Initialize the database
        conceptOperationInputRepository.saveAndFlush(conceptOperationInput);
        when(mockConceptOperationInputSearchRepository.search(queryStringQuery("id:" + conceptOperationInput.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(conceptOperationInput), PageRequest.of(0, 1), 1));
        // Search the conceptOperationInput
        restConceptOperationInputMockMvc.perform(get("/api/_search/concept-operation-inputs?query=id:" + conceptOperationInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conceptOperationInput.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptOperationInput.class);
        ConceptOperationInput conceptOperationInput1 = new ConceptOperationInput();
        conceptOperationInput1.setId(1L);
        ConceptOperationInput conceptOperationInput2 = new ConceptOperationInput();
        conceptOperationInput2.setId(conceptOperationInput1.getId());
        assertThat(conceptOperationInput1).isEqualTo(conceptOperationInput2);
        conceptOperationInput2.setId(2L);
        assertThat(conceptOperationInput1).isNotEqualTo(conceptOperationInput2);
        conceptOperationInput1.setId(null);
        assertThat(conceptOperationInput1).isNotEqualTo(conceptOperationInput2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConceptOperationInputDTO.class);
        ConceptOperationInputDTO conceptOperationInputDTO1 = new ConceptOperationInputDTO();
        conceptOperationInputDTO1.setId(1L);
        ConceptOperationInputDTO conceptOperationInputDTO2 = new ConceptOperationInputDTO();
        assertThat(conceptOperationInputDTO1).isNotEqualTo(conceptOperationInputDTO2);
        conceptOperationInputDTO2.setId(conceptOperationInputDTO1.getId());
        assertThat(conceptOperationInputDTO1).isEqualTo(conceptOperationInputDTO2);
        conceptOperationInputDTO2.setId(2L);
        assertThat(conceptOperationInputDTO1).isNotEqualTo(conceptOperationInputDTO2);
        conceptOperationInputDTO1.setId(null);
        assertThat(conceptOperationInputDTO1).isNotEqualTo(conceptOperationInputDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(conceptOperationInputMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(conceptOperationInputMapper.fromId(null)).isNull();
    }
}
