package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.CuentaProveedor;
import pe.com.iquitos.app.repository.CuentaProveedorRepository;
import pe.com.iquitos.app.repository.search.CuentaProveedorSearchRepository;
import pe.com.iquitos.app.service.CuentaProveedorService;
import pe.com.iquitos.app.service.dto.CuentaProveedorDTO;
import pe.com.iquitos.app.service.mapper.CuentaProveedorMapper;
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

import pe.com.iquitos.app.domain.enumeration.AccountTypeProvider;
/**
 * Test class for the CuentaProveedorResource REST controller.
 *
 * @see CuentaProveedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class CuentaProveedorResourceIntTest {

    private static final AccountTypeProvider DEFAULT_TIPO_CUENTA = AccountTypeProvider.CUENTA_CORRIENTE;
    private static final AccountTypeProvider UPDATED_TIPO_CUENTA = AccountTypeProvider.CUENTA_RECAUDADORA;

    private static final String DEFAULT_BANCO = "AAAAAAAAAA";
    private static final String UPDATED_BANCO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CUENTA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CUENTA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_DE_CUENTA = 1;
    private static final Integer UPDATED_NUMERO_DE_CUENTA = 2;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CuentaProveedorRepository cuentaProveedorRepository;

    @Autowired
    private CuentaProveedorMapper cuentaProveedorMapper;

    @Autowired
    private CuentaProveedorService cuentaProveedorService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.CuentaProveedorSearchRepositoryMockConfiguration
     */
    @Autowired
    private CuentaProveedorSearchRepository mockCuentaProveedorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCuentaProveedorMockMvc;

    private CuentaProveedor cuentaProveedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CuentaProveedorResource cuentaProveedorResource = new CuentaProveedorResource(cuentaProveedorService);
        this.restCuentaProveedorMockMvc = MockMvcBuilders.standaloneSetup(cuentaProveedorResource)
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
    public static CuentaProveedor createEntity(EntityManager em) {
        CuentaProveedor cuentaProveedor = new CuentaProveedor()
            .tipoCuenta(DEFAULT_TIPO_CUENTA)
            .banco(DEFAULT_BANCO)
            .nombreCuenta(DEFAULT_NOMBRE_CUENTA)
            .numeroDeCuenta(DEFAULT_NUMERO_DE_CUENTA)
            .fecha(DEFAULT_FECHA);
        return cuentaProveedor;
    }

    @Before
    public void initTest() {
        cuentaProveedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createCuentaProveedor() throws Exception {
        int databaseSizeBeforeCreate = cuentaProveedorRepository.findAll().size();

        // Create the CuentaProveedor
        CuentaProveedorDTO cuentaProveedorDTO = cuentaProveedorMapper.toDto(cuentaProveedor);
        restCuentaProveedorMockMvc.perform(post("/api/cuenta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaProveedorDTO)))
            .andExpect(status().isCreated());

        // Validate the CuentaProveedor in the database
        List<CuentaProveedor> cuentaProveedorList = cuentaProveedorRepository.findAll();
        assertThat(cuentaProveedorList).hasSize(databaseSizeBeforeCreate + 1);
        CuentaProveedor testCuentaProveedor = cuentaProveedorList.get(cuentaProveedorList.size() - 1);
        assertThat(testCuentaProveedor.getTipoCuenta()).isEqualTo(DEFAULT_TIPO_CUENTA);
        assertThat(testCuentaProveedor.getBanco()).isEqualTo(DEFAULT_BANCO);
        assertThat(testCuentaProveedor.getNombreCuenta()).isEqualTo(DEFAULT_NOMBRE_CUENTA);
        assertThat(testCuentaProveedor.getNumeroDeCuenta()).isEqualTo(DEFAULT_NUMERO_DE_CUENTA);
        assertThat(testCuentaProveedor.getFecha()).isEqualTo(DEFAULT_FECHA);

        // Validate the CuentaProveedor in Elasticsearch
        verify(mockCuentaProveedorSearchRepository, times(1)).save(testCuentaProveedor);
    }

    @Test
    @Transactional
    public void createCuentaProveedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cuentaProveedorRepository.findAll().size();

        // Create the CuentaProveedor with an existing ID
        cuentaProveedor.setId(1L);
        CuentaProveedorDTO cuentaProveedorDTO = cuentaProveedorMapper.toDto(cuentaProveedor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentaProveedorMockMvc.perform(post("/api/cuenta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CuentaProveedor in the database
        List<CuentaProveedor> cuentaProveedorList = cuentaProveedorRepository.findAll();
        assertThat(cuentaProveedorList).hasSize(databaseSizeBeforeCreate);

        // Validate the CuentaProveedor in Elasticsearch
        verify(mockCuentaProveedorSearchRepository, times(0)).save(cuentaProveedor);
    }

    @Test
    @Transactional
    public void checkBancoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaProveedorRepository.findAll().size();
        // set the field null
        cuentaProveedor.setBanco(null);

        // Create the CuentaProveedor, which fails.
        CuentaProveedorDTO cuentaProveedorDTO = cuentaProveedorMapper.toDto(cuentaProveedor);

        restCuentaProveedorMockMvc.perform(post("/api/cuenta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaProveedorDTO)))
            .andExpect(status().isBadRequest());

        List<CuentaProveedor> cuentaProveedorList = cuentaProveedorRepository.findAll();
        assertThat(cuentaProveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreCuentaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cuentaProveedorRepository.findAll().size();
        // set the field null
        cuentaProveedor.setNombreCuenta(null);

        // Create the CuentaProveedor, which fails.
        CuentaProveedorDTO cuentaProveedorDTO = cuentaProveedorMapper.toDto(cuentaProveedor);

        restCuentaProveedorMockMvc.perform(post("/api/cuenta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaProveedorDTO)))
            .andExpect(status().isBadRequest());

        List<CuentaProveedor> cuentaProveedorList = cuentaProveedorRepository.findAll();
        assertThat(cuentaProveedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCuentaProveedors() throws Exception {
        // Initialize the database
        cuentaProveedorRepository.saveAndFlush(cuentaProveedor);

        // Get all the cuentaProveedorList
        restCuentaProveedorMockMvc.perform(get("/api/cuenta-proveedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuentaProveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoCuenta").value(hasItem(DEFAULT_TIPO_CUENTA.toString())))
            .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO.toString())))
            .andExpect(jsonPath("$.[*].nombreCuenta").value(hasItem(DEFAULT_NOMBRE_CUENTA.toString())))
            .andExpect(jsonPath("$.[*].numeroDeCuenta").value(hasItem(DEFAULT_NUMERO_DE_CUENTA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getCuentaProveedor() throws Exception {
        // Initialize the database
        cuentaProveedorRepository.saveAndFlush(cuentaProveedor);

        // Get the cuentaProveedor
        restCuentaProveedorMockMvc.perform(get("/api/cuenta-proveedors/{id}", cuentaProveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cuentaProveedor.getId().intValue()))
            .andExpect(jsonPath("$.tipoCuenta").value(DEFAULT_TIPO_CUENTA.toString()))
            .andExpect(jsonPath("$.banco").value(DEFAULT_BANCO.toString()))
            .andExpect(jsonPath("$.nombreCuenta").value(DEFAULT_NOMBRE_CUENTA.toString()))
            .andExpect(jsonPath("$.numeroDeCuenta").value(DEFAULT_NUMERO_DE_CUENTA))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCuentaProveedor() throws Exception {
        // Get the cuentaProveedor
        restCuentaProveedorMockMvc.perform(get("/api/cuenta-proveedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCuentaProveedor() throws Exception {
        // Initialize the database
        cuentaProveedorRepository.saveAndFlush(cuentaProveedor);

        int databaseSizeBeforeUpdate = cuentaProveedorRepository.findAll().size();

        // Update the cuentaProveedor
        CuentaProveedor updatedCuentaProveedor = cuentaProveedorRepository.findById(cuentaProveedor.getId()).get();
        // Disconnect from session so that the updates on updatedCuentaProveedor are not directly saved in db
        em.detach(updatedCuentaProveedor);
        updatedCuentaProveedor
            .tipoCuenta(UPDATED_TIPO_CUENTA)
            .banco(UPDATED_BANCO)
            .nombreCuenta(UPDATED_NOMBRE_CUENTA)
            .numeroDeCuenta(UPDATED_NUMERO_DE_CUENTA)
            .fecha(UPDATED_FECHA);
        CuentaProveedorDTO cuentaProveedorDTO = cuentaProveedorMapper.toDto(updatedCuentaProveedor);

        restCuentaProveedorMockMvc.perform(put("/api/cuenta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaProveedorDTO)))
            .andExpect(status().isOk());

        // Validate the CuentaProveedor in the database
        List<CuentaProveedor> cuentaProveedorList = cuentaProveedorRepository.findAll();
        assertThat(cuentaProveedorList).hasSize(databaseSizeBeforeUpdate);
        CuentaProveedor testCuentaProveedor = cuentaProveedorList.get(cuentaProveedorList.size() - 1);
        assertThat(testCuentaProveedor.getTipoCuenta()).isEqualTo(UPDATED_TIPO_CUENTA);
        assertThat(testCuentaProveedor.getBanco()).isEqualTo(UPDATED_BANCO);
        assertThat(testCuentaProveedor.getNombreCuenta()).isEqualTo(UPDATED_NOMBRE_CUENTA);
        assertThat(testCuentaProveedor.getNumeroDeCuenta()).isEqualTo(UPDATED_NUMERO_DE_CUENTA);
        assertThat(testCuentaProveedor.getFecha()).isEqualTo(UPDATED_FECHA);

        // Validate the CuentaProveedor in Elasticsearch
        verify(mockCuentaProveedorSearchRepository, times(1)).save(testCuentaProveedor);
    }

    @Test
    @Transactional
    public void updateNonExistingCuentaProveedor() throws Exception {
        int databaseSizeBeforeUpdate = cuentaProveedorRepository.findAll().size();

        // Create the CuentaProveedor
        CuentaProveedorDTO cuentaProveedorDTO = cuentaProveedorMapper.toDto(cuentaProveedor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentaProveedorMockMvc.perform(put("/api/cuenta-proveedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cuentaProveedorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CuentaProveedor in the database
        List<CuentaProveedor> cuentaProveedorList = cuentaProveedorRepository.findAll();
        assertThat(cuentaProveedorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CuentaProveedor in Elasticsearch
        verify(mockCuentaProveedorSearchRepository, times(0)).save(cuentaProveedor);
    }

    @Test
    @Transactional
    public void deleteCuentaProveedor() throws Exception {
        // Initialize the database
        cuentaProveedorRepository.saveAndFlush(cuentaProveedor);

        int databaseSizeBeforeDelete = cuentaProveedorRepository.findAll().size();

        // Get the cuentaProveedor
        restCuentaProveedorMockMvc.perform(delete("/api/cuenta-proveedors/{id}", cuentaProveedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CuentaProveedor> cuentaProveedorList = cuentaProveedorRepository.findAll();
        assertThat(cuentaProveedorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CuentaProveedor in Elasticsearch
        verify(mockCuentaProveedorSearchRepository, times(1)).deleteById(cuentaProveedor.getId());
    }

    @Test
    @Transactional
    public void searchCuentaProveedor() throws Exception {
        // Initialize the database
        cuentaProveedorRepository.saveAndFlush(cuentaProveedor);
        when(mockCuentaProveedorSearchRepository.search(queryStringQuery("id:" + cuentaProveedor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cuentaProveedor), PageRequest.of(0, 1), 1));
        // Search the cuentaProveedor
        restCuentaProveedorMockMvc.perform(get("/api/_search/cuenta-proveedors?query=id:" + cuentaProveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuentaProveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoCuenta").value(hasItem(DEFAULT_TIPO_CUENTA.toString())))
            .andExpect(jsonPath("$.[*].banco").value(hasItem(DEFAULT_BANCO)))
            .andExpect(jsonPath("$.[*].nombreCuenta").value(hasItem(DEFAULT_NOMBRE_CUENTA)))
            .andExpect(jsonPath("$.[*].numeroDeCuenta").value(hasItem(DEFAULT_NUMERO_DE_CUENTA)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuentaProveedor.class);
        CuentaProveedor cuentaProveedor1 = new CuentaProveedor();
        cuentaProveedor1.setId(1L);
        CuentaProveedor cuentaProveedor2 = new CuentaProveedor();
        cuentaProveedor2.setId(cuentaProveedor1.getId());
        assertThat(cuentaProveedor1).isEqualTo(cuentaProveedor2);
        cuentaProveedor2.setId(2L);
        assertThat(cuentaProveedor1).isNotEqualTo(cuentaProveedor2);
        cuentaProveedor1.setId(null);
        assertThat(cuentaProveedor1).isNotEqualTo(cuentaProveedor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuentaProveedorDTO.class);
        CuentaProveedorDTO cuentaProveedorDTO1 = new CuentaProveedorDTO();
        cuentaProveedorDTO1.setId(1L);
        CuentaProveedorDTO cuentaProveedorDTO2 = new CuentaProveedorDTO();
        assertThat(cuentaProveedorDTO1).isNotEqualTo(cuentaProveedorDTO2);
        cuentaProveedorDTO2.setId(cuentaProveedorDTO1.getId());
        assertThat(cuentaProveedorDTO1).isEqualTo(cuentaProveedorDTO2);
        cuentaProveedorDTO2.setId(2L);
        assertThat(cuentaProveedorDTO1).isNotEqualTo(cuentaProveedorDTO2);
        cuentaProveedorDTO1.setId(null);
        assertThat(cuentaProveedorDTO1).isNotEqualTo(cuentaProveedorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cuentaProveedorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cuentaProveedorMapper.fromId(null)).isNull();
    }
}
