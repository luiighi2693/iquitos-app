package pe.com.iquitos.app.service.impl;

import pe.com.iquitos.app.domain.Variante;
import pe.com.iquitos.app.repository.VarianteRepository;
import pe.com.iquitos.app.repository.search.VarianteSearchRepository;
import pe.com.iquitos.app.service.ProductoService;
import pe.com.iquitos.app.domain.Producto;
import pe.com.iquitos.app.repository.ProductoRepository;
import pe.com.iquitos.app.repository.search.ProductoSearchRepository;
import pe.com.iquitos.app.service.dto.ProductoDTO;
import pe.com.iquitos.app.service.dto.VarianteDTO;
import pe.com.iquitos.app.service.mapper.ProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.iquitos.app.service.mapper.VarianteMapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    private final ProductoSearchRepository productoSearchRepository;

    private final VarianteRepository varianteRepository;
    private final VarianteMapper varianteMapper;
    private final VarianteSearchRepository varianteSearchRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper, ProductoSearchRepository productoSearchRepository,
                               VarianteRepository varianteRepository, VarianteMapper varianteMapper, VarianteSearchRepository varianteSearchRepository) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productoSearchRepository = productoSearchRepository;

        this.varianteRepository = varianteRepository;
        this.varianteMapper = varianteMapper;
        this.varianteSearchRepository = varianteSearchRepository;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);

        if (productoDTO.getId() != null) {
            //delete contacts and accounts that was erased in frontend
            productoRepository
                .findById(productoDTO.getId())
                .get()
                .getVariantes()
                .stream()
                .filter(variante -> !productoMapper
                    .toEntity(productoDTO)
                    .getVariantes()
                    .contains(variante))
                .collect(Collectors.toSet()).forEach(varianteRepository::delete);
        }

        Set<VarianteDTO> varianteDTOList = new HashSet<>();

        productoDTO.getVariantes().forEach(varianteDTO -> {
            Variante variante = varianteMapper.toEntity(varianteDTO);
            variante = varianteRepository.save(variante);
            varianteDTOList.add(varianteMapper.toDto(variante));
            varianteSearchRepository.save(variante);
        });

        productoDTO.setVariantes(varianteDTOList);

        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        ProductoDTO result = productoMapper.toDto(producto);
        productoSearchRepository.save(producto);
        return result;
    }

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        return productoRepository.findAll(pageable)
            .map(productoMapper::toDto);
    }

    /**
     * Get all the Producto with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProductoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return productoRepository.findAllWithEagerRelationships(pageable).map(productoMapper::toDto);
    }
    

    /**
     * Get one producto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoDTO> findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        return productoRepository.findOneWithEagerRelationships(id)
            .map(productoMapper::toDto);
    }

    /**
     * Delete the producto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
        productoSearchRepository.deleteById(id);
    }

    /**
     * Search for the producto corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Productos for query {}", query);
        return productoSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(productoMapper::toDto);
    }
}
