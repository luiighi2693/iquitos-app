package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.VariantService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.VariantDTO;
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
 * REST controller for managing Variant.
 */
@RestController
@RequestMapping("/api")
public class VariantResource {

    private final Logger log = LoggerFactory.getLogger(VariantResource.class);

    private static final String ENTITY_NAME = "variant";

    private VariantService variantService;

    public VariantResource(VariantService variantService) {
        this.variantService = variantService;
    }

    /**
     * POST  /variants : Create a new variant.
     *
     * @param variantDTO the variantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new variantDTO, or with status 400 (Bad Request) if the variant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/variants")
    @Timed
    public ResponseEntity<VariantDTO> createVariant(@RequestBody VariantDTO variantDTO) throws URISyntaxException {
        log.debug("REST request to save Variant : {}", variantDTO);
        if (variantDTO.getId() != null) {
            throw new BadRequestAlertException("A new variant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VariantDTO result = variantService.save(variantDTO);
        return ResponseEntity.created(new URI("/api/variants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /variants : Updates an existing variant.
     *
     * @param variantDTO the variantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated variantDTO,
     * or with status 400 (Bad Request) if the variantDTO is not valid,
     * or with status 500 (Internal Server Error) if the variantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/variants")
    @Timed
    public ResponseEntity<VariantDTO> updateVariant(@RequestBody VariantDTO variantDTO) throws URISyntaxException {
        log.debug("REST request to update Variant : {}", variantDTO);
        if (variantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VariantDTO result = variantService.save(variantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, variantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /variants : get all the variants.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of variants in body
     */
    @GetMapping("/variants")
    @Timed
    public ResponseEntity<List<VariantDTO>> getAllVariants(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Variants");
        Page<VariantDTO> page;
        if (eagerload) {
            page = variantService.findAllWithEagerRelationships(pageable);
        } else {
            page = variantService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/variants?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /variants/:id : get the "id" variant.
     *
     * @param id the id of the variantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the variantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/variants/{id}")
    @Timed
    public ResponseEntity<VariantDTO> getVariant(@PathVariable Long id) {
        log.debug("REST request to get Variant : {}", id);
        Optional<VariantDTO> variantDTO = variantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variantDTO);
    }

    /**
     * DELETE  /variants/:id : delete the "id" variant.
     *
     * @param id the id of the variantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/variants/{id}")
    @Timed
    public ResponseEntity<Void> deleteVariant(@PathVariable Long id) {
        log.debug("REST request to delete Variant : {}", id);
        variantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/variants?query=:query : search for the variant corresponding
     * to the query.
     *
     * @param query the query of the variant search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/variants")
    @Timed
    public ResponseEntity<List<VariantDTO>> searchVariants(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Variants for query {}", query);
        Page<VariantDTO> page = variantService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/variants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
