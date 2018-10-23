package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.UnitMeasurementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UnitMeasurement.
 */
public interface UnitMeasurementService {

    /**
     * Save a unitMeasurement.
     *
     * @param unitMeasurementDTO the entity to save
     * @return the persisted entity
     */
    UnitMeasurementDTO save(UnitMeasurementDTO unitMeasurementDTO);

    /**
     * Get all the unitMeasurements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UnitMeasurementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" unitMeasurement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UnitMeasurementDTO> findOne(Long id);

    /**
     * Delete the "id" unitMeasurement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the unitMeasurement corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UnitMeasurementDTO> search(String query, Pageable pageable);
}
