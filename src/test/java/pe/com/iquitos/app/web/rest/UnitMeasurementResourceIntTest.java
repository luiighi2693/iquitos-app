package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.UnitMeasurement;
import pe.com.iquitos.app.repository.UnitMeasurementRepository;
import pe.com.iquitos.app.repository.search.UnitMeasurementSearchRepository;
import pe.com.iquitos.app.service.UnitMeasurementService;
import pe.com.iquitos.app.service.dto.UnitMeasurementDTO;
import pe.com.iquitos.app.service.mapper.UnitMeasurementMapper;
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
 * Test class for the UnitMeasurementResource REST controller.
 *
 * @see UnitMeasurementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class UnitMeasurementResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_META_DATA = "AAAAAAAAAA";
    private static final String UPDATED_META_DATA = "BBBBBBBBBB";

    @Autowired
    private UnitMeasurementRepository unitMeasurementRepository;

    @Autowired
    private UnitMeasurementMapper unitMeasurementMapper;
    
    @Autowired
    private UnitMeasurementService unitMeasurementService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.UnitMeasurementSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnitMeasurementSearchRepository mockUnitMeasurementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnitMeasurementMockMvc;

    private UnitMeasurement unitMeasurement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitMeasurementResource unitMeasurementResource = new UnitMeasurementResource(unitMeasurementService);
        this.restUnitMeasurementMockMvc = MockMvcBuilders.standaloneSetup(unitMeasurementResource)
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
    public static UnitMeasurement createEntity(EntityManager em) {
        UnitMeasurement unitMeasurement = new UnitMeasurement()
            .value(DEFAULT_VALUE)
            .metaData(DEFAULT_META_DATA);
        return unitMeasurement;
    }

    @Before
    public void initTest() {
        unitMeasurement = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitMeasurement() throws Exception {
        int databaseSizeBeforeCreate = unitMeasurementRepository.findAll().size();

        // Create the UnitMeasurement
        UnitMeasurementDTO unitMeasurementDTO = unitMeasurementMapper.toDto(unitMeasurement);
        restUnitMeasurementMockMvc.perform(post("/api/unit-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasurementDTO)))
            .andExpect(status().isCreated());

        // Validate the UnitMeasurement in the database
        List<UnitMeasurement> unitMeasurementList = unitMeasurementRepository.findAll();
        assertThat(unitMeasurementList).hasSize(databaseSizeBeforeCreate + 1);
        UnitMeasurement testUnitMeasurement = unitMeasurementList.get(unitMeasurementList.size() - 1);
        assertThat(testUnitMeasurement.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testUnitMeasurement.getMetaData()).isEqualTo(DEFAULT_META_DATA);

        // Validate the UnitMeasurement in Elasticsearch
        verify(mockUnitMeasurementSearchRepository, times(1)).save(testUnitMeasurement);
    }

    @Test
    @Transactional
    public void createUnitMeasurementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitMeasurementRepository.findAll().size();

        // Create the UnitMeasurement with an existing ID
        unitMeasurement.setId(1L);
        UnitMeasurementDTO unitMeasurementDTO = unitMeasurementMapper.toDto(unitMeasurement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMeasurementMockMvc.perform(post("/api/unit-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasurementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitMeasurement in the database
        List<UnitMeasurement> unitMeasurementList = unitMeasurementRepository.findAll();
        assertThat(unitMeasurementList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnitMeasurement in Elasticsearch
        verify(mockUnitMeasurementSearchRepository, times(0)).save(unitMeasurement);
    }

    @Test
    @Transactional
    public void getAllUnitMeasurements() throws Exception {
        // Initialize the database
        unitMeasurementRepository.saveAndFlush(unitMeasurement);

        // Get all the unitMeasurementList
        restUnitMeasurementMockMvc.perform(get("/api/unit-measurements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitMeasurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getUnitMeasurement() throws Exception {
        // Initialize the database
        unitMeasurementRepository.saveAndFlush(unitMeasurement);

        // Get the unitMeasurement
        restUnitMeasurementMockMvc.perform(get("/api/unit-measurements/{id}", unitMeasurement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unitMeasurement.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.metaData").value(DEFAULT_META_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnitMeasurement() throws Exception {
        // Get the unitMeasurement
        restUnitMeasurementMockMvc.perform(get("/api/unit-measurements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitMeasurement() throws Exception {
        // Initialize the database
        unitMeasurementRepository.saveAndFlush(unitMeasurement);

        int databaseSizeBeforeUpdate = unitMeasurementRepository.findAll().size();

        // Update the unitMeasurement
        UnitMeasurement updatedUnitMeasurement = unitMeasurementRepository.findById(unitMeasurement.getId()).get();
        // Disconnect from session so that the updates on updatedUnitMeasurement are not directly saved in db
        em.detach(updatedUnitMeasurement);
        updatedUnitMeasurement
            .value(UPDATED_VALUE)
            .metaData(UPDATED_META_DATA);
        UnitMeasurementDTO unitMeasurementDTO = unitMeasurementMapper.toDto(updatedUnitMeasurement);

        restUnitMeasurementMockMvc.perform(put("/api/unit-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasurementDTO)))
            .andExpect(status().isOk());

        // Validate the UnitMeasurement in the database
        List<UnitMeasurement> unitMeasurementList = unitMeasurementRepository.findAll();
        assertThat(unitMeasurementList).hasSize(databaseSizeBeforeUpdate);
        UnitMeasurement testUnitMeasurement = unitMeasurementList.get(unitMeasurementList.size() - 1);
        assertThat(testUnitMeasurement.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testUnitMeasurement.getMetaData()).isEqualTo(UPDATED_META_DATA);

        // Validate the UnitMeasurement in Elasticsearch
        verify(mockUnitMeasurementSearchRepository, times(1)).save(testUnitMeasurement);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitMeasurement() throws Exception {
        int databaseSizeBeforeUpdate = unitMeasurementRepository.findAll().size();

        // Create the UnitMeasurement
        UnitMeasurementDTO unitMeasurementDTO = unitMeasurementMapper.toDto(unitMeasurement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMeasurementMockMvc.perform(put("/api/unit-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasurementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitMeasurement in the database
        List<UnitMeasurement> unitMeasurementList = unitMeasurementRepository.findAll();
        assertThat(unitMeasurementList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnitMeasurement in Elasticsearch
        verify(mockUnitMeasurementSearchRepository, times(0)).save(unitMeasurement);
    }

    @Test
    @Transactional
    public void deleteUnitMeasurement() throws Exception {
        // Initialize the database
        unitMeasurementRepository.saveAndFlush(unitMeasurement);

        int databaseSizeBeforeDelete = unitMeasurementRepository.findAll().size();

        // Get the unitMeasurement
        restUnitMeasurementMockMvc.perform(delete("/api/unit-measurements/{id}", unitMeasurement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UnitMeasurement> unitMeasurementList = unitMeasurementRepository.findAll();
        assertThat(unitMeasurementList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnitMeasurement in Elasticsearch
        verify(mockUnitMeasurementSearchRepository, times(1)).deleteById(unitMeasurement.getId());
    }

    @Test
    @Transactional
    public void searchUnitMeasurement() throws Exception {
        // Initialize the database
        unitMeasurementRepository.saveAndFlush(unitMeasurement);
        when(mockUnitMeasurementSearchRepository.search(queryStringQuery("id:" + unitMeasurement.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unitMeasurement), PageRequest.of(0, 1), 1));
        // Search the unitMeasurement
        restUnitMeasurementMockMvc.perform(get("/api/_search/unit-measurements?query=id:" + unitMeasurement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitMeasurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].metaData").value(hasItem(DEFAULT_META_DATA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitMeasurement.class);
        UnitMeasurement unitMeasurement1 = new UnitMeasurement();
        unitMeasurement1.setId(1L);
        UnitMeasurement unitMeasurement2 = new UnitMeasurement();
        unitMeasurement2.setId(unitMeasurement1.getId());
        assertThat(unitMeasurement1).isEqualTo(unitMeasurement2);
        unitMeasurement2.setId(2L);
        assertThat(unitMeasurement1).isNotEqualTo(unitMeasurement2);
        unitMeasurement1.setId(null);
        assertThat(unitMeasurement1).isNotEqualTo(unitMeasurement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitMeasurementDTO.class);
        UnitMeasurementDTO unitMeasurementDTO1 = new UnitMeasurementDTO();
        unitMeasurementDTO1.setId(1L);
        UnitMeasurementDTO unitMeasurementDTO2 = new UnitMeasurementDTO();
        assertThat(unitMeasurementDTO1).isNotEqualTo(unitMeasurementDTO2);
        unitMeasurementDTO2.setId(unitMeasurementDTO1.getId());
        assertThat(unitMeasurementDTO1).isEqualTo(unitMeasurementDTO2);
        unitMeasurementDTO2.setId(2L);
        assertThat(unitMeasurementDTO1).isNotEqualTo(unitMeasurementDTO2);
        unitMeasurementDTO1.setId(null);
        assertThat(unitMeasurementDTO1).isNotEqualTo(unitMeasurementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unitMeasurementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unitMeasurementMapper.fromId(null)).isNull();
    }
}
