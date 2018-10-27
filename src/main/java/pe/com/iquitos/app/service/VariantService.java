package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.VariantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Variant.
 */
public interface VariantService {

    /**
     * Save a variant.
     *
     * @param variantDTO the entity to save
     * @return the persisted entity
     */
    VariantDTO save(VariantDTO variantDTO);

    /**
     * Get all the variants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VariantDTO> findAll(Pageable pageable);

    /**
     * Get all the Variant with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<VariantDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" variant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VariantDTO> findOne(Long id);

    /**
     * Delete the "id" variant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the variant corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VariantDTO> search(String query, Pageable pageable);
}
