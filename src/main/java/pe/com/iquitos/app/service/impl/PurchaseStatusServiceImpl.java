package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.PurchaseStatusService;
import pe.com.iquitos.app.domain.PurchaseStatus;
import pe.com.iquitos.app.repository.PurchaseStatusRepository;
import pe.com.iquitos.app.repository.search.PurchaseStatusSearchRepository;
import pe.com.iquitos.app.service.dto.PurchaseStatusDTO;
import pe.com.iquitos.app.service.mapper.PurchaseStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PurchaseStatus.
 */
@Service
@Transactional
public class PurchaseStatusServiceImpl implements PurchaseStatusService {

    private final Logger log = LoggerFactory.getLogger(PurchaseStatusServiceImpl.class);

    private PurchaseStatusRepository purchaseStatusRepository;

    private PurchaseStatusMapper purchaseStatusMapper;

    private PurchaseStatusSearchRepository purchaseStatusSearchRepository;

    public PurchaseStatusServiceImpl(PurchaseStatusRepository purchaseStatusRepository, PurchaseStatusMapper purchaseStatusMapper, PurchaseStatusSearchRepository purchaseStatusSearchRepository) {
        this.purchaseStatusRepository = purchaseStatusRepository;
        this.purchaseStatusMapper = purchaseStatusMapper;
        this.purchaseStatusSearchRepository = purchaseStatusSearchRepository;
    }

    /**
     * Save a purchaseStatus.
     *
     * @param purchaseStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PurchaseStatusDTO save(PurchaseStatusDTO purchaseStatusDTO) {
        log.debug("Request to save PurchaseStatus : {}", purchaseStatusDTO);

        PurchaseStatus purchaseStatus = purchaseStatusMapper.toEntity(purchaseStatusDTO);
        purchaseStatus = purchaseStatusRepository.save(purchaseStatus);
        PurchaseStatusDTO result = purchaseStatusMapper.toDto(purchaseStatus);
        purchaseStatusSearchRepository.save(purchaseStatus);
        return result;
    }

    /**
     * Get all the purchaseStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseStatuses");
        return purchaseStatusRepository.findAll(pageable)
            .map(purchaseStatusMapper::toDto);
    }


    /**
     * Get one purchaseStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseStatusDTO> findOne(Long id) {
        log.debug("Request to get PurchaseStatus : {}", id);
        return purchaseStatusRepository.findById(id)
            .map(purchaseStatusMapper::toDto);
    }

    /**
     * Delete the purchaseStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseStatus : {}", id);
        purchaseStatusRepository.deleteById(id);
        purchaseStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the purchaseStatus corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PurchaseStatuses for query {}", query);
        return purchaseStatusSearchRepository.search(queryStringQuery(query), pageable)
            .map(purchaseStatusMapper::toDto);
    }
}
