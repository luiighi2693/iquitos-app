package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.SellHasProductService;
import pe.com.iquitos.app.domain.SellHasProduct;
import pe.com.iquitos.app.repository.SellHasProductRepository;
import pe.com.iquitos.app.repository.search.SellHasProductSearchRepository;
import pe.com.iquitos.app.service.dto.SellHasProductDTO;
import pe.com.iquitos.app.service.mapper.SellHasProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SellHasProduct.
 */
@Service
@Transactional
public class SellHasProductServiceImpl implements SellHasProductService {

    private final Logger log = LoggerFactory.getLogger(SellHasProductServiceImpl.class);

    private SellHasProductRepository sellHasProductRepository;

    private SellHasProductMapper sellHasProductMapper;

    private SellHasProductSearchRepository sellHasProductSearchRepository;

    public SellHasProductServiceImpl(SellHasProductRepository sellHasProductRepository, SellHasProductMapper sellHasProductMapper, SellHasProductSearchRepository sellHasProductSearchRepository) {
        this.sellHasProductRepository = sellHasProductRepository;
        this.sellHasProductMapper = sellHasProductMapper;
        this.sellHasProductSearchRepository = sellHasProductSearchRepository;
    }

    /**
     * Save a sellHasProduct.
     *
     * @param sellHasProductDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SellHasProductDTO save(SellHasProductDTO sellHasProductDTO) {
        log.debug("Request to save SellHasProduct : {}", sellHasProductDTO);

        SellHasProduct sellHasProduct = sellHasProductMapper.toEntity(sellHasProductDTO);
        sellHasProduct = sellHasProductRepository.save(sellHasProduct);
        SellHasProductDTO result = sellHasProductMapper.toDto(sellHasProduct);
        sellHasProductSearchRepository.save(sellHasProduct);
        return result;
    }

    /**
     * Get all the sellHasProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SellHasProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SellHasProducts");
        return sellHasProductRepository.findAll(pageable)
            .map(sellHasProductMapper::toDto);
    }


    /**
     * Get one sellHasProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SellHasProductDTO> findOne(Long id) {
        log.debug("Request to get SellHasProduct : {}", id);
        return sellHasProductRepository.findById(id)
            .map(sellHasProductMapper::toDto);
    }

    /**
     * Delete the sellHasProduct by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SellHasProduct : {}", id);
        sellHasProductRepository.deleteById(id);
        sellHasProductSearchRepository.deleteById(id);
    }

    /**
     * Search for the sellHasProduct corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SellHasProductDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SellHasProducts for query {}", query);
        return sellHasProductSearchRepository.search(queryStringQuery(query), pageable)
            .map(sellHasProductMapper::toDto);
    }
}
