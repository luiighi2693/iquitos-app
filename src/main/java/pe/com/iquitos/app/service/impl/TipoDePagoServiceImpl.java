package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.TipoDePagoService;
import pe.com.iquitos.app.domain.TipoDePago;
import pe.com.iquitos.app.repository.TipoDePagoRepository;
import pe.com.iquitos.app.repository.search.TipoDePagoSearchRepository;
import pe.com.iquitos.app.service.dto.TipoDePagoDTO;
import pe.com.iquitos.app.service.mapper.TipoDePagoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoDePago.
 */
@Service
@Transactional
public class TipoDePagoServiceImpl implements TipoDePagoService {

    private final Logger log = LoggerFactory.getLogger(TipoDePagoServiceImpl.class);

    private TipoDePagoRepository tipoDePagoRepository;

    private TipoDePagoMapper tipoDePagoMapper;

    private TipoDePagoSearchRepository tipoDePagoSearchRepository;

    public TipoDePagoServiceImpl(TipoDePagoRepository tipoDePagoRepository, TipoDePagoMapper tipoDePagoMapper, TipoDePagoSearchRepository tipoDePagoSearchRepository) {
        this.tipoDePagoRepository = tipoDePagoRepository;
        this.tipoDePagoMapper = tipoDePagoMapper;
        this.tipoDePagoSearchRepository = tipoDePagoSearchRepository;
    }

    /**
     * Save a tipoDePago.
     *
     * @param tipoDePagoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDePagoDTO save(TipoDePagoDTO tipoDePagoDTO) {
        log.debug("Request to save TipoDePago : {}", tipoDePagoDTO);

        TipoDePago tipoDePago = tipoDePagoMapper.toEntity(tipoDePagoDTO);
        tipoDePago = tipoDePagoRepository.save(tipoDePago);
        TipoDePagoDTO result = tipoDePagoMapper.toDto(tipoDePago);
        tipoDePagoSearchRepository.save(tipoDePago);
        return result;
    }

    /**
     * Get all the tipoDePagos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDePagoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDePagos");
        return tipoDePagoRepository.findAll(pageable)
            .map(tipoDePagoMapper::toDto);
    }


    /**
     * Get one tipoDePago by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDePagoDTO> findOne(Long id) {
        log.debug("Request to get TipoDePago : {}", id);
        return tipoDePagoRepository.findById(id)
            .map(tipoDePagoMapper::toDto);
    }

    /**
     * Delete the tipoDePago by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDePago : {}", id);
        tipoDePagoRepository.deleteById(id);
        tipoDePagoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDePago corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDePagoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDePagos for query {}", query);
        return tipoDePagoSearchRepository.search(queryStringQuery(query), pageable)
            .map(tipoDePagoMapper::toDto);
    }
}
