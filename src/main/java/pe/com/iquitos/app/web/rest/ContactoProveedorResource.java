package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ContactoProveedorService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ContactoProveedorDTO;
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
 * REST controller for managing ContactoProveedor.
 */
@RestController
@RequestMapping("/api")
public class ContactoProveedorResource {

    private final Logger log = LoggerFactory.getLogger(ContactoProveedorResource.class);

    private static final String ENTITY_NAME = "contactoProveedor";

    private final ContactoProveedorService contactoProveedorService;

    public ContactoProveedorResource(ContactoProveedorService contactoProveedorService) {
        this.contactoProveedorService = contactoProveedorService;
    }

    /**
     * POST  /contacto-proveedors : Create a new contactoProveedor.
     *
     * @param contactoProveedorDTO the contactoProveedorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactoProveedorDTO, or with status 400 (Bad Request) if the contactoProveedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contacto-proveedors")
    @Timed
    public ResponseEntity<ContactoProveedorDTO> createContactoProveedor(@Valid @RequestBody ContactoProveedorDTO contactoProveedorDTO) throws URISyntaxException {
        log.debug("REST request to save ContactoProveedor : {}", contactoProveedorDTO);
        if (contactoProveedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactoProveedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoProveedorDTO result = contactoProveedorService.save(contactoProveedorDTO);
        return ResponseEntity.created(new URI("/api/contacto-proveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contacto-proveedors : Updates an existing contactoProveedor.
     *
     * @param contactoProveedorDTO the contactoProveedorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactoProveedorDTO,
     * or with status 400 (Bad Request) if the contactoProveedorDTO is not valid,
     * or with status 500 (Internal Server Error) if the contactoProveedorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contacto-proveedors")
    @Timed
    public ResponseEntity<ContactoProveedorDTO> updateContactoProveedor(@Valid @RequestBody ContactoProveedorDTO contactoProveedorDTO) throws URISyntaxException {
        log.debug("REST request to update ContactoProveedor : {}", contactoProveedorDTO);
        if (contactoProveedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactoProveedorDTO result = contactoProveedorService.save(contactoProveedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactoProveedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contacto-proveedors : get all the contactoProveedors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactoProveedors in body
     */
    @GetMapping("/contacto-proveedors")
    @Timed
    public ResponseEntity<List<ContactoProveedorDTO>> getAllContactoProveedors(Pageable pageable) {
        log.debug("REST request to get a page of ContactoProveedors");
        Page<ContactoProveedorDTO> page = contactoProveedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contacto-proveedors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /contacto-proveedors/:id : get the "id" contactoProveedor.
     *
     * @param id the id of the contactoProveedorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactoProveedorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/contacto-proveedors/{id}")
    @Timed
    public ResponseEntity<ContactoProveedorDTO> getContactoProveedor(@PathVariable Long id) {
        log.debug("REST request to get ContactoProveedor : {}", id);
        Optional<ContactoProveedorDTO> contactoProveedorDTO = contactoProveedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoProveedorDTO);
    }

    /**
     * DELETE  /contacto-proveedors/:id : delete the "id" contactoProveedor.
     *
     * @param id the id of the contactoProveedorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contacto-proveedors/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactoProveedor(@PathVariable Long id) {
        log.debug("REST request to delete ContactoProveedor : {}", id);
        contactoProveedorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/contacto-proveedors?query=:query : search for the contactoProveedor corresponding
     * to the query.
     *
     * @param query the query of the contactoProveedor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/contacto-proveedors")
    @Timed
    public ResponseEntity<List<ContactoProveedorDTO>> searchContactoProveedors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ContactoProveedors for query {}", query);
        Page<ContactoProveedorDTO> page = contactoProveedorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/contacto-proveedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
