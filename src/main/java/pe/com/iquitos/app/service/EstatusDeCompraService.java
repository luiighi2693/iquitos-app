package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.EstatusDeCompraDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EstatusDeCompra.
 */
public interface EstatusDeCompraService {

    /**
     * Save a estatusDeCompra.
     *
     * @param estatusDeCompraDTO the entity to save
     * @return the persisted entity
     */
    EstatusDeCompraDTO save(EstatusDeCompraDTO estatusDeCompraDTO);

    /**
     * Get all the estatusDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstatusDeCompraDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusDeCompra.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EstatusDeCompraDTO> findOne(Long id);

    /**
     * Delete the "id" estatusDeCompra.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the estatusDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstatusDeCompraDTO> search(String query, Pageable pageable);
}
