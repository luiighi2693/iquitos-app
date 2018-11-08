package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.TipoDeOperacionDeIngresoService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeIngresoDTO;
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
 * REST controller for managing TipoDeOperacionDeIngreso.
 */
@RestController
@RequestMapping("/api")
public class TipoDeOperacionDeIngresoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDeOperacionDeIngresoResource.class);

    private static final String ENTITY_NAME = "tipoDeOperacionDeIngreso";

    private final TipoDeOperacionDeIngresoService tipoDeOperacionDeIngresoService;

    public TipoDeOperacionDeIngresoResource(TipoDeOperacionDeIngresoService tipoDeOperacionDeIngresoService) {
        this.tipoDeOperacionDeIngresoService = tipoDeOperacionDeIngresoService;
    }

    /**
     * POST  /tipo-de-operacion-de-ingresos : Create a new tipoDeOperacionDeIngreso.
     *
     * @param tipoDeOperacionDeIngresoDTO the tipoDeOperacionDeIngresoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDeOperacionDeIngresoDTO, or with status 400 (Bad Request) if the tipoDeOperacionDeIngreso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-de-operacion-de-ingresos")
    @Timed
    public ResponseEntity<TipoDeOperacionDeIngresoDTO> createTipoDeOperacionDeIngreso(@Valid @RequestBody TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDeOperacionDeIngreso : {}", tipoDeOperacionDeIngresoDTO);
        if (tipoDeOperacionDeIngresoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDeOperacionDeIngreso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDeOperacionDeIngresoDTO result = tipoDeOperacionDeIngresoService.save(tipoDeOperacionDeIngresoDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-operacion-de-ingresos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-de-operacion-de-ingresos : Updates an existing tipoDeOperacionDeIngreso.
     *
     * @param tipoDeOperacionDeIngresoDTO the tipoDeOperacionDeIngresoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDeOperacionDeIngresoDTO,
     * or with status 400 (Bad Request) if the tipoDeOperacionDeIngresoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDeOperacionDeIngresoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-de-operacion-de-ingresos")
    @Timed
    public ResponseEntity<TipoDeOperacionDeIngresoDTO> updateTipoDeOperacionDeIngreso(@Valid @RequestBody TipoDeOperacionDeIngresoDTO tipoDeOperacionDeIngresoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDeOperacionDeIngreso : {}", tipoDeOperacionDeIngresoDTO);
        if (tipoDeOperacionDeIngresoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDeOperacionDeIngresoDTO result = tipoDeOperacionDeIngresoService.save(tipoDeOperacionDeIngresoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDeOperacionDeIngresoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-de-operacion-de-ingresos : get all the tipoDeOperacionDeIngresos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDeOperacionDeIngresos in body
     */
    @GetMapping("/tipo-de-operacion-de-ingresos")
    @Timed
    public ResponseEntity<List<TipoDeOperacionDeIngresoDTO>> getAllTipoDeOperacionDeIngresos(Pageable pageable) {
        log.debug("REST request to get a page of TipoDeOperacionDeIngresos");
        Page<TipoDeOperacionDeIngresoDTO> page = tipoDeOperacionDeIngresoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-de-operacion-de-ingresos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tipo-de-operacion-de-ingresos/:id : get the "id" tipoDeOperacionDeIngreso.
     *
     * @param id the id of the tipoDeOperacionDeIngresoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDeOperacionDeIngresoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-de-operacion-de-ingresos/{id}")
    @Timed
    public ResponseEntity<TipoDeOperacionDeIngresoDTO> getTipoDeOperacionDeIngreso(@PathVariable Long id) {
        log.debug("REST request to get TipoDeOperacionDeIngreso : {}", id);
        Optional<TipoDeOperacionDeIngresoDTO> tipoDeOperacionDeIngresoDTO = tipoDeOperacionDeIngresoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDeOperacionDeIngresoDTO);
    }

    /**
     * DELETE  /tipo-de-operacion-de-ingresos/:id : delete the "id" tipoDeOperacionDeIngreso.
     *
     * @param id the id of the tipoDeOperacionDeIngresoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-de-operacion-de-ingresos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDeOperacionDeIngreso(@PathVariable Long id) {
        log.debug("REST request to delete TipoDeOperacionDeIngreso : {}", id);
        tipoDeOperacionDeIngresoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-de-operacion-de-ingresos?query=:query : search for the tipoDeOperacionDeIngreso corresponding
     * to the query.
     *
     * @param query the query of the tipoDeOperacionDeIngreso search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-de-operacion-de-ingresos")
    @Timed
    public ResponseEntity<List<TipoDeOperacionDeIngresoDTO>> searchTipoDeOperacionDeIngresos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDeOperacionDeIngresos for query {}", query);
        Page<TipoDeOperacionDeIngresoDTO> page = tipoDeOperacionDeIngresoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-de-operacion-de-ingresos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
