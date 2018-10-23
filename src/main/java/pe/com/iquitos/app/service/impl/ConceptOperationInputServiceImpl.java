package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ConceptOperationInputService;
import pe.com.iquitos.app.domain.ConceptOperationInput;
import pe.com.iquitos.app.repository.ConceptOperationInputRepository;
import pe.com.iquitos.app.repository.search.ConceptOperationInputSearchRepository;
import pe.com.iquitos.app.service.dto.ConceptOperationInputDTO;
import pe.com.iquitos.app.service.mapper.ConceptOperationInputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ConceptOperationInput.
 */
@Service
@Transactional
public class ConceptOperationInputServiceImpl implements ConceptOperationInputService {

    private final Logger log = LoggerFactory.getLogger(ConceptOperationInputServiceImpl.class);

    private ConceptOperationInputRepository conceptOperationInputRepository;

    private ConceptOperationInputMapper conceptOperationInputMapper;

    private ConceptOperationInputSearchRepository conceptOperationInputSearchRepository;

    public ConceptOperationInputServiceImpl(ConceptOperationInputRepository conceptOperationInputRepository, ConceptOperationInputMapper conceptOperationInputMapper, ConceptOperationInputSearchRepository conceptOperationInputSearchRepository) {
        this.conceptOperationInputRepository = conceptOperationInputRepository;
        this.conceptOperationInputMapper = conceptOperationInputMapper;
        this.conceptOperationInputSearchRepository = conceptOperationInputSearchRepository;
    }

    /**
     * Save a conceptOperationInput.
     *
     * @param conceptOperationInputDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConceptOperationInputDTO save(ConceptOperationInputDTO conceptOperationInputDTO) {
        log.debug("Request to save ConceptOperationInput : {}", conceptOperationInputDTO);

        ConceptOperationInput conceptOperationInput = conceptOperationInputMapper.toEntity(conceptOperationInputDTO);
        conceptOperationInput = conceptOperationInputRepository.save(conceptOperationInput);
        ConceptOperationInputDTO result = conceptOperationInputMapper.toDto(conceptOperationInput);
        conceptOperationInputSearchRepository.save(conceptOperationInput);
        return result;
    }

    /**
     * Get all the conceptOperationInputs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConceptOperationInputDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConceptOperationInputs");
        return conceptOperationInputRepository.findAll(pageable)
            .map(conceptOperationInputMapper::toDto);
    }


    /**
     * Get one conceptOperationInput by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConceptOperationInputDTO> findOne(Long id) {
        log.debug("Request to get ConceptOperationInput : {}", id);
        return conceptOperationInputRepository.findById(id)
            .map(conceptOperationInputMapper::toDto);
    }

    /**
     * Delete the conceptOperationInput by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConceptOperationInput : {}", id);
        conceptOperationInputRepository.deleteById(id);
        conceptOperationInputSearchRepository.deleteById(id);
    }

    /**
     * Search for the conceptOperationInput corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConceptOperationInputDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConceptOperationInputs for query {}", query);
        return conceptOperationInputSearchRepository.search(queryStringQuery(query), pageable)
            .map(conceptOperationInputMapper::toDto);
    }
}
