package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.AmortizacionService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.AmortizacionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Amortizacion.
 */
@RestController
@RequestMapping("/api")
public class AmortizacionResource {

    private final Logger log = LoggerFactory.getLogger(AmortizacionResource.class);

    private static final String ENTITY_NAME = "amortizacion";

    private final AmortizacionService amortizacionService;

    public AmortizacionResource(AmortizacionService amortizacionService) {
        this.amortizacionService = amortizacionService;
    }

    /**
     * POST  /amortizacions : Create a new amortizacion.
     *
     * @param amortizacionDTO the amortizacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new amortizacionDTO, or with status 400 (Bad Request) if the amortizacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/amortizacions")
    @Timed
    public ResponseEntity<AmortizacionDTO> createAmortizacion(@Valid @RequestBody AmortizacionDTO amortizacionDTO) throws URISyntaxException {
        log.debug("REST request to save Amortizacion : {}", amortizacionDTO);
        if (amortizacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizacionDTO result = amortizacionService.save(amortizacionDTO);
        return ResponseEntity.created(new URI("/api/amortizacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /amortizacions : Updates an existing amortizacion.
     *
     * @param amortizacionDTO the amortizacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated amortizacionDTO,
     * or with status 400 (Bad Request) if the amortizacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the amortizacionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/amortizacions")
    @Timed
    public ResponseEntity<AmortizacionDTO> updateAmortizacion(@Valid @RequestBody AmortizacionDTO amortizacionDTO) throws URISyntaxException {
        log.debug("REST request to update Amortizacion : {}", amortizacionDTO);
        if (amortizacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmortizacionDTO result = amortizacionService.save(amortizacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, amortizacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /amortizacions : get all the amortizacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of amortizacions in body
     */
    @GetMapping("/amortizacions")
    @Timed
    public ResponseEntity<List<AmortizacionDTO>> getAllAmortizacions(Pageable pageable) {
        log.debug("REST request to get a page of Amortizacions");
        Page<AmortizacionDTO> page = amortizacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/amortizacions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /amortizacions/:id : get the "id" amortizacion.
     *
     * @param id the id of the amortizacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the amortizacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/amortizacions/{id}")
    @Timed
    public ResponseEntity<AmortizacionDTO> getAmortizacion(@PathVariable Long id) {
        log.debug("REST request to get Amortizacion : {}", id);
        Optional<AmortizacionDTO> amortizacionDTO = amortizacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizacionDTO);
    }

    /**
     * DELETE  /amortizacions/:id : delete the "id" amortizacion.
     *
     * @param id the id of the amortizacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/amortizacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAmortizacion(@PathVariable Long id) {
        log.debug("REST request to delete Amortizacion : {}", id);
        amortizacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/amortizacions?query=:query : search for the amortizacion corresponding
     * to the query.
     *
     * @param query the query of the amortizacion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/amortizacions")
    @Timed
    public ResponseEntity<List<AmortizacionDTO>> searchAmortizacions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Amortizacions for query {}", query);
        Page<AmortizacionDTO> page = amortizacionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/amortizacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
