package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.DocumentTypePurchaseService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.DocumentTypePurchaseDTO;
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
 * REST controller for managing DocumentTypePurchase.
 */
@RestController
@RequestMapping("/api")
public class DocumentTypePurchaseResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTypePurchaseResource.class);

    private static final String ENTITY_NAME = "documentTypePurchase";

    private DocumentTypePurchaseService documentTypePurchaseService;

    public DocumentTypePurchaseResource(DocumentTypePurchaseService documentTypePurchaseService) {
        this.documentTypePurchaseService = documentTypePurchaseService;
    }

    /**
     * POST  /document-type-purchases : Create a new documentTypePurchase.
     *
     * @param documentTypePurchaseDTO the documentTypePurchaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentTypePurchaseDTO, or with status 400 (Bad Request) if the documentTypePurchase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-type-purchases")
    @Timed
    public ResponseEntity<DocumentTypePurchaseDTO> createDocumentTypePurchase(@RequestBody DocumentTypePurchaseDTO documentTypePurchaseDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentTypePurchase : {}", documentTypePurchaseDTO);
        if (documentTypePurchaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentTypePurchase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTypePurchaseDTO result = documentTypePurchaseService.save(documentTypePurchaseDTO);
        return ResponseEntity.created(new URI("/api/document-type-purchases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-type-purchases : Updates an existing documentTypePurchase.
     *
     * @param documentTypePurchaseDTO the documentTypePurchaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentTypePurchaseDTO,
     * or with status 400 (Bad Request) if the documentTypePurchaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentTypePurchaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-type-purchases")
    @Timed
    public ResponseEntity<DocumentTypePurchaseDTO> updateDocumentTypePurchase(@RequestBody DocumentTypePurchaseDTO documentTypePurchaseDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentTypePurchase : {}", documentTypePurchaseDTO);
        if (documentTypePurchaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentTypePurchaseDTO result = documentTypePurchaseService.save(documentTypePurchaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentTypePurchaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-type-purchases : get all the documentTypePurchases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentTypePurchases in body
     */
    @GetMapping("/document-type-purchases")
    @Timed
    public ResponseEntity<List<DocumentTypePurchaseDTO>> getAllDocumentTypePurchases(Pageable pageable) {
        log.debug("REST request to get a page of DocumentTypePurchases");
        Page<DocumentTypePurchaseDTO> page = documentTypePurchaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-type-purchases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /document-type-purchases/:id : get the "id" documentTypePurchase.
     *
     * @param id the id of the documentTypePurchaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentTypePurchaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/document-type-purchases/{id}")
    @Timed
    public ResponseEntity<DocumentTypePurchaseDTO> getDocumentTypePurchase(@PathVariable Long id) {
        log.debug("REST request to get DocumentTypePurchase : {}", id);
        Optional<DocumentTypePurchaseDTO> documentTypePurchaseDTO = documentTypePurchaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentTypePurchaseDTO);
    }

    /**
     * DELETE  /document-type-purchases/:id : delete the "id" documentTypePurchase.
     *
     * @param id the id of the documentTypePurchaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-type-purchases/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentTypePurchase(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTypePurchase : {}", id);
        documentTypePurchaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/document-type-purchases?query=:query : search for the documentTypePurchase corresponding
     * to the query.
     *
     * @param query the query of the documentTypePurchase search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/document-type-purchases")
    @Timed
    public ResponseEntity<List<DocumentTypePurchaseDTO>> searchDocumentTypePurchases(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentTypePurchases for query {}", query);
        Page<DocumentTypePurchaseDTO> page = documentTypePurchaseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/document-type-purchases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
