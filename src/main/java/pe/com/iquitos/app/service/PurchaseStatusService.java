package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.PurchaseStatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PurchaseStatus.
 */
public interface PurchaseStatusService {

    /**
     * Save a purchaseStatus.
     *
     * @param purchaseStatusDTO the entity to save
     * @return the persisted entity
     */
    PurchaseStatusDTO save(PurchaseStatusDTO purchaseStatusDTO);

    /**
     * Get all the purchaseStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchaseStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" purchaseStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PurchaseStatusDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the purchaseStatus corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchaseStatusDTO> search(String query, Pageable pageable);
}
