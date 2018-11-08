package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.CuentaProveedorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing CuentaProveedor.
 */
public interface CuentaProveedorService {

    /**
     * Save a cuentaProveedor.
     *
     * @param cuentaProveedorDTO the entity to save
     * @return the persisted entity
     */
    CuentaProveedorDTO save(CuentaProveedorDTO cuentaProveedorDTO);

    /**
     * Get all the cuentaProveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CuentaProveedorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cuentaProveedor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CuentaProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" cuentaProveedor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cuentaProveedor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CuentaProveedorDTO> search(String query, Pageable pageable);
}
