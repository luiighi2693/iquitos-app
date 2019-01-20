package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProductoDetalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductoDetalle and its DTO ProductoDetalleDTO.
 */
@Mapper(componentModel = "spring", uses = {VarianteMapper.class, ProductoMapper.class})
public interface ProductoDetalleMapper extends EntityMapper<ProductoDetalleDTO, ProductoDetalle> {



    default ProductoDetalle fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductoDetalle productoDetalle = new ProductoDetalle();
        productoDetalle.setId(id);
        return productoDetalle;
    }
}
