package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeCompraDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoDeDocumentoDeCompra.
 */
public interface TipoDeDocumentoDeCompraService {

    /**
     * Save a tipoDeDocumentoDeCompra.
     *
     * @param tipoDeDocumentoDeCompraDTO the entity to save
     * @return the persisted entity
     */
    TipoDeDocumentoDeCompraDTO save(TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO);

    /**
     * Get all the tipoDeDocumentoDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeDocumentoDeCompraDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoDeDocumentoDeCompra.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoDeDocumentoDeCompraDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDeDocumentoDeCompra.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoDeDocumentoDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeDocumentoDeCompraDTO> search(String query, Pageable pageable);
}
