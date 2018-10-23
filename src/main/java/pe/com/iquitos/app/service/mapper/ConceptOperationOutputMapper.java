package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ConceptOperationOutputDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConceptOperationOutput and its DTO ConceptOperationOutputDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConceptOperationOutputMapper extends EntityMapper<ConceptOperationOutputDTO, ConceptOperationOutput> {



    default ConceptOperationOutput fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConceptOperationOutput conceptOperationOutput = new ConceptOperationOutput();
        conceptOperationOutput.setId(id);
        return conceptOperationOutput;
    }
}
