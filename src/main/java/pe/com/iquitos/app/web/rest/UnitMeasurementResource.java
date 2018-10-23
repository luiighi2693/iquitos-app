package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.UnitMeasurementService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.UnitMeasurementDTO;
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
 * REST controller for managing UnitMeasurement.
 */
@RestController
@RequestMapping("/api")
public class UnitMeasurementResource {

    private final Logger log = LoggerFactory.getLogger(UnitMeasurementResource.class);

    private static final String ENTITY_NAME = "unitMeasurement";

    private UnitMeasurementService unitMeasurementService;

    public UnitMeasurementResource(UnitMeasurementService unitMeasurementService) {
        this.unitMeasurementService = unitMeasurementService;
    }

    /**
     * POST  /unit-measurements : Create a new unitMeasurement.
     *
     * @param unitMeasurementDTO the unitMeasurementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unitMeasurementDTO, or with status 400 (Bad Request) if the unitMeasurement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unit-measurements")
    @Timed
    public ResponseEntity<UnitMeasurementDTO> createUnitMeasurement(@RequestBody UnitMeasurementDTO unitMeasurementDTO) throws URISyntaxException {
        log.debug("REST request to save UnitMeasurement : {}", unitMeasurementDTO);
        if (unitMeasurementDTO.getId() != null) {
            throw new BadRequestAlertException("A new unitMeasurement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitMeasurementDTO result = unitMeasurementService.save(unitMeasurementDTO);
        return ResponseEntity.created(new URI("/api/unit-measurements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unit-measurements : Updates an existing unitMeasurement.
     *
     * @param unitMeasurementDTO the unitMeasurementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unitMeasurementDTO,
     * or with status 400 (Bad Request) if the unitMeasurementDTO is not valid,
     * or with status 500 (Internal Server Error) if the unitMeasurementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unit-measurements")
    @Timed
    public ResponseEntity<UnitMeasurementDTO> updateUnitMeasurement(@RequestBody UnitMeasurementDTO unitMeasurementDTO) throws URISyntaxException {
        log.debug("REST request to update UnitMeasurement : {}", unitMeasurementDTO);
        if (unitMeasurementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitMeasurementDTO result = unitMeasurementService.save(unitMeasurementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unitMeasurementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unit-measurements : get all the unitMeasurements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of unitMeasurements in body
     */
    @GetMapping("/unit-measurements")
    @Timed
    public ResponseEntity<List<UnitMeasurementDTO>> getAllUnitMeasurements(Pageable pageable) {
        log.debug("REST request to get a page of UnitMeasurements");
        Page<UnitMeasurementDTO> page = unitMeasurementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unit-measurements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /unit-measurements/:id : get the "id" unitMeasurement.
     *
     * @param id the id of the unitMeasurementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unitMeasurementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/unit-measurements/{id}")
    @Timed
    public ResponseEntity<UnitMeasurementDTO> getUnitMeasurement(@PathVariable Long id) {
        log.debug("REST request to get UnitMeasurement : {}", id);
        Optional<UnitMeasurementDTO> unitMeasurementDTO = unitMeasurementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitMeasurementDTO);
    }

    /**
     * DELETE  /unit-measurements/:id : delete the "id" unitMeasurement.
     *
     * @param id the id of the unitMeasurementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unit-measurements/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnitMeasurement(@PathVariable Long id) {
        log.debug("REST request to delete UnitMeasurement : {}", id);
        unitMeasurementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/unit-measurements?query=:query : search for the unitMeasurement corresponding
     * to the query.
     *
     * @param query the query of the unitMeasurement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/unit-measurements")
    @Timed
    public ResponseEntity<List<UnitMeasurementDTO>> searchUnitMeasurements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnitMeasurements for query {}", query);
        Page<UnitMeasurementDTO> page = unitMeasurementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/unit-measurements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
