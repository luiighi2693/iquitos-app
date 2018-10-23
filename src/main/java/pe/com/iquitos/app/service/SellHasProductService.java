package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.SellHasProductDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SellHasProduct.
 */
public interface SellHasProductService {

    /**
     * Save a sellHasProduct.
     *
     * @param sellHasProductDTO the entity to save
     * @return the persisted entity
     */
    SellHasProductDTO save(SellHasProductDTO sellHasProductDTO);

    /**
     * Get all the sellHasProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SellHasProductDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sellHasProduct.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SellHasProductDTO> findOne(Long id);

    /**
     * Delete the "id" sellHasProduct.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sellHasProduct corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SellHasProductDTO> search(String query, Pageable pageable);
}
