package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.OperacionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Operacion.
 */
public interface OperacionService {

    /**
     * Save a operacion.
     *
     * @param operacionDTO the entity to save
     * @return the persisted entity
     */
    OperacionDTO save(OperacionDTO operacionDTO);

    /**
     * Get all the operacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OperacionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" operacion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OperacionDTO> findOne(Long id);

    /**
     * Delete the "id" operacion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the operacion corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OperacionDTO> search(String query, Pageable pageable);
}
