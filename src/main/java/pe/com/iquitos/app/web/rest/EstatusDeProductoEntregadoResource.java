package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.EstatusDeProductoEntregadoService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.EstatusDeProductoEntregadoDTO;
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
 * REST controller for managing EstatusDeProductoEntregado.
 */
@RestController
@RequestMapping("/api")
public class EstatusDeProductoEntregadoResource {

    private final Logger log = LoggerFactory.getLogger(EstatusDeProductoEntregadoResource.class);

    private static final String ENTITY_NAME = "estatusDeProductoEntregado";

    private EstatusDeProductoEntregadoService estatusDeProductoEntregadoService;

    public EstatusDeProductoEntregadoResource(EstatusDeProductoEntregadoService estatusDeProductoEntregadoService) {
        this.estatusDeProductoEntregadoService = estatusDeProductoEntregadoService;
    }

    /**
     * POST  /estatus-de-producto-entregados : Create a new estatusDeProductoEntregado.
     *
     * @param estatusDeProductoEntregadoDTO the estatusDeProductoEntregadoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estatusDeProductoEntregadoDTO, or with status 400 (Bad Request) if the estatusDeProductoEntregado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estatus-de-producto-entregados")
    @Timed
    public ResponseEntity<EstatusDeProductoEntregadoDTO> createEstatusDeProductoEntregado(@Valid @RequestBody EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusDeProductoEntregado : {}", estatusDeProductoEntregadoDTO);
        if (estatusDeProductoEntregadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusDeProductoEntregado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusDeProductoEntregadoDTO result = estatusDeProductoEntregadoService.save(estatusDeProductoEntregadoDTO);
        return ResponseEntity.created(new URI("/api/estatus-de-producto-entregados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estatus-de-producto-entregados : Updates an existing estatusDeProductoEntregado.
     *
     * @param estatusDeProductoEntregadoDTO the estatusDeProductoEntregadoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estatusDeProductoEntregadoDTO,
     * or with status 400 (Bad Request) if the estatusDeProductoEntregadoDTO is not valid,
     * or with status 500 (Internal Server Error) if the estatusDeProductoEntregadoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estatus-de-producto-entregados")
    @Timed
    public ResponseEntity<EstatusDeProductoEntregadoDTO> updateEstatusDeProductoEntregado(@Valid @RequestBody EstatusDeProductoEntregadoDTO estatusDeProductoEntregadoDTO) throws URISyntaxException {
        log.debug("REST request to update EstatusDeProductoEntregado : {}", estatusDeProductoEntregadoDTO);
        if (estatusDeProductoEntregadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstatusDeProductoEntregadoDTO result = estatusDeProductoEntregadoService.save(estatusDeProductoEntregadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estatusDeProductoEntregadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estatus-de-producto-entregados : get all the estatusDeProductoEntregados.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of estatusDeProductoEntregados in body
     */
    @GetMapping("/estatus-de-producto-entregados")
    @Timed
    public ResponseEntity<List<EstatusDeProductoEntregadoDTO>> getAllEstatusDeProductoEntregados(Pageable pageable) {
        log.debug("REST request to get a page of EstatusDeProductoEntregados");
        Page<EstatusDeProductoEntregadoDTO> page = estatusDeProductoEntregadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estatus-de-producto-entregados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estatus-de-producto-entregados/:id : get the "id" estatusDeProductoEntregado.
     *
     * @param id the id of the estatusDeProductoEntregadoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estatusDeProductoEntregadoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/estatus-de-producto-entregados/{id}")
    @Timed
    public ResponseEntity<EstatusDeProductoEntregadoDTO> getEstatusDeProductoEntregado(@PathVariable Long id) {
        log.debug("REST request to get EstatusDeProductoEntregado : {}", id);
        Optional<EstatusDeProductoEntregadoDTO> estatusDeProductoEntregadoDTO = estatusDeProductoEntregadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusDeProductoEntregadoDTO);
    }

    /**
     * DELETE  /estatus-de-producto-entregados/:id : delete the "id" estatusDeProductoEntregado.
     *
     * @param id the id of the estatusDeProductoEntregadoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estatus-de-producto-entregados/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstatusDeProductoEntregado(@PathVariable Long id) {
        log.debug("REST request to delete EstatusDeProductoEntregado : {}", id);
        estatusDeProductoEntregadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/estatus-de-producto-entregados?query=:query : search for the estatusDeProductoEntregado corresponding
     * to the query.
     *
     * @param query the query of the estatusDeProductoEntregado search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/estatus-de-producto-entregados")
    @Timed
    public ResponseEntity<List<EstatusDeProductoEntregadoDTO>> searchEstatusDeProductoEntregados(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EstatusDeProductoEntregados for query {}", query);
        Page<EstatusDeProductoEntregadoDTO> page = estatusDeProductoEntregadoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/estatus-de-producto-entregados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
