package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.EstatusDeCompraService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.EstatusDeCompraDTO;
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
 * REST controller for managing EstatusDeCompra.
 */
@RestController
@RequestMapping("/api")
public class EstatusDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(EstatusDeCompraResource.class);

    private static final String ENTITY_NAME = "estatusDeCompra";

    private final EstatusDeCompraService estatusDeCompraService;

    public EstatusDeCompraResource(EstatusDeCompraService estatusDeCompraService) {
        this.estatusDeCompraService = estatusDeCompraService;
    }

    /**
     * POST  /estatus-de-compras : Create a new estatusDeCompra.
     *
     * @param estatusDeCompraDTO the estatusDeCompraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estatusDeCompraDTO, or with status 400 (Bad Request) if the estatusDeCompra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estatus-de-compras")
    @Timed
    public ResponseEntity<EstatusDeCompraDTO> createEstatusDeCompra(@Valid @RequestBody EstatusDeCompraDTO estatusDeCompraDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusDeCompra : {}", estatusDeCompraDTO);
        if (estatusDeCompraDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusDeCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusDeCompraDTO result = estatusDeCompraService.save(estatusDeCompraDTO);
        return ResponseEntity.created(new URI("/api/estatus-de-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estatus-de-compras : Updates an existing estatusDeCompra.
     *
     * @param estatusDeCompraDTO the estatusDeCompraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estatusDeCompraDTO,
     * or with status 400 (Bad Request) if the estatusDeCompraDTO is not valid,
     * or with status 500 (Internal Server Error) if the estatusDeCompraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estatus-de-compras")
    @Timed
    public ResponseEntity<EstatusDeCompraDTO> updateEstatusDeCompra(@Valid @RequestBody EstatusDeCompraDTO estatusDeCompraDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusDeCompra : {}", estatusDeCompraDTO);
        if (estatusDeCompraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusDeCompraDTO result = estatusDeCompraService.save(estatusDeCompraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estatusDeCompraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estatus-de-compras : get all the estatusDeCompras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estatusDeCompras in body
     */
    @GetMapping("/estatus-de-compras")
    @Timed
    public ResponseEntity<List<EstatusDeCompraDTO>> getAllEstatusDeCompras(Pageable pageable) {
        log.debug("REST request to get a page of EstatusDeCompras");
        Page<EstatusDeCompraDTO> page = estatusDeCompraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estatus-de-compras");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /estatus-de-compras/:id : get the "id" estatusDeCompra.
     *
     * @param id the id of the estatusDeCompraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estatusDeCompraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estatus-de-compras/{id}")
    @Timed
    public ResponseEntity<EstatusDeCompraDTO> getEstatusDeCompra(@PathVariable Long id) {
        log.debug("REST request to get EstatusDeCompra : {}", id);
        Optional<EstatusDeCompraDTO> estatusDeCompraDTO = estatusDeCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusDeCompraDTO);
    }

    /**
     * DELETE  /estatus-de-compras/:id : delete the "id" estatusDeCompra.
     *
     * @param id the id of the estatusDeCompraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estatus-de-compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstatusDeCompra(@PathVariable Long id) {
        log.debug("REST request to delete EstatusDeCompra : {}", id);
        estatusDeCompraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/estatus-de-compras?query=:query : search for the estatusDeCompra corresponding
     * to the query.
     *
     * @param query the query of the estatusDeCompra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/estatus-de-compras")
    @Timed
    public ResponseEntity<List<EstatusDeCompraDTO>> searchEstatusDeCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EstatusDeCompras for query {}", query);
        Page<EstatusDeCompraDTO> page = estatusDeCompraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/estatus-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
