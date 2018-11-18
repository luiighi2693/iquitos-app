package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.PagoDeProveedorService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.PagoDeProveedorDTO;
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
 * REST controller for managing PagoDeProveedor.
 */
@RestController
@RequestMapping("/api")
public class PagoDeProveedorResource {

    private final Logger log = LoggerFactory.getLogger(PagoDeProveedorResource.class);

    private static final String ENTITY_NAME = "pagoDeProveedor";

    private final PagoDeProveedorService pagoDeProveedorService;

    public PagoDeProveedorResource(PagoDeProveedorService pagoDeProveedorService) {
        this.pagoDeProveedorService = pagoDeProveedorService;
    }

    /**
     * POST  /pago-de-proveedors : Create a new pagoDeProveedor.
     *
     * @param pagoDeProveedorDTO the pagoDeProveedorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pagoDeProveedorDTO, or with status 400 (Bad Request) if the pagoDeProveedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pago-de-proveedors")
    @Timed
    public ResponseEntity<PagoDeProveedorDTO> createPagoDeProveedor(@Valid @RequestBody PagoDeProveedorDTO pagoDeProveedorDTO) throws URISyntaxException {
        log.debug("REST request to save PagoDeProveedor : {}", pagoDeProveedorDTO);
        if (pagoDeProveedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new pagoDeProveedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PagoDeProveedorDTO result = pagoDeProveedorService.save(pagoDeProveedorDTO);
        return ResponseEntity.created(new URI("/api/pago-de-proveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pago-de-proveedors : Updates an existing pagoDeProveedor.
     *
     * @param pagoDeProveedorDTO the pagoDeProveedorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pagoDeProveedorDTO,
     * or with status 400 (Bad Request) if the pagoDeProveedorDTO is not valid,
     * or with status 500 (Internal Server Error) if the pagoDeProveedorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pago-de-proveedors")
    @Timed
    public ResponseEntity<PagoDeProveedorDTO> updatePagoDeProveedor(@Valid @RequestBody PagoDeProveedorDTO pagoDeProveedorDTO) throws URISyntaxException {
        log.debug("REST request to update PagoDeProveedor : {}", pagoDeProveedorDTO);
        if (pagoDeProveedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PagoDeProveedorDTO result = pagoDeProveedorService.save(pagoDeProveedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pagoDeProveedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pago-de-proveedors : get all the pagoDeProveedors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pagoDeProveedors in body
     */
    @GetMapping("/pago-de-proveedors")
    @Timed
    public ResponseEntity<List<PagoDeProveedorDTO>> getAllPagoDeProveedors(Pageable pageable) {
        log.debug("REST request to get a page of PagoDeProveedors");
        Page<PagoDeProveedorDTO> page = pagoDeProveedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pago-de-proveedors");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /pago-de-proveedors/:id : get the "id" pagoDeProveedor.
     *
     * @param id the id of the pagoDeProveedorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pagoDeProveedorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pago-de-proveedors/{id}")
    @Timed
    public ResponseEntity<PagoDeProveedorDTO> getPagoDeProveedor(@PathVariable Long id) {
        log.debug("REST request to get PagoDeProveedor : {}", id);
        Optional<PagoDeProveedorDTO> pagoDeProveedorDTO = pagoDeProveedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pagoDeProveedorDTO);
    }

    /**
     * DELETE  /pago-de-proveedors/:id : delete the "id" pagoDeProveedor.
     *
     * @param id the id of the pagoDeProveedorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pago-de-proveedors/{id}")
    @Timed
    public ResponseEntity<Void> deletePagoDeProveedor(@PathVariable Long id) {
        log.debug("REST request to delete PagoDeProveedor : {}", id);
        pagoDeProveedorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pago-de-proveedors?query=:query : search for the pagoDeProveedor corresponding
     * to the query.
     *
     * @param query the query of the pagoDeProveedor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pago-de-proveedors")
    @Timed
    public ResponseEntity<List<PagoDeProveedorDTO>> searchPagoDeProveedors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PagoDeProveedors for query {}", query);
        Page<PagoDeProveedorDTO> page = pagoDeProveedorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pago-de-proveedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
