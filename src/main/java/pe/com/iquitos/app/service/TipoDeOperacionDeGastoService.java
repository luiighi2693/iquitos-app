package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.TipoDeOperacionDeGastoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoDeOperacionDeGasto.
 */
public interface TipoDeOperacionDeGastoService {

    /**
     * Save a tipoDeOperacionDeGasto.
     *
     * @param tipoDeOperacionDeGastoDTO the entity to save
     * @return the persisted entity
     */
    TipoDeOperacionDeGastoDTO save(TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO);

    /**
     * Get all the tipoDeOperacionDeGastos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeOperacionDeGastoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoDeOperacionDeGasto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoDeOperacionDeGastoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDeOperacionDeGasto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoDeOperacionDeGasto corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeOperacionDeGastoDTO> search(String query, Pageable pageable);
}
