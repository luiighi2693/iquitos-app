package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.CreditoService;
import pe.com.iquitos.app.domain.Credito;
import pe.com.iquitos.app.repository.CreditoRepository;
import pe.com.iquitos.app.repository.search.CreditoSearchRepository;
import pe.com.iquitos.app.service.dto.CreditoDTO;
import pe.com.iquitos.app.service.mapper.CreditoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Credito.
 */
@Service
@Transactional
public class CreditoServiceImpl implements CreditoService {

    private final Logger log = LoggerFactory.getLogger(CreditoServiceImpl.class);

    private final CreditoRepository creditoRepository;

    private final CreditoMapper creditoMapper;

    private final CreditoSearchRepository creditoSearchRepository;

    public CreditoServiceImpl(CreditoRepository creditoRepository, CreditoMapper creditoMapper, CreditoSearchRepository creditoSearchRepository) {
        this.creditoRepository = creditoRepository;
        this.creditoMapper = creditoMapper;
        this.creditoSearchRepository = creditoSearchRepository;
    }

    /**
     * Save a credito.
     *
     * @param creditoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CreditoDTO save(CreditoDTO creditoDTO) {
        log.debug("Request to save Credito : {}", creditoDTO);

        Credito credito = creditoMapper.toEntity(creditoDTO);
        credito = creditoRepository.save(credito);
        CreditoDTO result = creditoMapper.toDto(credito);
        creditoSearchRepository.save(credito);
        return result;
    }

    /**
     * Get all the creditos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CreditoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Creditos");
        return creditoRepository.findAll(pageable)
            .map(creditoMapper::toDto);
    }


    /**
     * Get one credito by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CreditoDTO> findOne(Long id) {
        log.debug("Request to get Credito : {}", id);
        return creditoRepository.findById(id)
            .map(creditoMapper::toDto);
    }

    /**
     * Delete the credito by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Credito : {}", id);
        creditoRepository.deleteById(id);
        creditoSearchRepository.deleteById(id);
    }

    /**
     * Search for the credito corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CreditoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Creditos for query {}", query);
        return creditoSearchRepository.search(queryStringQuery(query), pageable)
            .map(creditoMapper::toDto);
    }
}
