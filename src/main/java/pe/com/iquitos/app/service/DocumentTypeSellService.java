package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.DocumentTypeSellDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DocumentTypeSell.
 */
public interface DocumentTypeSellService {

    /**
     * Save a documentTypeSell.
     *
     * @param documentTypeSellDTO the entity to save
     * @return the persisted entity
     */
    DocumentTypeSellDTO save(DocumentTypeSellDTO documentTypeSellDTO);

    /**
     * Get all the documentTypeSells.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentTypeSellDTO> findAll(Pageable pageable);


    /**
     * Get the "id" documentTypeSell.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DocumentTypeSellDTO> findOne(Long id);

    /**
     * Delete the "id" documentTypeSell.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the documentTypeSell corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentTypeSellDTO> search(String query, Pageable pageable);
}
