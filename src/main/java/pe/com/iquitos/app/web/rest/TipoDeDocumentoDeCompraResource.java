package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.TipoDeDocumentoDeCompraService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeCompraDTO;
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
 * REST controller for managing TipoDeDocumentoDeCompra.
 */
@RestController
@RequestMapping("/api")
public class TipoDeDocumentoDeCompraResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeDocumentoDeCompraResource.class);

    private static final String ENTITY_NAME = "tipoDeDocumentoDeCompra";

    private TipoDeDocumentoDeCompraService tipoDeDocumentoDeCompraService;

    public TipoDeDocumentoDeCompraResource(TipoDeDocumentoDeCompraService tipoDeDocumentoDeCompraService) {
        this.tipoDeDocumentoDeCompraService = tipoDeDocumentoDeCompraService;
    }

    /**
     * POST  /tipo-de-documento-de-compras : Create a new tipoDeDocumentoDeCompra.
     *
     * @param tipoDeDocumentoDeCompraDTO the tipoDeDocumentoDeCompraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDeDocumentoDeCompraDTO, or with status 400 (Bad Request) if the tipoDeDocumentoDeCompra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-de-documento-de-compras")
    @Timed
    public ResponseEntity<TipoDeDocumentoDeCompraDTO> createTipoDeDocumentoDeCompra(@Valid @RequestBody TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDeDocumentoDeCompra : {}", tipoDeDocumentoDeCompraDTO);
        if (tipoDeDocumentoDeCompraDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeDocumentoDeCompra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeDocumentoDeCompraDTO result = tipoDeDocumentoDeCompraService.save(tipoDeDocumentoDeCompraDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-documento-de-compras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-de-documento-de-compras : Updates an existing tipoDeDocumentoDeCompra.
     *
     * @param tipoDeDocumentoDeCompraDTO the tipoDeDocumentoDeCompraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDeDocumentoDeCompraDTO,
     * or with status 400 (Bad Request) if the tipoDeDocumentoDeCompraDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDeDocumentoDeCompraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-de-documento-de-compras")
    @Timed
    public ResponseEntity<TipoDeDocumentoDeCompraDTO> updateTipoDeDocumentoDeCompra(@Valid @RequestBody TipoDeDocumentoDeCompraDTO tipoDeDocumentoDeCompraDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDeDocumentoDeCompra : {}", tipoDeDocumentoDeCompraDTO);
        if (tipoDeDocumentoDeCompraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeDocumentoDeCompraDTO result = tipoDeDocumentoDeCompraService.save(tipoDeDocumentoDeCompraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDeDocumentoDeCompraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-de-documento-de-compras : get all the tipoDeDocumentoDeCompras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDeDocumentoDeCompras in body
     */
    @GetMapping("/tipo-de-documento-de-compras")
    @Timed
    public ResponseEntity<List<TipoDeDocumentoDeCompraDTO>> getAllTipoDeDocumentoDeCompras(Pageable pageable) {
        log.debug("REST request to get a page of TipoDeDocumentoDeCompras");
        Page<TipoDeDocumentoDeCompraDTO> page = tipoDeDocumentoDeCompraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-de-documento-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-de-documento-de-compras/:id : get the "id" tipoDeDocumentoDeCompra.
     *
     * @param id the id of the tipoDeDocumentoDeCompraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDeDocumentoDeCompraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-de-documento-de-compras/{id}")
    @Timed
    public ResponseEntity<TipoDeDocumentoDeCompraDTO> getTipoDeDocumentoDeCompra(@PathVariable Long id) {
        log.debug("REST request to get TipoDeDocumentoDeCompra : {}", id);
        Optional<TipoDeDocumentoDeCompraDTO> tipoDeDocumentoDeCompraDTO = tipoDeDocumentoDeCompraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeDocumentoDeCompraDTO);
    }

    /**
     * DELETE  /tipo-de-documento-de-compras/:id : delete the "id" tipoDeDocumentoDeCompra.
     *
     * @param id the id of the tipoDeDocumentoDeCompraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-de-documento-de-compras/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDeDocumentoDeCompra(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeDocumentoDeCompra : {}", id);
        tipoDeDocumentoDeCompraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-de-documento-de-compras?query=:query : search for the tipoDeDocumentoDeCompra corresponding
     * to the query.
     *
     * @param query the query of the tipoDeDocumentoDeCompra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-de-documento-de-compras")
    @Timed
    public ResponseEntity<List<TipoDeDocumentoDeCompraDTO>> searchTipoDeDocumentoDeCompras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDeDocumentoDeCompras for query {}", query);
        Page<TipoDeDocumentoDeCompraDTO> page = tipoDeDocumentoDeCompraService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-de-documento-de-compras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
