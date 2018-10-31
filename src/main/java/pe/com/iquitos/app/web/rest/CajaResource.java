package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.CajaService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.CajaDTO;
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
 * REST controller for managing Caja.
 */
@RestController
@RequestMapping("/api")
public class CajaResource {

    private final Logger log = LoggerFactory.getLogger(CajaResource.class);

    private static final String ENTITY_NAME = "caja";

    private CajaService cajaService;

    public CajaResource(CajaService cajaService) {
        this.cajaService = cajaService;
    }

    /**
     * POST  /cajas : Create a new caja.
     *
     * @param cajaDTO the cajaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cajaDTO, or with status 400 (Bad Request) if the caja has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cajas")
    @Timed
    public ResponseEntity<CajaDTO> createCaja(@Valid @RequestBody CajaDTO cajaDTO) throws URISyntaxException {
        log.debug("REST request to save Caja : {}", cajaDTO);
        if (cajaDTO.getId() != null) {
            throw new BadRequestAlertException("A new caja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CajaDTO result = cajaService.save(cajaDTO);
        return ResponseEntity.created(new URI("/api/cajas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cajas : Updates an existing caja.
     *
     * @param cajaDTO the cajaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cajaDTO,
     * or with status 400 (Bad Request) if the cajaDTO is not valid,
     * or with status 500 (Internal Server Error) if the cajaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cajas")
    @Timed
    public ResponseEntity<CajaDTO> updateCaja(@Valid @RequestBody CajaDTO cajaDTO) throws URISyntaxException {
        log.debug("REST request to update Caja : {}", cajaDTO);
        if (cajaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CajaDTO result = cajaService.save(cajaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cajaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cajas : get all the cajas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cajas in body
     */
    @GetMapping("/cajas")
    @Timed
    public ResponseEntity<List<CajaDTO>> getAllCajas(Pageable pageable) {
        log.debug("REST request to get a page of Cajas");
        Page<CajaDTO> page = cajaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cajas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cajas/:id : get the "id" caja.
     *
     * @param id the id of the cajaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cajaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cajas/{id}")
    @Timed
    public ResponseEntity<CajaDTO> getCaja(@PathVariable Long id) {
        log.debug("REST request to get Caja : {}", id);
        Optional<CajaDTO> cajaDTO = cajaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cajaDTO);
    }

    /**
     * DELETE  /cajas/:id : delete the "id" caja.
     *
     * @param id the id of the cajaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cajas/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaja(@PathVariable Long id) {
        log.debug("REST request to delete Caja : {}", id);
        cajaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cajas?query=:query : search for the caja corresponding
     * to the query.
     *
     * @param query the query of the caja search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cajas")
    @Timed
    public ResponseEntity<List<CajaDTO>> searchCajas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Cajas for query {}", query);
        Page<CajaDTO> page = cajaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cajas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
