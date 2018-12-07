package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Producto and its DTO ProductoDTO.
 */
@Mapper(componentModel = "spring", uses = {UnidadDeMedidaMapper.class, CategoriaMapper.class, VarianteMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    @Mapping(source = "unidadDeMedida.id", target = "unidadDeMedidaId")
    @Mapping(source = "unidadDeMedida.nombre", target = "unidadDeMedidaNombre")
    @Mapping(source = "categoria.id", target = "categoriaId")
    @Mapping(source = "categoria.nombre", target = "categoriaNombre")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "unidadDeMedidaId", target = "unidadDeMedida")
    @Mapping(source = "categoriaId", target = "categoria")
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
