package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.PedidoService;
import pe.com.iquitos.app.domain.Pedido;
import pe.com.iquitos.app.repository.PedidoRepository;
import pe.com.iquitos.app.repository.search.PedidoSearchRepository;
import pe.com.iquitos.app.service.dto.PedidoDTO;
import pe.com.iquitos.app.service.mapper.PedidoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Pedido.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

    private final PedidoSearchRepository pedidoSearchRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, PedidoSearchRepository pedidoSearchRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.pedidoSearchRepository = pedidoSearchRepository;
    }

    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PedidoDTO save(PedidoDTO pedidoDTO) {
        log.debug("Request to save Pedido : {}", pedidoDTO);

        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido = pedidoRepository.save(pedido);
        PedidoDTO result = pedidoMapper.toDto(pedido);
        pedidoSearchRepository.save(pedido);
        return result;
    }

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pedidos");
        return pedidoRepository.findAll(pageable)
            .map(pedidoMapper::toDto);
    }

    /**
     * Get all the Pedido with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PedidoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return pedidoRepository.findAllWithEagerRelationships(pageable).map(pedidoMapper::toDto);
    }
    

    /**
     * Get one pedido by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PedidoDTO> findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findOneWithEagerRelationships(id)
            .map(pedidoMapper::toDto);
    }

    /**
     * Delete the pedido by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
        pedidoSearchRepository.deleteById(id);
    }

    /**
     * Search for the pedido corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PedidoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pedidos for query {}", query);
        return pedidoSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(pedidoMapper::toDto);
    }
}
