package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.PurchaseHasProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseHasProduct and its DTO PurchaseHasProductDTO.
 */
@Mapper(componentModel = "spring", uses = {PurchaseMapper.class})
public interface PurchaseHasProductMapper extends EntityMapper<PurchaseHasProductDTO, PurchaseHasProduct> {

    @Mapping(source = "purchase.id", target = "purchaseId")
    PurchaseHasProductDTO toDto(PurchaseHasProduct purchaseHasProduct);

    @Mapping(source = "purchaseId", target = "purchase")
    @Mapping(target = "products", ignore = true)
    PurchaseHasProduct toEntity(PurchaseHasProductDTO purchaseHasProductDTO);

    default PurchaseHasProduct fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseHasProduct purchaseHasProduct = new PurchaseHasProduct();
        purchaseHasProduct.setId(id);
        return purchaseHasProduct;
    }
}
