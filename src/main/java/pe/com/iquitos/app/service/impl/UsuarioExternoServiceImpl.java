package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.UsuarioExternoService;
import pe.com.iquitos.app.domain.UsuarioExterno;
import pe.com.iquitos.app.repository.UsuarioExternoRepository;
import pe.com.iquitos.app.repository.search.UsuarioExternoSearchRepository;
import pe.com.iquitos.app.service.dto.UsuarioExternoDTO;
import pe.com.iquitos.app.service.mapper.UsuarioExternoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UsuarioExterno.
 */
@Service
@Transactional
public class UsuarioExternoServiceImpl implements UsuarioExternoService {

    private final Logger log = LoggerFactory.getLogger(UsuarioExternoServiceImpl.class);

    private final UsuarioExternoRepository usuarioExternoRepository;

    private final UsuarioExternoMapper usuarioExternoMapper;

    private final UsuarioExternoSearchRepository usuarioExternoSearchRepository;

    public UsuarioExternoServiceImpl(UsuarioExternoRepository usuarioExternoRepository, UsuarioExternoMapper usuarioExternoMapper, UsuarioExternoSearchRepository usuarioExternoSearchRepository) {
        this.usuarioExternoRepository = usuarioExternoRepository;
        this.usuarioExternoMapper = usuarioExternoMapper;
        this.usuarioExternoSearchRepository = usuarioExternoSearchRepository;
    }

    /**
     * Save a usuarioExterno.
     *
     * @param usuarioExternoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UsuarioExternoDTO save(UsuarioExternoDTO usuarioExternoDTO) {
        log.debug("Request to save UsuarioExterno : {}", usuarioExternoDTO);

        UsuarioExterno usuarioExterno = usuarioExternoMapper.toEntity(usuarioExternoDTO);
        usuarioExterno = usuarioExternoRepository.save(usuarioExterno);
        UsuarioExternoDTO result = usuarioExternoMapper.toDto(usuarioExterno);
        usuarioExternoSearchRepository.save(usuarioExterno);
        return result;
    }

    /**
     * Get all the usuarioExternos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioExternoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UsuarioExternos");
        return usuarioExternoRepository.findAll(pageable)
            .map(usuarioExternoMapper::toDto);
    }


    /**
     * Get one usuarioExterno by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioExternoDTO> findOne(Long id) {
        log.debug("Request to get UsuarioExterno : {}", id);
        return usuarioExternoRepository.findById(id)
            .map(usuarioExternoMapper::toDto);
    }

    /**
     * Delete the usuarioExterno by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsuarioExterno : {}", id);
        usuarioExternoRepository.deleteById(id);
        usuarioExternoSearchRepository.deleteById(id);
    }

    /**
     * Search for the usuarioExterno corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioExternoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UsuarioExternos for query {}", query);
        return usuarioExternoSearchRepository.search(queryStringQuery(query), pageable)
            .map(usuarioExternoMapper::toDto);
    }
}
