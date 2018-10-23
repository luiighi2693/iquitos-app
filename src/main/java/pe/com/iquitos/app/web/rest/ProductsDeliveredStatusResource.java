package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ProductsDeliveredStatusService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ProductsDeliveredStatusDTO;
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
 * REST controller for managing ProductsDeliveredStatus.
 */
@RestController
@RequestMapping("/api")
public class ProductsDeliveredStatusResource {

    private final Logger log = LoggerFactory.getLogger(ProductsDeliveredStatusResource.class);

    private static final String ENTITY_NAME = "productsDeliveredStatus";

    private ProductsDeliveredStatusService productsDeliveredStatusService;

    public ProductsDeliveredStatusResource(ProductsDeliveredStatusService productsDeliveredStatusService) {
        this.productsDeliveredStatusService = productsDeliveredStatusService;
    }

    /**
     * POST  /products-delivered-statuses : Create a new productsDeliveredStatus.
     *
     * @param productsDeliveredStatusDTO the productsDeliveredStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productsDeliveredStatusDTO, or with status 400 (Bad Request) if the productsDeliveredStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products-delivered-statuses")
    @Timed
    public ResponseEntity<ProductsDeliveredStatusDTO> createProductsDeliveredStatus(@RequestBody ProductsDeliveredStatusDTO productsDeliveredStatusDTO) throws URISyntaxException {
        log.debug("REST request to save ProductsDeliveredStatus : {}", productsDeliveredStatusDTO);
        if (productsDeliveredStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new productsDeliveredStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductsDeliveredStatusDTO result = productsDeliveredStatusService.save(productsDeliveredStatusDTO);
        return ResponseEntity.created(new URI("/api/products-delivered-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products-delivered-statuses : Updates an existing productsDeliveredStatus.
     *
     * @param productsDeliveredStatusDTO the productsDeliveredStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productsDeliveredStatusDTO,
     * or with status 400 (Bad Request) if the productsDeliveredStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the productsDeliveredStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products-delivered-statuses")
    @Timed
    public ResponseEntity<ProductsDeliveredStatusDTO> updateProductsDeliveredStatus(@RequestBody ProductsDeliveredStatusDTO productsDeliveredStatusDTO) throws URISyntaxException {
        log.debug("REST request to update ProductsDeliveredStatus : {}", productsDeliveredStatusDTO);
        if (productsDeliveredStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductsDeliveredStatusDTO result = productsDeliveredStatusService.save(productsDeliveredStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productsDeliveredStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products-delivered-statuses : get all the productsDeliveredStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productsDeliveredStatuses in body
     */
    @GetMapping("/products-delivered-statuses")
    @Timed
    public ResponseEntity<List<ProductsDeliveredStatusDTO>> getAllProductsDeliveredStatuses(Pageable pageable) {
        log.debug("REST request to get a page of ProductsDeliveredStatuses");
        Page<ProductsDeliveredStatusDTO> page = productsDeliveredStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products-delivered-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /products-delivered-statuses/:id : get the "id" productsDeliveredStatus.
     *
     * @param id the id of the productsDeliveredStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productsDeliveredStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/products-delivered-statuses/{id}")
    @Timed
    public ResponseEntity<ProductsDeliveredStatusDTO> getProductsDeliveredStatus(@PathVariable Long id) {
        log.debug("REST request to get ProductsDeliveredStatus : {}", id);
        Optional<ProductsDeliveredStatusDTO> productsDeliveredStatusDTO = productsDeliveredStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productsDeliveredStatusDTO);
    }

    /**
     * DELETE  /products-delivered-statuses/:id : delete the "id" productsDeliveredStatus.
     *
     * @param id the id of the productsDeliveredStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products-delivered-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductsDeliveredStatus(@PathVariable Long id) {
        log.debug("REST request to delete ProductsDeliveredStatus : {}", id);
        productsDeliveredStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/products-delivered-statuses?query=:query : search for the productsDeliveredStatus corresponding
     * to the query.
     *
     * @param query the query of the productsDeliveredStatus search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/products-delivered-statuses")
    @Timed
    public ResponseEntity<List<ProductsDeliveredStatusDTO>> searchProductsDeliveredStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductsDeliveredStatuses for query {}", query);
        Page<ProductsDeliveredStatusDTO> page = productsDeliveredStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/products-delivered-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
