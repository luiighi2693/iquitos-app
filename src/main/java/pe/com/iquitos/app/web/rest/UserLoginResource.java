package pe.com.iquitos.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import pe.com.iquitos.app.service.UserLoginService;
import pe.com.iquitos.app.web.rest.errors.BadRequestAlertException;
import pe.com.iquitos.app.web.rest.util.HeaderUtil;
import pe.com.iquitos.app.web.rest.util.PaginationUtil;
import pe.com.iquitos.app.service.dto.UserLoginDTO;
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
 * REST controller for managing UserLogin.
 */
@RestController
@RequestMapping("/api")
public class UserLoginResource {

    private final Logger log = LoggerFactory.getLogger(UserLoginResource.class);

    private static final String ENTITY_NAME = "userLogin";

    private UserLoginService userLoginService;

    public UserLoginResource(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    /**
     * POST  /user-logins : Create a new userLogin.
     *
     * @param userLoginDTO the userLoginDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userLoginDTO, or with status 400 (Bad Request) if the userLogin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-logins")
    @Timed
    public ResponseEntity<UserLoginDTO> createUserLogin(@RequestBody UserLoginDTO userLoginDTO) throws URISyntaxException {
        log.debug("REST request to save UserLogin : {}", userLoginDTO);
        if (userLoginDTO.getId() != null) {
            throw new BadRequestAlertException("A new userLogin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserLoginDTO result = userLoginService.save(userLoginDTO);
        return ResponseEntity.created(new URI("/api/user-logins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-logins : Updates an existing userLogin.
     *
     * @param userLoginDTO the userLoginDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userLoginDTO,
     * or with status 400 (Bad Request) if the userLoginDTO is not valid,
     * or with status 500 (Internal Server Error) if the userLoginDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-logins")
    @Timed
    public ResponseEntity<UserLoginDTO> updateUserLogin(@RequestBody UserLoginDTO userLoginDTO) throws URISyntaxException {
        log.debug("REST request to update UserLogin : {}", userLoginDTO);
        if (userLoginDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserLoginDTO result = userLoginService.save(userLoginDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userLoginDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-logins : get all the userLogins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userLogins in body
     */
    @GetMapping("/user-logins")
    @Timed
    public ResponseEntity<List<UserLoginDTO>> getAllUserLogins(Pageable pageable) {
        log.debug("REST request to get a page of UserLogins");
        Page<UserLoginDTO> page = userLoginService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-logins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-logins/:id : get the "id" userLogin.
     *
     * @param id the id of the userLoginDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userLoginDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-logins/{id}")
    @Timed
    public ResponseEntity<UserLoginDTO> getUserLogin(@PathVariable Long id) {
        log.debug("REST request to get UserLogin : {}", id);
        Optional<UserLoginDTO> userLoginDTO = userLoginService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userLoginDTO);
    }

    /**
     * DELETE  /user-logins/:id : delete the "id" userLogin.
     *
     * @param id the id of the userLoginDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-logins/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserLogin(@PathVariable Long id) {
        log.debug("REST request to delete UserLogin : {}", id);
        userLoginService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/user-logins?query=:query : search for the userLogin corresponding
     * to the query.
     *
     * @param query the query of the userLogin search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/user-logins")
    @Timed
    public ResponseEntity<List<UserLoginDTO>> searchUserLogins(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UserLogins for query {}", query);
        Page<UserLoginDTO> page = userLoginService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/user-logins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
