package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ContactoProveedorService;
import pe.com.iquitos.app.domain.ContactoProveedor;
import pe.com.iquitos.app.repository.ContactoProveedorRepository;
import pe.com.iquitos.app.repository.search.ContactoProveedorSearchRepository;
import pe.com.iquitos.app.service.dto.ContactoProveedorDTO;
import pe.com.iquitos.app.service.mapper.ContactoProveedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ContactoProveedor.
 */
@Service
@Transactional
public class ContactoProveedorServiceImpl implements ContactoProveedorService {

    private final Logger log = LoggerFactory.getLogger(ContactoProveedorServiceImpl.class);

    private final ContactoProveedorRepository contactoProveedorRepository;

    private final ContactoProveedorMapper contactoProveedorMapper;

    private final ContactoProveedorSearchRepository contactoProveedorSearchRepository;

    public ContactoProveedorServiceImpl(ContactoProveedorRepository contactoProveedorRepository, ContactoProveedorMapper contactoProveedorMapper, ContactoProveedorSearchRepository contactoProveedorSearchRepository) {
        this.contactoProveedorRepository = contactoProveedorRepository;
        this.contactoProveedorMapper = contactoProveedorMapper;
        this.contactoProveedorSearchRepository = contactoProveedorSearchRepository;
    }

    /**
     * Save a contactoProveedor.
     *
     * @param contactoProveedorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactoProveedorDTO save(ContactoProveedorDTO contactoProveedorDTO) {
        log.debug("Request to save ContactoProveedor : {}", contactoProveedorDTO);

        ContactoProveedor contactoProveedor = contactoProveedorMapper.toEntity(contactoProveedorDTO);
        contactoProveedor = contactoProveedorRepository.save(contactoProveedor);
        ContactoProveedorDTO result = contactoProveedorMapper.toDto(contactoProveedor);
        contactoProveedorSearchRepository.save(contactoProveedor);
        return result;
    }

    /**
     * Get all the contactoProveedors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactoProveedorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactoProveedors");
        return contactoProveedorRepository.findAll(pageable)
            .map(contactoProveedorMapper::toDto);
    }


    /**
     * Get one contactoProveedor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactoProveedorDTO> findOne(Long id) {
        log.debug("Request to get ContactoProveedor : {}", id);
        return contactoProveedorRepository.findById(id)
            .map(contactoProveedorMapper::toDto);
    }

    /**
     * Delete the contactoProveedor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactoProveedor : {}", id);
        contactoProveedorRepository.deleteById(id);
        contactoProveedorSearchRepository.deleteById(id);
    }

    /**
     * Search for the contactoProveedor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactoProveedorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContactoProveedors for query {}", query);
        return contactoProveedorSearchRepository.search(queryStringQuery(query), pageable)
            .map(contactoProveedorMapper::toDto);
    }
}
