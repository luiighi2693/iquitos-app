package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Box;
import pe.com.iquitos.app.repository.BoxRepository;
import pe.com.iquitos.app.repository.search.BoxSearchRepository;
import pe.com.iquitos.app.service.BoxService;
import pe.com.iquitos.app.service.dto.BoxDTO;
import pe.com.iquitos.app.service.mapper.BoxMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Test class for the BoxResource REST controller.
 *
 * @see BoxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class BoxResourceIntTest {

    private static final Double DEFAULT_INIT_AMOUNT = 1D;
    private static final Double UPDATED_INIT_AMOUNT = 2D;

    private static final Double DEFAULT_ACTUAL_AMOUNT = 1D;
    private static final Double UPDATED_ACTUAL_AMOUNT = 2D;

    private static final LocalDate DEFAULT_INIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private BoxMapper boxMapper;
    
    @Autowired
    private BoxService boxService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.BoxSearchRepositoryMockConfiguration
     */
    @Autowired
    private BoxSearchRepository mockBoxSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBoxMockMvc;

    private Box box;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BoxResource boxResource = new BoxResource(boxService);
        this.restBoxMockMvc = MockMvcBuilders.standaloneSetup(boxResource)
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
    public static Box createEntity(EntityManager em) {
        Box box = new Box()
            .initAmount(DEFAULT_INIT_AMOUNT)
            .actualAmount(DEFAULT_ACTUAL_AMOUNT)
            .initDate(DEFAULT_INIT_DATE)
            .endDate(DEFAULT_END_DATE);
        return box;
    }

    @Before
    public void initTest() {
        box = createEntity(em);
    }

    @Test
    @Transactional
    public void createBox() throws Exception {
        int databaseSizeBeforeCreate = boxRepository.findAll().size();

        // Create the Box
        BoxDTO boxDTO = boxMapper.toDto(box);
        restBoxMockMvc.perform(post("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isCreated());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeCreate + 1);
        Box testBox = boxList.get(boxList.size() - 1);
        assertThat(testBox.getInitAmount()).isEqualTo(DEFAULT_INIT_AMOUNT);
        assertThat(testBox.getActualAmount()).isEqualTo(DEFAULT_ACTUAL_AMOUNT);
        assertThat(testBox.getInitDate()).isEqualTo(DEFAULT_INIT_DATE);
        assertThat(testBox.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the Box in Elasticsearch
        verify(mockBoxSearchRepository, times(1)).save(testBox);
    }

    @Test
    @Transactional
    public void createBoxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxRepository.findAll().size();

        // Create the Box with an existing ID
        box.setId(1L);
        BoxDTO boxDTO = boxMapper.toDto(box);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxMockMvc.perform(post("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeCreate);

        // Validate the Box in Elasticsearch
        verify(mockBoxSearchRepository, times(0)).save(box);
    }

    @Test
    @Transactional
    public void getAllBoxes() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        // Get all the boxList
        restBoxMockMvc.perform(get("/api/boxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(box.getId().intValue())))
            .andExpect(jsonPath("$.[*].initAmount").value(hasItem(DEFAULT_INIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getBox() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        // Get the box
        restBoxMockMvc.perform(get("/api/boxes/{id}", box.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(box.getId().intValue()))
            .andExpect(jsonPath("$.initAmount").value(DEFAULT_INIT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.actualAmount").value(DEFAULT_ACTUAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.initDate").value(DEFAULT_INIT_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBox() throws Exception {
        // Get the box
        restBoxMockMvc.perform(get("/api/boxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBox() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        int databaseSizeBeforeUpdate = boxRepository.findAll().size();

        // Update the box
        Box updatedBox = boxRepository.findById(box.getId()).get();
        // Disconnect from session so that the updates on updatedBox are not directly saved in db
        em.detach(updatedBox);
        updatedBox
            .initAmount(UPDATED_INIT_AMOUNT)
            .actualAmount(UPDATED_ACTUAL_AMOUNT)
            .initDate(UPDATED_INIT_DATE)
            .endDate(UPDATED_END_DATE);
        BoxDTO boxDTO = boxMapper.toDto(updatedBox);

        restBoxMockMvc.perform(put("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isOk());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeUpdate);
        Box testBox = boxList.get(boxList.size() - 1);
        assertThat(testBox.getInitAmount()).isEqualTo(UPDATED_INIT_AMOUNT);
        assertThat(testBox.getActualAmount()).isEqualTo(UPDATED_ACTUAL_AMOUNT);
        assertThat(testBox.getInitDate()).isEqualTo(UPDATED_INIT_DATE);
        assertThat(testBox.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the Box in Elasticsearch
        verify(mockBoxSearchRepository, times(1)).save(testBox);
    }

    @Test
    @Transactional
    public void updateNonExistingBox() throws Exception {
        int databaseSizeBeforeUpdate = boxRepository.findAll().size();

        // Create the Box
        BoxDTO boxDTO = boxMapper.toDto(box);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoxMockMvc.perform(put("/api/boxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Box in Elasticsearch
        verify(mockBoxSearchRepository, times(0)).save(box);
    }

    @Test
    @Transactional
    public void deleteBox() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        int databaseSizeBeforeDelete = boxRepository.findAll().size();

        // Get the box
        restBoxMockMvc.perform(delete("/api/boxes/{id}", box.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Box in Elasticsearch
        verify(mockBoxSearchRepository, times(1)).deleteById(box.getId());
    }

    @Test
    @Transactional
    public void searchBox() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);
        when(mockBoxSearchRepository.search(queryStringQuery("id:" + box.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(box), PageRequest.of(0, 1), 1));
        // Search the box
        restBoxMockMvc.perform(get("/api/_search/boxes?query=id:" + box.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(box.getId().intValue())))
            .andExpect(jsonPath("$.[*].initAmount").value(hasItem(DEFAULT_INIT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Box.class);
        Box box1 = new Box();
        box1.setId(1L);
        Box box2 = new Box();
        box2.setId(box1.getId());
        assertThat(box1).isEqualTo(box2);
        box2.setId(2L);
        assertThat(box1).isNotEqualTo(box2);
        box1.setId(null);
        assertThat(box1).isNotEqualTo(box2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BoxDTO.class);
        BoxDTO boxDTO1 = new BoxDTO();
        boxDTO1.setId(1L);
        BoxDTO boxDTO2 = new BoxDTO();
        assertThat(boxDTO1).isNotEqualTo(boxDTO2);
        boxDTO2.setId(boxDTO1.getId());
        assertThat(boxDTO1).isEqualTo(boxDTO2);
        boxDTO2.setId(2L);
        assertThat(boxDTO1).isNotEqualTo(boxDTO2);
        boxDTO1.setId(null);
        assertThat(boxDTO1).isNotEqualTo(boxDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(boxMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(boxMapper.fromId(null)).isNull();
    }
}
