package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ConceptOperationInputDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConceptOperationInput and its DTO ConceptOperationInputDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConceptOperationInputMapper extends EntityMapper<ConceptOperationInputDTO, ConceptOperationInput> {



    default ConceptOperationInput fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConceptOperationInput conceptOperationInput = new ConceptOperationInput();
        conceptOperationInput.setId(id);
        return conceptOperationInput;
    }
}
