package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.CreditoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Credito.
 */
public interface CreditoService {

    /**
     * Save a credito.
     *
     * @param creditoDTO the entity to save
     * @return the persisted entity
     */
    CreditoDTO save(CreditoDTO creditoDTO);

    /**
     * Get all the creditos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CreditoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" credito.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CreditoDTO> findOne(Long id);

    /**
     * Delete the "id" credito.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the credito corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CreditoDTO> search(String query, Pageable pageable);
}
