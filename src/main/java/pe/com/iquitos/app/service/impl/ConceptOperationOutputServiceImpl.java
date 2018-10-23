package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ConceptOperationOutputService;
import pe.com.iquitos.app.domain.ConceptOperationOutput;
import pe.com.iquitos.app.repository.ConceptOperationOutputRepository;
import pe.com.iquitos.app.repository.search.ConceptOperationOutputSearchRepository;
import pe.com.iquitos.app.service.dto.ConceptOperationOutputDTO;
import pe.com.iquitos.app.service.mapper.ConceptOperationOutputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ConceptOperationOutput.
 */
@Service
@Transactional
public class ConceptOperationOutputServiceImpl implements ConceptOperationOutputService {

    private final Logger log = LoggerFactory.getLogger(ConceptOperationOutputServiceImpl.class);

    private ConceptOperationOutputRepository conceptOperationOutputRepository;

    private ConceptOperationOutputMapper conceptOperationOutputMapper;

    private ConceptOperationOutputSearchRepository conceptOperationOutputSearchRepository;

    public ConceptOperationOutputServiceImpl(ConceptOperationOutputRepository conceptOperationOutputRepository, ConceptOperationOutputMapper conceptOperationOutputMapper, ConceptOperationOutputSearchRepository conceptOperationOutputSearchRepository) {
        this.conceptOperationOutputRepository = conceptOperationOutputRepository;
        this.conceptOperationOutputMapper = conceptOperationOutputMapper;
        this.conceptOperationOutputSearchRepository = conceptOperationOutputSearchRepository;
    }

    /**
     * Save a conceptOperationOutput.
     *
     * @param conceptOperationOutputDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConceptOperationOutputDTO save(ConceptOperationOutputDTO conceptOperationOutputDTO) {
        log.debug("Request to save ConceptOperationOutput : {}", conceptOperationOutputDTO);

        ConceptOperationOutput conceptOperationOutput = conceptOperationOutputMapper.toEntity(conceptOperationOutputDTO);
        conceptOperationOutput = conceptOperationOutputRepository.save(conceptOperationOutput);
        ConceptOperationOutputDTO result = conceptOperationOutputMapper.toDto(conceptOperationOutput);
        conceptOperationOutputSearchRepository.save(conceptOperationOutput);
        return result;
    }

    /**
     * Get all the conceptOperationOutputs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConceptOperationOutputDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConceptOperationOutputs");
        return conceptOperationOutputRepository.findAll(pageable)
            .map(conceptOperationOutputMapper::toDto);
    }


    /**
     * Get one conceptOperationOutput by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConceptOperationOutputDTO> findOne(Long id) {
        log.debug("Request to get ConceptOperationOutput : {}", id);
        return conceptOperationOutputRepository.findById(id)
            .map(conceptOperationOutputMapper::toDto);
    }

    /**
     * Delete the conceptOperationOutput by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConceptOperationOutput : {}", id);
        conceptOperationOutputRepository.deleteById(id);
        conceptOperationOutputSearchRepository.deleteById(id);
    }

    /**
     * Search for the conceptOperationOutput corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConceptOperationOutputDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConceptOperationOutputs for query {}", query);
        return conceptOperationOutputSearchRepository.search(queryStringQuery(query), pageable)
            .map(conceptOperationOutputMapper::toDto);
    }
}
