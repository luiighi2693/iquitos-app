package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.TipoDePagoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoDePago.
 */
public interface TipoDePagoService {

    /**
     * Save a tipoDePago.
     *
     * @param tipoDePagoDTO the entity to save
     * @return the persisted entity
     */
    TipoDePagoDTO save(TipoDePagoDTO tipoDePagoDTO);

    /**
     * Get all the tipoDePagos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDePagoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoDePago.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoDePagoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDePago.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoDePago corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDePagoDTO> search(String query, Pageable pageable);

    void reload();
}
