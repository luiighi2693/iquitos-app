package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ConceptOperationInputService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ConceptOperationInputDTO;
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
 * REST controller for managing ConceptOperationInput.
 */
@RestController
@RequestMapping("/api")
public class ConceptOperationInputResource {

    private final Logger log = LoggerFactory.getLogger(ConceptOperationInputResource.class);

    private static final String ENTITY_NAME = "conceptOperationInput";

    private ConceptOperationInputService conceptOperationInputService;

    public ConceptOperationInputResource(ConceptOperationInputService conceptOperationInputService) {
        this.conceptOperationInputService = conceptOperationInputService;
    }

    /**
     * POST  /concept-operation-inputs : Create a new conceptOperationInput.
     *
     * @param conceptOperationInputDTO the conceptOperationInputDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conceptOperationInputDTO, or with status 400 (Bad Request) if the conceptOperationInput has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/concept-operation-inputs")
    @Timed
    public ResponseEntity<ConceptOperationInputDTO> createConceptOperationInput(@RequestBody ConceptOperationInputDTO conceptOperationInputDTO) throws URISyntaxException {
        log.debug("REST request to save ConceptOperationInput : {}", conceptOperationInputDTO);
        if (conceptOperationInputDTO.getId() != null) {
            throw new BadRequestAlertException("A new conceptOperationInput cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptOperationInputDTO result = conceptOperationInputService.save(conceptOperationInputDTO);
        return ResponseEntity.created(new URI("/api/concept-operation-inputs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concept-operation-inputs : Updates an existing conceptOperationInput.
     *
     * @param conceptOperationInputDTO the conceptOperationInputDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conceptOperationInputDTO,
     * or with status 400 (Bad Request) if the conceptOperationInputDTO is not valid,
     * or with status 500 (Internal Server Error) if the conceptOperationInputDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/concept-operation-inputs")
    @Timed
    public ResponseEntity<ConceptOperationInputDTO> updateConceptOperationInput(@RequestBody ConceptOperationInputDTO conceptOperationInputDTO) throws URISyntaxException {
        log.debug("REST request to update ConceptOperationInput : {}", conceptOperationInputDTO);
        if (conceptOperationInputDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptOperationInputDTO result = conceptOperationInputService.save(conceptOperationInputDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conceptOperationInputDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concept-operation-inputs : get all the conceptOperationInputs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conceptOperationInputs in body
     */
    @GetMapping("/concept-operation-inputs")
    @Timed
    public ResponseEntity<List<ConceptOperationInputDTO>> getAllConceptOperationInputs(Pageable pageable) {
        log.debug("REST request to get a page of ConceptOperationInputs");
        Page<ConceptOperationInputDTO> page = conceptOperationInputService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/concept-operation-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /concept-operation-inputs/:id : get the "id" conceptOperationInput.
     *
     * @param id the id of the conceptOperationInputDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conceptOperationInputDTO, or with status 404 (Not Found)
     */
    @GetMapping("/concept-operation-inputs/{id}")
    @Timed
    public ResponseEntity<ConceptOperationInputDTO> getConceptOperationInput(@PathVariable Long id) {
        log.debug("REST request to get ConceptOperationInput : {}", id);
        Optional<ConceptOperationInputDTO> conceptOperationInputDTO = conceptOperationInputService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conceptOperationInputDTO);
    }

    /**
     * DELETE  /concept-operation-inputs/:id : delete the "id" conceptOperationInput.
     *
     * @param id the id of the conceptOperationInputDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/concept-operation-inputs/{id}")
    @Timed
    public ResponseEntity<Void> deleteConceptOperationInput(@PathVariable Long id) {
        log.debug("REST request to delete ConceptOperationInput : {}", id);
        conceptOperationInputService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/concept-operation-inputs?query=:query : search for the conceptOperationInput corresponding
     * to the query.
     *
     * @param query the query of the conceptOperationInput search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/concept-operation-inputs")
    @Timed
    public ResponseEntity<List<ConceptOperationInputDTO>> searchConceptOperationInputs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConceptOperationInputs for query {}", query);
        Page<ConceptOperationInputDTO> page = conceptOperationInputService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/concept-operation-inputs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
