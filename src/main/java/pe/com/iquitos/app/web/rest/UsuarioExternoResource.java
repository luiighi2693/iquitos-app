package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.UsuarioExternoService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.UsuarioExternoDTO;
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
 * REST controller for managing UsuarioExterno.
 */
@RestController
@RequestMapping("/api")
public class UsuarioExternoResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioExternoResource.class);

    private static final String ENTITY_NAME = "usuarioExterno";

    private final UsuarioExternoService usuarioExternoService;

    public UsuarioExternoResource(UsuarioExternoService usuarioExternoService) {
        this.usuarioExternoService = usuarioExternoService;
    }

    /**
     * POST  /usuario-externos : Create a new usuarioExterno.
     *
     * @param usuarioExternoDTO the usuarioExternoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new usuarioExternoDTO, or with status 400 (Bad Request) if the usuarioExterno has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/usuario-externos")
    @Timed
    public ResponseEntity<UsuarioExternoDTO> createUsuarioExterno(@Valid @RequestBody UsuarioExternoDTO usuarioExternoDTO) throws URISyntaxException {
        log.debug("REST request to save UsuarioExterno : {}", usuarioExternoDTO);
        if (usuarioExternoDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarioExterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsuarioExternoDTO result = usuarioExternoService.save(usuarioExternoDTO);
        return ResponseEntity.created(new URI("/api/usuario-externos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /usuario-externos : Updates an existing usuarioExterno.
     *
     * @param usuarioExternoDTO the usuarioExternoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated usuarioExternoDTO,
     * or with status 400 (Bad Request) if the usuarioExternoDTO is not valid,
     * or with status 500 (Internal Server Error) if the usuarioExternoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/usuario-externos")
    @Timed
    public ResponseEntity<UsuarioExternoDTO> updateUsuarioExterno(@Valid @RequestBody UsuarioExternoDTO usuarioExternoDTO) throws URISyntaxException {
        log.debug("REST request to update UsuarioExterno : {}", usuarioExternoDTO);
        if (usuarioExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsuarioExternoDTO result = usuarioExternoService.save(usuarioExternoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, usuarioExternoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /usuario-externos : get all the usuarioExternos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of usuarioExternos in body
     */
    @GetMapping("/usuario-externos")
    @Timed
    public ResponseEntity<List<UsuarioExternoDTO>> getAllUsuarioExternos(Pageable pageable) {
        log.debug("REST request to get a page of UsuarioExternos");
        Page<UsuarioExternoDTO> page = usuarioExternoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/usuario-externos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /usuario-externos/:id : get the "id" usuarioExterno.
     *
     * @param id the id of the usuarioExternoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the usuarioExternoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/usuario-externos/{id}")
    @Timed
    public ResponseEntity<UsuarioExternoDTO> getUsuarioExterno(@PathVariable Long id) {
        log.debug("REST request to get UsuarioExterno : {}", id);
        Optional<UsuarioExternoDTO> usuarioExternoDTO = usuarioExternoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioExternoDTO);
    }

    @GetMapping("/usuario-externos/dni/{dni}")
    @Timed
    public ResponseEntity<UsuarioExternoDTO> getUsuarioExternoByDni(@PathVariable Integer dni) {
        log.debug("REST request to get UsuarioExternoById : {}", dni);
        Optional<UsuarioExternoDTO> usuarioExternoDTO = usuarioExternoService.findOneByDni(dni);
        return ResponseUtil.wrapOrNotFound(usuarioExternoDTO);
    }

    /**
     * DELETE  /usuario-externos/:id : delete the "id" usuarioExterno.
     *
     * @param id the id of the usuarioExternoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/usuario-externos/{id}")
    @Timed
    public ResponseEntity<Void> deleteUsuarioExterno(@PathVariable Long id) {
        log.debug("REST request to delete UsuarioExterno : {}", id);
        usuarioExternoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/usuario-externos?query=:query : search for the usuarioExterno corresponding
     * to the query.
     *
     * @param query the query of the usuarioExterno search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/usuario-externos")
    @Timed
    public ResponseEntity<List<UsuarioExternoDTO>> searchUsuarioExternos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UsuarioExternos for query {}", query);
        Page<UsuarioExternoDTO> page = usuarioExternoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/usuario-externos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
