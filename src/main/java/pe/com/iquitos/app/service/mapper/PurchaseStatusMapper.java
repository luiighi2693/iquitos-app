package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.PurchaseStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseStatus and its DTO PurchaseStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseStatusMapper extends EntityMapper<PurchaseStatusDTO, PurchaseStatus> {



    default PurchaseStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseStatus purchaseStatus = new PurchaseStatus();
        purchaseStatus.setId(id);
        return purchaseStatus;
    }
}
