package pe.com.iquitos.app.service.impl;

import org.elasticsearch.index.query.QueryBuilders;
import pe.com.iquitos.app.domain.Amortizacion;
import pe.com.iquitos.app.domain.ProductoDetalle;
import pe.com.iquitos.app.repository.AmortizacionRepository;
import pe.com.iquitos.app.repository.ProductoDetalleRepository;
import pe.com.iquitos.app.repository.search.AmortizacionSearchRepository;
import pe.com.iquitos.app.repository.search.ProductoDetalleSearchRepository;
import pe.com.iquitos.app.service.VentaService;
import pe.com.iquitos.app.domain.Venta;
import pe.com.iquitos.app.repository.VentaRepository;
import pe.com.iquitos.app.repository.search.VentaSearchRepository;
import pe.com.iquitos.app.service.dto.AmortizacionDTO;
import pe.com.iquitos.app.service.dto.ProductoDetalleDTO;
import pe.com.iquitos.app.service.dto.VentaDTO;
import pe.com.iquitos.app.service.dto.custom.VentaDTOCustom;
import pe.com.iquitos.app.service.mapper.AmortizacionMapper;
import pe.com.iquitos.app.service.mapper.ProductoDetalleMapper;
import pe.com.iquitos.app.service.mapper.VentaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Venta.
 */
@Service
@Transactional
public class VentaServiceImpl implements VentaService {

    private final Logger log = LoggerFactory.getLogger(VentaServiceImpl.class);

    private final VentaRepository ventaRepository;

    private final VentaMapper ventaMapper;

    private final VentaSearchRepository ventaSearchRepository;

    private final ProductoDetalleRepository productoDetalleRepository;
    private final ProductoDetalleMapper productoDetalleMapper;
    private final ProductoDetalleSearchRepository productoDetalleSearchRepository;

    private final AmortizacionRepository amortizacionRepository;
    private final AmortizacionMapper amortizacionMapper;
    private final AmortizacionSearchRepository amortizacionSearchRepository;

    public VentaServiceImpl(VentaRepository ventaRepository, VentaMapper ventaMapper, VentaSearchRepository ventaSearchRepository,
                            ProductoDetalleRepository productoDetalleRepository, ProductoDetalleMapper productoDetalleMapper, ProductoDetalleSearchRepository productoDetalleSearchRepository,
                            AmortizacionRepository amortizacionRepository, AmortizacionMapper amortizacionMapper, AmortizacionSearchRepository amortizacionSearchRepository) {
        this.ventaRepository = ventaRepository;
        this.ventaMapper = ventaMapper;
        this.ventaSearchRepository = ventaSearchRepository;
        this.productoDetalleRepository = productoDetalleRepository;
        this.productoDetalleMapper = productoDetalleMapper;
        this.productoDetalleSearchRepository = productoDetalleSearchRepository;
        this.amortizacionRepository = amortizacionRepository;
        this.amortizacionMapper = amortizacionMapper;
        this.amortizacionSearchRepository = amortizacionSearchRepository;
    }

    /**
     * Save a venta.
     *
     * @param ventaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public VentaDTO save(VentaDTO ventaDTO) {
        log.debug("Request to save Venta : {}", ventaDTO);

        if (ventaDTO.getId() != null) {
            ventaRepository
                .findById(ventaDTO.getId())
                .get()
                .getProductoDetalles()
                .stream()
                .filter(variante -> !ventaMapper
                    .toEntity(ventaDTO)
                    .getProductoDetalles()
                    .contains(variante))
                .collect(Collectors.toSet()).forEach(productoDetalleRepository::delete);

            ventaRepository
                .findById(ventaDTO.getId())
                .get()
                .getAmortizacions()
                .stream()
                .filter(amortizacion -> !ventaMapper
                    .toEntity(ventaDTO)
                    .getAmortizacions()
                    .contains(amortizacion))
                .collect(Collectors.toSet()).forEach(amortizacionRepository::delete);
        }

        Set<ProductoDetalleDTO> productoDetalleDTOList = new HashSet<>();
        Set<AmortizacionDTO> amortizacionDTOList = new HashSet<>();

        ventaDTO.getProductoDetalles().forEach(productoDetalleDTO -> {
            ProductoDetalle productoDetalle = productoDetalleMapper.toEntity(productoDetalleDTO);
            productoDetalle = productoDetalleRepository.save(productoDetalle);
            productoDetalleDTOList.add(productoDetalleMapper.toDto(productoDetalle));
            productoDetalleSearchRepository.save(productoDetalle);
        });

        ventaDTO.getAmortizacions().forEach(amortizacionDTO -> {
            Amortizacion amortizacion = amortizacionMapper.toEntity(amortizacionDTO);
            amortizacion = amortizacionRepository.save(amortizacion);
            amortizacionDTOList.add(amortizacionMapper.toDto(amortizacion));
            amortizacionSearchRepository.save(amortizacion);
        });

        ventaDTO.setProductoDetalles(productoDetalleDTOList);
        ventaDTO.setAmortizacions(amortizacionDTOList);

        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        VentaDTO result = ventaMapper.toDto(venta);
        ventaSearchRepository.save(venta);

//        Venta finalVenta = venta;
//        ventaDTO.getAmortizacions().forEach(amortizacionDTO -> {
//            Amortizacion amortizacion = amortizacionMapper.toEntity(amortizacionDTO);
//            amortizacion.setVenta(finalVenta);
//            amortizacion = amortizacionRepository.save(amortizacion);
//            amortizacionSearchRepository.save(amortizacion);
//        });

        return result;
    }

    /**
     * Get all the ventas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VentaDTOCustom> findAll(Pageable pageable) {
        log.debug("Request to get all Ventas");
        return ventaRepository.findAll(pageable)
            .map(ventaMapper::toDtoCustom);
    }

    /**
     * Get all the Venta with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<VentaDTOCustom> findAllWithEagerRelationships(Pageable pageable) {
        return ventaRepository.findAllWithEagerRelationships(pageable).map(ventaMapper::toDtoCustom);
    }
    

    /**
     * Get one venta by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VentaDTO> findOne(Long id) {
        log.debug("Request to get Venta : {}", id);
        return ventaRepository.findOneWithEagerRelationships(id)
            .map(ventaMapper::toDto);
    }

    /**
     * Delete the venta by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venta : {}", id);
        ventaRepository.deleteById(id);
        ventaSearchRepository.deleteById(id);
    }

    /**
     * Search for the venta corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VentaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Ventas for query {}", query);
        return ventaSearchRepository.search(queryStringQuery("*"+query+"*"), pageable)
            .map(ventaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> searchByDateRange(LocalDate start, LocalDate end) {
        List<Venta> result = new ArrayList<>();
        ventaSearchRepository.search(QueryBuilders.rangeQuery("fecha").gte(start).lte(end)).forEach(result::add);
        return result;
    }

    @Override
    public void reload() {
        List<Venta> list =  ventaRepository.findAll();
        list.forEach(ventaSearchRepository::save);
    }
}
