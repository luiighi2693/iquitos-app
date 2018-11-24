package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.EstatusDeCompraService;
import pe.com.iquitos.app.domain.EstatusDeCompra;
import pe.com.iquitos.app.repository.EstatusDeCompraRepository;
import pe.com.iquitos.app.repository.search.EstatusDeCompraSearchRepository;
import pe.com.iquitos.app.service.dto.EstatusDeCompraDTO;
import pe.com.iquitos.app.service.mapper.EstatusDeCompraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EstatusDeCompra.
 */
@Service
@Transactional
public class EstatusDeCompraServiceImpl implements EstatusDeCompraService {

    private final Logger log = LoggerFactory.getLogger(EstatusDeCompraServiceImpl.class);

    private final EstatusDeCompraRepository estatusDeCompraRepository;

    private final EstatusDeCompraMapper estatusDeCompraMapper;

    private final EstatusDeCompraSearchRepository estatusDeCompraSearchRepository;

    public EstatusDeCompraServiceImpl(EstatusDeCompraRepository estatusDeCompraRepository, EstatusDeCompraMapper estatusDeCompraMapper, EstatusDeCompraSearchRepository estatusDeCompraSearchRepository) {
        this.estatusDeCompraRepository = estatusDeCompraRepository;
        this.estatusDeCompraMapper = estatusDeCompraMapper;
        this.estatusDeCompraSearchRepository = estatusDeCompraSearchRepository;
    }

    /**
     * Save a estatusDeCompra.
     *
     * @param estatusDeCompraDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstatusDeCompraDTO save(EstatusDeCompraDTO estatusDeCompraDTO) {
        log.debug("Request to save EstatusDeCompra : {}", estatusDeCompraDTO);

        EstatusDeCompra estatusDeCompra = estatusDeCompraMapper.toEntity(estatusDeCompraDTO);
        estatusDeCompra = estatusDeCompraRepository.save(estatusDeCompra);
        EstatusDeCompraDTO result = estatusDeCompraMapper.toDto(estatusDeCompra);
        estatusDeCompraSearchRepository.save(estatusDeCompra);
        return result;
    }

    /**
     * Get all the estatusDeCompras.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusDeCompraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusDeCompras");
        return estatusDeCompraRepository.findAll(pageable)
            .map(estatusDeCompraMapper::toDto);
    }


    /**
     * Get one estatusDeCompra by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusDeCompraDTO> findOne(Long id) {
        log.debug("Request to get EstatusDeCompra : {}", id);
        return estatusDeCompraRepository.findById(id)
            .map(estatusDeCompraMapper::toDto);
    }

    /**
     * Delete the estatusDeCompra by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusDeCompra : {}", id);
        estatusDeCompraRepository.deleteById(id);
        estatusDeCompraSearchRepository.deleteById(id);
    }

    /**
     * Search for the estatusDeCompra corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusDeCompraDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EstatusDeCompras for query {}", query);
        return estatusDeCompraSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(estatusDeCompraMapper::toDto);
    }
}
