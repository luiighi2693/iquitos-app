package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.VarianteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Variante.
 */
public interface VarianteService {

    /**
     * Save a variante.
     *
     * @param varianteDTO the entity to save
     * @return the persisted entity
     */
    VarianteDTO save(VarianteDTO varianteDTO);

    /**
     * Get all the variantes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VarianteDTO> findAll(Pageable pageable);

    /**
     * Get all the Variante with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<VarianteDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" variante.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VarianteDTO> findOne(Long id);

    /**
     * Delete the "id" variante.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the variante corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VarianteDTO> search(String query, Pageable pageable);
}
