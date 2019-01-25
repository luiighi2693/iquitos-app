package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ParametroSistemaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ParametroSistema.
 */
public interface ParametroSistemaService {

    /**
     * Save a parametroSistema.
     *
     * @param parametroSistemaDTO the entity to save
     * @return the persisted entity
     */
    ParametroSistemaDTO save(ParametroSistemaDTO parametroSistemaDTO);

    /**
     * Get all the parametroSistemas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParametroSistemaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" parametroSistema.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ParametroSistemaDTO> findOne(Long id);

    /**
     * Delete the "id" parametroSistema.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the parametroSistema corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ParametroSistemaDTO> search(String query, Pageable pageable);
}
