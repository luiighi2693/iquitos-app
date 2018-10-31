package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.EmpleadoService;
import pe.com.iquitos.app.domain.Empleado;
import pe.com.iquitos.app.repository.EmpleadoRepository;
import pe.com.iquitos.app.repository.search.EmpleadoSearchRepository;
import pe.com.iquitos.app.service.dto.EmpleadoDTO;
import pe.com.iquitos.app.service.mapper.EmpleadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Empleado.
 */
@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final Logger log = LoggerFactory.getLogger(EmpleadoServiceImpl.class);

    private EmpleadoRepository empleadoRepository;

    private EmpleadoMapper empleadoMapper;

    private EmpleadoSearchRepository empleadoSearchRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, EmpleadoMapper empleadoMapper, EmpleadoSearchRepository empleadoSearchRepository) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = empleadoMapper;
        this.empleadoSearchRepository = empleadoSearchRepository;
    }

    /**
     * Save a empleado.
     *
     * @param empleadoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmpleadoDTO save(EmpleadoDTO empleadoDTO) {
        log.debug("Request to save Empleado : {}", empleadoDTO);

        Empleado empleado = empleadoMapper.toEntity(empleadoDTO);
        empleado = empleadoRepository.save(empleado);
        EmpleadoDTO result = empleadoMapper.toDto(empleado);
        empleadoSearchRepository.save(empleado);
        return result;
    }

    /**
     * Get all the empleados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmpleadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Empleados");
        return empleadoRepository.findAll(pageable)
            .map(empleadoMapper::toDto);
    }


    /**
     * Get one empleado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmpleadoDTO> findOne(Long id) {
        log.debug("Request to get Empleado : {}", id);
        return empleadoRepository.findById(id)
            .map(empleadoMapper::toDto);
    }

    /**
     * Delete the empleado by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Empleado : {}", id);
        empleadoRepository.deleteById(id);
        empleadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the empleado corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmpleadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Empleados for query {}", query);
        return empleadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(empleadoMapper::toDto);
    }
}
