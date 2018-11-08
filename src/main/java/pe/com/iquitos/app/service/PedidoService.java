package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.PedidoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Pedido.
 */
public interface PedidoService {

    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save
     * @return the persisted entity
     */
    PedidoDTO save(PedidoDTO pedidoDTO);

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PedidoDTO> findAll(Pageable pageable);

    /**
     * Get all the Pedido with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<PedidoDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" pedido.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PedidoDTO> findOne(Long id);

    /**
     * Delete the "id" pedido.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pedido corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PedidoDTO> search(String query, Pageable pageable);
}
