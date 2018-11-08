package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.CompraDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Compra.
 */
public interface CompraService {

    /**
     * Save a compra.
     *
     * @param compraDTO the entity to save
     * @return the persisted entity
     */
    CompraDTO save(CompraDTO compraDTO);

    /**
     * Get all the compras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompraDTO> findAll(Pageable pageable);

    /**
     * Get all the Compra with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<CompraDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" compra.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CompraDTO> findOne(Long id);

    /**
     * Delete the "id" compra.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the compra corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompraDTO> search(String query, Pageable pageable);
}
