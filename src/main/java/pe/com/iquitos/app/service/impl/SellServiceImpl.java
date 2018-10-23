package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.SellService;
import pe.com.iquitos.app.domain.Sell;
import pe.com.iquitos.app.repository.SellRepository;
import pe.com.iquitos.app.repository.search.SellSearchRepository;
import pe.com.iquitos.app.service.dto.SellDTO;
import pe.com.iquitos.app.service.mapper.SellMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Sell.
 */
@Service
@Transactional
public class SellServiceImpl implements SellService {

    private final Logger log = LoggerFactory.getLogger(SellServiceImpl.class);

    private SellRepository sellRepository;

    private SellMapper sellMapper;

    private SellSearchRepository sellSearchRepository;

    public SellServiceImpl(SellRepository sellRepository, SellMapper sellMapper, SellSearchRepository sellSearchRepository) {
        this.sellRepository = sellRepository;
        this.sellMapper = sellMapper;
        this.sellSearchRepository = sellSearchRepository;
    }

    /**
     * Save a sell.
     *
     * @param sellDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SellDTO save(SellDTO sellDTO) {
        log.debug("Request to save Sell : {}", sellDTO);

        Sell sell = sellMapper.toEntity(sellDTO);
        sell = sellRepository.save(sell);
        SellDTO result = sellMapper.toDto(sell);
        sellSearchRepository.save(sell);
        return result;
    }

    /**
     * Get all the sells.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SellDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sells");
        return sellRepository.findAll(pageable)
            .map(sellMapper::toDto);
    }


    /**
     * Get one sell by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SellDTO> findOne(Long id) {
        log.debug("Request to get Sell : {}", id);
        return sellRepository.findById(id)
            .map(sellMapper::toDto);
    }

    /**
     * Delete the sell by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sell : {}", id);
        sellRepository.deleteById(id);
        sellSearchRepository.deleteById(id);
    }

    /**
     * Search for the sell corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SellDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sells for query {}", query);
        return sellSearchRepository.search(queryStringQuery(query), pageable)
            .map(sellMapper::toDto);
    }
}
