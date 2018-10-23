package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ProductsDeliveredStatusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProductsDeliveredStatus.
 */
public interface ProductsDeliveredStatusService {

    /**
     * Save a productsDeliveredStatus.
     *
     * @param productsDeliveredStatusDTO the entity to save
     * @return the persisted entity
     */
    ProductsDeliveredStatusDTO save(ProductsDeliveredStatusDTO productsDeliveredStatusDTO);

    /**
     * Get all the productsDeliveredStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductsDeliveredStatusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productsDeliveredStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductsDeliveredStatusDTO> findOne(Long id);

    /**
     * Delete the "id" productsDeliveredStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productsDeliveredStatus corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductsDeliveredStatusDTO> search(String query, Pageable pageable);
}
