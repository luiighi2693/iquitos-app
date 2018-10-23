package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ConceptOperationInputDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ConceptOperationInput.
 */
public interface ConceptOperationInputService {

    /**
     * Save a conceptOperationInput.
     *
     * @param conceptOperationInputDTO the entity to save
     * @return the persisted entity
     */
    ConceptOperationInputDTO save(ConceptOperationInputDTO conceptOperationInputDTO);

    /**
     * Get all the conceptOperationInputs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConceptOperationInputDTO> findAll(Pageable pageable);


    /**
     * Get the "id" conceptOperationInput.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ConceptOperationInputDTO> findOne(Long id);

    /**
     * Delete the "id" conceptOperationInput.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the conceptOperationInput corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConceptOperationInputDTO> search(String query, Pageable pageable);
}
