package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.EstatusDeProductoEntregadoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing EstatusDeProductoEntregado.
 */
public interface EstatusDeProductoEntregadoService {

    /**
     * Save a estatusDeProductoEntregado.
     *
     * @param estatusDeProductoEntregadoDTO the entity to save
     * @return the persisted entity
     */
    EstatusDeProductoEntregadoDTO save(EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO);

    /**
     * Get all the estatusDeProductoEntregados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstatusDeProductoEntregadoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" estatusDeProductoEntregado.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EstatusDeProductoEntregadoDTO> findOne(Long id);

    /**
     * Delete the "id" estatusDeProductoEntregado.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the estatusDeProductoEntregado corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EstatusDeProductoEntregadoDTO> search(String query, Pageable pageable);
}
