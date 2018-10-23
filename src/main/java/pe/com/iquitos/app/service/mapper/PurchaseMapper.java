package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.PurchaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Purchase and its DTO PurchaseDTO.
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class, DocumentTypePurchaseMapper.class, PurchaseStatusMapper.class, BoxMapper.class})
public interface PurchaseMapper extends EntityMapper<PurchaseDTO, Purchase> {

    @Mapping(source = "provider.id", target = "providerId")
    @Mapping(source = "documentTypePurchase.id", target = "documentTypePurchaseId")
    @Mapping(source = "purchaseStatus.id", target = "purchaseStatusId")
    @Mapping(source = "box.id", target = "boxId")
    PurchaseDTO toDto(Purchase purchase);

    @Mapping(source = "providerId", target = "provider")
    @Mapping(source = "documentTypePurchaseId", target = "documentTypePurchase")
    @Mapping(source = "purchaseStatusId", target = "purchaseStatus")
    @Mapping(source = "boxId", target = "box")
    Purchase toEntity(PurchaseDTO purchaseDTO);

    default Purchase fromId(Long id) {
        if (id == null) {
            return null;
        }
        Purchase purchase = new Purchase();
        purchase.setId(id);
        return purchase;
    }
}
