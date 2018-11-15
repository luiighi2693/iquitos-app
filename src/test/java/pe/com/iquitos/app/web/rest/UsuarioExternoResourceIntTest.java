package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.UsuarioExterno;
import pe.com.iquitos.app.repository.UsuarioExternoRepository;
import pe.com.iquitos.app.repository.search.UsuarioExternoSearchRepository;
import pe.com.iquitos.app.service.UsuarioExternoService;
import pe.com.iquitos.app.service.dto.UsuarioExternoDTO;
import pe.com.iquitos.app.service.mapper.UsuarioExternoMapper;
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
 * Test class for the UsuarioExternoResource REST controller.
 *
 * @see UsuarioExternoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class UsuarioExternoResourceIntTest {

    private static final Integer DEFAULT_DNI = 1;
    private static final Integer UPDATED_DNI = 2;

    private static final Integer DEFAULT_PIN = 1;
    private static final Integer UPDATED_PIN = 2;

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    @Autowired
    private UsuarioExternoRepository usuarioExternoRepository;

    @Autowired
    private UsuarioExternoMapper usuarioExternoMapper;

    @Autowired
    private UsuarioExternoService usuarioExternoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.UsuarioExternoSearchRepositoryMockConfiguration
     */
    @Autowired
    private UsuarioExternoSearchRepository mockUsuarioExternoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUsuarioExternoMockMvc;

    private UsuarioExterno usuarioExterno;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UsuarioExternoResource usuarioExternoResource = new UsuarioExternoResource(usuarioExternoService);
        this.restUsuarioExternoMockMvc = MockMvcBuilders.standaloneSetup(usuarioExternoResource)
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
    public static UsuarioExterno createEntity(EntityManager em) {
        UsuarioExterno usuarioExterno = new UsuarioExterno()
            .dni(DEFAULT_DNI)
            .pin(DEFAULT_PIN)
            .role(DEFAULT_ROLE);
        return usuarioExterno;
    }

    @Before
    public void initTest() {
        usuarioExterno = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarioExterno() throws Exception {
        int databaseSizeBeforeCreate = usuarioExternoRepository.findAll().size();

        // Create the UsuarioExterno
        UsuarioExternoDTO usuarioExternoDTO = usuarioExternoMapper.toDto(usuarioExterno);
        restUsuarioExternoMockMvc.perform(post("/api/usuario-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioExternoDTO)))
            .andExpect(status().isCreated());

        // Validate the UsuarioExterno in the database
        List<UsuarioExterno> usuarioExternoList = usuarioExternoRepository.findAll();
        assertThat(usuarioExternoList).hasSize(databaseSizeBeforeCreate + 1);
        UsuarioExterno testUsuarioExterno = usuarioExternoList.get(usuarioExternoList.size() - 1);
        assertThat(testUsuarioExterno.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testUsuarioExterno.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testUsuarioExterno.getRole()).isEqualTo(DEFAULT_ROLE);

        // Validate the UsuarioExterno in Elasticsearch
        verify(mockUsuarioExternoSearchRepository, times(1)).save(testUsuarioExterno);
    }

    @Test
    @Transactional
    public void createUsuarioExternoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuarioExternoRepository.findAll().size();

        // Create the UsuarioExterno with an existing ID
        usuarioExterno.setId(1L);
        UsuarioExternoDTO usuarioExternoDTO = usuarioExternoMapper.toDto(usuarioExterno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioExternoMockMvc.perform(post("/api/usuario-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioExternoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioExterno in the database
        List<UsuarioExterno> usuarioExternoList = usuarioExternoRepository.findAll();
        assertThat(usuarioExternoList).hasSize(databaseSizeBeforeCreate);

        // Validate the UsuarioExterno in Elasticsearch
        verify(mockUsuarioExternoSearchRepository, times(0)).save(usuarioExterno);
    }

    @Test
    @Transactional
    public void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioExternoRepository.findAll().size();
        // set the field null
        usuarioExterno.setDni(null);

        // Create the UsuarioExterno, which fails.
        UsuarioExternoDTO usuarioExternoDTO = usuarioExternoMapper.toDto(usuarioExterno);

        restUsuarioExternoMockMvc.perform(post("/api/usuario-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<UsuarioExterno> usuarioExternoList = usuarioExternoRepository.findAll();
        assertThat(usuarioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPinIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioExternoRepository.findAll().size();
        // set the field null
        usuarioExterno.setPin(null);

        // Create the UsuarioExterno, which fails.
        UsuarioExternoDTO usuarioExternoDTO = usuarioExternoMapper.toDto(usuarioExterno);

        restUsuarioExternoMockMvc.perform(post("/api/usuario-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioExternoDTO)))
            .andExpect(status().isBadRequest());

        List<UsuarioExterno> usuarioExternoList = usuarioExternoRepository.findAll();
        assertThat(usuarioExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarioExternos() throws Exception {
        // Initialize the database
        usuarioExternoRepository.saveAndFlush(usuarioExterno);

        // Get all the usuarioExternoList
        restUsuarioExternoMockMvc.perform(get("/api/usuario-externos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    
    @Test
    @Transactional
    public void getUsuarioExterno() throws Exception {
        // Initialize the database
        usuarioExternoRepository.saveAndFlush(usuarioExterno);

        // Get the usuarioExterno
        restUsuarioExternoMockMvc.perform(get("/api/usuario-externos/{id}", usuarioExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(usuarioExterno.getId().intValue()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUsuarioExterno() throws Exception {
        // Get the usuarioExterno
        restUsuarioExternoMockMvc.perform(get("/api/usuario-externos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarioExterno() throws Exception {
        // Initialize the database
        usuarioExternoRepository.saveAndFlush(usuarioExterno);

        int databaseSizeBeforeUpdate = usuarioExternoRepository.findAll().size();

        // Update the usuarioExterno
        UsuarioExterno updatedUsuarioExterno = usuarioExternoRepository.findById(usuarioExterno.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarioExterno are not directly saved in db
        em.detach(updatedUsuarioExterno);
        updatedUsuarioExterno
            .dni(UPDATED_DNI)
            .pin(UPDATED_PIN)
            .role(UPDATED_ROLE);
        UsuarioExternoDTO usuarioExternoDTO = usuarioExternoMapper.toDto(updatedUsuarioExterno);

        restUsuarioExternoMockMvc.perform(put("/api/usuario-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioExternoDTO)))
            .andExpect(status().isOk());

        // Validate the UsuarioExterno in the database
        List<UsuarioExterno> usuarioExternoList = usuarioExternoRepository.findAll();
        assertThat(usuarioExternoList).hasSize(databaseSizeBeforeUpdate);
        UsuarioExterno testUsuarioExterno = usuarioExternoList.get(usuarioExternoList.size() - 1);
        assertThat(testUsuarioExterno.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testUsuarioExterno.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testUsuarioExterno.getRole()).isEqualTo(UPDATED_ROLE);

        // Validate the UsuarioExterno in Elasticsearch
        verify(mockUsuarioExternoSearchRepository, times(1)).save(testUsuarioExterno);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarioExterno() throws Exception {
        int databaseSizeBeforeUpdate = usuarioExternoRepository.findAll().size();

        // Create the UsuarioExterno
        UsuarioExternoDTO usuarioExternoDTO = usuarioExternoMapper.toDto(usuarioExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioExternoMockMvc.perform(put("/api/usuario-externos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(usuarioExternoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsuarioExterno in the database
        List<UsuarioExterno> usuarioExternoList = usuarioExternoRepository.findAll();
        assertThat(usuarioExternoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UsuarioExterno in Elasticsearch
        verify(mockUsuarioExternoSearchRepository, times(0)).save(usuarioExterno);
    }

    @Test
    @Transactional
    public void deleteUsuarioExterno() throws Exception {
        // Initialize the database
        usuarioExternoRepository.saveAndFlush(usuarioExterno);

        int databaseSizeBeforeDelete = usuarioExternoRepository.findAll().size();

        // Get the usuarioExterno
        restUsuarioExternoMockMvc.perform(delete("/api/usuario-externos/{id}", usuarioExterno.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UsuarioExterno> usuarioExternoList = usuarioExternoRepository.findAll();
        assertThat(usuarioExternoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UsuarioExterno in Elasticsearch
        verify(mockUsuarioExternoSearchRepository, times(1)).deleteById(usuarioExterno.getId());
    }

    @Test
    @Transactional
    public void searchUsuarioExterno() throws Exception {
        // Initialize the database
        usuarioExternoRepository.saveAndFlush(usuarioExterno);
        when(mockUsuarioExternoSearchRepository.search(queryStringQuery("id:" + usuarioExterno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(usuarioExterno), PageRequest.of(0, 1), 1));
        // Search the usuarioExterno
        restUsuarioExternoMockMvc.perform(get("/api/_search/usuario-externos?query=id:" + usuarioExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarioExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioExterno.class);
        UsuarioExterno usuarioExterno1 = new UsuarioExterno();
        usuarioExterno1.setId(1L);
        UsuarioExterno usuarioExterno2 = new UsuarioExterno();
        usuarioExterno2.setId(usuarioExterno1.getId());
        assertThat(usuarioExterno1).isEqualTo(usuarioExterno2);
        usuarioExterno2.setId(2L);
        assertThat(usuarioExterno1).isNotEqualTo(usuarioExterno2);
        usuarioExterno1.setId(null);
        assertThat(usuarioExterno1).isNotEqualTo(usuarioExterno2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioExternoDTO.class);
        UsuarioExternoDTO usuarioExternoDTO1 = new UsuarioExternoDTO();
        usuarioExternoDTO1.setId(1L);
        UsuarioExternoDTO usuarioExternoDTO2 = new UsuarioExternoDTO();
        assertThat(usuarioExternoDTO1).isNotEqualTo(usuarioExternoDTO2);
        usuarioExternoDTO2.setId(usuarioExternoDTO1.getId());
        assertThat(usuarioExternoDTO1).isEqualTo(usuarioExternoDTO2);
        usuarioExternoDTO2.setId(2L);
        assertThat(usuarioExternoDTO1).isNotEqualTo(usuarioExternoDTO2);
        usuarioExternoDTO1.setId(null);
        assertThat(usuarioExternoDTO1).isNotEqualTo(usuarioExternoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(usuarioExternoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(usuarioExternoMapper.fromId(null)).isNull();
    }
}
