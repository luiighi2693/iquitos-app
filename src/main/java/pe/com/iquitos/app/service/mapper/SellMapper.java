package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.SellDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sell and its DTO SellDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, EmployeeMapper.class, BoxMapper.class, DocumentTypeSellMapper.class, PaymentTypeMapper.class, ProductsDeliveredStatusMapper.class})
public interface SellMapper extends EntityMapper<SellDTO, Sell> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "box.id", target = "boxId")
    @Mapping(source = "documentTypeSell.id", target = "documentTypeSellId")
    @Mapping(source = "paymentType.id", target = "paymentTypeId")
    @Mapping(source = "productsDeliveredStatus.id", target = "productsDeliveredStatusId")
    SellDTO toDto(Sell sell);

    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "boxId", target = "box")
    @Mapping(target = "amortizations", ignore = true)
    @Mapping(source = "documentTypeSellId", target = "documentTypeSell")
    @Mapping(source = "paymentTypeId", target = "paymentType")
    @Mapping(source = "productsDeliveredStatusId", target = "productsDeliveredStatus")
    Sell toEntity(SellDTO sellDTO);

    default Sell fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sell sell = new Sell();
        sell.setId(id);
        return sell;
    }
}
