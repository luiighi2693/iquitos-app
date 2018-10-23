package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ProviderPaymentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProviderPayment.
 */
public interface ProviderPaymentService {

    /**
     * Save a providerPayment.
     *
     * @param providerPaymentDTO the entity to save
     * @return the persisted entity
     */
    ProviderPaymentDTO save(ProviderPaymentDTO providerPaymentDTO);

    /**
     * Get all the providerPayments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProviderPaymentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" providerPayment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProviderPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" providerPayment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the providerPayment corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProviderPaymentDTO> search(String query, Pageable pageable);
}
