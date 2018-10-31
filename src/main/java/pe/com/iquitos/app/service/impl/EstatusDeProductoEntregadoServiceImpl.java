package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.EstatusDeProductoEntregadoService;
import pe.com.iquitos.app.domain.EstatusDeProductoEntregado;
import pe.com.iquitos.app.repository.EstatusDeProductoEntregadoRepository;
import pe.com.iquitos.app.repository.search.EstatusDeProductoEntregadoSearchRepository;
import pe.com.iquitos.app.service.dto.EstatusDeProductoEntregadoDTO;
import pe.com.iquitos.app.service.mapper.EstatusDeProductoEntregadoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EstatusDeProductoEntregado.
 */
@Service
@Transactional
public class EstatusDeProductoEntregadoServiceImpl implements EstatusDeProductoEntregadoService {

    private final Logger log = LoggerFactory.getLogger(EstatusDeProductoEntregadoServiceImpl.class);

    private EstatusDeProductoEntregadoRepository estatusDeProductoEntregadoRepository;

    private EstatusDeProductoEntregadoMapper estatusDeProductoEntregadoMapper;

    private EstatusDeProductoEntregadoSearchRepository estatusDeProductoEntregadoSearchRepository;

    public EstatusDeProductoEntregadoServiceImpl(EstatusDeProductoEntregadoRepository estatusDeProductoEntregadoRepository, EstatusDeProductoEntregadoMapper estatusDeProductoEntregadoMapper, EstatusDeProductoEntregadoSearchRepository estatusDeProductoEntregadoSearchRepository) {
        this.estatusDeProductoEntregadoRepository = estatusDeProductoEntregadoRepository;
        this.estatusDeProductoEntregadoMapper = estatusDeProductoEntregadoMapper;
        this.estatusDeProductoEntregadoSearchRepository = estatusDeProductoEntregadoSearchRepository;
    }

    /**
     * Save a estatusDeProductoEntregado.
     *
     * @param estatusDeProductoEntregadoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EstatusDeProductoEntregadoDTO save(EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO) {
        log.debug("Request to save EstatusDeProductoEntregado : {}", estatusDeProductoEntregadoDTO);

        EstatusDeProductoEntregado estatusDeProductoEntregado = estatusDeProductoEntregadoMapper.toEntity(estatusDeProductoEntregadoDTO);
        estatusDeProductoEntregado = estatusDeProductoEntregadoRepository.save(estatusDeProductoEntregado);
        EstatusDeProductoEntregadoDTO result = estatusDeProductoEntregadoMapper.toDto(estatusDeProductoEntregado);
        estatusDeProductoEntregadoSearchRepository.save(estatusDeProductoEntregado);
        return result;
    }

    /**
     * Get all the estatusDeProductoEntregados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusDeProductoEntregadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstatusDeProductoEntregados");
        return estatusDeProductoEntregadoRepository.findAll(pageable)
            .map(estatusDeProductoEntregadoMapper::toDto);
    }


    /**
     * Get one estatusDeProductoEntregado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusDeProductoEntregadoDTO> findOne(Long id) {
        log.debug("Request to get EstatusDeProductoEntregado : {}", id);
        return estatusDeProductoEntregadoRepository.findById(id)
            .map(estatusDeProductoEntregadoMapper::toDto);
    }

    /**
     * Delete the estatusDeProductoEntregado by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusDeProductoEntregado : {}", id);
        estatusDeProductoEntregadoRepository.deleteById(id);
        estatusDeProductoEntregadoSearchRepository.deleteById(id);
    }

    /**
     * Search for the estatusDeProductoEntregado corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EstatusDeProductoEntregadoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EstatusDeProductoEntregados for query {}", query);
        return estatusDeProductoEntregadoSearchRepository.search(queryStringQuery(query), pageable)
            .map(estatusDeProductoEntregadoMapper::toDto);
    }
}
