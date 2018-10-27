package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.UserLogin;
import pe.com.iquitos.app.repository.UserLoginRepository;
import pe.com.iquitos.app.repository.search.UserLoginSearchRepository;
import pe.com.iquitos.app.service.UserLoginService;
import pe.com.iquitos.app.service.dto.UserLoginDTO;
import pe.com.iquitos.app.service.mapper.UserLoginMapper;
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
 * Test class for the UserLoginResource REST controller.
 *
 * @see UserLoginResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class UserLoginResourceIntTest {

    private static final Integer DEFAULT_DNI = 1;
    private static final Integer UPDATED_DNI = 2;

    private static final Integer DEFAULT_PIN = 1;
    private static final Integer UPDATED_PIN = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private UserLoginMapper userLoginMapper;
    
    @Autowired
    private UserLoginService userLoginService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.UserLoginSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserLoginSearchRepository mockUserLoginSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserLoginMockMvc;

    private UserLogin userLogin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserLoginResource userLoginResource = new UserLoginResource(userLoginService);
        this.restUserLoginMockMvc = MockMvcBuilders.standaloneSetup(userLoginResource)
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
    public static UserLogin createEntity(EntityManager em) {
        UserLogin userLogin = new UserLogin()
            .dni(DEFAULT_DNI)
            .pin(DEFAULT_PIN)
            .email(DEFAULT_EMAIL);
        return userLogin;
    }

    @Before
    public void initTest() {
        userLogin = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserLogin() throws Exception {
        int databaseSizeBeforeCreate = userLoginRepository.findAll().size();

        // Create the UserLogin
        UserLoginDTO userLoginDTO = userLoginMapper.toDto(userLogin);
        restUserLoginMockMvc.perform(post("/api/user-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLoginDTO)))
            .andExpect(status().isCreated());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeCreate + 1);
        UserLogin testUserLogin = userLoginList.get(userLoginList.size() - 1);
        assertThat(testUserLogin.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testUserLogin.getPin()).isEqualTo(DEFAULT_PIN);
        assertThat(testUserLogin.getEmail()).isEqualTo(DEFAULT_EMAIL);

        // Validate the UserLogin in Elasticsearch
        verify(mockUserLoginSearchRepository, times(1)).save(testUserLogin);
    }

    @Test
    @Transactional
    public void createUserLoginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userLoginRepository.findAll().size();

        // Create the UserLogin with an existing ID
        userLogin.setId(1L);
        UserLoginDTO userLoginDTO = userLoginMapper.toDto(userLogin);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserLoginMockMvc.perform(post("/api/user-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLoginDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserLogin in Elasticsearch
        verify(mockUserLoginSearchRepository, times(0)).save(userLogin);
    }

    @Test
    @Transactional
    public void getAllUserLogins() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        // Get all the userLoginList
        restUserLoginMockMvc.perform(get("/api/user-logins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userLogin.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getUserLogin() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        // Get the userLogin
        restUserLoginMockMvc.perform(get("/api/user-logins/{id}", userLogin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userLogin.getId().intValue()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.pin").value(DEFAULT_PIN))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserLogin() throws Exception {
        // Get the userLogin
        restUserLoginMockMvc.perform(get("/api/user-logins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserLogin() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();

        // Update the userLogin
        UserLogin updatedUserLogin = userLoginRepository.findById(userLogin.getId()).get();
        // Disconnect from session so that the updates on updatedUserLogin are not directly saved in db
        em.detach(updatedUserLogin);
        updatedUserLogin
            .dni(UPDATED_DNI)
            .pin(UPDATED_PIN)
            .email(UPDATED_EMAIL);
        UserLoginDTO userLoginDTO = userLoginMapper.toDto(updatedUserLogin);

        restUserLoginMockMvc.perform(put("/api/user-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLoginDTO)))
            .andExpect(status().isOk());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);
        UserLogin testUserLogin = userLoginList.get(userLoginList.size() - 1);
        assertThat(testUserLogin.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testUserLogin.getPin()).isEqualTo(UPDATED_PIN);
        assertThat(testUserLogin.getEmail()).isEqualTo(UPDATED_EMAIL);

        // Validate the UserLogin in Elasticsearch
        verify(mockUserLoginSearchRepository, times(1)).save(testUserLogin);
    }

    @Test
    @Transactional
    public void updateNonExistingUserLogin() throws Exception {
        int databaseSizeBeforeUpdate = userLoginRepository.findAll().size();

        // Create the UserLogin
        UserLoginDTO userLoginDTO = userLoginMapper.toDto(userLogin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserLoginMockMvc.perform(put("/api/user-logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userLoginDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserLogin in the database
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserLogin in Elasticsearch
        verify(mockUserLoginSearchRepository, times(0)).save(userLogin);
    }

    @Test
    @Transactional
    public void deleteUserLogin() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);

        int databaseSizeBeforeDelete = userLoginRepository.findAll().size();

        // Get the userLogin
        restUserLoginMockMvc.perform(delete("/api/user-logins/{id}", userLogin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserLogin> userLoginList = userLoginRepository.findAll();
        assertThat(userLoginList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserLogin in Elasticsearch
        verify(mockUserLoginSearchRepository, times(1)).deleteById(userLogin.getId());
    }

    @Test
    @Transactional
    public void searchUserLogin() throws Exception {
        // Initialize the database
        userLoginRepository.saveAndFlush(userLogin);
        when(mockUserLoginSearchRepository.search(queryStringQuery("id:" + userLogin.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(userLogin), PageRequest.of(0, 1), 1));
        // Search the userLogin
        restUserLoginMockMvc.perform(get("/api/_search/user-logins?query=id:" + userLogin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userLogin.getId().intValue())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].pin").value(hasItem(DEFAULT_PIN)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLogin.class);
        UserLogin userLogin1 = new UserLogin();
        userLogin1.setId(1L);
        UserLogin userLogin2 = new UserLogin();
        userLogin2.setId(userLogin1.getId());
        assertThat(userLogin1).isEqualTo(userLogin2);
        userLogin2.setId(2L);
        assertThat(userLogin1).isNotEqualTo(userLogin2);
        userLogin1.setId(null);
        assertThat(userLogin1).isNotEqualTo(userLogin2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLoginDTO.class);
        UserLoginDTO userLoginDTO1 = new UserLoginDTO();
        userLoginDTO1.setId(1L);
        UserLoginDTO userLoginDTO2 = new UserLoginDTO();
        assertThat(userLoginDTO1).isNotEqualTo(userLoginDTO2);
        userLoginDTO2.setId(userLoginDTO1.getId());
        assertThat(userLoginDTO1).isEqualTo(userLoginDTO2);
        userLoginDTO2.setId(2L);
        assertThat(userLoginDTO1).isNotEqualTo(userLoginDTO2);
        userLoginDTO1.setId(null);
        assertThat(userLoginDTO1).isNotEqualTo(userLoginDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userLoginMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userLoginMapper.fromId(null)).isNull();
    }
}
