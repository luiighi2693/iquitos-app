package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ProductoDetalleService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ProductoDetalleDTO;
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
 * REST controller for managing ProductoDetalle.
 */
@RestController
@RequestMapping("/api")
public class ProductoDetalleResource {

    private final Logger log = LoggerFactory.getLogger(ProductoDetalleResource.class);

    private static final String ENTITY_NAME = "productoDetalle";

    private final ProductoDetalleService productoDetalleService;

    public ProductoDetalleResource(ProductoDetalleService productoDetalleService) {
        this.productoDetalleService = productoDetalleService;
    }

    /**
     * POST  /producto-detalles : Create a new productoDetalle.
     *
     * @param productoDetalleDTO the productoDetalleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productoDetalleDTO, or with status 400 (Bad Request) if the productoDetalle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/producto-detalles")
    @Timed
    public ResponseEntity<ProductoDetalleDTO> createProductoDetalle(@RequestBody ProductoDetalleDTO productoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to save ProductoDetalle : {}", productoDetalleDTO);
        if (productoDetalleDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoDetalleDTO result = productoDetalleService.save(productoDetalleDTO);
        return ResponseEntity.created(new URI("/api/producto-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /producto-detalles : Updates an existing productoDetalle.
     *
     * @param productoDetalleDTO the productoDetalleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productoDetalleDTO,
     * or with status 400 (Bad Request) if the productoDetalleDTO is not valid,
     * or with status 500 (Internal Server Error) if the productoDetalleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/producto-detalles")
    @Timed
    public ResponseEntity<ProductoDetalleDTO> updateProductoDetalle(@RequestBody ProductoDetalleDTO productoDetalleDTO) throws URISyntaxException {
        log.debug("REST request to update ProductoDetalle : {}", productoDetalleDTO);
        if (productoDetalleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoDetalleDTO result = productoDetalleService.save(productoDetalleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productoDetalleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /producto-detalles : get all the productoDetalles.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of productoDetalles in body
     */
    @GetMapping("/producto-detalles")
    @Timed
    public ResponseEntity<List<ProductoDetalleDTO>> getAllProductoDetalles(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ProductoDetalles");
        Page<ProductoDetalleDTO> page;
        if (eagerload) {
            page = productoDetalleService.findAllWithEagerRelationships(pageable);
        } else {
            page = productoDetalleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/producto-detalles?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /producto-detalles/:id : get the "id" productoDetalle.
     *
     * @param id the id of the productoDetalleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productoDetalleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/producto-detalles/{id}")
    @Timed
    public ResponseEntity<ProductoDetalleDTO> getProductoDetalle(@PathVariable Long id) {
        log.debug("REST request to get ProductoDetalle : {}", id);
        Optional<ProductoDetalleDTO> productoDetalleDTO = productoDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoDetalleDTO);
    }

    /**
     * DELETE  /producto-detalles/:id : delete the "id" productoDetalle.
     *
     * @param id the id of the productoDetalleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/producto-detalles/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductoDetalle(@PathVariable Long id) {
        log.debug("REST request to delete ProductoDetalle : {}", id);
        productoDetalleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/producto-detalles?query=:query : search for the productoDetalle corresponding
     * to the query.
     *
     * @param query the query of the productoDetalle search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/producto-detalles")
    @Timed
    public ResponseEntity<List<ProductoDetalleDTO>> searchProductoDetalles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductoDetalles for query {}", query);
        Page<ProductoDetalleDTO> page = productoDetalleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/producto-detalles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/producto-detalles/reload")
    @Timed
    public ResponseEntity<String> reload() {
        log.debug("RELOAD ITEMS");
        productoDetalleService.reload();
        System.out.println("*/*/*/*/*/*/*/*/*/*/*/*producto-detalles loaded*/*/*/*/*/*/*/*/*/");
        return new ResponseEntity<>("producto-detalles loaded", HttpStatus.OK);
    }

}
