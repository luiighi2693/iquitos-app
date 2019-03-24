package pe.com.iquitos.app.service;

import pe.com.iquitos.app.domain.Venta;
import pe.com.iquitos.app.service.dto.VentaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.com.iquitos.app.service.dto.custom.VentaDTOCustom;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Venta.
 */
public interface VentaService {

    /**
     * Save a venta.
     *
     * @param ventaDTO the entity to save
     * @return the persisted entity
     */
    VentaDTO save(VentaDTO ventaDTO);

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VentaDTOCustom> findAll(Pageable pageable);

    /**
     * Get all the Venta with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<VentaDTOCustom> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" venta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<VentaDTO> findOne(Long id);

    /**
     * Delete the "id" venta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the venta corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VentaDTO> search(String query, Pageable pageable);

    List<Venta> searchByDateRange(LocalDate start, LocalDate end);

    void reload();
}
