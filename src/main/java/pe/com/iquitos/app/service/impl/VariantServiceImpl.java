package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.VariantService;
import pe.com.iquitos.app.domain.Variant;
import pe.com.iquitos.app.repository.VariantRepository;
import pe.com.iquitos.app.repository.search.VariantSearchRepository;
import pe.com.iquitos.app.service.dto.VariantDTO;
import pe.com.iquitos.app.service.mapper.VariantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Variant.
 */
@Service
@Transactional
public class VariantServiceImpl implements VariantService {

    private final Logger log = LoggerFactory.getLogger(VariantServiceImpl.class);

    private VariantRepository variantRepository;

    private VariantMapper variantMapper;

    private VariantSearchRepository variantSearchRepository;

    public VariantServiceImpl(VariantRepository variantRepository, VariantMapper variantMapper, VariantSearchRepository variantSearchRepository) {
        this.variantRepository = variantRepository;
        this.variantMapper = variantMapper;
        this.variantSearchRepository = variantSearchRepository;
    }

    /**
     * Save a variant.
     *
     * @param variantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VariantDTO save(VariantDTO variantDTO) {
        log.debug("Request to save Variant : {}", variantDTO);

        Variant variant = variantMapper.toEntity(variantDTO);
        variant = variantRepository.save(variant);
        VariantDTO result = variantMapper.toDto(variant);
        variantSearchRepository.save(variant);
        return result;
    }

    /**
     * Get all the variants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VariantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Variants");
        return variantRepository.findAll(pageable)
            .map(variantMapper::toDto);
    }


    /**
     * Get one variant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VariantDTO> findOne(Long id) {
        log.debug("Request to get Variant : {}", id);
        return variantRepository.findById(id)
            .map(variantMapper::toDto);
    }

    /**
     * Delete the variant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Variant : {}", id);
        variantRepository.deleteById(id);
        variantSearchRepository.deleteById(id);
    }

    /**
     * Search for the variant corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VariantDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Variants for query {}", query);
        return variantSearchRepository.search(queryStringQuery(query), pageable)
            .map(variantMapper::toDto);
    }
}
