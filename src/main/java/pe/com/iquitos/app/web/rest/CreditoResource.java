package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.CreditoService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.CreditoDTO;
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
 * REST controller for managing Credito.
 */
@RestController
@RequestMapping("/api")
public class CreditoResource {

    private final Logger log = LoggerFactory.getLogger(CreditoResource.class);

    private static final String ENTITY_NAME = "credito";

    private final CreditoService creditoService;

    public CreditoResource(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    /**
     * POST  /creditos : Create a new credito.
     *
     * @param creditoDTO the creditoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new creditoDTO, or with status 400 (Bad Request) if the credito has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/creditos")
    @Timed
    public ResponseEntity<CreditoDTO> createCredito(@Valid @RequestBody CreditoDTO creditoDTO) throws URISyntaxException {
        log.debug("REST request to save Credito : {}", creditoDTO);
        if (creditoDTO.getId() != null) {
            throw new BadRequestAlertException("A new credito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditoDTO result = creditoService.save(creditoDTO);
        return ResponseEntity.created(new URI("/api/creditos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /creditos : Updates an existing credito.
     *
     * @param creditoDTO the creditoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated creditoDTO,
     * or with status 400 (Bad Request) if the creditoDTO is not valid,
     * or with status 500 (Internal Server Error) if the creditoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/creditos")
    @Timed
    public ResponseEntity<CreditoDTO> updateCredito(@Valid @RequestBody CreditoDTO creditoDTO) throws URISyntaxException {
        log.debug("REST request to update Credito : {}", creditoDTO);
        if (creditoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CreditoDTO result = creditoService.save(creditoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, creditoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /creditos : get all the creditos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of creditos in body
     */
    @GetMapping("/creditos")
    @Timed
    public ResponseEntity<List<CreditoDTO>> getAllCreditos(Pageable pageable) {
        log.debug("REST request to get a page of Creditos");
        Page<CreditoDTO> page = creditoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/creditos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /creditos/:id : get the "id" credito.
     *
     * @param id the id of the creditoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the creditoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/creditos/{id}")
    @Timed
    public ResponseEntity<CreditoDTO> getCredito(@PathVariable Long id) {
        log.debug("REST request to get Credito : {}", id);
        Optional<CreditoDTO> creditoDTO = creditoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditoDTO);
    }

    /**
     * DELETE  /creditos/:id : delete the "id" credito.
     *
     * @param id the id of the creditoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/creditos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCredito(@PathVariable Long id) {
        log.debug("REST request to delete Credito : {}", id);
        creditoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/creditos?query=:query : search for the credito corresponding
     * to the query.
     *
     * @param query the query of the credito search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/creditos")
    @Timed
    public ResponseEntity<List<CreditoDTO>> searchCreditos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Creditos for query {}", query);
        Page<CreditoDTO> page = creditoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/creditos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
