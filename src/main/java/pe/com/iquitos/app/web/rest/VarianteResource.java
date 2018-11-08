package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.VarianteService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.VarianteDTO;
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
 * REST controller for managing Variante.
 */
@RestController
@RequestMapping("/api")
public class VarianteResource {

    private final Logger log = LoggerFactory.getLogger(VarianteResource.class);

    private static final String ENTITY_NAME = "variante";

    private final VarianteService varianteService;

    public VarianteResource(VarianteService varianteService) {
        this.varianteService = varianteService;
    }

    /**
     * POST  /variantes : Create a new variante.
     *
     * @param varianteDTO the varianteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new varianteDTO, or with status 400 (Bad Request) if the variante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/variantes")
    @Timed
    public ResponseEntity<VarianteDTO> createVariante(@Valid @RequestBody VarianteDTO varianteDTO) throws URISyntaxException {
        log.debug("REST request to save Variante : {}", varianteDTO);
        if (varianteDTO.getId() != null) {
            throw new BadRequestAlertException("A new variante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VarianteDTO result = varianteService.save(varianteDTO);
        return ResponseEntity.created(new URI("/api/variantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /variantes : Updates an existing variante.
     *
     * @param varianteDTO the varianteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated varianteDTO,
     * or with status 400 (Bad Request) if the varianteDTO is not valid,
     * or with status 500 (Internal Server Error) if the varianteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/variantes")
    @Timed
    public ResponseEntity<VarianteDTO> updateVariante(@Valid @RequestBody VarianteDTO varianteDTO) throws URISyntaxException {
        log.debug("REST request to update Variante : {}", varianteDTO);
        if (varianteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VarianteDTO result = varianteService.save(varianteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, varianteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /variantes : get all the variantes.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of variantes in body
     */
    @GetMapping("/variantes")
    @Timed
    public ResponseEntity<List<VarianteDTO>> getAllVariantes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Variantes");
        Page<VarianteDTO> page;
        if (eagerload) {
            page = varianteService.findAllWithEagerRelationships(pageable);
        } else {
            page = varianteService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/variantes?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /variantes/:id : get the "id" variante.
     *
     * @param id the id of the varianteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the varianteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/variantes/{id}")
    @Timed
    public ResponseEntity<VarianteDTO> getVariante(@PathVariable Long id) {
        log.debug("REST request to get Variante : {}", id);
        Optional<VarianteDTO> varianteDTO = varianteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(varianteDTO);
    }

    /**
     * DELETE  /variantes/:id : delete the "id" variante.
     *
     * @param id the id of the varianteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/variantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVariante(@PathVariable Long id) {
        log.debug("REST request to delete Variante : {}", id);
        varianteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/variantes?query=:query : search for the variante corresponding
     * to the query.
     *
     * @param query the query of the variante search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/variantes")
    @Timed
    public ResponseEntity<List<VarianteDTO>> searchVariantes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Variantes for query {}", query);
        Page<VarianteDTO> page = varianteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/variantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
