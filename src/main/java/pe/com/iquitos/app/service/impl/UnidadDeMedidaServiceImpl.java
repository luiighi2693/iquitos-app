package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.UnidadDeMedidaService;
import pe.com.iquitos.app.domain.UnidadDeMedida;
import pe.com.iquitos.app.repository.UnidadDeMedidaRepository;
import pe.com.iquitos.app.repository.search.UnidadDeMedidaSearchRepository;
import pe.com.iquitos.app.service.dto.UnidadDeMedidaDTO;
import pe.com.iquitos.app.service.mapper.UnidadDeMedidaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UnidadDeMedida.
 */
@Service
@Transactional
public class UnidadDeMedidaServiceImpl implements UnidadDeMedidaService {

    private final Logger log = LoggerFactory.getLogger(UnidadDeMedidaServiceImpl.class);

    private final UnidadDeMedidaRepository unidadDeMedidaRepository;

    private final UnidadDeMedidaMapper unidadDeMedidaMapper;

    private final UnidadDeMedidaSearchRepository unidadDeMedidaSearchRepository;

    public UnidadDeMedidaServiceImpl(UnidadDeMedidaRepository unidadDeMedidaRepository, UnidadDeMedidaMapper unidadDeMedidaMapper, UnidadDeMedidaSearchRepository unidadDeMedidaSearchRepository) {
        this.unidadDeMedidaRepository = unidadDeMedidaRepository;
        this.unidadDeMedidaMapper = unidadDeMedidaMapper;
        this.unidadDeMedidaSearchRepository = unidadDeMedidaSearchRepository;
    }

    /**
     * Save a unidadDeMedida.
     *
     * @param unidadDeMedidaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UnidadDeMedidaDTO save(UnidadDeMedidaDTO unidadDeMedidaDTO) {
        log.debug("Request to save UnidadDeMedida : {}", unidadDeMedidaDTO);

        UnidadDeMedida unidadDeMedida = unidadDeMedidaMapper.toEntity(unidadDeMedidaDTO);
        unidadDeMedida = unidadDeMedidaRepository.save(unidadDeMedida);
        UnidadDeMedidaDTO result = unidadDeMedidaMapper.toDto(unidadDeMedida);
        unidadDeMedidaSearchRepository.save(unidadDeMedida);
        return result;
    }

    /**
     * Get all the unidadDeMedidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnidadDeMedidaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnidadDeMedidas");
        return unidadDeMedidaRepository.findAll(pageable)
            .map(unidadDeMedidaMapper::toDto);
    }


    /**
     * Get one unidadDeMedida by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnidadDeMedidaDTO> findOne(Long id) {
        log.debug("Request to get UnidadDeMedida : {}", id);
        return unidadDeMedidaRepository.findById(id)
            .map(unidadDeMedidaMapper::toDto);
    }

    /**
     * Delete the unidadDeMedida by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnidadDeMedida : {}", id);
        unidadDeMedidaRepository.deleteById(id);
        unidadDeMedidaSearchRepository.deleteById(id);
    }

    /**
     * Search for the unidadDeMedida corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnidadDeMedidaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnidadDeMedidas for query {}", query);
        return unidadDeMedidaSearchRepository.search(queryStringQuery(query), pageable)
            .map(unidadDeMedidaMapper::toDto);
    }
}
