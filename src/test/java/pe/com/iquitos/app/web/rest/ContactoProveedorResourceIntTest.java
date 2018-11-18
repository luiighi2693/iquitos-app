package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ContactoProveedor;
import pe.com.iquitos.app.repository.ContactoProveedorRepository;
import pe.com.iquitos.app.repository.search.ContactoProveedorSearchRepository;
import pe.com.iquitos.app.service.ContactoProveedorService;
import pe.com.iquitos.app.service.dto.ContactoProveedorDTO;
import pe.com.iquitos.app.service.mapper.ContactoProveedorMapper;
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
 * Test class for the ContactoProveedorResource REST controller.
 *
 * @see ContactoProveedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ContactoProveedorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEFONO = 1;
    private static final Integer UPDATED_TELEFONO = 2;

    @Autowired
    private ContactoProveedorRepository contactoProveedorRepository;

    @Autowired
    private ContactoProveedorMapper contactoProveedorMapper;

    @Autowired
    private ContactoProveedorService contactoProveedorService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ContactoProveedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private ContactoProveedorSearchRepository mockContactoProveedorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContactoProveedorMockMvc;

    private ContactoProveedor contactoProveedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactoProveedorResource contactoProveedorResource = new ContactoProveedorResource(contactoProveedorService);
        this.restContactoProveedorMockMvc = MockMvcBuilders.standaloneSetup(contactoProveedorResource)
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
    public static ContactoProveedor createEntity(EntityManager em) {
        ContactoProveedor contactoProveedor = new ContactoProveedor()
            .nombre(DEFAULT_NOMBRE)
            .cargo(DEFAULT_CARGO)
            .producto(DEFAULT_PRODUCTO)
            .telefono(DEFAULT_TELEFONO);
        return contactoProveedor;
    }

    @Before
    public void initTest() {
        contactoProveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactoProveedor() throws Exception {
        int databaseSizeBeforeCreate = contactoProveedorRepository.findAll().size();

        // Create the ContactoProveedor
        ContactoProveedorDTO contactoProveedorDTO = contactoProveedorMapper.toDto(contactoProveedor);
        restContactoProveedorMockMvc.perform(post("/api/contacto-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the ContactoProveedor in the database
        List<ContactoProveedor> contactoProveedorList = contactoProveedorRepository.findAll();
        assertThat(contactoProveedorList).hasSize(databaseSizeBeforeCreate + 1);
        ContactoProveedor testContactoProveedor = contactoProveedorList.get(contactoProveedorList.size() - 1);
        assertThat(testContactoProveedor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContactoProveedor.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testContactoProveedor.getProducto()).isEqualTo(DEFAULT_PRODUCTO);
        assertThat(testContactoProveedor.getTelefono()).isEqualTo(DEFAULT_TELEFONO);

        // Validate the ContactoProveedor in Elasticsearch
        verify(mockContactoProveedorSearchRepository, times(1)).save(testContactoProveedor);
    }

    @Test
    @Transactional
    public void createContactoProveedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactoProveedorRepository.findAll().size();

        // Create the ContactoProveedor with an existing ID
        contactoProveedor.setId(1L);
        ContactoProveedorDTO contactoProveedorDTO = contactoProveedorMapper.toDto(contactoProveedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactoProveedorMockMvc.perform(post("/api/contacto-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoProveedor in the database
        List<ContactoProveedor> contactoProveedorList = contactoProveedorRepository.findAll();
        assertThat(contactoProveedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the ContactoProveedor in Elasticsearch
        verify(mockContactoProveedorSearchRepository, times(0)).save(contactoProveedor);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoProveedorRepository.findAll().size();
        // set the field null
        contactoProveedor.setNombre(null);

        // Create the ContactoProveedor, which fails.
        ContactoProveedorDTO contactoProveedorDTO = contactoProveedorMapper.toDto(contactoProveedor);

        restContactoProveedorMockMvc.perform(post("/api/contacto-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProveedorDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoProveedor> contactoProveedorList = contactoProveedorRepository.findAll();
        assertThat(contactoProveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCargoIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactoProveedorRepository.findAll().size();
        // set the field null
        contactoProveedor.setCargo(null);

        // Create the ContactoProveedor, which fails.
        ContactoProveedorDTO contactoProveedorDTO = contactoProveedorMapper.toDto(contactoProveedor);

        restContactoProveedorMockMvc.perform(post("/api/contacto-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProveedorDTO)))
            .andExpect(status().isBadRequest());

        List<ContactoProveedor> contactoProveedorList = contactoProveedorRepository.findAll();
        assertThat(contactoProveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactoProveedors() throws Exception {
        // Initialize the database
        contactoProveedorRepository.saveAndFlush(contactoProveedor);

        // Get all the contactoProveedorList
        restContactoProveedorMockMvc.perform(get("/api/contacto-proveedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoProveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO.toString())))
            .andExpect(jsonPath("$.[*].producto").value(hasItem(DEFAULT_PRODUCTO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));
    }
    
    @Test
    @Transactional
    public void getContactoProveedor() throws Exception {
        // Initialize the database
        contactoProveedorRepository.saveAndFlush(contactoProveedor);

        // Get the contactoProveedor
        restContactoProveedorMockMvc.perform(get("/api/contacto-proveedors/{id}", contactoProveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactoProveedor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO.toString()))
            .andExpect(jsonPath("$.producto").value(DEFAULT_PRODUCTO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO));
    }

    @Test
    @Transactional
    public void getNonExistingContactoProveedor() throws Exception {
        // Get the contactoProveedor
        restContactoProveedorMockMvc.perform(get("/api/contacto-proveedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactoProveedor() throws Exception {
        // Initialize the database
        contactoProveedorRepository.saveAndFlush(contactoProveedor);

        int databaseSizeBeforeUpdate = contactoProveedorRepository.findAll().size();

        // Update the contactoProveedor
        ContactoProveedor updatedContactoProveedor = contactoProveedorRepository.findById(contactoProveedor.getId()).get();
        // Disconnect from session so that the updates on updatedContactoProveedor are not directly saved in db
        em.detach(updatedContactoProveedor);
        updatedContactoProveedor
            .nombre(UPDATED_NOMBRE)
            .cargo(UPDATED_CARGO)
            .producto(UPDATED_PRODUCTO)
            .telefono(UPDATED_TELEFONO);
        ContactoProveedorDTO contactoProveedorDTO = contactoProveedorMapper.toDto(updatedContactoProveedor);

        restContactoProveedorMockMvc.perform(put("/api/contacto-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProveedorDTO)))
            .andExpect(status().isOk());

        // Validate the ContactoProveedor in the database
        List<ContactoProveedor> contactoProveedorList = contactoProveedorRepository.findAll();
        assertThat(contactoProveedorList).hasSize(databaseSizeBeforeUpdate);
        ContactoProveedor testContactoProveedor = contactoProveedorList.get(contactoProveedorList.size() - 1);
        assertThat(testContactoProveedor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContactoProveedor.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testContactoProveedor.getProducto()).isEqualTo(UPDATED_PRODUCTO);
        assertThat(testContactoProveedor.getTelefono()).isEqualTo(UPDATED_TELEFONO);

        // Validate the ContactoProveedor in Elasticsearch
        verify(mockContactoProveedorSearchRepository, times(1)).save(testContactoProveedor);
    }

    @Test
    @Transactional
    public void updateNonExistingContactoProveedor() throws Exception {
        int databaseSizeBeforeUpdate = contactoProveedorRepository.findAll().size();

        // Create the ContactoProveedor
        ContactoProveedorDTO contactoProveedorDTO = contactoProveedorMapper.toDto(contactoProveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactoProveedorMockMvc.perform(put("/api/contacto-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactoProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContactoProveedor in the database
        List<ContactoProveedor> contactoProveedorList = contactoProveedorRepository.findAll();
        assertThat(contactoProveedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ContactoProveedor in Elasticsearch
        verify(mockContactoProveedorSearchRepository, times(0)).save(contactoProveedor);
    }

    @Test
    @Transactional
    public void deleteContactoProveedor() throws Exception {
        // Initialize the database
        contactoProveedorRepository.saveAndFlush(contactoProveedor);

        int databaseSizeBeforeDelete = contactoProveedorRepository.findAll().size();

        // Get the contactoProveedor
        restContactoProveedorMockMvc.perform(delete("/api/contacto-proveedors/{id}", contactoProveedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactoProveedor> contactoProveedorList = contactoProveedorRepository.findAll();
        assertThat(contactoProveedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ContactoProveedor in Elasticsearch
        verify(mockContactoProveedorSearchRepository, times(1)).deleteById(contactoProveedor.getId());
    }

    @Test
    @Transactional
    public void searchContactoProveedor() throws Exception {
        // Initialize the database
        contactoProveedorRepository.saveAndFlush(contactoProveedor);
        when(mockContactoProveedorSearchRepository.search(queryStringQuery("id:" + contactoProveedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(contactoProveedor), PageRequest.of(0, 1), 1));
        // Search the contactoProveedor
        restContactoProveedorMockMvc.perform(get("/api/_search/contacto-proveedors?query=id:" + contactoProveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactoProveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].producto").value(hasItem(DEFAULT_PRODUCTO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoProveedor.class);
        ContactoProveedor contactoProveedor1 = new ContactoProveedor();
        contactoProveedor1.setId(1L);
        ContactoProveedor contactoProveedor2 = new ContactoProveedor();
        contactoProveedor2.setId(contactoProveedor1.getId());
        assertThat(contactoProveedor1).isEqualTo(contactoProveedor2);
        contactoProveedor2.setId(2L);
        assertThat(contactoProveedor1).isNotEqualTo(contactoProveedor2);
        contactoProveedor1.setId(null);
        assertThat(contactoProveedor1).isNotEqualTo(contactoProveedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactoProveedorDTO.class);
        ContactoProveedorDTO contactoProveedorDTO1 = new ContactoProveedorDTO();
        contactoProveedorDTO1.setId(1L);
        ContactoProveedorDTO contactoProveedorDTO2 = new ContactoProveedorDTO();
        assertThat(contactoProveedorDTO1).isNotEqualTo(contactoProveedorDTO2);
        contactoProveedorDTO2.setId(contactoProveedorDTO1.getId());
        assertThat(contactoProveedorDTO1).isEqualTo(contactoProveedorDTO2);
        contactoProveedorDTO2.setId(2L);
        assertThat(contactoProveedorDTO1).isNotEqualTo(contactoProveedorDTO2);
        contactoProveedorDTO1.setId(null);
        assertThat(contactoProveedorDTO1).isNotEqualTo(contactoProveedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contactoProveedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contactoProveedorMapper.fromId(null)).isNull();
    }
}
