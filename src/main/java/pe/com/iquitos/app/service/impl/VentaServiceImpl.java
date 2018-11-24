package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.VentaService;
import pe.com.iquitos.app.domain.Venta;
import pe.com.iquitos.app.repository.VentaRepository;
import pe.com.iquitos.app.repository.search.VentaSearchRepository;
import pe.com.iquitos.app.service.dto.VentaDTO;
import pe.com.iquitos.app.service.mapper.VentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Venta.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    private final VentaSearchRepository ventaSearchRepository;

    public VentaServiceImpl(VentaRepository ventaRepository, VentaMapper ventaMapper, VentaSearchRepository ventaSearchRepository) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.ventaSearchRepository = ventaSearchRepository;
    }

    /**
     * Save a venta.
     *
     * @param ventaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);

        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        VentaDTO result = ventaMapper.toDto(venta);
        ventaSearchRepository.save(venta);
        return result;
    }

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable)
            .map(ventaMapper::toDto);
    }

    /**
     * Get all the Venta with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<VentaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ventaRepository.findAllWithEagerRelationships(pageable).map(ventaMapper::toDto);
    }
    

    /**
     * Get one venta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findOneWithEagerRelationships(id)
            .map(ventaMapper::toDto);
    }

    /**
     * Delete the venta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
        ventaSearchRepository.deleteById(id);
    }

    /**
     * Search for the venta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VentaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ventas for query {}", query);
        return ventaSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(ventaMapper::toDto);
    }
}
