package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.PurchaseStatusService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.PurchaseStatusDTO;
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
 * REST controller for managing PurchaseStatus.
 */
@RestController
@RequestMapping("/api")
public class PurchaseStatusResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseStatusResource.class);

    private static final String ENTITY_NAME = "purchaseStatus";

    private PurchaseStatusService purchaseStatusService;

    public PurchaseStatusResource(PurchaseStatusService purchaseStatusService) {
        this.purchaseStatusService = purchaseStatusService;
    }

    /**
     * POST  /purchase-statuses : Create a new purchaseStatus.
     *
     * @param purchaseStatusDTO the purchaseStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseStatusDTO, or with status 400 (Bad Request) if the purchaseStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-statuses")
    @Timed
    public ResponseEntity<PurchaseStatusDTO> createPurchaseStatus(@RequestBody PurchaseStatusDTO purchaseStatusDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseStatus : {}", purchaseStatusDTO);
        if (purchaseStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseStatusDTO result = purchaseStatusService.save(purchaseStatusDTO);
        return ResponseEntity.created(new URI("/api/purchase-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-statuses : Updates an existing purchaseStatus.
     *
     * @param purchaseStatusDTO the purchaseStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseStatusDTO,
     * or with status 400 (Bad Request) if the purchaseStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-statuses")
    @Timed
    public ResponseEntity<PurchaseStatusDTO> updatePurchaseStatus(@RequestBody PurchaseStatusDTO purchaseStatusDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseStatus : {}", purchaseStatusDTO);
        if (purchaseStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseStatusDTO result = purchaseStatusService.save(purchaseStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-statuses : get all the purchaseStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseStatuses in body
     */
    @GetMapping("/purchase-statuses")
    @Timed
    public ResponseEntity<List<PurchaseStatusDTO>> getAllPurchaseStatuses(Pageable pageable) {
        log.debug("REST request to get a page of PurchaseStatuses");
        Page<PurchaseStatusDTO> page = purchaseStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /purchase-statuses/:id : get the "id" purchaseStatus.
     *
     * @param id the id of the purchaseStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-statuses/{id}")
    @Timed
    public ResponseEntity<PurchaseStatusDTO> getPurchaseStatus(@PathVariable Long id) {
        log.debug("REST request to get PurchaseStatus : {}", id);
        Optional<PurchaseStatusDTO> purchaseStatusDTO = purchaseStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseStatusDTO);
    }

    /**
     * DELETE  /purchase-statuses/:id : delete the "id" purchaseStatus.
     *
     * @param id the id of the purchaseStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchaseStatus(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseStatus : {}", id);
        purchaseStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/purchase-statuses?query=:query : search for the purchaseStatus corresponding
     * to the query.
     *
     * @param query the query of the purchaseStatus search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/purchase-statuses")
    @Timed
    public ResponseEntity<List<PurchaseStatusDTO>> searchPurchaseStatuses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PurchaseStatuses for query {}", query);
        Page<PurchaseStatusDTO> page = purchaseStatusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/purchase-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
