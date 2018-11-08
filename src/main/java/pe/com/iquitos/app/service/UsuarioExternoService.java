package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.UsuarioExternoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UsuarioExterno.
 */
public interface UsuarioExternoService {

    /**
     * Save a usuarioExterno.
     *
     * @param usuarioExternoDTO the entity to save
     * @return the persisted entity
     */
    UsuarioExternoDTO save(UsuarioExternoDTO usuarioExternoDTO);

    /**
     * Get all the usuarioExternos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UsuarioExternoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" usuarioExterno.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UsuarioExternoDTO> findOne(Long id);

    /**
     * Delete the "id" usuarioExterno.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the usuarioExterno corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UsuarioExternoDTO> search(String query, Pageable pageable);
}
