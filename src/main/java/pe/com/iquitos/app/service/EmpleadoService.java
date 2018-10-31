package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.EmpleadoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Empleado.
 */
public interface EmpleadoService {

    /**
     * Save a empleado.
     *
     * @param empleadoDTO the entity to save
     * @return the persisted entity
     */
    EmpleadoDTO save(EmpleadoDTO empleadoDTO);

    /**
     * Get all the empleados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmpleadoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" empleado.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmpleadoDTO> findOne(Long id);

    /**
     * Delete the "id" empleado.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the empleado corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmpleadoDTO> search(String query, Pageable pageable);
}
