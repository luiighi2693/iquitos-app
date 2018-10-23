package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProductsDeliveredStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductsDeliveredStatus and its DTO ProductsDeliveredStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductsDeliveredStatusMapper extends EntityMapper<ProductsDeliveredStatusDTO, ProductsDeliveredStatus> {



    default ProductsDeliveredStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductsDeliveredStatus productsDeliveredStatus = new ProductsDeliveredStatus();
        productsDeliveredStatus.setId(id);
        return productsDeliveredStatus;
    }
}
