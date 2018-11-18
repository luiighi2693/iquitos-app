package pe.com.iquitos.app.web.rest;

import pe.com.iquitos.app.IquitosApp;

import pe.com.iquitos.app.domain.Empleado;
import pe.com.iquitos.app.repository.EmpleadoRepository;
import pe.com.iquitos.app.repository.search.EmpleadoSearchRepository;
import pe.com.iquitos.app.service.EmpleadoService;
import pe.com.iquitos.app.service.dto.EmpleadoDTO;
import pe.com.iquitos.app.service.mapper.EmpleadoMapper;
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
import org.springframework.util.Base64Utils;

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

import pe.com.iquitos.app.domain.enumeration.Sex;
import pe.com.iquitos.app.domain.enumeration.EmployeeRole;
/**
 * Test class for the EmpleadoResource REST controller.
 *
 * @see EmpleadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IquitosApp.class)
public class EmpleadoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DNI = 1;
    private static final Integer UPDATED_DNI = 2;

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_DE_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_DE_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Sex DEFAULT_SEXO = Sex.MASCULINO;
    private static final Sex UPDATED_SEXO = Sex.FEMENINO;

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final EmployeeRole DEFAULT_ROL_EMPLEADO = EmployeeRole.VENDEDOR;
    private static final EmployeeRole UPDATED_ROL_EMPLEADO = EmployeeRole.VENDEDOR;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * This repository is mocked in the pe.com.iquitos.app.repository.search test package.
     *
     * @see pe.com.iquitos.app.repository.search.EmpleadoSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmpleadoSearchRepository mockEmpleadoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmpleadoMockMvc;

    private Empleado empleado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpleadoResource empleadoResource = new EmpleadoResource(empleadoService);
        this.restEmpleadoMockMvc = MockMvcBuilders.standaloneSetup(empleadoResource)
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
    public static Empleado createEntity(EntityManager em) {
        Empleado empleado = new Empleado()
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .dni(DEFAULT_DNI)
            .direccion(DEFAULT_DIRECCION)
            .correo(DEFAULT_CORREO)
            .fechaDeNacimiento(DEFAULT_FECHA_DE_NACIMIENTO)
            .sexo(DEFAULT_SEXO)
            .telefono(DEFAULT_TELEFONO)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .rolEmpleado(DEFAULT_ROL_EMPLEADO);
        return empleado;
    }

    @Before
    public void initTest() {
        empleado = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpleado() throws Exception {
        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);
        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isCreated());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate + 1);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEmpleado.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testEmpleado.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testEmpleado.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testEmpleado.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testEmpleado.getFechaDeNacimiento()).isEqualTo(DEFAULT_FECHA_DE_NACIMIENTO);
        assertThat(testEmpleado.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testEmpleado.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testEmpleado.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testEmpleado.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testEmpleado.getRolEmpleado()).isEqualTo(DEFAULT_ROL_EMPLEADO);

        // Validate the Empleado in Elasticsearch
        verify(mockEmpleadoSearchRepository, times(1)).save(testEmpleado);
    }

    @Test
    @Transactional
    public void createEmpleadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empleadoRepository.findAll().size();

        // Create the Empleado with an existing ID
        empleado.setId(1L);
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Empleado in Elasticsearch
        verify(mockEmpleadoSearchRepository, times(0)).save(empleado);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setNombre(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setApellido(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setDni(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setDireccion(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorreoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setCorreo(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = empleadoRepository.findAll().size();
        // set the field null
        empleado.setTelefono(null);

        // Create the Empleado, which fails.
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        restEmpleadoMockMvc.perform(post("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmpleados() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get all the empleadoList
        restEmpleadoMockMvc.perform(get("/api/empleados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO.toString())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].fechaDeNacimiento").value(hasItem(DEFAULT_FECHA_DE_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].rolEmpleado").value(hasItem(DEFAULT_ROL_EMPLEADO.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        // Get the empleado
        restEmpleadoMockMvc.perform(get("/api/empleados/{id}", empleado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empleado.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO.toString()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.fechaDeNacimiento").value(DEFAULT_FECHA_DE_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.rolEmpleado").value(DEFAULT_ROL_EMPLEADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpleado() throws Exception {
        // Get the empleado
        restEmpleadoMockMvc.perform(get("/api/empleados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Update the empleado
        Empleado updatedEmpleado = empleadoRepository.findById(empleado.getId()).get();
        // Disconnect from session so that the updates on updatedEmpleado are not directly saved in db
        em.detach(updatedEmpleado);
        updatedEmpleado
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .dni(UPDATED_DNI)
            .direccion(UPDATED_DIRECCION)
            .correo(UPDATED_CORREO)
            .fechaDeNacimiento(UPDATED_FECHA_DE_NACIMIENTO)
            .sexo(UPDATED_SEXO)
            .telefono(UPDATED_TELEFONO)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .rolEmpleado(UPDATED_ROL_EMPLEADO);
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(updatedEmpleado);

        restEmpleadoMockMvc.perform(put("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isOk());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);
        Empleado testEmpleado = empleadoList.get(empleadoList.size() - 1);
        assertThat(testEmpleado.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEmpleado.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testEmpleado.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testEmpleado.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testEmpleado.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testEmpleado.getFechaDeNacimiento()).isEqualTo(UPDATED_FECHA_DE_NACIMIENTO);
        assertThat(testEmpleado.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testEmpleado.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testEmpleado.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testEmpleado.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testEmpleado.getRolEmpleado()).isEqualTo(UPDATED_ROL_EMPLEADO);

        // Validate the Empleado in Elasticsearch
        verify(mockEmpleadoSearchRepository, times(1)).save(testEmpleado);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpleado() throws Exception {
        int databaseSizeBeforeUpdate = empleadoRepository.findAll().size();

        // Create the Empleado
        EmpleadoDTO empleadoDTO = empleadoMapper.toDto(empleado);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpleadoMockMvc.perform(put("/api/empleados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empleadoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empleado in the database
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Empleado in Elasticsearch
        verify(mockEmpleadoSearchRepository, times(0)).save(empleado);
    }

    @Test
    @Transactional
    public void deleteEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);

        int databaseSizeBeforeDelete = empleadoRepository.findAll().size();

        // Get the empleado
        restEmpleadoMockMvc.perform(delete("/api/empleados/{id}", empleado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Empleado> empleadoList = empleadoRepository.findAll();
        assertThat(empleadoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Empleado in Elasticsearch
        verify(mockEmpleadoSearchRepository, times(1)).deleteById(empleado.getId());
    }

    @Test
    @Transactional
    public void searchEmpleado() throws Exception {
        // Initialize the database
        empleadoRepository.saveAndFlush(empleado);
        when(mockEmpleadoSearchRepository.search(queryStringQuery("id:" + empleado.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(empleado), PageRequest.of(0, 1), 1));
        // Search the empleado
        restEmpleadoMockMvc.perform(get("/api/_search/empleados?query=id:" + empleado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empleado.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].fechaDeNacimiento").value(hasItem(DEFAULT_FECHA_DE_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].rolEmpleado").value(hasItem(DEFAULT_ROL_EMPLEADO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empleado.class);
        Empleado empleado1 = new Empleado();
        empleado1.setId(1L);
        Empleado empleado2 = new Empleado();
        empleado2.setId(empleado1.getId());
        assertThat(empleado1).isEqualTo(empleado2);
        empleado2.setId(2L);
        assertThat(empleado1).isNotEqualTo(empleado2);
        empleado1.setId(null);
        assertThat(empleado1).isNotEqualTo(empleado2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpleadoDTO.class);
        EmpleadoDTO empleadoDTO1 = new EmpleadoDTO();
        empleadoDTO1.setId(1L);
        EmpleadoDTO empleadoDTO2 = new EmpleadoDTO();
        assertThat(empleadoDTO1).isNotEqualTo(empleadoDTO2);
        empleadoDTO2.setId(empleadoDTO1.getId());
        assertThat(empleadoDTO1).isEqualTo(empleadoDTO2);
        empleadoDTO2.setId(2L);
        assertThat(empleadoDTO1).isNotEqualTo(empleadoDTO2);
        empleadoDTO1.setId(null);
        assertThat(empleadoDTO1).isNotEqualTo(empleadoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(empleadoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(empleadoMapper.fromId(null)).isNull();
    }
}
