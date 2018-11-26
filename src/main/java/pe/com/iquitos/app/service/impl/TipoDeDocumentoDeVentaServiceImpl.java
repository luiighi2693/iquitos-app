package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.TipoDeDocumentoDeVentaService;
import pe.com.iquitos.app.domain.TipoDeDocumentoDeVenta;
import pe.com.iquitos.app.repository.TipoDeDocumentoDeVentaRepository;
import pe.com.iquitos.app.repository.search.TipoDeDocumentoDeVentaSearchRepository;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeVentaDTO;
import pe.com.iquitos.app.service.mapper.TipoDeDocumentoDeVentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoDeDocumentoDeVenta.
 */
@Service
@Transactional
public class TipoDeDocumentoDeVentaServiceImpl implements TipoDeDocumentoDeVentaService {

    private final Logger log = LoggerFactory.getLogger(TipoDeDocumentoDeVentaServiceImpl.class);

    private final TipoDeDocumentoDeVentaRepository tipoDeDocumentoDeVentaRepository;

    private final TipoDeDocumentoDeVentaMapper tipoDeDocumentoDeVentaMapper;

    private final TipoDeDocumentoDeVentaSearchRepository tipoDeDocumentoDeVentaSearchRepository;

    public TipoDeDocumentoDeVentaServiceImpl(TipoDeDocumentoDeVentaRepository tipoDeDocumentoDeVentaRepository, TipoDeDocumentoDeVentaMapper tipoDeDocumentoDeVentaMapper, TipoDeDocumentoDeVentaSearchRepository tipoDeDocumentoDeVentaSearchRepository) {
        this.tipoDeDocumentoDeVentaRepository = tipoDeDocumentoDeVentaRepository;
        this.tipoDeDocumentoDeVentaMapper = tipoDeDocumentoDeVentaMapper;
        this.tipoDeDocumentoDeVentaSearchRepository = tipoDeDocumentoDeVentaSearchRepository;
    }

    /**
     * Save a tipoDeDocumentoDeVenta.
     *
     * @param tipoDeDocumentoDeVentaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDeDocumentoDeVentaDTO save(TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO) {
        log.debug("Request to save TipoDeDocumentoDeVenta : {}", tipoDeDocumentoDeVentaDTO);

        TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta = tipoDeDocumentoDeVentaMapper.toEntity(tipoDeDocumentoDeVentaDTO);
        tipoDeDocumentoDeVenta = tipoDeDocumentoDeVentaRepository.save(tipoDeDocumentoDeVenta);
        TipoDeDocumentoDeVentaDTO result = tipoDeDocumentoDeVentaMapper.toDto(tipoDeDocumentoDeVenta);
        tipoDeDocumentoDeVentaSearchRepository.save(tipoDeDocumentoDeVenta);
        return result;
    }

    /**
     * Get all the tipoDeDocumentoDeVentas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeDocumentoDeVentaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeDocumentoDeVentas");
        return tipoDeDocumentoDeVentaRepository.findAll(pageable)
            .map(tipoDeDocumentoDeVentaMapper::toDto);
    }


    /**
     * Get one tipoDeDocumentoDeVenta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeDocumentoDeVentaDTO> findOne(Long id) {
        log.debug("Request to get TipoDeDocumentoDeVenta : {}", id);
        return tipoDeDocumentoDeVentaRepository.findById(id)
            .map(tipoDeDocumentoDeVentaMapper::toDto);
    }

    /**
     * Delete the tipoDeDocumentoDeVenta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeDocumentoDeVenta : {}", id);
        tipoDeDocumentoDeVentaRepository.deleteById(id);
        tipoDeDocumentoDeVentaSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDeDocumentoDeVenta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeDocumentoDeVentaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDeDocumentoDeVentas for query {}", query);
        return tipoDeDocumentoDeVentaSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(tipoDeDocumentoDeVentaMapper::toDto);
    }
}
