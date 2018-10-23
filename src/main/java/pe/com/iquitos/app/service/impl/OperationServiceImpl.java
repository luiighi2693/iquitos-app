package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.OperationService;
import pe.com.iquitos.app.domain.Operation;
import pe.com.iquitos.app.repository.OperationRepository;
import pe.com.iquitos.app.repository.search.OperationSearchRepository;
import pe.com.iquitos.app.service.dto.OperationDTO;
import pe.com.iquitos.app.service.mapper.OperationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Operation.
 */
@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    private final Logger log = LoggerFactory.getLogger(OperationServiceImpl.class);

    private OperationRepository operationRepository;

    private OperationMapper operationMapper;

    private OperationSearchRepository operationSearchRepository;

    public OperationServiceImpl(OperationRepository operationRepository, OperationMapper operationMapper, OperationSearchRepository operationSearchRepository) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
        this.operationSearchRepository = operationSearchRepository;
    }

    /**
     * Save a operation.
     *
     * @param operationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OperationDTO save(OperationDTO operationDTO) {
        log.debug("Request to save Operation : {}", operationDTO);

        Operation operation = operationMapper.toEntity(operationDTO);
        operation = operationRepository.save(operation);
        OperationDTO result = operationMapper.toDto(operation);
        operationSearchRepository.save(operation);
        return result;
    }

    /**
     * Get all the operations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OperationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Operations");
        return operationRepository.findAll(pageable)
            .map(operationMapper::toDto);
    }


    /**
     * Get one operation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OperationDTO> findOne(Long id) {
        log.debug("Request to get Operation : {}", id);
        return operationRepository.findById(id)
            .map(operationMapper::toDto);
    }

    /**
     * Delete the operation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.deleteById(id);
        operationSearchRepository.deleteById(id);
    }

    /**
     * Search for the operation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OperationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Operations for query {}", query);
        return operationSearchRepository.search(queryStringQuery(query), pageable)
            .map(operationMapper::toDto);
    }
}
