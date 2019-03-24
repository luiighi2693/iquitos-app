package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.TipoDeDocumentoService;
import pe.com.iquitos.app.domain.TipoDeDocumento;
import pe.com.iquitos.app.repository.TipoDeDocumentoRepository;
import pe.com.iquitos.app.repository.search.TipoDeDocumentoSearchRepository;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDTO;
import pe.com.iquitos.app.service.mapper.TipoDeDocumentoMapper;
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
 * Service Implementation for managing TipoDeDocumento.
 */
@Service
@Transactional
public class TipoDeDocumentoServiceImpl implements TipoDeDocumentoService {

    private final Logger log = LoggerFactory.getLogger(TipoDeDocumentoServiceImpl.class);

    private final TipoDeDocumentoRepository tipoDeDocumentoRepository;

    private final TipoDeDocumentoMapper tipoDeDocumentoMapper;

    private final TipoDeDocumentoSearchRepository tipoDeDocumentoSearchRepository;

    public TipoDeDocumentoServiceImpl(TipoDeDocumentoRepository tipoDeDocumentoRepository, TipoDeDocumentoMapper tipoDeDocumentoMapper, TipoDeDocumentoSearchRepository tipoDeDocumentoSearchRepository) {
        this.tipoDeDocumentoRepository = tipoDeDocumentoRepository;
        this.tipoDeDocumentoMapper = tipoDeDocumentoMapper;
        this.tipoDeDocumentoSearchRepository = tipoDeDocumentoSearchRepository;
    }

    /**
     * Save a tipoDeDocumento.
     *
     * @param tipoDeDocumentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoDeDocumentoDTO save(TipoDeDocumentoDTO tipoDeDocumentoDTO) {
        log.debug("Request to save TipoDeDocumento : {}", tipoDeDocumentoDTO);

        TipoDeDocumento tipoDeDocumento = tipoDeDocumentoMapper.toEntity(tipoDeDocumentoDTO);
        tipoDeDocumento = tipoDeDocumentoRepository.save(tipoDeDocumento);
        TipoDeDocumentoDTO result = tipoDeDocumentoMapper.toDto(tipoDeDocumento);
        tipoDeDocumentoSearchRepository.save(tipoDeDocumento);
        return result;
    }

    /**
     * Get all the tipoDeDocumentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeDocumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoDeDocumentos");
        return tipoDeDocumentoRepository.findAll(pageable)
            .map(tipoDeDocumentoMapper::toDto);
    }


    /**
     * Get one tipoDeDocumento by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoDeDocumentoDTO> findOne(Long id) {
        log.debug("Request to get TipoDeDocumento : {}", id);
        return tipoDeDocumentoRepository.findById(id)
            .map(tipoDeDocumentoMapper::toDto);
    }

    /**
     * Delete the tipoDeDocumento by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoDeDocumento : {}", id);
        tipoDeDocumentoRepository.deleteById(id);
        tipoDeDocumentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the tipoDeDocumento corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoDeDocumentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TipoDeDocumentos for query {}", query);
        return tipoDeDocumentoSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(tipoDeDocumentoMapper::toDto);
    }

    @Override
    public void reload() {
        List<TipoDeDocumento> list =  tipoDeDocumentoRepository.findAll();
        list.forEach(tipoDeDocumentoSearchRepository::save);
    }
}
