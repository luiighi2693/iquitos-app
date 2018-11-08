package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.TipoDeOperacionDeIngresoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoDeOperacionDeIngreso.
 */
public interface TipoDeOperacionDeIngresoService {

    /**
     * Save a tipoDeOperacionDeIngreso.
     *
     * @param tipoDeOperacionDeIngresoDTO the entity to save
     * @return the persisted entity
     */
    TipoDeOperacionDeIngresoDTO save(TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO);

    /**
     * Get all the tipoDeOperacionDeIngresos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeOperacionDeIngresoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoDeOperacionDeIngreso.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoDeOperacionDeIngresoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDeOperacionDeIngreso.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoDeOperacionDeIngreso corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeOperacionDeIngresoDTO> search(String query, Pageable pageable);
}
