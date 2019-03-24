package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ProductoDetalleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProductoDetalle.
 */
public interface ProductoDetalleService {

    /**
     * Save a productoDetalle.
     *
     * @param productoDetalleDTO the entity to save
     * @return the persisted entity
     */
    ProductoDetalleDTO save(ProductoDetalleDTO productoDetalleDTO);

    /**
     * Get all the productoDetalles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductoDetalleDTO> findAll(Pageable pageable);

    /**
     * Get all the ProductoDetalle with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<ProductoDetalleDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" productoDetalle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductoDetalleDTO> findOne(Long id);

    /**
     * Delete the "id" productoDetalle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productoDetalle corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductoDetalleDTO> search(String query, Pageable pageable);

    void reload();
}
