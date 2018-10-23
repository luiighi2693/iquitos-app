package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.service.PaymentTypeService;
import pe.com.iquitos.app.domain.PaymentType;
import pe.com.iquitos.app.repository.PaymentTypeRepository;
import pe.com.iquitos.app.repository.search.PaymentTypeSearchRepository;
import pe.com.iquitos.app.service.dto.PaymentTypeDTO;
import pe.com.iquitos.app.service.mapper.PaymentTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PaymentType.
 */
@Service
@Transactional
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeServiceImpl.class);

    private PaymentTypeRepository paymentTypeRepository;

    private PaymentTypeMapper paymentTypeMapper;

    private PaymentTypeSearchRepository paymentTypeSearchRepository;

    public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepository, PaymentTypeMapper paymentTypeMapper, PaymentTypeSearchRepository paymentTypeSearchRepository) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeMapper = paymentTypeMapper;
        this.paymentTypeSearchRepository = paymentTypeSearchRepository;
    }

    /**
     * Save a paymentType.
     *
     * @param paymentTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PaymentTypeDTO save(PaymentTypeDTO paymentTypeDTO) {
        log.debug("Request to save PaymentType : {}", paymentTypeDTO);

        PaymentType paymentType = paymentTypeMapper.toEntity(paymentTypeDTO);
        paymentType = paymentTypeRepository.save(paymentType);
        PaymentTypeDTO result = paymentTypeMapper.toDto(paymentType);
        paymentTypeSearchRepository.save(paymentType);
        return result;
    }

    /**
     * Get all the paymentTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentTypes");
        return paymentTypeRepository.findAll(pageable)
            .map(paymentTypeMapper::toDto);
    }


    /**
     * Get one paymentType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentTypeDTO> findOne(Long id) {
        log.debug("Request to get PaymentType : {}", id);
        return paymentTypeRepository.findById(id)
            .map(paymentTypeMapper::toDto);
    }

    /**
     * Delete the paymentType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentType : {}", id);
        paymentTypeRepository.deleteById(id);
        paymentTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the paymentType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PaymentTypes for query {}", query);
        return paymentTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(paymentTypeMapper::toDto);
    }
}
