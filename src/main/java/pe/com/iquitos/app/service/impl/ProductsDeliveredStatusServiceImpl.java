package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ProductsDeliveredStatusService;
import pe.com.iquitos.app.domain.ProductsDeliveredStatus;
import pe.com.iquitos.app.repository.ProductsDeliveredStatusRepository;
import pe.com.iquitos.app.repository.search.ProductsDeliveredStatusSearchRepository;
import pe.com.iquitos.app.service.dto.ProductsDeliveredStatusDTO;
import pe.com.iquitos.app.service.mapper.ProductsDeliveredStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductsDeliveredStatus.
 */
@Service
@Transactional
public class ProductsDeliveredStatusServiceImpl implements ProductsDeliveredStatusService {

    private final Logger log = LoggerFactory.getLogger(ProductsDeliveredStatusServiceImpl.class);

    private ProductsDeliveredStatusRepository productsDeliveredStatusRepository;

    private ProductsDeliveredStatusMapper productsDeliveredStatusMapper;

    private ProductsDeliveredStatusSearchRepository productsDeliveredStatusSearchRepository;

    public ProductsDeliveredStatusServiceImpl(ProductsDeliveredStatusRepository productsDeliveredStatusRepository, ProductsDeliveredStatusMapper productsDeliveredStatusMapper, ProductsDeliveredStatusSearchRepository productsDeliveredStatusSearchRepository) {
        this.productsDeliveredStatusRepository = productsDeliveredStatusRepository;
        this.productsDeliveredStatusMapper = productsDeliveredStatusMapper;
        this.productsDeliveredStatusSearchRepository = productsDeliveredStatusSearchRepository;
    }

    /**
     * Save a productsDeliveredStatus.
     *
     * @param productsDeliveredStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductsDeliveredStatusDTO save(ProductsDeliveredStatusDTO productsDeliveredStatusDTO) {
        log.debug("Request to save ProductsDeliveredStatus : {}", productsDeliveredStatusDTO);

        ProductsDeliveredStatus productsDeliveredStatus = productsDeliveredStatusMapper.toEntity(productsDeliveredStatusDTO);
        productsDeliveredStatus = productsDeliveredStatusRepository.save(productsDeliveredStatus);
        ProductsDeliveredStatusDTO result = productsDeliveredStatusMapper.toDto(productsDeliveredStatus);
        productsDeliveredStatusSearchRepository.save(productsDeliveredStatus);
        return result;
    }

    /**
     * Get all the productsDeliveredStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductsDeliveredStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductsDeliveredStatuses");
        return productsDeliveredStatusRepository.findAll(pageable)
            .map(productsDeliveredStatusMapper::toDto);
    }


    /**
     * Get one productsDeliveredStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductsDeliveredStatusDTO> findOne(Long id) {
        log.debug("Request to get ProductsDeliveredStatus : {}", id);
        return productsDeliveredStatusRepository.findById(id)
            .map(productsDeliveredStatusMapper::toDto);
    }

    /**
     * Delete the productsDeliveredStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductsDeliveredStatus : {}", id);
        productsDeliveredStatusRepository.deleteById(id);
        productsDeliveredStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the productsDeliveredStatus corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductsDeliveredStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductsDeliveredStatuses for query {}", query);
        return productsDeliveredStatusSearchRepository.search(queryStringQuery(query), pageable)
            .map(productsDeliveredStatusMapper::toDto);
    }
}
