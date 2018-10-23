package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.DocumentTypePurchaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DocumentTypePurchase.
 */
public interface DocumentTypePurchaseService {

    /**
     * Save a documentTypePurchase.
     *
     * @param documentTypePurchaseDTO the entity to save
     * @return the persisted entity
     */
    DocumentTypePurchaseDTO save(DocumentTypePurchaseDTO documentTypePurchaseDTO);

    /**
     * Get all the documentTypePurchases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentTypePurchaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" documentTypePurchase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DocumentTypePurchaseDTO> findOne(Long id);

    /**
     * Delete the "id" documentTypePurchase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the documentTypePurchase corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentTypePurchaseDTO> search(String query, Pageable pageable);
}
