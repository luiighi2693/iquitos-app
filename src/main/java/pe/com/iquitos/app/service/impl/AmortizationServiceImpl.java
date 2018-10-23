package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.AmortizationService;
import pe.com.iquitos.app.domain.Amortization;
import pe.com.iquitos.app.repository.AmortizationRepository;
import pe.com.iquitos.app.repository.search.AmortizationSearchRepository;
import pe.com.iquitos.app.service.dto.AmortizationDTO;
import pe.com.iquitos.app.service.mapper.AmortizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Amortization.
 */
@Service
@Transactional
public class AmortizationServiceImpl implements AmortizationService {

    private final Logger log = LoggerFactory.getLogger(AmortizationServiceImpl.class);

    private AmortizationRepository amortizationRepository;

    private AmortizationMapper amortizationMapper;

    private AmortizationSearchRepository amortizationSearchRepository;

    public AmortizationServiceImpl(AmortizationRepository amortizationRepository, AmortizationMapper amortizationMapper, AmortizationSearchRepository amortizationSearchRepository) {
        this.amortizationRepository = amortizationRepository;
        this.amortizationMapper = amortizationMapper;
        this.amortizationSearchRepository = amortizationSearchRepository;
    }

    /**
     * Save a amortization.
     *
     * @param amortizationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AmortizationDTO save(AmortizationDTO amortizationDTO) {
        log.debug("Request to save Amortization : {}", amortizationDTO);

        Amortization amortization = amortizationMapper.toEntity(amortizationDTO);
        amortization = amortizationRepository.save(amortization);
        AmortizationDTO result = amortizationMapper.toDto(amortization);
        amortizationSearchRepository.save(amortization);
        return result;
    }

    /**
     * Get all the amortizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Amortizations");
        return amortizationRepository.findAll(pageable)
            .map(amortizationMapper::toDto);
    }


    /**
     * Get one amortization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizationDTO> findOne(Long id) {
        log.debug("Request to get Amortization : {}", id);
        return amortizationRepository.findById(id)
            .map(amortizationMapper::toDto);
    }

    /**
     * Delete the amortization by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Amortization : {}", id);
        amortizationRepository.deleteById(id);
        amortizationSearchRepository.deleteById(id);
    }

    /**
     * Search for the amortization corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Amortizations for query {}", query);
        return amortizationSearchRepository.search(queryStringQuery(query), pageable)
            .map(amortizationMapper::toDto);
    }
}
