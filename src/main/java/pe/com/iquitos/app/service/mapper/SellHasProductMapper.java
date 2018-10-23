package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.SellHasProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SellHasProduct and its DTO SellHasProductDTO.
 */
@Mapper(componentModel = "spring", uses = {SellMapper.class, VariantMapper.class})
public interface SellHasProductMapper extends EntityMapper<SellHasProductDTO, SellHasProduct> {

    @Mapping(source = "sell.id", target = "sellId")
    @Mapping(source = "variant.id", target = "variantId")
    SellHasProductDTO toDto(SellHasProduct sellHasProduct);

    @Mapping(source = "sellId", target = "sell")
    @Mapping(source = "variantId", target = "variant")
    @Mapping(target = "products", ignore = true)
    SellHasProduct toEntity(SellHasProductDTO sellHasProductDTO);

    default SellHasProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        SellHasProduct sellHasProduct = new SellHasProduct();
        sellHasProduct.setId(id);
        return sellHasProduct;
    }
}
