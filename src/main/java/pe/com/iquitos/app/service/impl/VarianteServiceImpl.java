package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.VarianteService;
import pe.com.iquitos.app.domain.Variante;
import pe.com.iquitos.app.repository.VarianteRepository;
import pe.com.iquitos.app.repository.search.VarianteSearchRepository;
import pe.com.iquitos.app.service.dto.VarianteDTO;
import pe.com.iquitos.app.service.mapper.VarianteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Variante.
 */
@Service
@Transactional
public class VarianteServiceImpl implements VarianteService {

    private final Logger log = LoggerFactory.getLogger(VarianteServiceImpl.class);

    private final VarianteRepository varianteRepository;

    private final VarianteMapper varianteMapper;

    private final VarianteSearchRepository varianteSearchRepository;

    public VarianteServiceImpl(VarianteRepository varianteRepository, VarianteMapper varianteMapper, VarianteSearchRepository varianteSearchRepository) {
        this.varianteRepository = varianteRepository;
        this.varianteMapper = varianteMapper;
        this.varianteSearchRepository = varianteSearchRepository;
    }

    /**
     * Save a variante.
     *
     * @param varianteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VarianteDTO save(VarianteDTO varianteDTO) {
        log.debug("Request to save Variante : {}", varianteDTO);

        Variante variante = varianteMapper.toEntity(varianteDTO);
        variante = varianteRepository.save(variante);
        VarianteDTO result = varianteMapper.toDto(variante);
        varianteSearchRepository.save(variante);
        return result;
    }

    /**
     * Get all the variantes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VarianteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Variantes");
        return varianteRepository.findAll(pageable)
            .map(varianteMapper::toDto);
    }

    /**
     * Get all the Variante with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<VarianteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return varianteRepository.findAllWithEagerRelationships(pageable).map(varianteMapper::toDto);
    }
    

    /**
     * Get one variante by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VarianteDTO> findOne(Long id) {
        log.debug("Request to get Variante : {}", id);
        return varianteRepository.findOneWithEagerRelationships(id)
            .map(varianteMapper::toDto);
    }

    /**
     * Delete the variante by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Variante : {}", id);
        varianteRepository.deleteById(id);
        varianteSearchRepository.deleteById(id);
    }

    /**
     * Search for the variante corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VarianteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Variantes for query {}", query);
        return varianteSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(varianteMapper::toDto);
    }
}
