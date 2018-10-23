package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.PurchaseHasProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PurchaseHasProduct.
 */
public interface PurchaseHasProductService {

    /**
     * Save a purchaseHasProduct.
     *
     * @param purchaseHasProductDTO the entity to save
     * @return the persisted entity
     */
    PurchaseHasProductDTO save(PurchaseHasProductDTO purchaseHasProductDTO);

    /**
     * Get all the purchaseHasProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchaseHasProductDTO> findAll(Pageable pageable);


    /**
     * Get the "id" purchaseHasProduct.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PurchaseHasProductDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseHasProduct.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the purchaseHasProduct corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchaseHasProductDTO> search(String query, Pageable pageable);
}
