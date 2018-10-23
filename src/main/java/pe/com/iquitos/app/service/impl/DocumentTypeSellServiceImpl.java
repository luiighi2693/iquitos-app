package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.DocumentTypeSellService;
import pe.com.iquitos.app.domain.DocumentTypeSell;
import pe.com.iquitos.app.repository.DocumentTypeSellRepository;
import pe.com.iquitos.app.repository.search.DocumentTypeSellSearchRepository;
import pe.com.iquitos.app.service.dto.DocumentTypeSellDTO;
import pe.com.iquitos.app.service.mapper.DocumentTypeSellMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DocumentTypeSell.
 */
@Service
@Transactional
public class DocumentTypeSellServiceImpl implements DocumentTypeSellService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeSellServiceImpl.class);

    private DocumentTypeSellRepository documentTypeSellRepository;

    private DocumentTypeSellMapper documentTypeSellMapper;

    private DocumentTypeSellSearchRepository documentTypeSellSearchRepository;

    public DocumentTypeSellServiceImpl(DocumentTypeSellRepository documentTypeSellRepository, DocumentTypeSellMapper documentTypeSellMapper, DocumentTypeSellSearchRepository documentTypeSellSearchRepository) {
        this.documentTypeSellRepository = documentTypeSellRepository;
        this.documentTypeSellMapper = documentTypeSellMapper;
        this.documentTypeSellSearchRepository = documentTypeSellSearchRepository;
    }

    /**
     * Save a documentTypeSell.
     *
     * @param documentTypeSellDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DocumentTypeSellDTO save(DocumentTypeSellDTO documentTypeSellDTO) {
        log.debug("Request to save DocumentTypeSell : {}", documentTypeSellDTO);

        DocumentTypeSell documentTypeSell = documentTypeSellMapper.toEntity(documentTypeSellDTO);
        documentTypeSell = documentTypeSellRepository.save(documentTypeSell);
        DocumentTypeSellDTO result = documentTypeSellMapper.toDto(documentTypeSell);
        documentTypeSellSearchRepository.save(documentTypeSell);
        return result;
    }

    /**
     * Get all the documentTypeSells.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypeSellDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTypeSells");
        return documentTypeSellRepository.findAll(pageable)
            .map(documentTypeSellMapper::toDto);
    }


    /**
     * Get one documentTypeSell by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentTypeSellDTO> findOne(Long id) {
        log.debug("Request to get DocumentTypeSell : {}", id);
        return documentTypeSellRepository.findById(id)
            .map(documentTypeSellMapper::toDto);
    }

    /**
     * Delete the documentTypeSell by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentTypeSell : {}", id);
        documentTypeSellRepository.deleteById(id);
        documentTypeSellSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentTypeSell corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypeSellDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentTypeSells for query {}", query);
        return documentTypeSellSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentTypeSellMapper::toDto);
    }
}
