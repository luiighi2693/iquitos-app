package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.AmortizationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Amortization.
 */
public interface AmortizationService {

    /**
     * Save a amortization.
     *
     * @param amortizationDTO the entity to save
     * @return the persisted entity
     */
    AmortizationDTO save(AmortizationDTO amortizationDTO);

    /**
     * Get all the amortizations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AmortizationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" amortization.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AmortizationDTO> findOne(Long id);

    /**
     * Delete the "id" amortization.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the amortization corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AmortizationDTO> search(String query, Pageable pageable);
}
