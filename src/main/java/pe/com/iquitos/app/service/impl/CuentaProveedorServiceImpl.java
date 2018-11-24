package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.CuentaProveedorService;
import pe.com.iquitos.app.domain.CuentaProveedor;
import pe.com.iquitos.app.repository.CuentaProveedorRepository;
import pe.com.iquitos.app.repository.search.CuentaProveedorSearchRepository;
import pe.com.iquitos.app.service.dto.CuentaProveedorDTO;
import pe.com.iquitos.app.service.mapper.CuentaProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CuentaProveedor.
 */
@Service
@Transactional
public class CuentaProveedorServiceImpl implements CuentaProveedorService {

    private final Logger log = LoggerFactory.getLogger(CuentaProveedorServiceImpl.class);

    private final CuentaProveedorRepository cuentaProveedorRepository;

    private final CuentaProveedorMapper cuentaProveedorMapper;

    private final CuentaProveedorSearchRepository cuentaProveedorSearchRepository;

    public CuentaProveedorServiceImpl(CuentaProveedorRepository cuentaProveedorRepository, CuentaProveedorMapper cuentaProveedorMapper, CuentaProveedorSearchRepository cuentaProveedorSearchRepository) {
        this.cuentaProveedorRepository = cuentaProveedorRepository;
        this.cuentaProveedorMapper = cuentaProveedorMapper;
        this.cuentaProveedorSearchRepository = cuentaProveedorSearchRepository;
    }

    /**
     * Save a cuentaProveedor.
     *
     * @param cuentaProveedorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CuentaProveedorDTO save(CuentaProveedorDTO cuentaProveedorDTO) {
        log.debug("Request to save CuentaProveedor : {}", cuentaProveedorDTO);

        CuentaProveedor cuentaProveedor = cuentaProveedorMapper.toEntity(cuentaProveedorDTO);
        cuentaProveedor = cuentaProveedorRepository.save(cuentaProveedor);
        CuentaProveedorDTO result = cuentaProveedorMapper.toDto(cuentaProveedor);
        cuentaProveedorSearchRepository.save(cuentaProveedor);
        return result;
    }

    /**
     * Get all the cuentaProveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CuentaProveedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CuentaProveedors");
        return cuentaProveedorRepository.findAll(pageable)
            .map(cuentaProveedorMapper::toDto);
    }


    /**
     * Get one cuentaProveedor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CuentaProveedorDTO> findOne(Long id) {
        log.debug("Request to get CuentaProveedor : {}", id);
        return cuentaProveedorRepository.findById(id)
            .map(cuentaProveedorMapper::toDto);
    }

    /**
     * Delete the cuentaProveedor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CuentaProveedor : {}", id);
        cuentaProveedorRepository.deleteById(id);
        cuentaProveedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the cuentaProveedor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CuentaProveedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CuentaProveedors for query {}", query);
        return cuentaProveedorSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(cuentaProveedorMapper::toDto);
    }
}
