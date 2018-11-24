package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ProductosRelacionadosTagsService;
import pe.com.iquitos.app.domain.ProductosRelacionadosTags;
import pe.com.iquitos.app.repository.ProductosRelacionadosTagsRepository;
import pe.com.iquitos.app.repository.search.ProductosRelacionadosTagsSearchRepository;
import pe.com.iquitos.app.service.dto.ProductosRelacionadosTagsDTO;
import pe.com.iquitos.app.service.mapper.ProductosRelacionadosTagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductosRelacionadosTags.
 */
@Service
@Transactional
public class ProductosRelacionadosTagsServiceImpl implements ProductosRelacionadosTagsService {

    private final Logger log = LoggerFactory.getLogger(ProductosRelacionadosTagsServiceImpl.class);

    private final ProductosRelacionadosTagsRepository productosRelacionadosTagsRepository;

    private final ProductosRelacionadosTagsMapper productosRelacionadosTagsMapper;

    private final ProductosRelacionadosTagsSearchRepository productosRelacionadosTagsSearchRepository;

    public ProductosRelacionadosTagsServiceImpl(ProductosRelacionadosTagsRepository productosRelacionadosTagsRepository, ProductosRelacionadosTagsMapper productosRelacionadosTagsMapper, ProductosRelacionadosTagsSearchRepository productosRelacionadosTagsSearchRepository) {
        this.productosRelacionadosTagsRepository = productosRelacionadosTagsRepository;
        this.productosRelacionadosTagsMapper = productosRelacionadosTagsMapper;
        this.productosRelacionadosTagsSearchRepository = productosRelacionadosTagsSearchRepository;
    }

    /**
     * Save a productosRelacionadosTags.
     *
     * @param productosRelacionadosTagsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductosRelacionadosTagsDTO save(ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO) {
        log.debug("Request to save ProductosRelacionadosTags : {}", productosRelacionadosTagsDTO);

        ProductosRelacionadosTags productosRelacionadosTags = productosRelacionadosTagsMapper.toEntity(productosRelacionadosTagsDTO);
        productosRelacionadosTags = productosRelacionadosTagsRepository.save(productosRelacionadosTags);
        ProductosRelacionadosTagsDTO result = productosRelacionadosTagsMapper.toDto(productosRelacionadosTags);
        productosRelacionadosTagsSearchRepository.save(productosRelacionadosTags);
        return result;
    }

    /**
     * Get all the productosRelacionadosTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductosRelacionadosTagsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductosRelacionadosTags");
        return productosRelacionadosTagsRepository.findAll(pageable)
            .map(productosRelacionadosTagsMapper::toDto);
    }


    /**
     * Get one productosRelacionadosTags by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductosRelacionadosTagsDTO> findOne(Long id) {
        log.debug("Request to get ProductosRelacionadosTags : {}", id);
        return productosRelacionadosTagsRepository.findById(id)
            .map(productosRelacionadosTagsMapper::toDto);
    }

    /**
     * Delete the productosRelacionadosTags by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductosRelacionadosTags : {}", id);
        productosRelacionadosTagsRepository.deleteById(id);
        productosRelacionadosTagsSearchRepository.deleteById(id);
    }

    /**
     * Search for the productosRelacionadosTags corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductosRelacionadosTagsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductosRelacionadosTags for query {}", query);
        return productosRelacionadosTagsSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(productosRelacionadosTagsMapper::toDto);
    }
}
