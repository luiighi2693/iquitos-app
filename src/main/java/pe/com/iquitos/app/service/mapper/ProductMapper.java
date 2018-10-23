package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {UnitMeasurementMapper.class, CategoryMapper.class, VariantMapper.class, SellHasProductMapper.class, PurchaseHasProductMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "unitMeasurement.id", target = "unitMeasurementId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "vartiant.id", target = "vartiantId")
    @Mapping(source = "variant.id", target = "variantId")
    @Mapping(source = "sellHasProduct.id", target = "sellHasProductId")
    @Mapping(source = "purchaseHasProduct.id", target = "purchaseHasProductId")
    ProductDTO toDto(Product product);

    @Mapping(source = "unitMeasurementId", target = "unitMeasurement")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "vartiantId", target = "vartiant")
    @Mapping(source = "variantId", target = "variant")
    @Mapping(source = "sellHasProductId", target = "sellHasProduct")
    @Mapping(source = "purchaseHasProductId", target = "purchaseHasProduct")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
