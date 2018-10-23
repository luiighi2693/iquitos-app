package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ProviderPaymentService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ProviderPaymentDTO;
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
 * REST controller for managing ProviderPayment.
 */
@RestController
@RequestMapping("/api")
public class ProviderPaymentResource {

    private final Logger log = LoggerFactory.getLogger(ProviderPaymentResource.class);

    private static final String ENTITY_NAME = "providerPayment";

    private ProviderPaymentService providerPaymentService;

    public ProviderPaymentResource(ProviderPaymentService providerPaymentService) {
        this.providerPaymentService = providerPaymentService;
    }

    /**
     * POST  /provider-payments : Create a new providerPayment.
     *
     * @param providerPaymentDTO the providerPaymentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerPaymentDTO, or with status 400 (Bad Request) if the providerPayment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provider-payments")
    @Timed
    public ResponseEntity<ProviderPaymentDTO> createProviderPayment(@RequestBody ProviderPaymentDTO providerPaymentDTO) throws URISyntaxException {
        log.debug("REST request to save ProviderPayment : {}", providerPaymentDTO);
        if (providerPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new providerPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProviderPaymentDTO result = providerPaymentService.save(providerPaymentDTO);
        return ResponseEntity.created(new URI("/api/provider-payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provider-payments : Updates an existing providerPayment.
     *
     * @param providerPaymentDTO the providerPaymentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerPaymentDTO,
     * or with status 400 (Bad Request) if the providerPaymentDTO is not valid,
     * or with status 500 (Internal Server Error) if the providerPaymentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provider-payments")
    @Timed
    public ResponseEntity<ProviderPaymentDTO> updateProviderPayment(@RequestBody ProviderPaymentDTO providerPaymentDTO) throws URISyntaxException {
        log.debug("REST request to update ProviderPayment : {}", providerPaymentDTO);
        if (providerPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProviderPaymentDTO result = providerPaymentService.save(providerPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerPaymentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provider-payments : get all the providerPayments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of providerPayments in body
     */
    @GetMapping("/provider-payments")
    @Timed
    public ResponseEntity<List<ProviderPaymentDTO>> getAllProviderPayments(Pageable pageable) {
        log.debug("REST request to get a page of ProviderPayments");
        Page<ProviderPaymentDTO> page = providerPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provider-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /provider-payments/:id : get the "id" providerPayment.
     *
     * @param id the id of the providerPaymentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerPaymentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/provider-payments/{id}")
    @Timed
    public ResponseEntity<ProviderPaymentDTO> getProviderPayment(@PathVariable Long id) {
        log.debug("REST request to get ProviderPayment : {}", id);
        Optional<ProviderPaymentDTO> providerPaymentDTO = providerPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(providerPaymentDTO);
    }

    /**
     * DELETE  /provider-payments/:id : delete the "id" providerPayment.
     *
     * @param id the id of the providerPaymentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-payments/{id}")
    @Timed
    public ResponseEntity<Void> deleteProviderPayment(@PathVariable Long id) {
        log.debug("REST request to delete ProviderPayment : {}", id);
        providerPaymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/provider-payments?query=:query : search for the providerPayment corresponding
     * to the query.
     *
     * @param query the query of the providerPayment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/provider-payments")
    @Timed
    public ResponseEntity<List<ProviderPaymentDTO>> searchProviderPayments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProviderPayments for query {}", query);
        Page<ProviderPaymentDTO> page = providerPaymentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/provider-payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
