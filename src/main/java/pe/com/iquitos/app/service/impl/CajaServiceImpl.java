package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.CajaService;
import pe.com.iquitos.app.domain.Caja;
import pe.com.iquitos.app.repository.CajaRepository;
import pe.com.iquitos.app.repository.search.CajaSearchRepository;
import pe.com.iquitos.app.service.dto.CajaDTO;
import pe.com.iquitos.app.service.mapper.CajaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Caja.
 */
@Service
@Transactional
public class CajaServiceImpl implements CajaService {

    private final Logger log = LoggerFactory.getLogger(CajaServiceImpl.class);

    private CajaRepository cajaRepository;

    private CajaMapper cajaMapper;

    private CajaSearchRepository cajaSearchRepository;

    public CajaServiceImpl(CajaRepository cajaRepository, CajaMapper cajaMapper, CajaSearchRepository cajaSearchRepository) {
        this.cajaRepository = cajaRepository;
        this.cajaMapper = cajaMapper;
        this.cajaSearchRepository = cajaSearchRepository;
    }

    /**
     * Save a caja.
     *
     * @param cajaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CajaDTO save(CajaDTO cajaDTO) {
        log.debug("Request to save Caja : {}", cajaDTO);

        Caja caja = cajaMapper.toEntity(cajaDTO);
        caja = cajaRepository.save(caja);
        CajaDTO result = cajaMapper.toDto(caja);
        cajaSearchRepository.save(caja);
        return result;
    }

    /**
     * Get all the cajas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CajaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cajas");
        return cajaRepository.findAll(pageable)
            .map(cajaMapper::toDto);
    }


    /**
     * Get one caja by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CajaDTO> findOne(Long id) {
        log.debug("Request to get Caja : {}", id);
        return cajaRepository.findById(id)
            .map(cajaMapper::toDto);
    }

    /**
     * Delete the caja by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Caja : {}", id);
        cajaRepository.deleteById(id);
        cajaSearchRepository.deleteById(id);
    }

    /**
     * Search for the caja corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CajaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cajas for query {}", query);
        return cajaSearchRepository.search(queryStringQuery(query), pageable)
            .map(cajaMapper::toDto);
    }
}
