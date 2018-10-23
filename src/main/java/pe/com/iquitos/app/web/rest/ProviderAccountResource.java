package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ProviderAccountService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ProviderAccountDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ProviderAccount.
 */
@RestController
@RequestMapping("/api")
public class ProviderAccountResource {

    private final Logger log = LoggerFactory.getLogger(ProviderAccountResource.class);

    private static final String ENTITY_NAME = "providerAccount";

    private ProviderAccountService providerAccountService;

    public ProviderAccountResource(ProviderAccountService providerAccountService) {
        this.providerAccountService = providerAccountService;
    }

    /**
     * POST  /provider-accounts : Create a new providerAccount.
     *
     * @param providerAccountDTO the providerAccountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerAccountDTO, or with status 400 (Bad Request) if the providerAccount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provider-accounts")
    @Timed
    public ResponseEntity<ProviderAccountDTO> createProviderAccount(@RequestBody ProviderAccountDTO providerAccountDTO) throws URISyntaxException {
        log.debug("REST request to save ProviderAccount : {}", providerAccountDTO);
        if (providerAccountDTO.getId() != null) {
            throw new BadRequestAlertException("A new providerAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProviderAccountDTO result = providerAccountService.save(providerAccountDTO);
        return ResponseEntity.created(new URI("/api/provider-accounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provider-accounts : Updates an existing providerAccount.
     *
     * @param providerAccountDTO the providerAccountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerAccountDTO,
     * or with status 400 (Bad Request) if the providerAccountDTO is not valid,
     * or with status 500 (Internal Server Error) if the providerAccountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provider-accounts")
    @Timed
    public ResponseEntity<ProviderAccountDTO> updateProviderAccount(@RequestBody ProviderAccountDTO providerAccountDTO) throws URISyntaxException {
        log.debug("REST request to update ProviderAccount : {}", providerAccountDTO);
        if (providerAccountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProviderAccountDTO result = providerAccountService.save(providerAccountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerAccountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provider-accounts : get all the providerAccounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of providerAccounts in body
     */
    @GetMapping("/provider-accounts")
    @Timed
    public ResponseEntity<List<ProviderAccountDTO>> getAllProviderAccounts(Pageable pageable) {
        log.debug("REST request to get a page of ProviderAccounts");
        Page<ProviderAccountDTO> page = providerAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provider-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /provider-accounts/:id : get the "id" providerAccount.
     *
     * @param id the id of the providerAccountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerAccountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/provider-accounts/{id}")
    @Timed
    public ResponseEntity<ProviderAccountDTO> getProviderAccount(@PathVariable Long id) {
        log.debug("REST request to get ProviderAccount : {}", id);
        Optional<ProviderAccountDTO> providerAccountDTO = providerAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(providerAccountDTO);
    }

    /**
     * DELETE  /provider-accounts/:id : delete the "id" providerAccount.
     *
     * @param id the id of the providerAccountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-accounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteProviderAccount(@PathVariable Long id) {
        log.debug("REST request to delete ProviderAccount : {}", id);
        providerAccountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/provider-accounts?query=:query : search for the providerAccount corresponding
     * to the query.
     *
     * @param query the query of the providerAccount search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/provider-accounts")
    @Timed
    public ResponseEntity<List<ProviderAccountDTO>> searchProviderAccounts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProviderAccounts for query {}", query);
        Page<ProviderAccountDTO> page = providerAccountService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/provider-accounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
