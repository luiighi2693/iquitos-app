package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.AmortizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amortization and its DTO AmortizationDTO.
 */
@Mapper(componentModel = "spring", uses = {DocumentTypeSellMapper.class, PaymentTypeMapper.class, SellMapper.class})
public interface AmortizationMapper extends EntityMapper<AmortizationDTO, Amortization> {

    @Mapping(source = "documentTypeSell.id", target = "documentTypeSellId")
    @Mapping(source = "paymentType.id", target = "paymentTypeId")
    @Mapping(source = "sell.id", target = "sellId")
    AmortizationDTO toDto(Amortization amortization);

    @Mapping(source = "documentTypeSellId", target = "documentTypeSell")
    @Mapping(source = "paymentTypeId", target = "paymentType")
    @Mapping(source = "sellId", target = "sell")
    Amortization toEntity(AmortizationDTO amortizationDTO);

    default Amortization fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amortization amortization = new Amortization();
        amortization.setId(id);
        return amortization;
    }
}
