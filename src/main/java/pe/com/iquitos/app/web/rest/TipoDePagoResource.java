package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.TipoDePagoService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.TipoDePagoDTO;
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
 * REST controller for managing TipoDePago.
 */
@RestController
@RequestMapping("/api")
public class TipoDePagoResource {

    private final Logger log = LoggerFactory.getLogger(TipoDePagoResource.class);

    private static final String ENTITY_NAME = "tipoDePago";

    private final TipoDePagoService tipoDePagoService;

    public TipoDePagoResource(TipoDePagoService tipoDePagoService) {
        this.tipoDePagoService = tipoDePagoService;
    }

    /**
     * POST  /tipo-de-pagos : Create a new tipoDePago.
     *
     * @param tipoDePagoDTO the tipoDePagoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoDePagoDTO, or with status 400 (Bad Request) if the tipoDePago has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-de-pagos")
    @Timed
    public ResponseEntity<TipoDePagoDTO> createTipoDePago(@Valid @RequestBody TipoDePagoDTO tipoDePagoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDePago : {}", tipoDePagoDTO);
        if (tipoDePagoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDePago cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDePagoDTO result = tipoDePagoService.save(tipoDePagoDTO);
        return ResponseEntity.created(new URI("/api/tipo-de-pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-de-pagos : Updates an existing tipoDePago.
     *
     * @param tipoDePagoDTO the tipoDePagoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoDePagoDTO,
     * or with status 400 (Bad Request) if the tipoDePagoDTO is not valid,
     * or with status 500 (Internal Server Error) if the tipoDePagoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-de-pagos")
    @Timed
    public ResponseEntity<TipoDePagoDTO> updateTipoDePago(@Valid @RequestBody TipoDePagoDTO tipoDePagoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoDePago : {}", tipoDePagoDTO);
        if (tipoDePagoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDePagoDTO result = tipoDePagoService.save(tipoDePagoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoDePagoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-de-pagos : get all the tipoDePagos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoDePagos in body
     */
    @GetMapping("/tipo-de-pagos")
    @Timed
    public ResponseEntity<List<TipoDePagoDTO>> getAllTipoDePagos(Pageable pageable) {
        log.debug("REST request to get a page of TipoDePagos");
        Page<TipoDePagoDTO> page = tipoDePagoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-de-pagos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /tipo-de-pagos/:id : get the "id" tipoDePago.
     *
     * @param id the id of the tipoDePagoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoDePagoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-de-pagos/{id}")
    @Timed
    public ResponseEntity<TipoDePagoDTO> getTipoDePago(@PathVariable Long id) {
        log.debug("REST request to get TipoDePago : {}", id);
        Optional<TipoDePagoDTO> tipoDePagoDTO = tipoDePagoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDePagoDTO);
    }

    /**
     * DELETE  /tipo-de-pagos/:id : delete the "id" tipoDePago.
     *
     * @param id the id of the tipoDePagoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-de-pagos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoDePago(@PathVariable Long id) {
        log.debug("REST request to delete TipoDePago : {}", id);
        tipoDePagoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipo-de-pagos?query=:query : search for the tipoDePago corresponding
     * to the query.
     *
     * @param query the query of the tipoDePago search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/tipo-de-pagos")
    @Timed
    public ResponseEntity<List<TipoDePagoDTO>> searchTipoDePagos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TipoDePagos for query {}", query);
        Page<TipoDePagoDTO> page = tipoDePagoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tipo-de-pagos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
