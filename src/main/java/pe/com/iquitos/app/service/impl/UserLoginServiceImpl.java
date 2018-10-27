package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.UserLoginService;
import pe.com.iquitos.app.domain.UserLogin;
import pe.com.iquitos.app.repository.UserLoginRepository;
import pe.com.iquitos.app.repository.search.UserLoginSearchRepository;
import pe.com.iquitos.app.service.dto.UserLoginDTO;
import pe.com.iquitos.app.service.mapper.UserLoginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UserLogin.
 */
@Service
@Transactional
public class UserLoginServiceImpl implements UserLoginService {

    private final Logger log = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    private UserLoginRepository userLoginRepository;

    private UserLoginMapper userLoginMapper;

    private UserLoginSearchRepository userLoginSearchRepository;

    public UserLoginServiceImpl(UserLoginRepository userLoginRepository, UserLoginMapper userLoginMapper, UserLoginSearchRepository userLoginSearchRepository) {
        this.userLoginRepository = userLoginRepository;
        this.userLoginMapper = userLoginMapper;
        this.userLoginSearchRepository = userLoginSearchRepository;
    }

    /**
     * Save a userLogin.
     *
     * @param userLoginDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserLoginDTO save(UserLoginDTO userLoginDTO) {
        log.debug("Request to save UserLogin : {}", userLoginDTO);

        UserLogin userLogin = userLoginMapper.toEntity(userLoginDTO);
        userLogin = userLoginRepository.save(userLogin);
        UserLoginDTO result = userLoginMapper.toDto(userLogin);
        userLoginSearchRepository.save(userLogin);
        return result;
    }

    /**
     * Get all the userLogins.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserLoginDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserLogins");
        return userLoginRepository.findAll(pageable)
            .map(userLoginMapper::toDto);
    }


    /**
     * Get one userLogin by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserLoginDTO> findOne(Long id) {
        log.debug("Request to get UserLogin : {}", id);
        return userLoginRepository.findById(id)
            .map(userLoginMapper::toDto);
    }

    /**
     * Delete the userLogin by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserLogin : {}", id);
        userLoginRepository.deleteById(id);
        userLoginSearchRepository.deleteById(id);
    }

    /**
     * Search for the userLogin corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserLoginDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UserLogins for query {}", query);
        return userLoginSearchRepository.search(queryStringQuery(query), pageable)
            .map(userLoginMapper::toDto);
    }
}
