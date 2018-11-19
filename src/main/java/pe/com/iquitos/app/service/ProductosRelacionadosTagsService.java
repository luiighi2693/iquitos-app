package pe.com.iquitos.app.service;

import pe.com.iquitos.app.service.dto.ProductosRelacionadosTagsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProductosRelacionadosTags.
 */
public interface ProductosRelacionadosTagsService {

    /**
     * Save a productosRelacionadosTags.
     *
     * @param productosRelacionadosTagsDTO the entity to save
     * @return the persisted entity
     */
    ProductosRelacionadosTagsDTO save(ProductosRelacionadosTagsDTO productosRelacionadosTagsDTO);

    /**
     * Get all the productosRelacionadosTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductosRelacionadosTagsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productosRelacionadosTags.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductosRelacionadosTagsDTO> findOne(Long id);

    /**
     * Delete the "id" productosRelacionadosTags.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productosRelacionadosTags corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductosRelacionadosTagsDTO> search(String query, Pageable pageable);
}
