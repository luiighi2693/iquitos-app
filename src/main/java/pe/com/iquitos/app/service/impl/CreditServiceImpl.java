package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.CreditService;
import pe.com.iquitos.app.domain.Credit;
import pe.com.iquitos.app.repository.CreditRepository;
import pe.com.iquitos.app.repository.search.CreditSearchRepository;
import pe.com.iquitos.app.service.dto.CreditDTO;
import pe.com.iquitos.app.service.mapper.CreditMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Credit.
 */
@Service
@Transactional
public class CreditServiceImpl implements CreditService {

    private final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

    private CreditRepository creditRepository;

    private CreditMapper creditMapper;

    private CreditSearchRepository creditSearchRepository;

    public CreditServiceImpl(CreditRepository creditRepository, CreditMapper creditMapper, CreditSearchRepository creditSearchRepository) {
        this.creditRepository = creditRepository;
        this.creditMapper = creditMapper;
        this.creditSearchRepository = creditSearchRepository;
    }

    /**
     * Save a credit.
     *
     * @param creditDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditDTO save(CreditDTO creditDTO) {
        log.debug("Request to save Credit : {}", creditDTO);

        Credit credit = creditMapper.toEntity(creditDTO);
        credit = creditRepository.save(credit);
        CreditDTO result = creditMapper.toDto(credit);
        creditSearchRepository.save(credit);
        return result;
    }

    /**
     * Get all the credits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CreditDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Credits");
        return creditRepository.findAll(pageable)
            .map(creditMapper::toDto);
    }


    /**
     * Get one credit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditDTO> findOne(Long id) {
        log.debug("Request to get Credit : {}", id);
        return creditRepository.findById(id)
            .map(creditMapper::toDto);
    }

    /**
     * Delete the credit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Credit : {}", id);
        creditRepository.deleteById(id);
        creditSearchRepository.deleteById(id);
    }

    /**
     * Search for the credit corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CreditDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Credits for query {}", query);
        return creditSearchRepository.search(queryStringQuery(query), pageable)
            .map(creditMapper::toDto);
    }
}
