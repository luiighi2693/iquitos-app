package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.OperationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operation and its DTO OperationDTO.
 */
@Mapper(componentModel = "spring", uses = {BoxMapper.class, PaymentTypeMapper.class, ConceptOperationInputMapper.class, ConceptOperationOutputMapper.class})
public interface OperationMapper extends EntityMapper<OperationDTO, Operation> {

    @Mapping(source = "box.id", target = "boxId")
    @Mapping(source = "paymentType.id", target = "paymentTypeId")
    @Mapping(source = "conceptOperationInput.id", target = "conceptOperationInputId")
    @Mapping(source = "conceptOperationOutput.id", target = "conceptOperationOutputId")
    OperationDTO toDto(Operation operation);

    @Mapping(source = "boxId", target = "box")
    @Mapping(source = "paymentTypeId", target = "paymentType")
    @Mapping(source = "conceptOperationInputId", target = "conceptOperationInput")
    @Mapping(source = "conceptOperationOutputId", target = "conceptOperationOutput")
    Operation toEntity(OperationDTO operationDTO);

    default Operation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operation operation = new Operation();
        operation.setId(id);
        return operation;
    }
}
