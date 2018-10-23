package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.PurchaseHasProductService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.PurchaseHasProductDTO;
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
 * REST controller for managing PurchaseHasProduct.
 */
@RestController
@RequestMapping("/api")
public class PurchaseHasProductResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseHasProductResource.class);

    private static final String ENTITY_NAME = "purchaseHasProduct";

    private PurchaseHasProductService purchaseHasProductService;

    public PurchaseHasProductResource(PurchaseHasProductService purchaseHasProductService) {
        this.purchaseHasProductService = purchaseHasProductService;
    }

    /**
     * POST  /purchase-has-products : Create a new purchaseHasProduct.
     *
     * @param purchaseHasProductDTO the purchaseHasProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseHasProductDTO, or with status 400 (Bad Request) if the purchaseHasProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-has-products")
    @Timed
    public ResponseEntity<PurchaseHasProductDTO> createPurchaseHasProduct(@RequestBody PurchaseHasProductDTO purchaseHasProductDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseHasProduct : {}", purchaseHasProductDTO);
        if (purchaseHasProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseHasProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseHasProductDTO result = purchaseHasProductService.save(purchaseHasProductDTO);
        return ResponseEntity.created(new URI("/api/purchase-has-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-has-products : Updates an existing purchaseHasProduct.
     *
     * @param purchaseHasProductDTO the purchaseHasProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseHasProductDTO,
     * or with status 400 (Bad Request) if the purchaseHasProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseHasProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-has-products")
    @Timed
    public ResponseEntity<PurchaseHasProductDTO> updatePurchaseHasProduct(@RequestBody PurchaseHasProductDTO purchaseHasProductDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseHasProduct : {}", purchaseHasProductDTO);
        if (purchaseHasProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseHasProductDTO result = purchaseHasProductService.save(purchaseHasProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseHasProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-has-products : get all the purchaseHasProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseHasProducts in body
     */
    @GetMapping("/purchase-has-products")
    @Timed
    public ResponseEntity<List<PurchaseHasProductDTO>> getAllPurchaseHasProducts(Pageable pageable) {
        log.debug("REST request to get a page of PurchaseHasProducts");
        Page<PurchaseHasProductDTO> page = purchaseHasProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-has-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-has-products/:id : get the "id" purchaseHasProduct.
     *
     * @param id the id of the purchaseHasProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseHasProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-has-products/{id}")
    @Timed
    public ResponseEntity<PurchaseHasProductDTO> getPurchaseHasProduct(@PathVariable Long id) {
        log.debug("REST request to get PurchaseHasProduct : {}", id);
        Optional<PurchaseHasProductDTO> purchaseHasProductDTO = purchaseHasProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseHasProductDTO);
    }

    /**
     * DELETE  /purchase-has-products/:id : delete the "id" purchaseHasProduct.
     *
     * @param id the id of the purchaseHasProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-has-products/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseHasProduct(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseHasProduct : {}", id);
        purchaseHasProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/purchase-has-products?query=:query : search for the purchaseHasProduct corresponding
     * to the query.
     *
     * @param query the query of the purchaseHasProduct search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/purchase-has-products")
    @Timed
    public ResponseEntity<List<PurchaseHasProductDTO>> searchPurchaseHasProducts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PurchaseHasProducts for query {}", query);
        Page<PurchaseHasProductDTO> page = purchaseHasProductService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/purchase-has-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
