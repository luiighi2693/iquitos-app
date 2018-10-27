package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.OrderProductService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.OrderProductDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing OrderProduct.
 */
@RestController
@RequestMapping("/api")
public class OrderProductResource {

    private final Logger log = LoggerFactory.getLogger(OrderProductResource.class);

    private static final String ENTITY_NAME = "orderProduct";

    private OrderProductService orderProductService;

    public OrderProductResource(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    /**
     * POST  /order-products : Create a new orderProduct.
     *
     * @param orderProductDTO the orderProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderProductDTO, or with status 400 (Bad Request) if the orderProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/order-products")
    @Timed
    public ResponseEntity<OrderProductDTO> createOrderProduct(@RequestBody OrderProductDTO orderProductDTO) throws URISyntaxException {
        log.debug("REST request to save OrderProduct : {}", orderProductDTO);
        if (orderProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderProductDTO result = orderProductService.save(orderProductDTO);
        return ResponseEntity.created(new URI("/api/order-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /order-products : Updates an existing orderProduct.
     *
     * @param orderProductDTO the orderProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderProductDTO,
     * or with status 400 (Bad Request) if the orderProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/order-products")
    @Timed
    public ResponseEntity<OrderProductDTO> updateOrderProduct(@RequestBody OrderProductDTO orderProductDTO) throws URISyntaxException {
        log.debug("REST request to update OrderProduct : {}", orderProductDTO);
        if (orderProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderProductDTO result = orderProductService.save(orderProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /order-products : get all the orderProducts.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of orderProducts in body
     */
    @GetMapping("/order-products")
    @Timed
    public ResponseEntity<List<OrderProductDTO>> getAllOrderProducts(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of OrderProducts");
        Page<OrderProductDTO> page;
        if (eagerload) {
            page = orderProductService.findAllWithEagerRelationships(pageable);
        } else {
            page = orderProductService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/order-products?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /order-products/:id : get the "id" orderProduct.
     *
     * @param id the id of the orderProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/order-products/{id}")
    @Timed
    public ResponseEntity<OrderProductDTO> getOrderProduct(@PathVariable Long id) {
        log.debug("REST request to get OrderProduct : {}", id);
        Optional<OrderProductDTO> orderProductDTO = orderProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderProductDTO);
    }

    /**
     * DELETE  /order-products/:id : delete the "id" orderProduct.
     *
     * @param id the id of the orderProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/order-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable Long id) {
        log.debug("REST request to delete OrderProduct : {}", id);
        orderProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/order-products?query=:query : search for the orderProduct corresponding
     * to the query.
     *
     * @param query the query of the orderProduct search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/order-products")
    @Timed
    public ResponseEntity<List<OrderProductDTO>> searchOrderProducts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OrderProducts for query {}", query);
        Page<OrderProductDTO> page = orderProductService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/order-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
