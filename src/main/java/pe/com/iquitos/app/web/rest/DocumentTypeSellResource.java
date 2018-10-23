package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.DocumentTypeSellService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.DocumentTypeSellDTO;
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
 * REST controller for managing DocumentTypeSell.
 */
@RestController
@RequestMapping("/api")
public class DocumentTypeSellResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeSellResource.class);

    private static final String ENTITY_NAME = "documentTypeSell";

    private DocumentTypeSellService documentTypeSellService;

    public DocumentTypeSellResource(DocumentTypeSellService documentTypeSellService) {
        this.documentTypeSellService = documentTypeSellService;
    }

    /**
     * POST  /document-type-sells : Create a new documentTypeSell.
     *
     * @param documentTypeSellDTO the documentTypeSellDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentTypeSellDTO, or with status 400 (Bad Request) if the documentTypeSell has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-type-sells")
    @Timed
    public ResponseEntity<DocumentTypeSellDTO> createDocumentTypeSell(@RequestBody DocumentTypeSellDTO documentTypeSellDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentTypeSell : {}", documentTypeSellDTO);
        if (documentTypeSellDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentTypeSell cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTypeSellDTO result = documentTypeSellService.save(documentTypeSellDTO);
        return ResponseEntity.created(new URI("/api/document-type-sells/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-type-sells : Updates an existing documentTypeSell.
     *
     * @param documentTypeSellDTO the documentTypeSellDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentTypeSellDTO,
     * or with status 400 (Bad Request) if the documentTypeSellDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentTypeSellDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-type-sells")
    @Timed
    public ResponseEntity<DocumentTypeSellDTO> updateDocumentTypeSell(@RequestBody DocumentTypeSellDTO documentTypeSellDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentTypeSell : {}", documentTypeSellDTO);
        if (documentTypeSellDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentTypeSellDTO result = documentTypeSellService.save(documentTypeSellDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentTypeSellDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-type-sells : get all the documentTypeSells.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentTypeSells in body
     */
    @GetMapping("/document-type-sells")
    @Timed
    public ResponseEntity<List<DocumentTypeSellDTO>> getAllDocumentTypeSells(Pageable pageable) {
        log.debug("REST request to get a page of DocumentTypeSells");
        Page<DocumentTypeSellDTO> page = documentTypeSellService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-type-sells");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /document-type-sells/:id : get the "id" documentTypeSell.
     *
     * @param id the id of the documentTypeSellDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentTypeSellDTO, or with status 404 (Not Found)
     */
    @GetMapping("/document-type-sells/{id}")
    @Timed
    public ResponseEntity<DocumentTypeSellDTO> getDocumentTypeSell(@PathVariable Long id) {
        log.debug("REST request to get DocumentTypeSell : {}", id);
        Optional<DocumentTypeSellDTO> documentTypeSellDTO = documentTypeSellService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentTypeSellDTO);
    }

    /**
     * DELETE  /document-type-sells/:id : delete the "id" documentTypeSell.
     *
     * @param id the id of the documentTypeSellDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-type-sells/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentTypeSell(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTypeSell : {}", id);
        documentTypeSellService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/document-type-sells?query=:query : search for the documentTypeSell corresponding
     * to the query.
     *
     * @param query the query of the documentTypeSell search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/document-type-sells")
    @Timed
    public ResponseEntity<List<DocumentTypeSellDTO>> searchDocumentTypeSells(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentTypeSells for query {}", query);
        Page<DocumentTypeSellDTO> page = documentTypeSellService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/document-type-sells");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
