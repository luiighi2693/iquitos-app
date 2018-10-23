package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ProviderAccountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProviderAccount.
 */
public interface ProviderAccountService {

    /**
     * Save a providerAccount.
     *
     * @param providerAccountDTO the entity to save
     * @return the persisted entity
     */
    ProviderAccountDTO save(ProviderAccountDTO providerAccountDTO);

    /**
     * Get all the providerAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProviderAccountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" providerAccount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProviderAccountDTO> findOne(Long id);

    /**
     * Delete the "id" providerAccount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the providerAccount corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProviderAccountDTO> search(String query, Pageable pageable);
}
