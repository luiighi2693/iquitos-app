package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.DocumentTypePurchaseService;
import pe.com.iquitos.app.domain.DocumentTypePurchase;
import pe.com.iquitos.app.repository.DocumentTypePurchaseRepository;
import pe.com.iquitos.app.repository.search.DocumentTypePurchaseSearchRepository;
import pe.com.iquitos.app.service.dto.DocumentTypePurchaseDTO;
import pe.com.iquitos.app.service.mapper.DocumentTypePurchaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DocumentTypePurchase.
 */
@Service
@Transactional
public class DocumentTypePurchaseServiceImpl implements DocumentTypePurchaseService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypePurchaseServiceImpl.class);

    private DocumentTypePurchaseRepository documentTypePurchaseRepository;

    private DocumentTypePurchaseMapper documentTypePurchaseMapper;

    private DocumentTypePurchaseSearchRepository documentTypePurchaseSearchRepository;

    public DocumentTypePurchaseServiceImpl(DocumentTypePurchaseRepository documentTypePurchaseRepository, DocumentTypePurchaseMapper documentTypePurchaseMapper, DocumentTypePurchaseSearchRepository documentTypePurchaseSearchRepository) {
        this.documentTypePurchaseRepository = documentTypePurchaseRepository;
        this.documentTypePurchaseMapper = documentTypePurchaseMapper;
        this.documentTypePurchaseSearchRepository = documentTypePurchaseSearchRepository;
    }

    /**
     * Save a documentTypePurchase.
     *
     * @param documentTypePurchaseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DocumentTypePurchaseDTO save(DocumentTypePurchaseDTO documentTypePurchaseDTO) {
        log.debug("Request to save DocumentTypePurchase : {}", documentTypePurchaseDTO);

        DocumentTypePurchase documentTypePurchase = documentTypePurchaseMapper.toEntity(documentTypePurchaseDTO);
        documentTypePurchase = documentTypePurchaseRepository.save(documentTypePurchase);
        DocumentTypePurchaseDTO result = documentTypePurchaseMapper.toDto(documentTypePurchase);
        documentTypePurchaseSearchRepository.save(documentTypePurchase);
        return result;
    }

    /**
     * Get all the documentTypePurchases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypePurchaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTypePurchases");
        return documentTypePurchaseRepository.findAll(pageable)
            .map(documentTypePurchaseMapper::toDto);
    }


    /**
     * Get one documentTypePurchase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentTypePurchaseDTO> findOne(Long id) {
        log.debug("Request to get DocumentTypePurchase : {}", id);
        return documentTypePurchaseRepository.findById(id)
            .map(documentTypePurchaseMapper::toDto);
    }

    /**
     * Delete the documentTypePurchase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentTypePurchase : {}", id);
        documentTypePurchaseRepository.deleteById(id);
        documentTypePurchaseSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentTypePurchase corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypePurchaseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentTypePurchases for query {}", query);
        return documentTypePurchaseSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentTypePurchaseMapper::toDto);
    }
}
