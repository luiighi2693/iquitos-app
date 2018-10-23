package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.BoxDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Box.
 */
public interface BoxService {

    /**
     * Save a box.
     *
     * @param boxDTO the entity to save
     * @return the persisted entity
     */
    BoxDTO save(BoxDTO boxDTO);

    /**
     * Get all the boxes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BoxDTO> findAll(Pageable pageable);


    /**
     * Get the "id" box.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BoxDTO> findOne(Long id);

    /**
     * Delete the "id" box.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the box corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BoxDTO> search(String query, Pageable pageable);
}
