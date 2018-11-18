package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.AmortizacionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Amortizacion.
 */
public interface AmortizacionService {

    /**
     * Save a amortizacion.
     *
     * @param amortizacionDTO the entity to save
     * @return the persisted entity
     */
    AmortizacionDTO save(AmortizacionDTO amortizacionDTO);

    /**
     * Get all the amortizacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AmortizacionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortizacion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AmortizacionDTO> findOne(Long id);

    /**
     * Delete the "id" amortizacion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the amortizacion corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AmortizacionDTO> search(String query, Pageable pageable);
}
