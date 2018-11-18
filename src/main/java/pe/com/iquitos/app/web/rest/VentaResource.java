package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.VentaService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.VentaDTO;
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
 * REST controller for managing Venta.
 */
@RestController
@RequestMapping("/api")
public class VentaResource {

    private final Logger log = LoggerFactory.getLogger(VentaResource.class);

    private static final String ENTITY_NAME = "venta";

    private final VentaService ventaService;

    public VentaResource(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    /**
     * POST  /ventas : Create a new venta.
     *
     * @param ventaDTO the ventaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ventaDTO, or with status 400 (Bad Request) if the venta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ventas")
    @Timed
    public ResponseEntity<VentaDTO> createVenta(@Valid @RequestBody VentaDTO ventaDTO) throws URISyntaxException {
        log.debug("REST request to save Venta : {}", ventaDTO);
        if (ventaDTO.getId() != null) {
            throw new BadRequestAlertException("A new venta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VentaDTO result = ventaService.save(ventaDTO);
        return ResponseEntity.created(new URI("/api/ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ventas : Updates an existing venta.
     *
     * @param ventaDTO the ventaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ventaDTO,
     * or with status 400 (Bad Request) if the ventaDTO is not valid,
     * or with status 500 (Internal Server Error) if the ventaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ventas")
    @Timed
    public ResponseEntity<VentaDTO> updateVenta(@Valid @RequestBody VentaDTO ventaDTO) throws URISyntaxException {
        log.debug("REST request to update Venta : {}", ventaDTO);
        if (ventaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VentaDTO result = ventaService.save(ventaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ventaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ventas : get all the ventas.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of ventas in body
     */
    @GetMapping("/ventas")
    @Timed
    public ResponseEntity<List<VentaDTO>> getAllVentas(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Ventas");
        Page<VentaDTO> page;
        if (eagerload) {
            page = ventaService.findAllWithEagerRelationships(pageable);
        } else {
            page = ventaService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/ventas?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /ventas/:id : get the "id" venta.
     *
     * @param id the id of the ventaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ventaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ventas/{id}")
    @Timed
    public ResponseEntity<VentaDTO> getVenta(@PathVariable Long id) {
        log.debug("REST request to get Venta : {}", id);
        Optional<VentaDTO> ventaDTO = ventaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ventaDTO);
    }

    /**
     * DELETE  /ventas/:id : delete the "id" venta.
     *
     * @param id the id of the ventaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ventas/{id}")
    @Timed
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        log.debug("REST request to delete Venta : {}", id);
        ventaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ventas?query=:query : search for the venta corresponding
     * to the query.
     *
     * @param query the query of the venta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/ventas")
    @Timed
    public ResponseEntity<List<VentaDTO>> searchVentas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Ventas for query {}", query);
        Page<VentaDTO> page = ventaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/ventas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
