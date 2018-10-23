package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ConceptOperationOutputDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ConceptOperationOutput.
 */
public interface ConceptOperationOutputService {

    /**
     * Save a conceptOperationOutput.
     *
     * @param conceptOperationOutputDTO the entity to save
     * @return the persisted entity
     */
    ConceptOperationOutputDTO save(ConceptOperationOutputDTO conceptOperationOutputDTO);

    /**
     * Get all the conceptOperationOutputs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConceptOperationOutputDTO> findAll(Pageable pageable);


    /**
     * Get the "id" conceptOperationOutput.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConceptOperationOutputDTO> findOne(Long id);

    /**
     * Delete the "id" conceptOperationOutput.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the conceptOperationOutput corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConceptOperationOutputDTO> search(String query, Pageable pageable);
}
