package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.domain.TipoDeDocumentoDeVenta;
import pe.com.iquitos.app.service.AmortizacionService;
import pe.com.iquitos.app.domain.Amortizacion;
import pe.com.iquitos.app.repository.AmortizacionRepository;
import pe.com.iquitos.app.repository.search.AmortizacionSearchRepository;
import pe.com.iquitos.app.service.dto.AmortizacionDTO;
import pe.com.iquitos.app.service.mapper.AmortizacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Amortizacion.
 */
@Service
@Transactional
public class AmortizacionServiceImpl implements AmortizacionService {

    private final Logger log = LoggerFactory.getLogger(AmortizacionServiceImpl.class);

    private final AmortizacionRepository amortizacionRepository;

    private final AmortizacionMapper amortizacionMapper;

    private final AmortizacionSearchRepository amortizacionSearchRepository;

    public AmortizacionServiceImpl(AmortizacionRepository amortizacionRepository, AmortizacionMapper amortizacionMapper, AmortizacionSearchRepository amortizacionSearchRepository) {
        this.amortizacionRepository = amortizacionRepository;
        this.amortizacionMapper = amortizacionMapper;
        this.amortizacionSearchRepository = amortizacionSearchRepository;
    }

    /**
     * Save a amortizacion.
     *
     * @param amortizacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AmortizacionDTO save(AmortizacionDTO amortizacionDTO) {
        log.debug("Request to save Amortizacion : {}", amortizacionDTO);

        Amortizacion amortizacion = amortizacionMapper.toEntity(amortizacionDTO);
        amortizacion = amortizacionRepository.save(amortizacion);
        AmortizacionDTO result = amortizacionMapper.toDto(amortizacion);
        amortizacionSearchRepository.save(amortizacion);
        return result;
    }

    /**
     * Get all the amortizacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Amortizacions");
        return amortizacionRepository.findAll(pageable)
            .map(amortizacionMapper::toDto);
    }


    /**
     * Get one amortizacion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AmortizacionDTO> findOne(Long id) {
        log.debug("Request to get Amortizacion : {}", id);
        return amortizacionRepository.findById(id)
            .map(amortizacionMapper::toDto);
    }

    /**
     * Delete the amortizacion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Amortizacion : {}", id);
        amortizacionRepository.deleteById(id);
        amortizacionSearchRepository.deleteById(id);
    }

    /**
     * Search for the amortizacion corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AmortizacionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Amortizacions for query {}", query);
        return amortizacionSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(amortizacionMapper::toDto);
    }

    @Override
    public Optional<Long> countAmortizacionsByTipoDeDocumentoDeVentaId(Long tipoDeDocumentoDeVentaId) {
        log.debug("Request to countAmortizacionsByTipoDeDocumentoDeVenta : {}", tipoDeDocumentoDeVentaId);
        return amortizacionRepository.countAmortizacionsByTipoDeDocumentoDeVentaId(tipoDeDocumentoDeVentaId);
    }
}
