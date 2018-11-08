package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.TipoDeOperacionDeGastoService;
import pe.com.iquitos.app.domain.TipoDeOperacionDeGasto;
import pe.com.iquitos.app.repository.TipoDeOperacionDeGastoRepository;
import pe.com.iquitos.app.repository.search.TipoDeOperacionDeGastoSearchRepository;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeGastoDTO;
import pe.com.iquitos.app.service.mapper.TipoDeOperacionDeGastoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoDeOperacionDeGasto.
 */
@Service
@Transactional
public class TipoDeOperacionDeGastoServiceImpl implements TipoDeOperacionDeGastoService {

    private final Logger log = LoggerFactory.getLogger(TipoDeOperacionDeGastoServiceImpl.class);

    private final TipoDeOperacionDeGastoRepository tipoDeOperacionDeGastoRepository;

    private final TipoDeOperacionDeGastoMapper tipoDeOperacionDeGastoMapper;

    private final TipoDeOperacionDeGastoSearchRepository tipoDeOperacionDeGastoSearchRepository;

    public TipoDeOperacionDeGastoServiceImpl(TipoDeOperacionDeGastoRepository tipoDeOperacionDeGastoRepository, TipoDeOperacionDeGastoMapper tipoDeOperacionDeGastoMapper, TipoDeOperacionDeGastoSearchRepository tipoDeOperacionDeGastoSearchRepository) {
        this.tipoDeOperacionDeGastoRepository = tipoDeOperacionDeGastoRepository;
        this.tipoDeOperacionDeGastoMapper = tipoDeOperacionDeGastoMapper;
        this.tipoDeOperacionDeGastoSearchRepository = tipoDeOperacionDeGastoSearchRepository;
    }

    /**
     * Save a tipoDeOperacionDeGasto.
     *
     * @param tipoDeOperacionDeGastoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDeOperacionDeGastoDTO save(TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO) {
        log.debug("Request to save TipoDeOperacionDeGasto : {}", tipoDeOperacionDeGastoDTO);

        TipoDeOperacionDeGasto tipoDeOperacionDeGasto = tipoDeOperacionDeGastoMapper.toEntity(tipoDeOperacionDeGastoDTO);
        tipoDeOperacionDeGasto = tipoDeOperacionDeGastoRepository.save(tipoDeOperacionDeGasto);
        TipoDeOperacionDeGastoDTO result = tipoDeOperacionDeGastoMapper.toDto(tipoDeOperacionDeGasto);
        tipoDeOperacionDeGastoSearchRepository.save(tipoDeOperacionDeGasto);
        return result;
    }

    /**
     * Get all the tipoDeOperacionDeGastos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeOperacionDeGastoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeOperacionDeGastos");
        return tipoDeOperacionDeGastoRepository.findAll(pageable)
            .map(tipoDeOperacionDeGastoMapper::toDto);
    }


    /**
     * Get one tipoDeOperacionDeGasto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeOperacionDeGastoDTO> findOne(Long id) {
        log.debug("Request to get TipoDeOperacionDeGasto : {}", id);
        return tipoDeOperacionDeGastoRepository.findById(id)
            .map(tipoDeOperacionDeGastoMapper::toDto);
    }

    /**
     * Delete the tipoDeOperacionDeGasto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeOperacionDeGasto : {}", id);
        tipoDeOperacionDeGastoRepository.deleteById(id);
        tipoDeOperacionDeGastoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDeOperacionDeGasto corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeOperacionDeGastoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDeOperacionDeGastos for query {}", query);
        return tipoDeOperacionDeGastoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoDeOperacionDeGastoMapper::toDto);
    }
}
