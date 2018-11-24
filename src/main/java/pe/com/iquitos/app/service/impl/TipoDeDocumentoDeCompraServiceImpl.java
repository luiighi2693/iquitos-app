package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.TipoDeDocumentoDeCompraService;
import pe.com.iquitos.app.domain.TipoDeDocumentoDeCompra;
import pe.com.iquitos.app.repository.TipoDeDocumentoDeCompraRepository;
import pe.com.iquitos.app.repository.search.TipoDeDocumentoDeCompraSearchRepository;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeCompraDTO;
import pe.com.iquitos.app.service.mapper.TipoDeDocumentoDeCompraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TipoDeDocumentoDeCompra.
 */
@Service
@Transactional
public class TipoDeDocumentoDeCompraServiceImpl implements TipoDeDocumentoDeCompraService {

    private final Logger log = LoggerFactory.getLogger(TipoDeDocumentoDeCompraServiceImpl.class);

    private final TipoDeDocumentoDeCompraRepository tipoDeDocumentoDeCompraRepository;

    private final TipoDeDocumentoDeCompraMapper tipoDeDocumentoDeCompraMapper;

    private final TipoDeDocumentoDeCompraSearchRepository tipoDeDocumentoDeCompraSearchRepository;

    public TipoDeDocumentoDeCompraServiceImpl(TipoDeDocumentoDeCompraRepository tipoDeDocumentoDeCompraRepository, TipoDeDocumentoDeCompraMapper tipoDeDocumentoDeCompraMapper, TipoDeDocumentoDeCompraSearchRepository tipoDeDocumentoDeCompraSearchRepository) {
        this.tipoDeDocumentoDeCompraRepository = tipoDeDocumentoDeCompraRepository;
        this.tipoDeDocumentoDeCompraMapper = tipoDeDocumentoDeCompraMapper;
        this.tipoDeDocumentoDeCompraSearchRepository = tipoDeDocumentoDeCompraSearchRepository;
    }

    /**
     * Save a tipoDeDocumentoDeCompra.
     *
     * @param tipoDeDocumentoDeCompraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDeDocumentoDeCompraDTO save(TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO) {
        log.debug("Request to save TipoDeDocumentoDeCompra : {}", tipoDeDocumentoDeCompraDTO);

        TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompraMapper.toEntity(tipoDeDocumentoDeCompraDTO);
        tipoDeDocumentoDeCompra = tipoDeDocumentoDeCompraRepository.save(tipoDeDocumentoDeCompra);
        TipoDeDocumentoDeCompraDTO result = tipoDeDocumentoDeCompraMapper.toDto(tipoDeDocumentoDeCompra);
        tipoDeDocumentoDeCompraSearchRepository.save(tipoDeDocumentoDeCompra);
        return result;
    }

    /**
     * Get all the tipoDeDocumentoDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeDocumentoDeCompraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeDocumentoDeCompras");
        return tipoDeDocumentoDeCompraRepository.findAll(pageable)
            .map(tipoDeDocumentoDeCompraMapper::toDto);
    }


    /**
     * Get one tipoDeDocumentoDeCompra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeDocumentoDeCompraDTO> findOne(Long id) {
        log.debug("Request to get TipoDeDocumentoDeCompra : {}", id);
        return tipoDeDocumentoDeCompraRepository.findById(id)
            .map(tipoDeDocumentoDeCompraMapper::toDto);
    }

    /**
     * Delete the tipoDeDocumentoDeCompra by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeDocumentoDeCompra : {}", id);
        tipoDeDocumentoDeCompraRepository.deleteById(id);
        tipoDeDocumentoDeCompraSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDeDocumentoDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeDocumentoDeCompraDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDeDocumentoDeCompras for query {}", query);
        return tipoDeDocumentoDeCompraSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(tipoDeDocumentoDeCompraMapper::toDto);
    }
}
