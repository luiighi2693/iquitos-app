package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.BoxService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.BoxDTO;
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
 * REST controller for managing Box.
 */
@RestController
@RequestMapping("/api")
public class BoxResource {

    private final Logger log = LoggerFactory.getLogger(BoxResource.class);

    private static final String ENTITY_NAME = "box";

    private BoxService boxService;

    public BoxResource(BoxService boxService) {
        this.boxService = boxService;
    }

    /**
     * POST  /boxes : Create a new box.
     *
     * @param boxDTO the boxDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new boxDTO, or with status 400 (Bad Request) if the box has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/boxes")
    @Timed
    public ResponseEntity<BoxDTO> createBox(@RequestBody BoxDTO boxDTO) throws URISyntaxException {
        log.debug("REST request to save Box : {}", boxDTO);
        if (boxDTO.getId() != null) {
            throw new BadRequestAlertException("A new box cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoxDTO result = boxService.save(boxDTO);
        return ResponseEntity.created(new URI("/api/boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /boxes : Updates an existing box.
     *
     * @param boxDTO the boxDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated boxDTO,
     * or with status 400 (Bad Request) if the boxDTO is not valid,
     * or with status 500 (Internal Server Error) if the boxDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/boxes")
    @Timed
    public ResponseEntity<BoxDTO> updateBox(@RequestBody BoxDTO boxDTO) throws URISyntaxException {
        log.debug("REST request to update Box : {}", boxDTO);
        if (boxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoxDTO result = boxService.save(boxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, boxDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /boxes : get all the boxes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of boxes in body
     */
    @GetMapping("/boxes")
    @Timed
    public ResponseEntity<List<BoxDTO>> getAllBoxes(Pageable pageable) {
        log.debug("REST request to get a page of Boxes");
        Page<BoxDTO> page = boxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/boxes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /boxes/:id : get the "id" box.
     *
     * @param id the id of the boxDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the boxDTO, or with status 404 (Not Found)
     */
    @GetMapping("/boxes/{id}")
    @Timed
    public ResponseEntity<BoxDTO> getBox(@PathVariable Long id) {
        log.debug("REST request to get Box : {}", id);
        Optional<BoxDTO> boxDTO = boxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boxDTO);
    }

    /**
     * DELETE  /boxes/:id : delete the "id" box.
     *
     * @param id the id of the boxDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/boxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBox(@PathVariable Long id) {
        log.debug("REST request to delete Box : {}", id);
        boxService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/boxes?query=:query : search for the box corresponding
     * to the query.
     *
     * @param query the query of the box search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/boxes")
    @Timed
    public ResponseEntity<List<BoxDTO>> searchBoxes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Boxes for query {}", query);
        Page<BoxDTO> page = boxService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/boxes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
