package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.UnitMeasurementService;
import pe.com.iquitos.app.domain.UnitMeasurement;
import pe.com.iquitos.app.repository.UnitMeasurementRepository;
import pe.com.iquitos.app.repository.search.UnitMeasurementSearchRepository;
import pe.com.iquitos.app.service.dto.UnitMeasurementDTO;
import pe.com.iquitos.app.service.mapper.UnitMeasurementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UnitMeasurement.
 */
@Service
@Transactional
public class UnitMeasurementServiceImpl implements UnitMeasurementService {

    private final Logger log = LoggerFactory.getLogger(UnitMeasurementServiceImpl.class);

    private UnitMeasurementRepository unitMeasurementRepository;

    private UnitMeasurementMapper unitMeasurementMapper;

    private UnitMeasurementSearchRepository unitMeasurementSearchRepository;

    public UnitMeasurementServiceImpl(UnitMeasurementRepository unitMeasurementRepository, UnitMeasurementMapper unitMeasurementMapper, UnitMeasurementSearchRepository unitMeasurementSearchRepository) {
        this.unitMeasurementRepository = unitMeasurementRepository;
        this.unitMeasurementMapper = unitMeasurementMapper;
        this.unitMeasurementSearchRepository = unitMeasurementSearchRepository;
    }

    /**
     * Save a unitMeasurement.
     *
     * @param unitMeasurementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UnitMeasurementDTO save(UnitMeasurementDTO unitMeasurementDTO) {
        log.debug("Request to save UnitMeasurement : {}", unitMeasurementDTO);

        UnitMeasurement unitMeasurement = unitMeasurementMapper.toEntity(unitMeasurementDTO);
        unitMeasurement = unitMeasurementRepository.save(unitMeasurement);
        UnitMeasurementDTO result = unitMeasurementMapper.toDto(unitMeasurement);
        unitMeasurementSearchRepository.save(unitMeasurement);
        return result;
    }

    /**
     * Get all the unitMeasurements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnitMeasurementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnitMeasurements");
        return unitMeasurementRepository.findAll(pageable)
            .map(unitMeasurementMapper::toDto);
    }


    /**
     * Get one unitMeasurement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnitMeasurementDTO> findOne(Long id) {
        log.debug("Request to get UnitMeasurement : {}", id);
        return unitMeasurementRepository.findById(id)
            .map(unitMeasurementMapper::toDto);
    }

    /**
     * Delete the unitMeasurement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnitMeasurement : {}", id);
        unitMeasurementRepository.deleteById(id);
        unitMeasurementSearchRepository.deleteById(id);
    }

    /**
     * Search for the unitMeasurement corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UnitMeasurementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UnitMeasurements for query {}", query);
        return unitMeasurementSearchRepository.search(queryStringQuery(query), pageable)
            .map(unitMeasurementMapper::toDto);
    }
}
