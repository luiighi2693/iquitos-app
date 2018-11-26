package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.OperacionService;
import pe.com.iquitos.app.domain.Operacion;
import pe.com.iquitos.app.repository.OperacionRepository;
import pe.com.iquitos.app.repository.search.OperacionSearchRepository;
import pe.com.iquitos.app.service.dto.OperacionDTO;
import pe.com.iquitos.app.service.mapper.OperacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Operacion.
 */
@Service
@Transactional
public class OperacionServiceImpl implements OperacionService {

    private final Logger log = LoggerFactory.getLogger(OperacionServiceImpl.class);

    private final OperacionRepository operacionRepository;

    private final OperacionMapper operacionMapper;

    private final OperacionSearchRepository operacionSearchRepository;

    public OperacionServiceImpl(OperacionRepository operacionRepository, OperacionMapper operacionMapper, OperacionSearchRepository operacionSearchRepository) {
        this.operacionRepository = operacionRepository;
        this.operacionMapper = operacionMapper;
        this.operacionSearchRepository = operacionSearchRepository;
    }

    /**
     * Save a operacion.
     *
     * @param operacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OperacionDTO save(OperacionDTO operacionDTO) {
        log.debug("Request to save Operacion : {}", operacionDTO);

        Operacion operacion = operacionMapper.toEntity(operacionDTO);
        operacion = operacionRepository.save(operacion);
        OperacionDTO result = operacionMapper.toDto(operacion);
        operacionSearchRepository.save(operacion);
        return result;
    }

    /**
     * Get all the operacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OperacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Operacions");
        return operacionRepository.findAll(pageable)
            .map(operacionMapper::toDto);
    }


    /**
     * Get one operacion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OperacionDTO> findOne(Long id) {
        log.debug("Request to get Operacion : {}", id);
        return operacionRepository.findById(id)
            .map(operacionMapper::toDto);
    }

    /**
     * Delete the operacion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operacion : {}", id);
        operacionRepository.deleteById(id);
        operacionSearchRepository.deleteById(id);
    }

    /**
     * Search for the operacion corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OperacionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Operacions for query {}", query);
        return operacionSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(operacionMapper::toDto);
    }
}
