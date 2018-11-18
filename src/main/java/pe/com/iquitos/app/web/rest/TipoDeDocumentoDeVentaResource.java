package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.TipoDeDocumentoDeVentaService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeVentaDTO;
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
 * REST controller for managing TipoDeDocumentoDeVenta.
 */
@RestController
@RequestMapping("/api")
public class TipoDeDocumentoDeVentaResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeDocumentoDeVentaResource.class);

    private static final String ENTITY_NAME = "tipoDeDocumentoDeVenta";

    private final TipoDeDocumentoDeVentaService tipoDeDocumentoDeVentaService;

    public TipoDeDocumentoDeVentaResource(TipoDeDocumentoDeVentaService tipoDeDocumentoDeVentaService) {
        this.tipoDeDocumentoDeVentaService = tipoDeDocumentoDeVentaService;
    }

    /**
     * POST  /tipo-de-documento-de-ventas : Create a new tipoDeDocumentoDeVenta.
     *
     * @param tipoDeDocumentoDeVentaDTO the tipoDeDocumentoDeVentaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDeDocumentoDeVentaDTO, or with status 400 (Bad Request) if the tipoDeDocumentoDeVenta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-de-documento-de-ventas")
    @Timed
    public ResponseEntity<TipoDeDocumentoDeVentaDTO> createTipoDeDocumentoDeVenta(@Valid @RequestBody TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDeDocumentoDeVenta : {}", tipoDeDocumentoDeVentaDTO);
        if (tipoDeDocumentoDeVentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeDocumentoDeVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeDocumentoDeVentaDTO result = tipoDeDocumentoDeVentaService.save(tipoDeDocumentoDeVentaDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-documento-de-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-de-documento-de-ventas : Updates an existing tipoDeDocumentoDeVenta.
     *
     * @param tipoDeDocumentoDeVentaDTO the tipoDeDocumentoDeVentaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDeDocumentoDeVentaDTO,
     * or with status 400 (Bad Request) if the tipoDeDocumentoDeVentaDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDeDocumentoDeVentaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-de-documento-de-ventas")
    @Timed
    public ResponseEntity<TipoDeDocumentoDeVentaDTO> updateTipoDeDocumentoDeVenta(@Valid @RequestBody TipoDeDocumentoDeVentaDTO tipoDeDocumentoDeVentaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDeDocumentoDeVenta : {}", tipoDeDocumentoDeVentaDTO);
        if (tipoDeDocumentoDeVentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeDocumentoDeVentaDTO result = tipoDeDocumentoDeVentaService.save(tipoDeDocumentoDeVentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDeDocumentoDeVentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-de-documento-de-ventas : get all the tipoDeDocumentoDeVentas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDeDocumentoDeVentas in body
     */
    @GetMapping("/tipo-de-documento-de-ventas")
    @Timed
    public ResponseEntity<List<TipoDeDocumentoDeVentaDTO>> getAllTipoDeDocumentoDeVentas(Pageable pageable) {
        log.debug("REST request to get a page of TipoDeDocumentoDeVentas");
        Page<TipoDeDocumentoDeVentaDTO> page = tipoDeDocumentoDeVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-de-documento-de-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tipo-de-documento-de-ventas/:id : get the "id" tipoDeDocumentoDeVenta.
     *
     * @param id the id of the tipoDeDocumentoDeVentaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDeDocumentoDeVentaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-de-documento-de-ventas/{id}")
    @Timed
    public ResponseEntity<TipoDeDocumentoDeVentaDTO> getTipoDeDocumentoDeVenta(@PathVariable Long id) {
        log.debug("REST request to get TipoDeDocumentoDeVenta : {}", id);
        Optional<TipoDeDocumentoDeVentaDTO> tipoDeDocumentoDeVentaDTO = tipoDeDocumentoDeVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeDocumentoDeVentaDTO);
    }

    /**
     * DELETE  /tipo-de-documento-de-ventas/:id : delete the "id" tipoDeDocumentoDeVenta.
     *
     * @param id the id of the tipoDeDocumentoDeVentaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-de-documento-de-ventas/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDeDocumentoDeVenta(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeDocumentoDeVenta : {}", id);
        tipoDeDocumentoDeVentaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-de-documento-de-ventas?query=:query : search for the tipoDeDocumentoDeVenta corresponding
     * to the query.
     *
     * @param query the query of the tipoDeDocumentoDeVenta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-de-documento-de-ventas")
    @Timed
    public ResponseEntity<List<TipoDeDocumentoDeVentaDTO>> searchTipoDeDocumentoDeVentas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDeDocumentoDeVentas for query {}", query);
        Page<TipoDeDocumentoDeVentaDTO> page = tipoDeDocumentoDeVentaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-de-documento-de-ventas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
