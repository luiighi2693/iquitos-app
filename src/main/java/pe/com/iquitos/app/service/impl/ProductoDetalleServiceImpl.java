package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ProductoDetalleService;
import pe.com.iquitos.app.domain.ProductoDetalle;
import pe.com.iquitos.app.repository.ProductoDetalleRepository;
import pe.com.iquitos.app.repository.search.ProductoDetalleSearchRepository;
import pe.com.iquitos.app.service.dto.ProductoDetalleDTO;
import pe.com.iquitos.app.service.mapper.ProductoDetalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProductoDetalle.
 */
@Service
@Transactional
public class ProductoDetalleServiceImpl implements ProductoDetalleService {

    private final Logger log = LoggerFactory.getLogger(ProductoDetalleServiceImpl.class);

    private final ProductoDetalleRepository productoDetalleRepository;

    private final ProductoDetalleMapper productoDetalleMapper;

    private final ProductoDetalleSearchRepository productoDetalleSearchRepository;

    public ProductoDetalleServiceImpl(ProductoDetalleRepository productoDetalleRepository, ProductoDetalleMapper productoDetalleMapper, ProductoDetalleSearchRepository productoDetalleSearchRepository) {
        this.productoDetalleRepository = productoDetalleRepository;
        this.productoDetalleMapper = productoDetalleMapper;
        this.productoDetalleSearchRepository = productoDetalleSearchRepository;
    }

    /**
     * Save a productoDetalle.
     *
     * @param productoDetalleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductoDetalleDTO save(ProductoDetalleDTO productoDetalleDTO) {
        log.debug("Request to save ProductoDetalle : {}", productoDetalleDTO);

        ProductoDetalle productoDetalle = productoDetalleMapper.toEntity(productoDetalleDTO);
        productoDetalle = productoDetalleRepository.save(productoDetalle);
        ProductoDetalleDTO result = productoDetalleMapper.toDto(productoDetalle);
        productoDetalleSearchRepository.save(productoDetalle);
        return result;
    }

    /**
     * Get all the productoDetalles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDetalleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoDetalles");
        return productoDetalleRepository.findAll(pageable)
            .map(productoDetalleMapper::toDto);
    }

    /**
     * Get all the ProductoDetalle with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProductoDetalleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productoDetalleRepository.findAllWithEagerRelationships(pageable).map(productoDetalleMapper::toDto);
    }
    

    /**
     * Get one productoDetalle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDetalleDTO> findOne(Long id) {
        log.debug("Request to get ProductoDetalle : {}", id);
        return productoDetalleRepository.findOneWithEagerRelationships(id)
            .map(productoDetalleMapper::toDto);
    }

    /**
     * Delete the productoDetalle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoDetalle : {}", id);
        productoDetalleRepository.deleteById(id);
        productoDetalleSearchRepository.deleteById(id);
    }

    /**
     * Search for the productoDetalle corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDetalleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProductoDetalles for query {}", query);
        return productoDetalleSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(productoDetalleMapper::toDto);
    }

    @Override
    public void reload() {
        List<ProductoDetalle> list =  productoDetalleRepository.findAll();
        list.forEach(productoDetalleSearchRepository::save);
    }
}
