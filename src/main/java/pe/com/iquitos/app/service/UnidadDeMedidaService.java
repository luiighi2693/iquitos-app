package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.UnidadDeMedidaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UnidadDeMedida.
 */
public interface UnidadDeMedidaService {

    /**
     * Save a unidadDeMedida.
     *
     * @param unidadDeMedidaDTO the entity to save
     * @return the persisted entity
     */
    UnidadDeMedidaDTO save(UnidadDeMedidaDTO unidadDeMedidaDTO);

    /**
     * Get all the unidadDeMedidas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UnidadDeMedidaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" unidadDeMedida.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UnidadDeMedidaDTO> findOne(Long id);

    /**
     * Delete the "id" unidadDeMedida.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the unidadDeMedida corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UnidadDeMedidaDTO> search(String query, Pageable pageable);
}
