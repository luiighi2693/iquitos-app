package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ParametroSistemaService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ParametroSistemaDTO;
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
 * REST controller for managing ParametroSistema.
 */
@RestController
@RequestMapping("/api")
public class ParametroSistemaResource {

    private final Logger log = LoggerFactory.getLogger(ParametroSistemaResource.class);

    private static final String ENTITY_NAME = "parametroSistema";

    private final ParametroSistemaService parametroSistemaService;

    public ParametroSistemaResource(ParametroSistemaService parametroSistemaService) {
        this.parametroSistemaService = parametroSistemaService;
    }

    /**
     * POST  /parametro-sistemas : Create a new parametroSistema.
     *
     * @param parametroSistemaDTO the parametroSistemaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new parametroSistemaDTO, or with status 400 (Bad Request) if the parametroSistema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/parametro-sistemas")
    @Timed
    public ResponseEntity<ParametroSistemaDTO> createParametroSistema(@Valid @RequestBody ParametroSistemaDTO parametroSistemaDTO) throws URISyntaxException {
        log.debug("REST request to save ParametroSistema : {}", parametroSistemaDTO);
        if (parametroSistemaDTO.getId() != null) {
            throw new BadRequestAlertException("A new parametroSistema cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametroSistemaDTO result = parametroSistemaService.save(parametroSistemaDTO);
        return ResponseEntity.created(new URI("/api/parametro-sistemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parametro-sistemas : Updates an existing parametroSistema.
     *
     * @param parametroSistemaDTO the parametroSistemaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated parametroSistemaDTO,
     * or with status 400 (Bad Request) if the parametroSistemaDTO is not valid,
     * or with status 500 (Internal Server Error) if the parametroSistemaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/parametro-sistemas")
    @Timed
    public ResponseEntity<ParametroSistemaDTO> updateParametroSistema(@Valid @RequestBody ParametroSistemaDTO parametroSistemaDTO) throws URISyntaxException {
        log.debug("REST request to update ParametroSistema : {}", parametroSistemaDTO);
        if (parametroSistemaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametroSistemaDTO result = parametroSistemaService.save(parametroSistemaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, parametroSistemaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parametro-sistemas : get all the parametroSistemas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of parametroSistemas in body
     */
    @GetMapping("/parametro-sistemas")
    @Timed
    public ResponseEntity<List<ParametroSistemaDTO>> getAllParametroSistemas(Pageable pageable) {
        log.debug("REST request to get a page of ParametroSistemas");
        Page<ParametroSistemaDTO> page = parametroSistemaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/parametro-sistemas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /parametro-sistemas/:id : get the "id" parametroSistema.
     *
     * @param id the id of the parametroSistemaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the parametroSistemaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/parametro-sistemas/{id}")
    @Timed
    public ResponseEntity<ParametroSistemaDTO> getParametroSistema(@PathVariable Long id) {
        log.debug("REST request to get ParametroSistema : {}", id);
        Optional<ParametroSistemaDTO> parametroSistemaDTO = parametroSistemaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametroSistemaDTO);
    }

    /**
     * DELETE  /parametro-sistemas/:id : delete the "id" parametroSistema.
     *
     * @param id the id of the parametroSistemaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/parametro-sistemas/{id}")
    @Timed
    public ResponseEntity<Void> deleteParametroSistema(@PathVariable Long id) {
        log.debug("REST request to delete ParametroSistema : {}", id);
        parametroSistemaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/parametro-sistemas?query=:query : search for the parametroSistema corresponding
     * to the query.
     *
     * @param query the query of the parametroSistema search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/parametro-sistemas")
    @Timed
    public ResponseEntity<List<ParametroSistemaDTO>> searchParametroSistemas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ParametroSistemas for query {}", query);
        Page<ParametroSistemaDTO> page = parametroSistemaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/parametro-sistemas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/parametro-sistemas/reload")
    @Timed
    public ResponseEntity<String> reload() {
        log.debug("RELOAD ITEMS");
        parametroSistemaService.reload();
        System.out.println("*/*/*/*/*/*/*/*/*/*/*/*parametro-sistemas loaded*/*/*/*/*/*/*/*/*/");
        return new ResponseEntity<>("parametro-sistemas loaded", HttpStatus.OK);
    }
}
