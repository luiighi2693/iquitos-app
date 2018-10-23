package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.SellHasProductService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.SellHasProductDTO;
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
 * REST controller for managing SellHasProduct.
 */
@RestController
@RequestMapping("/api")
public class SellHasProductResource {

    private final Logger log = LoggerFactory.getLogger(SellHasProductResource.class);

    private static final String ENTITY_NAME = "sellHasProduct";

    private SellHasProductService sellHasProductService;

    public SellHasProductResource(SellHasProductService sellHasProductService) {
        this.sellHasProductService = sellHasProductService;
    }

    /**
     * POST  /sell-has-products : Create a new sellHasProduct.
     *
     * @param sellHasProductDTO the sellHasProductDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sellHasProductDTO, or with status 400 (Bad Request) if the sellHasProduct has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sell-has-products")
    @Timed
    public ResponseEntity<SellHasProductDTO> createSellHasProduct(@RequestBody SellHasProductDTO sellHasProductDTO) throws URISyntaxException {
        log.debug("REST request to save SellHasProduct : {}", sellHasProductDTO);
        if (sellHasProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new sellHasProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SellHasProductDTO result = sellHasProductService.save(sellHasProductDTO);
        return ResponseEntity.created(new URI("/api/sell-has-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sell-has-products : Updates an existing sellHasProduct.
     *
     * @param sellHasProductDTO the sellHasProductDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sellHasProductDTO,
     * or with status 400 (Bad Request) if the sellHasProductDTO is not valid,
     * or with status 500 (Internal Server Error) if the sellHasProductDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sell-has-products")
    @Timed
    public ResponseEntity<SellHasProductDTO> updateSellHasProduct(@RequestBody SellHasProductDTO sellHasProductDTO) throws URISyntaxException {
        log.debug("REST request to update SellHasProduct : {}", sellHasProductDTO);
        if (sellHasProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SellHasProductDTO result = sellHasProductService.save(sellHasProductDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sellHasProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sell-has-products : get all the sellHasProducts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sellHasProducts in body
     */
    @GetMapping("/sell-has-products")
    @Timed
    public ResponseEntity<List<SellHasProductDTO>> getAllSellHasProducts(Pageable pageable) {
        log.debug("REST request to get a page of SellHasProducts");
        Page<SellHasProductDTO> page = sellHasProductService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sell-has-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sell-has-products/:id : get the "id" sellHasProduct.
     *
     * @param id the id of the sellHasProductDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sellHasProductDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sell-has-products/{id}")
    @Timed
    public ResponseEntity<SellHasProductDTO> getSellHasProduct(@PathVariable Long id) {
        log.debug("REST request to get SellHasProduct : {}", id);
        Optional<SellHasProductDTO> sellHasProductDTO = sellHasProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sellHasProductDTO);
    }

    /**
     * DELETE  /sell-has-products/:id : delete the "id" sellHasProduct.
     *
     * @param id the id of the sellHasProductDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sell-has-products/{id}")
    @Timed
    public ResponseEntity<Void> deleteSellHasProduct(@PathVariable Long id) {
        log.debug("REST request to delete SellHasProduct : {}", id);
        sellHasProductService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sell-has-products?query=:query : search for the sellHasProduct corresponding
     * to the query.
     *
     * @param query the query of the sellHasProduct search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sell-has-products")
    @Timed
    public ResponseEntity<List<SellHasProductDTO>> searchSellHasProducts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SellHasProducts for query {}", query);
        Page<SellHasProductDTO> page = sellHasProductService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sell-has-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
