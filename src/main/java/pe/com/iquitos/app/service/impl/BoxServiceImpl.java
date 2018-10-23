package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.BoxService;
import pe.com.iquitos.app.domain.Box;
import pe.com.iquitos.app.repository.BoxRepository;
import pe.com.iquitos.app.repository.search.BoxSearchRepository;
import pe.com.iquitos.app.service.dto.BoxDTO;
import pe.com.iquitos.app.service.mapper.BoxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Box.
 */
@Service
@Transactional
public class BoxServiceImpl implements BoxService {

    private final Logger log = LoggerFactory.getLogger(BoxServiceImpl.class);

    private BoxRepository boxRepository;

    private BoxMapper boxMapper;

    private BoxSearchRepository boxSearchRepository;

    public BoxServiceImpl(BoxRepository boxRepository, BoxMapper boxMapper, BoxSearchRepository boxSearchRepository) {
        this.boxRepository = boxRepository;
        this.boxMapper = boxMapper;
        this.boxSearchRepository = boxSearchRepository;
    }

    /**
     * Save a box.
     *
     * @param boxDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BoxDTO save(BoxDTO boxDTO) {
        log.debug("Request to save Box : {}", boxDTO);

        Box box = boxMapper.toEntity(boxDTO);
        box = boxRepository.save(box);
        BoxDTO result = boxMapper.toDto(box);
        boxSearchRepository.save(box);
        return result;
    }

    /**
     * Get all the boxes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BoxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Boxes");
        return boxRepository.findAll(pageable)
            .map(boxMapper::toDto);
    }


    /**
     * Get one box by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BoxDTO> findOne(Long id) {
        log.debug("Request to get Box : {}", id);
        return boxRepository.findById(id)
            .map(boxMapper::toDto);
    }

    /**
     * Delete the box by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Box : {}", id);
        boxRepository.deleteById(id);
        boxSearchRepository.deleteById(id);
    }

    /**
     * Search for the box corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BoxDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Boxes for query {}", query);
        return boxSearchRepository.search(queryStringQuery(query), pageable)
            .map(boxMapper::toDto);
    }
}
