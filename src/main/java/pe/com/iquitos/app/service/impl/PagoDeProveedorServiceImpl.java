package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.PagoDeProveedorService;
import pe.com.iquitos.app.domain.PagoDeProveedor;
import pe.com.iquitos.app.repository.PagoDeProveedorRepository;
import pe.com.iquitos.app.repository.search.PagoDeProveedorSearchRepository;
import pe.com.iquitos.app.service.dto.PagoDeProveedorDTO;
import pe.com.iquitos.app.service.mapper.PagoDeProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PagoDeProveedor.
 */
@Service
@Transactional
public class PagoDeProveedorServiceImpl implements PagoDeProveedorService {

    private final Logger log = LoggerFactory.getLogger(PagoDeProveedorServiceImpl.class);

    private final PagoDeProveedorRepository pagoDeProveedorRepository;

    private final PagoDeProveedorMapper pagoDeProveedorMapper;

    private final PagoDeProveedorSearchRepository pagoDeProveedorSearchRepository;

    public PagoDeProveedorServiceImpl(PagoDeProveedorRepository pagoDeProveedorRepository, PagoDeProveedorMapper pagoDeProveedorMapper, PagoDeProveedorSearchRepository pagoDeProveedorSearchRepository) {
        this.pagoDeProveedorRepository = pagoDeProveedorRepository;
        this.pagoDeProveedorMapper = pagoDeProveedorMapper;
        this.pagoDeProveedorSearchRepository = pagoDeProveedorSearchRepository;
    }

    /**
     * Save a pagoDeProveedor.
     *
     * @param pagoDeProveedorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PagoDeProveedorDTO save(PagoDeProveedorDTO pagoDeProveedorDTO) {
        log.debug("Request to save PagoDeProveedor : {}", pagoDeProveedorDTO);

        PagoDeProveedor pagoDeProveedor = pagoDeProveedorMapper.toEntity(pagoDeProveedorDTO);
        pagoDeProveedor = pagoDeProveedorRepository.save(pagoDeProveedor);
        PagoDeProveedorDTO result = pagoDeProveedorMapper.toDto(pagoDeProveedor);
        pagoDeProveedorSearchRepository.save(pagoDeProveedor);
        return result;
    }

    /**
     * Get all the pagoDeProveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PagoDeProveedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PagoDeProveedors");
        return pagoDeProveedorRepository.findAll(pageable)
            .map(pagoDeProveedorMapper::toDto);
    }


    /**
     * Get one pagoDeProveedor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PagoDeProveedorDTO> findOne(Long id) {
        log.debug("Request to get PagoDeProveedor : {}", id);
        return pagoDeProveedorRepository.findById(id)
            .map(pagoDeProveedorMapper::toDto);
    }

    /**
     * Delete the pagoDeProveedor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PagoDeProveedor : {}", id);
        pagoDeProveedorRepository.deleteById(id);
        pagoDeProveedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the pagoDeProveedor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PagoDeProveedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PagoDeProveedors for query {}", query);
        return pagoDeProveedorSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(pagoDeProveedorMapper::toDto);
    }
}
