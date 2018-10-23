package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ConceptOperationOutputService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ConceptOperationOutputDTO;
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
 * REST controller for managing ConceptOperationOutput.
 */
@RestController
@RequestMapping("/api")
public class ConceptOperationOutputResource {

    private final Logger log = LoggerFactory.getLogger(ConceptOperationOutputResource.class);

    private static final String ENTITY_NAME = "conceptOperationOutput";

    private ConceptOperationOutputService conceptOperationOutputService;

    public ConceptOperationOutputResource(ConceptOperationOutputService conceptOperationOutputService) {
        this.conceptOperationOutputService = conceptOperationOutputService;
    }

    /**
     * POST  /concept-operation-outputs : Create a new conceptOperationOutput.
     *
     * @param conceptOperationOutputDTO the conceptOperationOutputDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conceptOperationOutputDTO, or with status 400 (Bad Request) if the conceptOperationOutput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/concept-operation-outputs")
    @Timed
    public ResponseEntity<ConceptOperationOutputDTO> createConceptOperationOutput(@RequestBody ConceptOperationOutputDTO conceptOperationOutputDTO) throws URISyntaxException {
        log.debug("REST request to save ConceptOperationOutput : {}", conceptOperationOutputDTO);
        if (conceptOperationOutputDTO.getId() != null) {
            throw new BadRequestAlertException("A new conceptOperationOutput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptOperationOutputDTO result = conceptOperationOutputService.save(conceptOperationOutputDTO);
        return ResponseEntity.created(new URI("/api/concept-operation-outputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concept-operation-outputs : Updates an existing conceptOperationOutput.
     *
     * @param conceptOperationOutputDTO the conceptOperationOutputDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conceptOperationOutputDTO,
     * or with status 400 (Bad Request) if the conceptOperationOutputDTO is not valid,
     * or with status 500 (Internal Server Error) if the conceptOperationOutputDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/concept-operation-outputs")
    @Timed
    public ResponseEntity<ConceptOperationOutputDTO> updateConceptOperationOutput(@RequestBody ConceptOperationOutputDTO conceptOperationOutputDTO) throws URISyntaxException {
        log.debug("REST request to update ConceptOperationOutput : {}", conceptOperationOutputDTO);
        if (conceptOperationOutputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptOperationOutputDTO result = conceptOperationOutputService.save(conceptOperationOutputDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conceptOperationOutputDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concept-operation-outputs : get all the conceptOperationOutputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conceptOperationOutputs in body
     */
    @GetMapping("/concept-operation-outputs")
    @Timed
    public ResponseEntity<List<ConceptOperationOutputDTO>> getAllConceptOperationOutputs(Pageable pageable) {
        log.debug("REST request to get a page of ConceptOperationOutputs");
        Page<ConceptOperationOutputDTO> page = conceptOperationOutputService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/concept-operation-outputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /concept-operation-outputs/:id : get the "id" conceptOperationOutput.
     *
     * @param id the id of the conceptOperationOutputDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conceptOperationOutputDTO, or with status 404 (Not Found)
     */
    @GetMapping("/concept-operation-outputs/{id}")
    @Timed
    public ResponseEntity<ConceptOperationOutputDTO> getConceptOperationOutput(@PathVariable Long id) {
        log.debug("REST request to get ConceptOperationOutput : {}", id);
        Optional<ConceptOperationOutputDTO> conceptOperationOutputDTO = conceptOperationOutputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conceptOperationOutputDTO);
    }

    /**
     * DELETE  /concept-operation-outputs/:id : delete the "id" conceptOperationOutput.
     *
     * @param id the id of the conceptOperationOutputDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/concept-operation-outputs/{id}")
    @Timed
    public ResponseEntity<Void> deleteConceptOperationOutput(@PathVariable Long id) {
        log.debug("REST request to delete ConceptOperationOutput : {}", id);
        conceptOperationOutputService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/concept-operation-outputs?query=:query : search for the conceptOperationOutput corresponding
     * to the query.
     *
     * @param query the query of the conceptOperationOutput search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/concept-operation-outputs")
    @Timed
    public ResponseEntity<List<ConceptOperationOutputDTO>> searchConceptOperationOutputs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConceptOperationOutputs for query {}", query);
        Page<ConceptOperationOutputDTO> page = conceptOperationOutputService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/concept-operation-outputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
