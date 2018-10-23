package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.ProviderAccount;
import pe.com.iquitos.app.repository.ProviderAccountRepository;
import pe.com.iquitos.app.repository.search.ProviderAccountSearchRepository;
import pe.com.iquitos.app.service.ProviderAccountService;
import pe.com.iquitos.app.service.dto.ProviderAccountDTO;
import pe.com.iquitos.app.service.mapper.ProviderAccountMapper;
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

import pe.com.iquitos.app.domain.enumeration.ProviderStatus;
/**
 * Test class for the ProviderAccountResource REST controller.
 *
 * @see ProviderAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class ProviderAccountResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ProviderStatus DEFAULT_STATUS = ProviderStatus.ACTIVO;
    private static final ProviderStatus UPDATED_STATUS = ProviderStatus.INACTIVO;

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCOUNT_NUMBER = 1;
    private static final Integer UPDATED_ACCOUNT_NUMBER = 2;

    private static final LocalDate DEFAULT_INIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INIT_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProviderAccountRepository providerAccountRepository;

    @Autowired
    private ProviderAccountMapper providerAccountMapper;
    
    @Autowired
    private ProviderAccountService providerAccountService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.ProviderAccountSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProviderAccountSearchRepository mockProviderAccountSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProviderAccountMockMvc;

    private ProviderAccount providerAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProviderAccountResource providerAccountResource = new ProviderAccountResource(providerAccountService);
        this.restProviderAccountMockMvc = MockMvcBuilders.standaloneSetup(providerAccountResource)
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
    public static ProviderAccount createEntity(EntityManager em) {
        ProviderAccount providerAccount = new ProviderAccount()
            .code(DEFAULT_CODE)
            .status(DEFAULT_STATUS)
            .bank(DEFAULT_BANK)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .initDate(DEFAULT_INIT_DATE);
        return providerAccount;
    }

    @Before
    public void initTest() {
        providerAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createProviderAccount() throws Exception {
        int databaseSizeBeforeCreate = providerAccountRepository.findAll().size();

        // Create the ProviderAccount
        ProviderAccountDTO providerAccountDTO = providerAccountMapper.toDto(providerAccount);
        restProviderAccountMockMvc.perform(post("/api/provider-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the ProviderAccount in the database
        List<ProviderAccount> providerAccountList = providerAccountRepository.findAll();
        assertThat(providerAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ProviderAccount testProviderAccount = providerAccountList.get(providerAccountList.size() - 1);
        assertThat(testProviderAccount.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProviderAccount.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProviderAccount.getBank()).isEqualTo(DEFAULT_BANK);
        assertThat(testProviderAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testProviderAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testProviderAccount.getInitDate()).isEqualTo(DEFAULT_INIT_DATE);

        // Validate the ProviderAccount in Elasticsearch
        verify(mockProviderAccountSearchRepository, times(1)).save(testProviderAccount);
    }

    @Test
    @Transactional
    public void createProviderAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = providerAccountRepository.findAll().size();

        // Create the ProviderAccount with an existing ID
        providerAccount.setId(1L);
        ProviderAccountDTO providerAccountDTO = providerAccountMapper.toDto(providerAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProviderAccountMockMvc.perform(post("/api/provider-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProviderAccount in the database
        List<ProviderAccount> providerAccountList = providerAccountRepository.findAll();
        assertThat(providerAccountList).hasSize(databaseSizeBeforeCreate);

        // Validate the ProviderAccount in Elasticsearch
        verify(mockProviderAccountSearchRepository, times(0)).save(providerAccount);
    }

    @Test
    @Transactional
    public void getAllProviderAccounts() throws Exception {
        // Initialize the database
        providerAccountRepository.saveAndFlush(providerAccount);

        // Get all the providerAccountList
        restProviderAccountMockMvc.perform(get("/api/provider-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProviderAccount() throws Exception {
        // Initialize the database
        providerAccountRepository.saveAndFlush(providerAccount);

        // Get the providerAccount
        restProviderAccountMockMvc.perform(get("/api/provider-accounts/{id}", providerAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(providerAccount.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK.toString()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.initDate").value(DEFAULT_INIT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProviderAccount() throws Exception {
        // Get the providerAccount
        restProviderAccountMockMvc.perform(get("/api/provider-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProviderAccount() throws Exception {
        // Initialize the database
        providerAccountRepository.saveAndFlush(providerAccount);

        int databaseSizeBeforeUpdate = providerAccountRepository.findAll().size();

        // Update the providerAccount
        ProviderAccount updatedProviderAccount = providerAccountRepository.findById(providerAccount.getId()).get();
        // Disconnect from session so that the updates on updatedProviderAccount are not directly saved in db
        em.detach(updatedProviderAccount);
        updatedProviderAccount
            .code(UPDATED_CODE)
            .status(UPDATED_STATUS)
            .bank(UPDATED_BANK)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .initDate(UPDATED_INIT_DATE);
        ProviderAccountDTO providerAccountDTO = providerAccountMapper.toDto(updatedProviderAccount);

        restProviderAccountMockMvc.perform(put("/api/provider-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerAccountDTO)))
            .andExpect(status().isOk());

        // Validate the ProviderAccount in the database
        List<ProviderAccount> providerAccountList = providerAccountRepository.findAll();
        assertThat(providerAccountList).hasSize(databaseSizeBeforeUpdate);
        ProviderAccount testProviderAccount = providerAccountList.get(providerAccountList.size() - 1);
        assertThat(testProviderAccount.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProviderAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProviderAccount.getBank()).isEqualTo(UPDATED_BANK);
        assertThat(testProviderAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testProviderAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testProviderAccount.getInitDate()).isEqualTo(UPDATED_INIT_DATE);

        // Validate the ProviderAccount in Elasticsearch
        verify(mockProviderAccountSearchRepository, times(1)).save(testProviderAccount);
    }

    @Test
    @Transactional
    public void updateNonExistingProviderAccount() throws Exception {
        int databaseSizeBeforeUpdate = providerAccountRepository.findAll().size();

        // Create the ProviderAccount
        ProviderAccountDTO providerAccountDTO = providerAccountMapper.toDto(providerAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProviderAccountMockMvc.perform(put("/api/provider-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(providerAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProviderAccount in the database
        List<ProviderAccount> providerAccountList = providerAccountRepository.findAll();
        assertThat(providerAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ProviderAccount in Elasticsearch
        verify(mockProviderAccountSearchRepository, times(0)).save(providerAccount);
    }

    @Test
    @Transactional
    public void deleteProviderAccount() throws Exception {
        // Initialize the database
        providerAccountRepository.saveAndFlush(providerAccount);

        int databaseSizeBeforeDelete = providerAccountRepository.findAll().size();

        // Get the providerAccount
        restProviderAccountMockMvc.perform(delete("/api/provider-accounts/{id}", providerAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProviderAccount> providerAccountList = providerAccountRepository.findAll();
        assertThat(providerAccountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ProviderAccount in Elasticsearch
        verify(mockProviderAccountSearchRepository, times(1)).deleteById(providerAccount.getId());
    }

    @Test
    @Transactional
    public void searchProviderAccount() throws Exception {
        // Initialize the database
        providerAccountRepository.saveAndFlush(providerAccount);
        when(mockProviderAccountSearchRepository.search(queryStringQuery("id:" + providerAccount.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(providerAccount), PageRequest.of(0, 1), 1));
        // Search the providerAccount
        restProviderAccountMockMvc.perform(get("/api/_search/provider-accounts?query=id:" + providerAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(providerAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].initDate").value(hasItem(DEFAULT_INIT_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderAccount.class);
        ProviderAccount providerAccount1 = new ProviderAccount();
        providerAccount1.setId(1L);
        ProviderAccount providerAccount2 = new ProviderAccount();
        providerAccount2.setId(providerAccount1.getId());
        assertThat(providerAccount1).isEqualTo(providerAccount2);
        providerAccount2.setId(2L);
        assertThat(providerAccount1).isNotEqualTo(providerAccount2);
        providerAccount1.setId(null);
        assertThat(providerAccount1).isNotEqualTo(providerAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProviderAccountDTO.class);
        ProviderAccountDTO providerAccountDTO1 = new ProviderAccountDTO();
        providerAccountDTO1.setId(1L);
        ProviderAccountDTO providerAccountDTO2 = new ProviderAccountDTO();
        assertThat(providerAccountDTO1).isNotEqualTo(providerAccountDTO2);
        providerAccountDTO2.setId(providerAccountDTO1.getId());
        assertThat(providerAccountDTO1).isEqualTo(providerAccountDTO2);
        providerAccountDTO2.setId(2L);
        assertThat(providerAccountDTO1).isNotEqualTo(providerAccountDTO2);
        providerAccountDTO1.setId(null);
        assertThat(providerAccountDTO1).isNotEqualTo(providerAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(providerAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(providerAccountMapper.fromId(null)).isNull();
    }
}
