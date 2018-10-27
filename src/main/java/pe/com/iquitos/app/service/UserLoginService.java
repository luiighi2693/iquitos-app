package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.UserLoginDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UserLogin.
 */
public interface UserLoginService {

    /**
     * Save a userLogin.
     *
     * @param userLoginDTO the entity to save
     * @return the persisted entity
     */
    UserLoginDTO save(UserLoginDTO userLoginDTO);

    /**
     * Get all the userLogins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserLoginDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userLogin.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserLoginDTO> findOne(Long id);

    /**
     * Delete the "id" userLogin.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the userLogin corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserLoginDTO> search(String query, Pageable pageable);
}
