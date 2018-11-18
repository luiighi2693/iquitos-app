package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.TipoDeOperacionDeIngresoService;
import pe.com.iquitos.app.domain.TipoDeOperacionDeIngreso;
import pe.com.iquitos.app.repository.TipoDeOperacionDeIngresoRepository;
import pe.com.iquitos.app.repository.search.TipoDeOperacionDeIngresoSearchRepository;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeIngresoDTO;
import pe.com.iquitos.app.service.mapper.TipoDeOperacionDeIngresoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoDeOperacionDeIngreso.
 */
@Service
@Transactional
public class TipoDeOperacionDeIngresoServiceImpl implements TipoDeOperacionDeIngresoService {

    private final Logger log = LoggerFactory.getLogger(TipoDeOperacionDeIngresoServiceImpl.class);

    private final TipoDeOperacionDeIngresoRepository tipoDeOperacionDeIngresoRepository;

    private final TipoDeOperacionDeIngresoMapper tipoDeOperacionDeIngresoMapper;

    private final TipoDeOperacionDeIngresoSearchRepository tipoDeOperacionDeIngresoSearchRepository;

    public TipoDeOperacionDeIngresoServiceImpl(TipoDeOperacionDeIngresoRepository tipoDeOperacionDeIngresoRepository, TipoDeOperacionDeIngresoMapper tipoDeOperacionDeIngresoMapper, TipoDeOperacionDeIngresoSearchRepository tipoDeOperacionDeIngresoSearchRepository) {
        this.tipoDeOperacionDeIngresoRepository = tipoDeOperacionDeIngresoRepository;
        this.tipoDeOperacionDeIngresoMapper = tipoDeOperacionDeIngresoMapper;
        this.tipoDeOperacionDeIngresoSearchRepository = tipoDeOperacionDeIngresoSearchRepository;
    }

    /**
     * Save a tipoDeOperacionDeIngreso.
     *
     * @param tipoDeOperacionDeIngresoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDeOperacionDeIngresoDTO save(TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO) {
        log.debug("Request to save TipoDeOperacionDeIngreso : {}", tipoDeOperacionDeIngresoDTO);

        TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso = tipoDeOperacionDeIngresoMapper.toEntity(tipoDeOperacionDeIngresoDTO);
        tipoDeOperacionDeIngreso = tipoDeOperacionDeIngresoRepository.save(tipoDeOperacionDeIngreso);
        TipoDeOperacionDeIngresoDTO result = tipoDeOperacionDeIngresoMapper.toDto(tipoDeOperacionDeIngreso);
        tipoDeOperacionDeIngresoSearchRepository.save(tipoDeOperacionDeIngreso);
        return result;
    }

    /**
     * Get all the tipoDeOperacionDeIngresos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeOperacionDeIngresoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeOperacionDeIngresos");
        return tipoDeOperacionDeIngresoRepository.findAll(pageable)
            .map(tipoDeOperacionDeIngresoMapper::toDto);
    }


    /**
     * Get one tipoDeOperacionDeIngreso by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeOperacionDeIngresoDTO> findOne(Long id) {
        log.debug("Request to get TipoDeOperacionDeIngreso : {}", id);
        return tipoDeOperacionDeIngresoRepository.findById(id)
            .map(tipoDeOperacionDeIngresoMapper::toDto);
    }

    /**
     * Delete the tipoDeOperacionDeIngreso by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeOperacionDeIngreso : {}", id);
        tipoDeOperacionDeIngresoRepository.deleteById(id);
        tipoDeOperacionDeIngresoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDeOperacionDeIngreso corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeOperacionDeIngresoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDeOperacionDeIngresos for query {}", query);
        return tipoDeOperacionDeIngresoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoDeOperacionDeIngresoMapper::toDto);
    }
}
