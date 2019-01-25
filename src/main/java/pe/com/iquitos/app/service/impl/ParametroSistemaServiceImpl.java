package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ParametroSistemaService;
import pe.com.iquitos.app.domain.ParametroSistema;
import pe.com.iquitos.app.repository.ParametroSistemaRepository;
import pe.com.iquitos.app.repository.search.ParametroSistemaSearchRepository;
import pe.com.iquitos.app.service.dto.ParametroSistemaDTO;
import pe.com.iquitos.app.service.mapper.ParametroSistemaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ParametroSistema.
 */
@Service
@Transactional
public class ParametroSistemaServiceImpl implements ParametroSistemaService {

    private final Logger log = LoggerFactory.getLogger(ParametroSistemaServiceImpl.class);

    private final ParametroSistemaRepository parametroSistemaRepository;

    private final ParametroSistemaMapper parametroSistemaMapper;

    private final ParametroSistemaSearchRepository parametroSistemaSearchRepository;

    public ParametroSistemaServiceImpl(ParametroSistemaRepository parametroSistemaRepository, ParametroSistemaMapper parametroSistemaMapper, ParametroSistemaSearchRepository parametroSistemaSearchRepository) {
        this.parametroSistemaRepository = parametroSistemaRepository;
        this.parametroSistemaMapper = parametroSistemaMapper;
        this.parametroSistemaSearchRepository = parametroSistemaSearchRepository;
    }

    /**
     * Save a parametroSistema.
     *
     * @param parametroSistemaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ParametroSistemaDTO save(ParametroSistemaDTO parametroSistemaDTO) {
        log.debug("Request to save ParametroSistema : {}", parametroSistemaDTO);

        ParametroSistema parametroSistema = parametroSistemaMapper.toEntity(parametroSistemaDTO);
        parametroSistema = parametroSistemaRepository.save(parametroSistema);
        ParametroSistemaDTO result = parametroSistemaMapper.toDto(parametroSistema);
        parametroSistemaSearchRepository.save(parametroSistema);
        return result;
    }

    /**
     * Get all the parametroSistemas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParametroSistemaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ParametroSistemas");
        return parametroSistemaRepository.findAll(pageable)
            .map(parametroSistemaMapper::toDto);
    }


    /**
     * Get one parametroSistema by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ParametroSistemaDTO> findOne(Long id) {
        log.debug("Request to get ParametroSistema : {}", id);
        return parametroSistemaRepository.findById(id)
            .map(parametroSistemaMapper::toDto);
    }

    /**
     * Delete the parametroSistema by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ParametroSistema : {}", id);
        parametroSistemaRepository.deleteById(id);
        parametroSistemaSearchRepository.deleteById(id);
    }

    /**
     * Search for the parametroSistema corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ParametroSistemaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ParametroSistemas for query {}", query);
        return parametroSistemaSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(parametroSistemaMapper::toDto);
    }
}
