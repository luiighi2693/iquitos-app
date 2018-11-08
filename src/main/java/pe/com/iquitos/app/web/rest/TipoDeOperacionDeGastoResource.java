package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.TipoDeOperacionDeGastoService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeGastoDTO;
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
 * REST controller for managing TipoDeOperacionDeGasto.
 */
@RestController
@RequestMapping("/api")
public class TipoDeOperacionDeGastoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeOperacionDeGastoResource.class);

    private static final String ENTITY_NAME = "tipoDeOperacionDeGasto";

    private TipoDeOperacionDeGastoService tipoDeOperacionDeGastoService;

    public TipoDeOperacionDeGastoResource(TipoDeOperacionDeGastoService tipoDeOperacionDeGastoService) {
        this.tipoDeOperacionDeGastoService = tipoDeOperacionDeGastoService;
    }

    /**
     * POST  /tipo-de-operacion-de-gastos : Create a new tipoDeOperacionDeGasto.
     *
     * @param tipoDeOperacionDeGastoDTO the tipoDeOperacionDeGastoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDeOperacionDeGastoDTO, or with status 400 (Bad Request) if the tipoDeOperacionDeGasto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-de-operacion-de-gastos")
    @Timed
    public ResponseEntity<TipoDeOperacionDeGastoDTO> createTipoDeOperacionDeGasto(@Valid @RequestBody TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDeOperacionDeGasto : {}", tipoDeOperacionDeGastoDTO);
        if (tipoDeOperacionDeGastoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeOperacionDeGasto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeOperacionDeGastoDTO result = tipoDeOperacionDeGastoService.save(tipoDeOperacionDeGastoDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-operacion-de-gastos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-de-operacion-de-gastos : Updates an existing tipoDeOperacionDeGasto.
     *
     * @param tipoDeOperacionDeGastoDTO the tipoDeOperacionDeGastoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDeOperacionDeGastoDTO,
     * or with status 400 (Bad Request) if the tipoDeOperacionDeGastoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDeOperacionDeGastoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-de-operacion-de-gastos")
    @Timed
    public ResponseEntity<TipoDeOperacionDeGastoDTO> updateTipoDeOperacionDeGasto(@Valid @RequestBody TipoDeOperacionDeGastoDTO tipoDeOperacionDeGastoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDeOperacionDeGasto : {}", tipoDeOperacionDeGastoDTO);
        if (tipoDeOperacionDeGastoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeOperacionDeGastoDTO result = tipoDeOperacionDeGastoService.save(tipoDeOperacionDeGastoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDeOperacionDeGastoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-de-operacion-de-gastos : get all the tipoDeOperacionDeGastos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDeOperacionDeGastos in body
     */
    @GetMapping("/tipo-de-operacion-de-gastos")
    @Timed
    public ResponseEntity<List<TipoDeOperacionDeGastoDTO>> getAllTipoDeOperacionDeGastos(Pageable pageable) {
        log.debug("REST request to get a page of TipoDeOperacionDeGastos");
        Page<TipoDeOperacionDeGastoDTO> page = tipoDeOperacionDeGastoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-de-operacion-de-gastos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-de-operacion-de-gastos/:id : get the "id" tipoDeOperacionDeGasto.
     *
     * @param id the id of the tipoDeOperacionDeGastoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDeOperacionDeGastoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-de-operacion-de-gastos/{id}")
    @Timed
    public ResponseEntity<TipoDeOperacionDeGastoDTO> getTipoDeOperacionDeGasto(@PathVariable Long id) {
        log.debug("REST request to get TipoDeOperacionDeGasto : {}", id);
        Optional<TipoDeOperacionDeGastoDTO> tipoDeOperacionDeGastoDTO = tipoDeOperacionDeGastoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeOperacionDeGastoDTO);
    }

    /**
     * DELETE  /tipo-de-operacion-de-gastos/:id : delete the "id" tipoDeOperacionDeGasto.
     *
     * @param id the id of the tipoDeOperacionDeGastoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-de-operacion-de-gastos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDeOperacionDeGasto(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeOperacionDeGasto : {}", id);
        tipoDeOperacionDeGastoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-de-operacion-de-gastos?query=:query : search for the tipoDeOperacionDeGasto corresponding
     * to the query.
     *
     * @param query the query of the tipoDeOperacionDeGasto search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-de-operacion-de-gastos")
    @Timed
    public ResponseEntity<List<TipoDeOperacionDeGastoDTO>> searchTipoDeOperacionDeGastos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDeOperacionDeGastos for query {}", query);
        Page<TipoDeOperacionDeGastoDTO> page = tipoDeOperacionDeGastoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-de-operacion-de-gastos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
