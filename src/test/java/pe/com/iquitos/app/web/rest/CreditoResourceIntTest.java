package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Credito;
import pe.com.iquitos.app.repository.CreditoRepository;
import pe.com.iquitos.app.repository.search.CreditoSearchRepository;
import pe.com.iquitos.app.service.CreditoService;
import pe.com.iquitos.app.service.dto.CreditoDTO;
import pe.com.iquitos.app.service.mapper.CreditoMapper;
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
 * Test class for the CreditoResource REST controller.
 *
 * @see CreditoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class CreditoResourceIntTest {

    private static final Double DEFAULT_MONTO = 1D;
    private static final Double UPDATED_MONTO = 2D;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MODO_DE_PAGO = 1D;
    private static final Double UPDATED_MODO_DE_PAGO = 2D;

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final Double DEFAULT_MONTO_TOTAL = 1D;
    private static final Double UPDATED_MONTO_TOTAL = 2D;

    private static final LocalDate DEFAULT_FECHA_LIMITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_LIMITE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NOTA_DE_CREDITO = "AAAAAAAAAA";
    private static final String UPDATED_NOTA_DE_CREDITO = "BBBBBBBBBB";

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private CreditoMapper creditoMapper;

    @Autowired
    private CreditoService creditoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.CreditoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CreditoSearchRepository mockCreditoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCreditoMockMvc;

    private Credito credito;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditoResource creditoResource = new CreditoResource(creditoService);
        this.restCreditoMockMvc = MockMvcBuilders.standaloneSetup(creditoResource)
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
    public static Credito createEntity(EntityManager em) {
        Credito credito = new Credito()
            .monto(DEFAULT_MONTO)
            .fecha(DEFAULT_FECHA)
            .modoDePago(DEFAULT_MODO_DE_PAGO)
            .numero(DEFAULT_NUMERO)
            .montoTotal(DEFAULT_MONTO_TOTAL)
            .fechaLimite(DEFAULT_FECHA_LIMITE)
            .notaDeCredito(DEFAULT_NOTA_DE_CREDITO);
        return credito;
    }

    @Before
    public void initTest() {
        credito = createEntity(em);
    }

    @Test
    @Transactional
    public void createCredito() throws Exception {
        int databaseSizeBeforeCreate = creditoRepository.findAll().size();

        // Create the Credito
        CreditoDTO creditoDTO = creditoMapper.toDto(credito);
        restCreditoMockMvc.perform(post("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isCreated());

        // Validate the Credito in the database
        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeCreate + 1);
        Credito testCredito = creditoList.get(creditoList.size() - 1);
        assertThat(testCredito.getMonto()).isEqualTo(DEFAULT_MONTO);
        assertThat(testCredito.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCredito.getModoDePago()).isEqualTo(DEFAULT_MODO_DE_PAGO);
        assertThat(testCredito.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCredito.getMontoTotal()).isEqualTo(DEFAULT_MONTO_TOTAL);
        assertThat(testCredito.getFechaLimite()).isEqualTo(DEFAULT_FECHA_LIMITE);
        assertThat(testCredito.getNotaDeCredito()).isEqualTo(DEFAULT_NOTA_DE_CREDITO);

        // Validate the Credito in Elasticsearch
        verify(mockCreditoSearchRepository, times(1)).save(testCredito);
    }

    @Test
    @Transactional
    public void createCreditoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditoRepository.findAll().size();

        // Create the Credito with an existing ID
        credito.setId(1L);
        CreditoDTO creditoDTO = creditoMapper.toDto(credito);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditoMockMvc.perform(post("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Credito in the database
        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Credito in Elasticsearch
        verify(mockCreditoSearchRepository, times(0)).save(credito);
    }

    @Test
    @Transactional
    public void checkMontoIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditoRepository.findAll().size();
        // set the field null
        credito.setMonto(null);

        // Create the Credito, which fails.
        CreditoDTO creditoDTO = creditoMapper.toDto(credito);

        restCreditoMockMvc.perform(post("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isBadRequest());

        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModoDePagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditoRepository.findAll().size();
        // set the field null
        credito.setModoDePago(null);

        // Create the Credito, which fails.
        CreditoDTO creditoDTO = creditoMapper.toDto(credito);

        restCreditoMockMvc.perform(post("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isBadRequest());

        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontoTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditoRepository.findAll().size();
        // set the field null
        credito.setMontoTotal(null);

        // Create the Credito, which fails.
        CreditoDTO creditoDTO = creditoMapper.toDto(credito);

        restCreditoMockMvc.perform(post("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isBadRequest());

        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotaDeCreditoIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditoRepository.findAll().size();
        // set the field null
        credito.setNotaDeCredito(null);

        // Create the Credito, which fails.
        CreditoDTO creditoDTO = creditoMapper.toDto(credito);

        restCreditoMockMvc.perform(post("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isBadRequest());

        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCreditos() throws Exception {
        // Initialize the database
        creditoRepository.saveAndFlush(credito);

        // Get all the creditoList
        restCreditoMockMvc.perform(get("/api/creditos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credito.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].modoDePago").value(hasItem(DEFAULT_MODO_DE_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].montoTotal").value(hasItem(DEFAULT_MONTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaLimite").value(hasItem(DEFAULT_FECHA_LIMITE.toString())))
            .andExpect(jsonPath("$.[*].notaDeCredito").value(hasItem(DEFAULT_NOTA_DE_CREDITO.toString())));
    }
    
    @Test
    @Transactional
    public void getCredito() throws Exception {
        // Initialize the database
        creditoRepository.saveAndFlush(credito);

        // Get the credito
        restCreditoMockMvc.perform(get("/api/creditos/{id}", credito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(credito.getId().intValue()))
            .andExpect(jsonPath("$.monto").value(DEFAULT_MONTO.doubleValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.modoDePago").value(DEFAULT_MODO_DE_PAGO.doubleValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.montoTotal").value(DEFAULT_MONTO_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.fechaLimite").value(DEFAULT_FECHA_LIMITE.toString()))
            .andExpect(jsonPath("$.notaDeCredito").value(DEFAULT_NOTA_DE_CREDITO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCredito() throws Exception {
        // Get the credito
        restCreditoMockMvc.perform(get("/api/creditos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCredito() throws Exception {
        // Initialize the database
        creditoRepository.saveAndFlush(credito);

        int databaseSizeBeforeUpdate = creditoRepository.findAll().size();

        // Update the credito
        Credito updatedCredito = creditoRepository.findById(credito.getId()).get();
        // Disconnect from session so that the updates on updatedCredito are not directly saved in db
        em.detach(updatedCredito);
        updatedCredito
            .monto(UPDATED_MONTO)
            .fecha(UPDATED_FECHA)
            .modoDePago(UPDATED_MODO_DE_PAGO)
            .numero(UPDATED_NUMERO)
            .montoTotal(UPDATED_MONTO_TOTAL)
            .fechaLimite(UPDATED_FECHA_LIMITE)
            .notaDeCredito(UPDATED_NOTA_DE_CREDITO);
        CreditoDTO creditoDTO = creditoMapper.toDto(updatedCredito);

        restCreditoMockMvc.perform(put("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isOk());

        // Validate the Credito in the database
        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeUpdate);
        Credito testCredito = creditoList.get(creditoList.size() - 1);
        assertThat(testCredito.getMonto()).isEqualTo(UPDATED_MONTO);
        assertThat(testCredito.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCredito.getModoDePago()).isEqualTo(UPDATED_MODO_DE_PAGO);
        assertThat(testCredito.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCredito.getMontoTotal()).isEqualTo(UPDATED_MONTO_TOTAL);
        assertThat(testCredito.getFechaLimite()).isEqualTo(UPDATED_FECHA_LIMITE);
        assertThat(testCredito.getNotaDeCredito()).isEqualTo(UPDATED_NOTA_DE_CREDITO);

        // Validate the Credito in Elasticsearch
        verify(mockCreditoSearchRepository, times(1)).save(testCredito);
    }

    @Test
    @Transactional
    public void updateNonExistingCredito() throws Exception {
        int databaseSizeBeforeUpdate = creditoRepository.findAll().size();

        // Create the Credito
        CreditoDTO creditoDTO = creditoMapper.toDto(credito);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditoMockMvc.perform(put("/api/creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Credito in the database
        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Credito in Elasticsearch
        verify(mockCreditoSearchRepository, times(0)).save(credito);
    }

    @Test
    @Transactional
    public void deleteCredito() throws Exception {
        // Initialize the database
        creditoRepository.saveAndFlush(credito);

        int databaseSizeBeforeDelete = creditoRepository.findAll().size();

        // Get the credito
        restCreditoMockMvc.perform(delete("/api/creditos/{id}", credito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Credito> creditoList = creditoRepository.findAll();
        assertThat(creditoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Credito in Elasticsearch
        verify(mockCreditoSearchRepository, times(1)).deleteById(credito.getId());
    }

    @Test
    @Transactional
    public void searchCredito() throws Exception {
        // Initialize the database
        creditoRepository.saveAndFlush(credito);
        when(mockCreditoSearchRepository.search(queryStringQuery("id:" + credito.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(credito), PageRequest.of(0, 1), 1));
        // Search the credito
        restCreditoMockMvc.perform(get("/api/_search/creditos?query=id:" + credito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credito.getId().intValue())))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(DEFAULT_MONTO.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].modoDePago").value(hasItem(DEFAULT_MODO_DE_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].montoTotal").value(hasItem(DEFAULT_MONTO_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaLimite").value(hasItem(DEFAULT_FECHA_LIMITE.toString())))
            .andExpect(jsonPath("$.[*].notaDeCredito").value(hasItem(DEFAULT_NOTA_DE_CREDITO)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Credito.class);
        Credito credito1 = new Credito();
        credito1.setId(1L);
        Credito credito2 = new Credito();
        credito2.setId(credito1.getId());
        assertThat(credito1).isEqualTo(credito2);
        credito2.setId(2L);
        assertThat(credito1).isNotEqualTo(credito2);
        credito1.setId(null);
        assertThat(credito1).isNotEqualTo(credito2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditoDTO.class);
        CreditoDTO creditoDTO1 = new CreditoDTO();
        creditoDTO1.setId(1L);
        CreditoDTO creditoDTO2 = new CreditoDTO();
        assertThat(creditoDTO1).isNotEqualTo(creditoDTO2);
        creditoDTO2.setId(creditoDTO1.getId());
        assertThat(creditoDTO1).isEqualTo(creditoDTO2);
        creditoDTO2.setId(2L);
        assertThat(creditoDTO1).isNotEqualTo(creditoDTO2);
        creditoDTO1.setId(null);
        assertThat(creditoDTO1).isNotEqualTo(creditoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditoMapper.fromId(null)).isNull();
    }
}
