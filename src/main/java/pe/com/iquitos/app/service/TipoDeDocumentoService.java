package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.TipoDeDocumentoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TipoDeDocumento.
 */
public interface TipoDeDocumentoService {

    /**
     * Save a tipoDeDocumento.
     *
     * @param tipoDeDocumentoDTO the entity to save
     * @return the persisted entity
     */
    TipoDeDocumentoDTO save(TipoDeDocumentoDTO tipoDeDocumentoDTO);

    /**
     * Get all the tipoDeDocumentos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeDocumentoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoDeDocumento.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TipoDeDocumentoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoDeDocumento.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tipoDeDocumento corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TipoDeDocumentoDTO> search(String query, Pageable pageable);

    void reload();
}
