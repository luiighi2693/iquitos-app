package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.ProviderPaymentService;
import pe.com.iquitos.app.domain.ProviderPayment;
import pe.com.iquitos.app.repository.ProviderPaymentRepository;
import pe.com.iquitos.app.repository.search.ProviderPaymentSearchRepository;
import pe.com.iquitos.app.service.dto.ProviderPaymentDTO;
import pe.com.iquitos.app.service.mapper.ProviderPaymentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProviderPayment.
 */
@Service
@Transactional
public class ProviderPaymentServiceImpl implements ProviderPaymentService {

    private final Logger log = LoggerFactory.getLogger(ProviderPaymentServiceImpl.class);

    private ProviderPaymentRepository providerPaymentRepository;

    private ProviderPaymentMapper providerPaymentMapper;

    private ProviderPaymentSearchRepository providerPaymentSearchRepository;

    public ProviderPaymentServiceImpl(ProviderPaymentRepository providerPaymentRepository, ProviderPaymentMapper providerPaymentMapper, ProviderPaymentSearchRepository providerPaymentSearchRepository) {
        this.providerPaymentRepository = providerPaymentRepository;
        this.providerPaymentMapper = providerPaymentMapper;
        this.providerPaymentSearchRepository = providerPaymentSearchRepository;
    }

    /**
     * Save a providerPayment.
     *
     * @param providerPaymentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProviderPaymentDTO save(ProviderPaymentDTO providerPaymentDTO) {
        log.debug("Request to save ProviderPayment : {}", providerPaymentDTO);

        ProviderPayment providerPayment = providerPaymentMapper.toEntity(providerPaymentDTO);
        providerPayment = providerPaymentRepository.save(providerPayment);
        ProviderPaymentDTO result = providerPaymentMapper.toDto(providerPayment);
        providerPaymentSearchRepository.save(providerPayment);
        return result;
    }

    /**
     * Get all the providerPayments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProviderPaymentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProviderPayments");
        return providerPaymentRepository.findAll(pageable)
            .map(providerPaymentMapper::toDto);
    }


    /**
     * Get one providerPayment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProviderPaymentDTO> findOne(Long id) {
        log.debug("Request to get ProviderPayment : {}", id);
        return providerPaymentRepository.findById(id)
            .map(providerPaymentMapper::toDto);
    }

    /**
     * Delete the providerPayment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProviderPayment : {}", id);
        providerPaymentRepository.deleteById(id);
        providerPaymentSearchRepository.deleteById(id);
    }

    /**
     * Search for the providerPayment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProviderPaymentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProviderPayments for query {}", query);
        return providerPaymentSearchRepository.search(queryStringQuery(query), pageable)
            .map(providerPaymentMapper::toDto);
    }
}
