package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeVentaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoDeDocumentoDeVenta.
 */
public interface TipoDeDocumentoDeVentaService {

    /**
     * Save a tipoDeDocumentoDeVenta.
     *
     * @param tipoDeDocumentoDeVentaDTO the entity to save
     * @return the persisted entity
     */
    TipoDeDocumentoDeVentaDTO save(TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO);

    /**
     * Get all the tipoDeDocumentoDeVentas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeDocumentoDeVentaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoDeDocumentoDeVenta.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoDeDocumentoDeVentaDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDeDocumentoDeVenta.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoDeDocumentoDeVenta corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeDocumentoDeVentaDTO> search(String query, Pageable pageable);
}
