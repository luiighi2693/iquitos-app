package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.DocumentTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DocumentType.
 */
public interface DocumentTypeService {

    /**
     * Save a documentType.
     *
     * @param documentTypeDTO the entity to save
     * @return the persisted entity
     */
    DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO);

    /**
     * Get all the documentTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" documentType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DocumentTypeDTO> findOne(Long id);

    /**
     * Delete the "id" documentType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the documentType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentTypeDTO> search(String query, Pageable pageable);
}
