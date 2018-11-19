package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProductosRelacionadosTagsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductosRelacionadosTags and its DTO ProductosRelacionadosTagsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductosRelacionadosTagsMapper extends EntityMapper<ProductosRelacionadosTagsDTO, ProductosRelacionadosTags> {



    default ProductosRelacionadosTags fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductosRelacionadosTags productosRelacionadosTags = new ProductosRelacionadosTags();
        productosRelacionadosTags.setId(id);
        return productosRelacionadosTags;
    }
}
