package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.CuentaProveedorService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.CuentaProveedorDTO;
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
 * REST controller for managing CuentaProveedor.
 */
@RestController
@RequestMapping("/api")
public class CuentaProveedorResource {

    private final Logger log = LoggerFactory.getLogger(CuentaProveedorResource.class);

    private static final String ENTITY_NAME = "cuentaProveedor";

    private CuentaProveedorService cuentaProveedorService;

    public CuentaProveedorResource(CuentaProveedorService cuentaProveedorService) {
        this.cuentaProveedorService = cuentaProveedorService;
    }

    /**
     * POST  /cuenta-proveedors : Create a new cuentaProveedor.
     *
     * @param cuentaProveedorDTO the cuentaProveedorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cuentaProveedorDTO, or with status 400 (Bad Request) if the cuentaProveedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cuenta-proveedors")
    @Timed
    public ResponseEntity<CuentaProveedorDTO> createCuentaProveedor(@Valid @RequestBody CuentaProveedorDTO cuentaProveedorDTO) throws URISyntaxException {
        log.debug("REST request to save CuentaProveedor : {}", cuentaProveedorDTO);
        if (cuentaProveedorDTO.getId() != null) {
            throw new BadRequestAlertException("A new cuentaProveedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CuentaProveedorDTO result = cuentaProveedorService.save(cuentaProveedorDTO);
        return ResponseEntity.created(new URI("/api/cuenta-proveedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cuenta-proveedors : Updates an existing cuentaProveedor.
     *
     * @param cuentaProveedorDTO the cuentaProveedorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cuentaProveedorDTO,
     * or with status 400 (Bad Request) if the cuentaProveedorDTO is not valid,
     * or with status 500 (Internal Server Error) if the cuentaProveedorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cuenta-proveedors")
    @Timed
    public ResponseEntity<CuentaProveedorDTO> updateCuentaProveedor(@Valid @RequestBody CuentaProveedorDTO cuentaProveedorDTO) throws URISyntaxException {
        log.debug("REST request to update CuentaProveedor : {}", cuentaProveedorDTO);
        if (cuentaProveedorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CuentaProveedorDTO result = cuentaProveedorService.save(cuentaProveedorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cuentaProveedorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cuenta-proveedors : get all the cuentaProveedors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cuentaProveedors in body
     */
    @GetMapping("/cuenta-proveedors")
    @Timed
    public ResponseEntity<List<CuentaProveedorDTO>> getAllCuentaProveedors(Pageable pageable) {
        log.debug("REST request to get a page of CuentaProveedors");
        Page<CuentaProveedorDTO> page = cuentaProveedorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cuenta-proveedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cuenta-proveedors/:id : get the "id" cuentaProveedor.
     *
     * @param id the id of the cuentaProveedorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cuentaProveedorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cuenta-proveedors/{id}")
    @Timed
    public ResponseEntity<CuentaProveedorDTO> getCuentaProveedor(@PathVariable Long id) {
        log.debug("REST request to get CuentaProveedor : {}", id);
        Optional<CuentaProveedorDTO> cuentaProveedorDTO = cuentaProveedorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuentaProveedorDTO);
    }

    /**
     * DELETE  /cuenta-proveedors/:id : delete the "id" cuentaProveedor.
     *
     * @param id the id of the cuentaProveedorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cuenta-proveedors/{id}")
    @Timed
    public ResponseEntity<Void> deleteCuentaProveedor(@PathVariable Long id) {
        log.debug("REST request to delete CuentaProveedor : {}", id);
        cuentaProveedorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cuenta-proveedors?query=:query : search for the cuentaProveedor corresponding
     * to the query.
     *
     * @param query the query of the cuentaProveedor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cuenta-proveedors")
    @Timed
    public ResponseEntity<List<CuentaProveedorDTO>> searchCuentaProveedors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CuentaProveedors for query {}", query);
        Page<CuentaProveedorDTO> page = cuentaProveedorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cuenta-proveedors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
