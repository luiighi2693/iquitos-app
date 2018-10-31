package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.PagoDeProveedorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PagoDeProveedor.
 */
public interface PagoDeProveedorService {

    /**
     * Save a pagoDeProveedor.
     *
     * @param pagoDeProveedorDTO the entity to save
     * @return the persisted entity
     */
    PagoDeProveedorDTO save(PagoDeProveedorDTO pagoDeProveedorDTO);

    /**
     * Get all the pagoDeProveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PagoDeProveedorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pagoDeProveedor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PagoDeProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" pagoDeProveedor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pagoDeProveedor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PagoDeProveedorDTO> search(String query, Pageable pageable);
}
