package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.PurchaseHasProductService;
import pe.com.iquitos.app.domain.PurchaseHasProduct;
import pe.com.iquitos.app.repository.PurchaseHasProductRepository;
import pe.com.iquitos.app.repository.search.PurchaseHasProductSearchRepository;
import pe.com.iquitos.app.service.dto.PurchaseHasProductDTO;
import pe.com.iquitos.app.service.mapper.PurchaseHasProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PurchaseHasProduct.
 */
@Service
@Transactional
public class PurchaseHasProductServiceImpl implements PurchaseHasProductService {

    private final Logger log = LoggerFactory.getLogger(PurchaseHasProductServiceImpl.class);

    private PurchaseHasProductRepository purchaseHasProductRepository;

    private PurchaseHasProductMapper purchaseHasProductMapper;

    private PurchaseHasProductSearchRepository purchaseHasProductSearchRepository;

    public PurchaseHasProductServiceImpl(PurchaseHasProductRepository purchaseHasProductRepository, PurchaseHasProductMapper purchaseHasProductMapper, PurchaseHasProductSearchRepository purchaseHasProductSearchRepository) {
        this.purchaseHasProductRepository = purchaseHasProductRepository;
        this.purchaseHasProductMapper = purchaseHasProductMapper;
        this.purchaseHasProductSearchRepository = purchaseHasProductSearchRepository;
    }

    /**
     * Save a purchaseHasProduct.
     *
     * @param purchaseHasProductDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PurchaseHasProductDTO save(PurchaseHasProductDTO purchaseHasProductDTO) {
        log.debug("Request to save PurchaseHasProduct : {}", purchaseHasProductDTO);

        PurchaseHasProduct purchaseHasProduct = purchaseHasProductMapper.toEntity(purchaseHasProductDTO);
        purchaseHasProduct = purchaseHasProductRepository.save(purchaseHasProduct);
        PurchaseHasProductDTO result = purchaseHasProductMapper.toDto(purchaseHasProduct);
        purchaseHasProductSearchRepository.save(purchaseHasProduct);
        return result;
    }

    /**
     * Get all the purchaseHasProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseHasProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseHasProducts");
        return purchaseHasProductRepository.findAll(pageable)
            .map(purchaseHasProductMapper::toDto);
    }


    /**
     * Get one purchaseHasProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseHasProductDTO> findOne(Long id) {
        log.debug("Request to get PurchaseHasProduct : {}", id);
        return purchaseHasProductRepository.findById(id)
            .map(purchaseHasProductMapper::toDto);
    }

    /**
     * Delete the purchaseHasProduct by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseHasProduct : {}", id);
        purchaseHasProductRepository.deleteById(id);
        purchaseHasProductSearchRepository.deleteById(id);
    }

    /**
     * Search for the purchaseHasProduct corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseHasProductDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PurchaseHasProducts for query {}", query);
        return purchaseHasProductSearchRepository.search(queryStringQuery(query), pageable)
            .map(purchaseHasProductMapper::toDto);
    }
}
