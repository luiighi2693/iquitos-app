package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ProviderService;
import pe.com.iquitos.app.domain.Provider;
import pe.com.iquitos.app.repository.ProviderRepository;
import pe.com.iquitos.app.repository.search.ProviderSearchRepository;
import pe.com.iquitos.app.service.dto.ProviderDTO;
import pe.com.iquitos.app.service.mapper.ProviderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Provider.
 */
@Service
@Transactional
public class ProviderServiceImpl implements ProviderService {

    private final Logger log = LoggerFactory.getLogger(ProviderServiceImpl.class);

    private ProviderRepository providerRepository;

    private ProviderMapper providerMapper;

    private ProviderSearchRepository providerSearchRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository, ProviderMapper providerMapper, ProviderSearchRepository providerSearchRepository) {
        this.providerRepository = providerRepository;
        this.providerMapper = providerMapper;
        this.providerSearchRepository = providerSearchRepository;
    }

    /**
     * Save a provider.
     *
     * @param providerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderDTO save(ProviderDTO providerDTO) {
        log.debug("Request to save Provider : {}", providerDTO);

        Provider provider = providerMapper.toEntity(providerDTO);
        provider = providerRepository.save(provider);
        ProviderDTO result = providerMapper.toDto(provider);
        providerSearchRepository.save(provider);
        return result;
    }

    /**
     * Get all the providers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProviderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Providers");
        return providerRepository.findAll(pageable)
            .map(providerMapper::toDto);
    }


    /**
     * Get one provider by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProviderDTO> findOne(Long id) {
        log.debug("Request to get Provider : {}", id);
        return providerRepository.findById(id)
            .map(providerMapper::toDto);
    }

    /**
     * Delete the provider by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Provider : {}", id);
        providerRepository.deleteById(id);
        providerSearchRepository.deleteById(id);
    }

    /**
     * Search for the provider corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProviderDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Providers for query {}", query);
        return providerSearchRepository.search(queryStringQuery(query), pageable)
            .map(providerMapper::toDto);
    }
}
