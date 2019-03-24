package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.TipoDeDocumentoService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDTO;
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
 * REST controller for managing TipoDeDocumento.
 */
@RestController
@RequestMapping("/api")
public class TipoDeDocumentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeDocumentoResource.class);

    private static final String ENTITY_NAME = "tipoDeDocumento";

    private final TipoDeDocumentoService tipoDeDocumentoService;

    public TipoDeDocumentoResource(TipoDeDocumentoService tipoDeDocumentoService) {
        this.tipoDeDocumentoService = tipoDeDocumentoService;
    }

    /**
     * POST  /tipo-de-documentos : Create a new tipoDeDocumento.
     *
     * @param tipoDeDocumentoDTO the tipoDeDocumentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDeDocumentoDTO, or with status 400 (Bad Request) if the tipoDeDocumento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-de-documentos")
    @Timed
    public ResponseEntity<TipoDeDocumentoDTO> createTipoDeDocumento(@Valid @RequestBody TipoDeDocumentoDTO tipoDeDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDeDocumento : {}", tipoDeDocumentoDTO);
        if (tipoDeDocumentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeDocumento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeDocumentoDTO result = tipoDeDocumentoService.save(tipoDeDocumentoDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-documentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-de-documentos : Updates an existing tipoDeDocumento.
     *
     * @param tipoDeDocumentoDTO the tipoDeDocumentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDeDocumentoDTO,
     * or with status 400 (Bad Request) if the tipoDeDocumentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDeDocumentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-de-documentos")
    @Timed
    public ResponseEntity<TipoDeDocumentoDTO> updateTipoDeDocumento(@Valid @RequestBody TipoDeDocumentoDTO tipoDeDocumentoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDeDocumento : {}", tipoDeDocumentoDTO);
        if (tipoDeDocumentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeDocumentoDTO result = tipoDeDocumentoService.save(tipoDeDocumentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDeDocumentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-de-documentos : get all the tipoDeDocumentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDeDocumentos in body
     */
    @GetMapping("/tipo-de-documentos")
    @Timed
    public ResponseEntity<List<TipoDeDocumentoDTO>> getAllTipoDeDocumentos(Pageable pageable) {
        log.debug("REST request to get a page of TipoDeDocumentos");
        Page<TipoDeDocumentoDTO> page = tipoDeDocumentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-de-documentos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tipo-de-documentos/:id : get the "id" tipoDeDocumento.
     *
     * @param id the id of the tipoDeDocumentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDeDocumentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-de-documentos/{id}")
    @Timed
    public ResponseEntity<TipoDeDocumentoDTO> getTipoDeDocumento(@PathVariable Long id) {
        log.debug("REST request to get TipoDeDocumento : {}", id);
        Optional<TipoDeDocumentoDTO> tipoDeDocumentoDTO = tipoDeDocumentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeDocumentoDTO);
    }

    /**
     * DELETE  /tipo-de-documentos/:id : delete the "id" tipoDeDocumento.
     *
     * @param id the id of the tipoDeDocumentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-de-documentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDeDocumento(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeDocumento : {}", id);
        tipoDeDocumentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-de-documentos?query=:query : search for the tipoDeDocumento corresponding
     * to the query.
     *
     * @param query the query of the tipoDeDocumento search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-de-documentos")
    @Timed
    public ResponseEntity<List<TipoDeDocumentoDTO>> searchTipoDeDocumentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDeDocumentos for query {}", query);
        Page<TipoDeDocumentoDTO> page = tipoDeDocumentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-de-documentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/tipo-de-documentos/reload")
    @Timed
    public ResponseEntity<String> reload() {
        log.debug("RELOAD ITEMS");
        tipoDeDocumentoService.reload();
        System.out.println("*/*/*/*/*/*/*/*/*/*/*/*tipo-de-documentos loaded*/*/*/*/*/*/*/*/*/");
        return new ResponseEntity<>("tipo-de-documentos loaded", HttpStatus.OK);
    }
}
