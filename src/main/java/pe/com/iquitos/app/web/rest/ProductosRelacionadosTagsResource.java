package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.ProductosRelacionadosTagsService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.ProductosRelacionadosTagsDTO;
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
 * REST controller for managing ProductosRelacionadosTags.
 */
@RestController
@RequestMapping("/api")
public class ProductosRelacionadosTagsResource {

    private final Logger log = LoggerFactory.getLogger(ProductosRelacionadosTagsResource.class);

    private static final String ENTITY_NAME = "productosRelacionadosTags";

    private final ProductosRelacionadosTagsService productosRelacionadosTagsService;

    public ProductosRelacionadosTagsResource(ProductosRelacionadosTagsService productosRelacionadosTagsService) {
        this.productosRelacionadosTagsService = productosRelacionadosTagsService;
    }

    /**
     * POST  /productos-relacionados-tags : Create a new productosRelacionadosTags.
     *
     * @param productosRelacionadosTagsDTO the productosRelacionadosTagsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productosRelacionadosTagsDTO, or with status 400 (Bad Request) if the productosRelacionadosTags has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productos-relacionados-tags")
    @Timed
    public ResponseEntity<ProductosRelacionadosTagsDTO> createProductosRelacionadosTags(@Valid @RequestBody ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO) throws URISyntaxException {
        log.debug("REST request to save ProductosRelacionadosTags : {}", productosRelacionadosTagsDTO);
        if (productosRelacionadosTagsDTO.getId() != null) {
            throw new BadRequestAlertException("A new productosRelacionadosTags cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductosRelacionadosTagsDTO result = productosRelacionadosTagsService.save(productosRelacionadosTagsDTO);
        return ResponseEntity.created(new URI("/api/productos-relacionados-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productos-relacionados-tags : Updates an existing productosRelacionadosTags.
     *
     * @param productosRelacionadosTagsDTO the productosRelacionadosTagsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productosRelacionadosTagsDTO,
     * or with status 400 (Bad Request) if the productosRelacionadosTagsDTO is not valid,
     * or with status 500 (Internal Server Error) if the productosRelacionadosTagsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productos-relacionados-tags")
    @Timed
    public ResponseEntity<ProductosRelacionadosTagsDTO> updateProductosRelacionadosTags(@Valid @RequestBody ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO) throws URISyntaxException {
        log.debug("REST request to update ProductosRelacionadosTags : {}", productosRelacionadosTagsDTO);
        if (productosRelacionadosTagsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductosRelacionadosTagsDTO result = productosRelacionadosTagsService.save(productosRelacionadosTagsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productosRelacionadosTagsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productos-relacionados-tags : get all the productosRelacionadosTags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productosRelacionadosTags in body
     */
    @GetMapping("/productos-relacionados-tags")
    @Timed
    public ResponseEntity<List<ProductosRelacionadosTagsDTO>> getAllProductosRelacionadosTags(Pageable pageable) {
        log.debug("REST request to get a page of ProductosRelacionadosTags");
        Page<ProductosRelacionadosTagsDTO> page = productosRelacionadosTagsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/productos-relacionados-tags");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /productos-relacionados-tags/:id : get the "id" productosRelacionadosTags.
     *
     * @param id the id of the productosRelacionadosTagsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productosRelacionadosTagsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/productos-relacionados-tags/{id}")
    @Timed
    public ResponseEntity<ProductosRelacionadosTagsDTO> getProductosRelacionadosTags(@PathVariable Long id) {
        log.debug("REST request to get ProductosRelacionadosTags : {}", id);
        Optional<ProductosRelacionadosTagsDTO> productosRelacionadosTagsDTO = productosRelacionadosTagsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productosRelacionadosTagsDTO);
    }

    /**
     * DELETE  /productos-relacionados-tags/:id : delete the "id" productosRelacionadosTags.
     *
     * @param id the id of the productosRelacionadosTagsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productos-relacionados-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductosRelacionadosTags(@PathVariable Long id) {
        log.debug("REST request to delete ProductosRelacionadosTags : {}", id);
        productosRelacionadosTagsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/productos-relacionados-tags?query=:query : search for the productosRelacionadosTags corresponding
     * to the query.
     *
     * @param query the query of the productosRelacionadosTags search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/productos-relacionados-tags")
    @Timed
    public ResponseEntity<List<ProductosRelacionadosTagsDTO>> searchProductosRelacionadosTags(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductosRelacionadosTags for query {}", query);
        Page<ProductosRelacionadosTagsDTO> page = productosRelacionadosTagsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/productos-relacionados-tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
