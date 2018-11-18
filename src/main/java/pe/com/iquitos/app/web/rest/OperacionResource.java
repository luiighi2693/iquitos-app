package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.OperacionService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.OperacionDTO;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Operacion.
 */
@RestController
@RequestMapping("/api")
public class OperacionResource {

    private final Logger log = LoggerFactory.getLogger(OperacionResource.class);

    private static final String ENTITY_NAME = "operacion";

    private final OperacionService operacionService;

    public OperacionResource(OperacionService operacionService) {
        this.operacionService = operacionService;
    }

    /**
     * POST  /operacions : Create a new operacion.
     *
     * @param operacionDTO the operacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operacionDTO, or with status 400 (Bad Request) if the operacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operacions")
    @Timed
    public ResponseEntity<OperacionDTO> createOperacion(@Valid @RequestBody OperacionDTO operacionDTO) throws URISyntaxException {
        log.debug("REST request to save Operacion : {}", operacionDTO);
        if (operacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new operacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OperacionDTO result = operacionService.save(operacionDTO);
        return ResponseEntity.created(new URI("/api/operacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operacions : Updates an existing operacion.
     *
     * @param operacionDTO the operacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operacionDTO,
     * or with status 400 (Bad Request) if the operacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the operacionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operacions")
    @Timed
    public ResponseEntity<OperacionDTO> updateOperacion(@Valid @RequestBody OperacionDTO operacionDTO) throws URISyntaxException {
        log.debug("REST request to update Operacion : {}", operacionDTO);
        if (operacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OperacionDTO result = operacionService.save(operacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operacions : get all the operacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operacions in body
     */
    @GetMapping("/operacions")
    @Timed
    public ResponseEntity<List<OperacionDTO>> getAllOperacions(Pageable pageable) {
        log.debug("REST request to get a page of Operacions");
        Page<OperacionDTO> page = operacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operacions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /operacions/:id : get the "id" operacion.
     *
     * @param id the id of the operacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/operacions/{id}")
    @Timed
    public ResponseEntity<OperacionDTO> getOperacion(@PathVariable Long id) {
        log.debug("REST request to get Operacion : {}", id);
        Optional<OperacionDTO> operacionDTO = operacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(operacionDTO);
    }

    /**
     * DELETE  /operacions/:id : delete the "id" operacion.
     *
     * @param id the id of the operacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperacion(@PathVariable Long id) {
        log.debug("REST request to delete Operacion : {}", id);
        operacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operacions?query=:query : search for the operacion corresponding
     * to the query.
     *
     * @param query the query of the operacion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operacions")
    @Timed
    public ResponseEntity<List<OperacionDTO>> searchOperacions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Operacions for query {}", query);
        Page<OperacionDTO> page = operacionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
