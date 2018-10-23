package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.AmortizationService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.AmortizationDTO;
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
 * REST controller for managing Amortization.
 */
@RestController
@RequestMapping("/api")
public class AmortizationResource {

    private final Logger log = LoggerFactory.getLogger(AmortizationResource.class);

    private static final String ENTITY_NAME = "amortization";

    private AmortizationService amortizationService;

    public AmortizationResource(AmortizationService amortizationService) {
        this.amortizationService = amortizationService;
    }

    /**
     * POST  /amortizations : Create a new amortization.
     *
     * @param amortizationDTO the amortizationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amortizationDTO, or with status 400 (Bad Request) if the amortization has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amortizations")
    @Timed
    public ResponseEntity<AmortizationDTO> createAmortization(@RequestBody AmortizationDTO amortizationDTO) throws URISyntaxException {
        log.debug("REST request to save Amortization : {}", amortizationDTO);
        if (amortizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizationDTO result = amortizationService.save(amortizationDTO);
        return ResponseEntity.created(new URI("/api/amortizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amortizations : Updates an existing amortization.
     *
     * @param amortizationDTO the amortizationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amortizationDTO,
     * or with status 400 (Bad Request) if the amortizationDTO is not valid,
     * or with status 500 (Internal Server Error) if the amortizationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amortizations")
    @Timed
    public ResponseEntity<AmortizationDTO> updateAmortization(@RequestBody AmortizationDTO amortizationDTO) throws URISyntaxException {
        log.debug("REST request to update Amortization : {}", amortizationDTO);
        if (amortizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmortizationDTO result = amortizationService.save(amortizationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amortizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amortizations : get all the amortizations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of amortizations in body
     */
    @GetMapping("/amortizations")
    @Timed
    public ResponseEntity<List<AmortizationDTO>> getAllAmortizations(Pageable pageable) {
        log.debug("REST request to get a page of Amortizations");
        Page<AmortizationDTO> page = amortizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amortizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /amortizations/:id : get the "id" amortization.
     *
     * @param id the id of the amortizationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amortizationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amortizations/{id}")
    @Timed
    public ResponseEntity<AmortizationDTO> getAmortization(@PathVariable Long id) {
        log.debug("REST request to get Amortization : {}", id);
        Optional<AmortizationDTO> amortizationDTO = amortizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizationDTO);
    }

    /**
     * DELETE  /amortizations/:id : delete the "id" amortization.
     *
     * @param id the id of the amortizationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amortizations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmortization(@PathVariable Long id) {
        log.debug("REST request to delete Amortization : {}", id);
        amortizationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/amortizations?query=:query : search for the amortization corresponding
     * to the query.
     *
     * @param query the query of the amortization search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/amortizations")
    @Timed
    public ResponseEntity<List<AmortizationDTO>> searchAmortizations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Amortizations for query {}", query);
        Page<AmortizationDTO> page = amortizationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/amortizations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
