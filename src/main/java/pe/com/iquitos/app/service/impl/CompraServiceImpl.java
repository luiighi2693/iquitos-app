package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.CompraService;
import pe.com.iquitos.app.domain.Compra;
import pe.com.iquitos.app.repository.CompraRepository;
import pe.com.iquitos.app.repository.search.CompraSearchRepository;
import pe.com.iquitos.app.service.dto.CompraDTO;
import pe.com.iquitos.app.service.mapper.CompraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Compra.
 */
@Service
@Transactional
public class CompraServiceImpl implements CompraService {

    private final Logger log = LoggerFactory.getLogger(CompraServiceImpl.class);

    private final CompraRepository compraRepository;

    private final CompraMapper compraMapper;

    private final CompraSearchRepository compraSearchRepository;

    public CompraServiceImpl(CompraRepository compraRepository, CompraMapper compraMapper, CompraSearchRepository compraSearchRepository) {
        this.compraRepository = compraRepository;
        this.compraMapper = compraMapper;
        this.compraSearchRepository = compraSearchRepository;
    }

    /**
     * Save a compra.
     *
     * @param compraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompraDTO save(CompraDTO compraDTO) {
        log.debug("Request to save Compra : {}", compraDTO);

        Compra compra = compraMapper.toEntity(compraDTO);
        compra = compraRepository.save(compra);
        CompraDTO result = compraMapper.toDto(compra);
        compraSearchRepository.save(compra);
        return result;
    }

    /**
     * Get all the compras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Compras");
        return compraRepository.findAll(pageable)
            .map(compraMapper::toDto);
    }

    /**
     * Get all the Compra with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<CompraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return compraRepository.findAllWithEagerRelationships(pageable).map(compraMapper::toDto);
    }
    

    /**
     * Get one compra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompraDTO> findOne(Long id) {
        log.debug("Request to get Compra : {}", id);
        return compraRepository.findOneWithEagerRelationships(id)
            .map(compraMapper::toDto);
    }

    /**
     * Delete the compra by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compra : {}", id);
        compraRepository.deleteById(id);
        compraSearchRepository.deleteById(id);
    }

    /**
     * Search for the compra corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompraDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Compras for query {}", query);
        return compraSearchRepository.search(queryStringQuery(query), pageable)
            .map(compraMapper::toDto);
    }
}
