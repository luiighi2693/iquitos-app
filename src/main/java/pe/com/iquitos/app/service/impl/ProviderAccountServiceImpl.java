package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ProviderAccountService;
import pe.com.iquitos.app.domain.ProviderAccount;
import pe.com.iquitos.app.repository.ProviderAccountRepository;
import pe.com.iquitos.app.repository.search.ProviderAccountSearchRepository;
import pe.com.iquitos.app.service.dto.ProviderAccountDTO;
import pe.com.iquitos.app.service.mapper.ProviderAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProviderAccount.
 */
@Service
@Transactional
public class ProviderAccountServiceImpl implements ProviderAccountService {

    private final Logger log = LoggerFactory.getLogger(ProviderAccountServiceImpl.class);

    private ProviderAccountRepository providerAccountRepository;

    private ProviderAccountMapper providerAccountMapper;

    private ProviderAccountSearchRepository providerAccountSearchRepository;

    public ProviderAccountServiceImpl(ProviderAccountRepository providerAccountRepository, ProviderAccountMapper providerAccountMapper, ProviderAccountSearchRepository providerAccountSearchRepository) {
        this.providerAccountRepository = providerAccountRepository;
        this.providerAccountMapper = providerAccountMapper;
        this.providerAccountSearchRepository = providerAccountSearchRepository;
    }

    /**
     * Save a providerAccount.
     *
     * @param providerAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderAccountDTO save(ProviderAccountDTO providerAccountDTO) {
        log.debug("Request to save ProviderAccount : {}", providerAccountDTO);

        ProviderAccount providerAccount = providerAccountMapper.toEntity(providerAccountDTO);
        providerAccount = providerAccountRepository.save(providerAccount);
        ProviderAccountDTO result = providerAccountMapper.toDto(providerAccount);
        providerAccountSearchRepository.save(providerAccount);
        return result;
    }

    /**
     * Get all the providerAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProviderAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProviderAccounts");
        return providerAccountRepository.findAll(pageable)
            .map(providerAccountMapper::toDto);
    }


    /**
     * Get one providerAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProviderAccountDTO> findOne(Long id) {
        log.debug("Request to get ProviderAccount : {}", id);
        return providerAccountRepository.findById(id)
            .map(providerAccountMapper::toDto);
    }

    /**
     * Delete the providerAccount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProviderAccount : {}", id);
        providerAccountRepository.deleteById(id);
        providerAccountSearchRepository.deleteById(id);
    }

    /**
     * Search for the providerAccount corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProviderAccountDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProviderAccounts for query {}", query);
        return providerAccountSearchRepository.search(queryStringQuery(query), pageable)
            .map(providerAccountMapper::toDto);
    }
}
