package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProviderPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProviderPayment and its DTO ProviderPaymentDTO.
 */
@Mapper(componentModel = "spring", uses = {DocumentTypePurchaseMapper.class, PaymentTypeMapper.class})
public interface ProviderPaymentMapper extends EntityMapper<ProviderPaymentDTO, ProviderPayment> {

    @Mapping(source = "documentTypePurchase.id", target = "documentTypePurchaseId")
    @Mapping(source = "paymentType.id", target = "paymentTypeId")
    ProviderPaymentDTO toDto(ProviderPayment providerPayment);

    @Mapping(source = "documentTypePurchaseId", target = "documentTypePurchase")
    @Mapping(source = "paymentTypeId", target = "paymentType")
    ProviderPayment toEntity(ProviderPaymentDTO providerPaymentDTO);

    default ProviderPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProviderPayment providerPayment = new ProviderPayment();
        providerPayment.setId(id);
        return providerPayment;
    }
}
