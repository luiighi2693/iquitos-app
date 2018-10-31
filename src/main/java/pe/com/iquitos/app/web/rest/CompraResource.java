package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.CompraService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.CompraDTO;
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
 * REST controller for managing Compra.
 */
@RestController
@RequestMapping("/api")
public class CompraResource {

    private final Logger log = LoggerFactory.getLogger(CompraResource.class);

    private static final String ENTITY_NAME = "compra";

    private CompraService compraService;

    public CompraResource(CompraService compraService) {
        this.compraService = compraService;
    }

    /**
     * POST  /compras : Create a new compra.
     *
     * @param compraDTO the compraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compraDTO, or with status 400 (Bad Request) if the compra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compras")
    @Timed
    public ResponseEntity<CompraDTO> createCompra(@Valid @RequestBody CompraDTO compraDTO) throws URISyntaxException {
        log.debug("REST request to save Compra : {}", compraDTO);
        if (compraDTO.getId() != null) {
            throw new BadRequestAlertException("A new compra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraDTO result = compraService.save(compraDTO);
        return ResponseEntity.created(new URI("/api/compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compras : Updates an existing compra.
     *
     * @param compraDTO the compraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compraDTO,
     * or with status 400 (Bad Request) if the compraDTO is not valid,
     * or with status 500 (Internal Server Error) if the compraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compras")
    @Timed
    public ResponseEntity<CompraDTO> updateCompra(@Valid @RequestBody CompraDTO compraDTO) throws URISyntaxException {
        log.debug("REST request to update Compra : {}", compraDTO);
        if (compraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompraDTO result = compraService.save(compraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compras : get all the compras.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of compras in body
     */
    @GetMapping("/compras")
    @Timed
    public ResponseEntity<List<CompraDTO>> getAllCompras(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Compras");
        Page<CompraDTO> page;
        if (eagerload) {
            page = compraService.findAllWithEagerRelationships(pageable);
        } else {
            page = compraService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/compras?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /compras/:id : get the "id" compra.
     *
     * @param id the id of the compraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compras/{id}")
    @Timed
    public ResponseEntity<CompraDTO> getCompra(@PathVariable Long id) {
        log.debug("REST request to get Compra : {}", id);
        Optional<CompraDTO> compraDTO = compraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compraDTO);
    }

    /**
     * DELETE  /compras/:id : delete the "id" compra.
     *
     * @param id the id of the compraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompra(@PathVariable Long id) {
        log.debug("REST request to delete Compra : {}", id);
        compraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/compras?query=:query : search for the compra corresponding
     * to the query.
     *
     * @param query the query of the compra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/compras")
    @Timed
    public ResponseEntity<List<CompraDTO>> searchCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Compras for query {}", query);
        Page<CompraDTO> page = compraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
