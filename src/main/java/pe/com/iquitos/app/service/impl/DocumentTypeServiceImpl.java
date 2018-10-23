package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.DocumentTypeService;
import pe.com.iquitos.app.domain.DocumentType;
import pe.com.iquitos.app.repository.DocumentTypeRepository;
import pe.com.iquitos.app.repository.search.DocumentTypeSearchRepository;
import pe.com.iquitos.app.service.dto.DocumentTypeDTO;
import pe.com.iquitos.app.service.mapper.DocumentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DocumentType.
 */
@Service
@Transactional
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final Logger log = LoggerFactory.getLogger(DocumentTypeServiceImpl.class);

    private DocumentTypeRepository documentTypeRepository;

    private DocumentTypeMapper documentTypeMapper;

    private DocumentTypeSearchRepository documentTypeSearchRepository;

    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository, DocumentTypeMapper documentTypeMapper, DocumentTypeSearchRepository documentTypeSearchRepository) {
        this.documentTypeRepository = documentTypeRepository;
        this.documentTypeMapper = documentTypeMapper;
        this.documentTypeSearchRepository = documentTypeSearchRepository;
    }

    /**
     * Save a documentType.
     *
     * @param documentTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DocumentTypeDTO save(DocumentTypeDTO documentTypeDTO) {
        log.debug("Request to save DocumentType : {}", documentTypeDTO);

        DocumentType documentType = documentTypeMapper.toEntity(documentTypeDTO);
        documentType = documentTypeRepository.save(documentType);
        DocumentTypeDTO result = documentTypeMapper.toDto(documentType);
        documentTypeSearchRepository.save(documentType);
        return result;
    }

    /**
     * Get all the documentTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTypes");
        return documentTypeRepository.findAll(pageable)
            .map(documentTypeMapper::toDto);
    }


    /**
     * Get one documentType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DocumentTypeDTO> findOne(Long id) {
        log.debug("Request to get DocumentType : {}", id);
        return documentTypeRepository.findById(id)
            .map(documentTypeMapper::toDto);
    }

    /**
     * Delete the documentType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentType : {}", id);
        documentTypeRepository.deleteById(id);
        documentTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentTypes for query {}", query);
        return documentTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentTypeMapper::toDto);
    }
}
