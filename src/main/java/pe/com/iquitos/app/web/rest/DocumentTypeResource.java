package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.DocumentTypeService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.DocumentTypeDTO;
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
 * REST controller for managing DocumentType.
 */
@RestController
@RequestMapping("/api")
public class DocumentTypeResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeResource.class);

    private static final String ENTITY_NAME = "documentType";

    private DocumentTypeService documentTypeService;

    public DocumentTypeResource(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    /**
     * POST  /document-types : Create a new documentType.
     *
     * @param documentTypeDTO the documentTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentTypeDTO, or with status 400 (Bad Request) if the documentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-types")
    @Timed
    public ResponseEntity<DocumentTypeDTO> createDocumentType(@RequestBody DocumentTypeDTO documentTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DocumentType : {}", documentTypeDTO);
        if (documentTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);
        return ResponseEntity.created(new URI("/api/document-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-types : Updates an existing documentType.
     *
     * @param documentTypeDTO the documentTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentTypeDTO,
     * or with status 400 (Bad Request) if the documentTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the documentTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-types")
    @Timed
    public ResponseEntity<DocumentTypeDTO> updateDocumentType(@RequestBody DocumentTypeDTO documentTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DocumentType : {}", documentTypeDTO);
        if (documentTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentTypeDTO result = documentTypeService.save(documentTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-types : get all the documentTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentTypes in body
     */
    @GetMapping("/document-types")
    @Timed
    public ResponseEntity<List<DocumentTypeDTO>> getAllDocumentTypes(Pageable pageable) {
        log.debug("REST request to get a page of DocumentTypes");
        Page<DocumentTypeDTO> page = documentTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /document-types/:id : get the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/document-types/{id}")
    @Timed
    public ResponseEntity<DocumentTypeDTO> getDocumentType(@PathVariable Long id) {
        log.debug("REST request to get DocumentType : {}", id);
        Optional<DocumentTypeDTO> documentTypeDTO = documentTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentTypeDTO);
    }

    /**
     * DELETE  /document-types/:id : delete the "id" documentType.
     *
     * @param id the id of the documentTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Long id) {
        log.debug("REST request to delete DocumentType : {}", id);
        documentTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/document-types?query=:query : search for the documentType corresponding
     * to the query.
     *
     * @param query the query of the documentType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/document-types")
    @Timed
    public ResponseEntity<List<DocumentTypeDTO>> searchDocumentTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DocumentTypes for query {}", query);
        Page<DocumentTypeDTO> page = documentTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/document-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
