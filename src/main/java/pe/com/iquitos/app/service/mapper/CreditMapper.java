package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.CreditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Credit and its DTO CreditDTO.
 */
@Mapper(componentModel = "spring", uses = {SellMapper.class, PurchaseMapper.class})
public interface CreditMapper extends EntityMapper<CreditDTO, Credit> {

    @Mapping(source = "sell.id", target = "sellId")
    @Mapping(source = "purchase.id", target = "purchaseId")
    CreditDTO toDto(Credit credit);

    @Mapping(source = "sellId", target = "sell")
    @Mapping(source = "purchaseId", target = "purchase")
    Credit toEntity(CreditDTO creditDTO);

    default Credit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Credit credit = new Credit();
        credit.setId(id);
        return credit;
    }
}
