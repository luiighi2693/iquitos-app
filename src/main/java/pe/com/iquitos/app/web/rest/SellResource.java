package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.SellService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.SellDTO;
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
 * REST controller for managing Sell.
 */
@RestController
@RequestMapping("/api")
public class SellResource {

    private final Logger log = LoggerFactory.getLogger(SellResource.class);

    private static final String ENTITY_NAME = "sell";

    private SellService sellService;

    public SellResource(SellService sellService) {
        this.sellService = sellService;
    }

    /**
     * POST  /sells : Create a new sell.
     *
     * @param sellDTO the sellDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sellDTO, or with status 400 (Bad Request) if the sell has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sells")
    @Timed
    public ResponseEntity<SellDTO> createSell(@RequestBody SellDTO sellDTO) throws URISyntaxException {
        log.debug("REST request to save Sell : {}", sellDTO);
        if (sellDTO.getId() != null) {
            throw new BadRequestAlertException("A new sell cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SellDTO result = sellService.save(sellDTO);
        return ResponseEntity.created(new URI("/api/sells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sells : Updates an existing sell.
     *
     * @param sellDTO the sellDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sellDTO,
     * or with status 400 (Bad Request) if the sellDTO is not valid,
     * or with status 500 (Internal Server Error) if the sellDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sells")
    @Timed
    public ResponseEntity<SellDTO> updateSell(@RequestBody SellDTO sellDTO) throws URISyntaxException {
        log.debug("REST request to update Sell : {}", sellDTO);
        if (sellDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SellDTO result = sellService.save(sellDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sellDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sells : get all the sells.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of sells in body
     */
    @GetMapping("/sells")
    @Timed
    public ResponseEntity<List<SellDTO>> getAllSells(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Sells");
        Page<SellDTO> page;
        if (eagerload) {
            page = sellService.findAllWithEagerRelationships(pageable);
        } else {
            page = sellService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/sells?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sells/:id : get the "id" sell.
     *
     * @param id the id of the sellDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sellDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sells/{id}")
    @Timed
    public ResponseEntity<SellDTO> getSell(@PathVariable Long id) {
        log.debug("REST request to get Sell : {}", id);
        Optional<SellDTO> sellDTO = sellService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sellDTO);
    }

    /**
     * DELETE  /sells/:id : delete the "id" sell.
     *
     * @param id the id of the sellDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sells/{id}")
    @Timed
    public ResponseEntity<Void> deleteSell(@PathVariable Long id) {
        log.debug("REST request to delete Sell : {}", id);
        sellService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sells?query=:query : search for the sell corresponding
     * to the query.
     *
     * @param query the query of the sell search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sells")
    @Timed
    public ResponseEntity<List<SellDTO>> searchSells(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sells for query {}", query);
        Page<SellDTO> page = sellService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sells");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
