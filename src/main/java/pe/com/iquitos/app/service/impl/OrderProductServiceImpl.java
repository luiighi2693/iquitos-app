package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.OrderProductService;
import pe.com.iquitos.app.domain.OrderProduct;
import pe.com.iquitos.app.repository.OrderProductRepository;
import pe.com.iquitos.app.repository.search.OrderProductSearchRepository;
import pe.com.iquitos.app.service.dto.OrderProductDTO;
import pe.com.iquitos.app.service.mapper.OrderProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OrderProduct.
 */
@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

    private final Logger log = LoggerFactory.getLogger(OrderProductServiceImpl.class);

    private OrderProductRepository orderProductRepository;

    private OrderProductMapper orderProductMapper;

    private OrderProductSearchRepository orderProductSearchRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository, OrderProductMapper orderProductMapper, OrderProductSearchRepository orderProductSearchRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderProductMapper = orderProductMapper;
        this.orderProductSearchRepository = orderProductSearchRepository;
    }

    /**
     * Save a orderProduct.
     *
     * @param orderProductDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OrderProductDTO save(OrderProductDTO orderProductDTO) {
        log.debug("Request to save OrderProduct : {}", orderProductDTO);

        OrderProduct orderProduct = orderProductMapper.toEntity(orderProductDTO);
        orderProduct = orderProductRepository.save(orderProduct);
        OrderProductDTO result = orderProductMapper.toDto(orderProduct);
        orderProductSearchRepository.save(orderProduct);
        return result;
    }

    /**
     * Get all the orderProducts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderProducts");
        return orderProductRepository.findAll(pageable)
            .map(orderProductMapper::toDto);
    }

    /**
     * Get all the OrderProduct with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<OrderProductDTO> findAllWithEagerRelationships(Pageable pageable) {
        return orderProductRepository.findAllWithEagerRelationships(pageable).map(orderProductMapper::toDto);
    }
    

    /**
     * Get one orderProduct by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderProductDTO> findOne(Long id) {
        log.debug("Request to get OrderProduct : {}", id);
        return orderProductRepository.findOneWithEagerRelationships(id)
            .map(orderProductMapper::toDto);
    }

    /**
     * Delete the orderProduct by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderProduct : {}", id);
        orderProductRepository.deleteById(id);
        orderProductSearchRepository.deleteById(id);
    }

    /**
     * Search for the orderProduct corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderProductDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OrderProducts for query {}", query);
        return orderProductSearchRepository.search(queryStringQuery(query), pageable)
            .map(orderProductMapper::toDto);
    }
}
