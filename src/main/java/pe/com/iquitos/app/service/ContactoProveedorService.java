package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ContactoProveedorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ContactoProveedor.
 */
public interface ContactoProveedorService {

    /**
     * Save a contactoProveedor.
     *
     * @param contactoProveedorDTO the entity to save
     * @return the persisted entity
     */
    ContactoProveedorDTO save(ContactoProveedorDTO contactoProveedorDTO);

    /**
     * Get all the contactoProveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContactoProveedorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" contactoProveedor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ContactoProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" contactoProveedor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the contactoProveedor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContactoProveedorDTO> search(String query, Pageable pageable);
}
