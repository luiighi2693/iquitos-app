package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.UnidadDeMedidaService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.UnidadDeMedidaDTO;
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
 * REST controller for managing UnidadDeMedida.
 */
@RestController
@RequestMapping("/api")
public class UnidadDeMedidaResource {

    private final Logger log = LoggerFactory.getLogger(UnidadDeMedidaResource.class);

    private static final String ENTITY_NAME = "unidadDeMedida";

    private UnidadDeMedidaService unidadDeMedidaService;

    public UnidadDeMedidaResource(UnidadDeMedidaService unidadDeMedidaService) {
        this.unidadDeMedidaService = unidadDeMedidaService;
    }

    /**
     * POST  /unidad-de-medidas : Create a new unidadDeMedida.
     *
     * @param unidadDeMedidaDTO the unidadDeMedidaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unidadDeMedidaDTO, or with status 400 (Bad Request) if the unidadDeMedida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unidad-de-medidas")
    @Timed
    public ResponseEntity<UnidadDeMedidaDTO> createUnidadDeMedida(@Valid @RequestBody UnidadDeMedidaDTO unidadDeMedidaDTO) throws URISyntaxException {
        log.debug("REST request to save UnidadDeMedida : {}", unidadDeMedidaDTO);
        if (unidadDeMedidaDTO.getId() != null) {
            throw new BadRequestAlertException("A new unidadDeMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadDeMedidaDTO result = unidadDeMedidaService.save(unidadDeMedidaDTO);
        return ResponseEntity.created(new URI("/api/unidad-de-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unidad-de-medidas : Updates an existing unidadDeMedida.
     *
     * @param unidadDeMedidaDTO the unidadDeMedidaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unidadDeMedidaDTO,
     * or with status 400 (Bad Request) if the unidadDeMedidaDTO is not valid,
     * or with status 500 (Internal Server Error) if the unidadDeMedidaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unidad-de-medidas")
    @Timed
    public ResponseEntity<UnidadDeMedidaDTO> updateUnidadDeMedida(@Valid @RequestBody UnidadDeMedidaDTO unidadDeMedidaDTO) throws URISyntaxException {
        log.debug("REST request to update UnidadDeMedida : {}", unidadDeMedidaDTO);
        if (unidadDeMedidaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadDeMedidaDTO result = unidadDeMedidaService.save(unidadDeMedidaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unidadDeMedidaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unidad-de-medidas : get all the unidadDeMedidas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of unidadDeMedidas in body
     */
    @GetMapping("/unidad-de-medidas")
    @Timed
    public ResponseEntity<List<UnidadDeMedidaDTO>> getAllUnidadDeMedidas(Pageable pageable) {
        log.debug("REST request to get a page of UnidadDeMedidas");
        Page<UnidadDeMedidaDTO> page = unidadDeMedidaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unidad-de-medidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /unidad-de-medidas/:id : get the "id" unidadDeMedida.
     *
     * @param id the id of the unidadDeMedidaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unidadDeMedidaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/unidad-de-medidas/{id}")
    @Timed
    public ResponseEntity<UnidadDeMedidaDTO> getUnidadDeMedida(@PathVariable Long id) {
        log.debug("REST request to get UnidadDeMedida : {}", id);
        Optional<UnidadDeMedidaDTO> unidadDeMedidaDTO = unidadDeMedidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadDeMedidaDTO);
    }

    /**
     * DELETE  /unidad-de-medidas/:id : delete the "id" unidadDeMedida.
     *
     * @param id the id of the unidadDeMedidaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unidad-de-medidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnidadDeMedida(@PathVariable Long id) {
        log.debug("REST request to delete UnidadDeMedida : {}", id);
        unidadDeMedidaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/unidad-de-medidas?query=:query : search for the unidadDeMedida corresponding
     * to the query.
     *
     * @param query the query of the unidadDeMedida search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/unidad-de-medidas")
    @Timed
    public ResponseEntity<List<UnidadDeMedidaDTO>> searchUnidadDeMedidas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnidadDeMedidas for query {}", query);
        Page<UnidadDeMedidaDTO> page = unidadDeMedidaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/unidad-de-medidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
