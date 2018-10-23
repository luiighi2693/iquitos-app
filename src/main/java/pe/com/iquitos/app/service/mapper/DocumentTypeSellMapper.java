package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.DocumentTypeSellDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DocumentTypeSell and its DTO DocumentTypeSellDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentTypeSellMapper extends EntityMapper<DocumentTypeSellDTO, DocumentTypeSell> {



    default DocumentTypeSell fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentTypeSell documentTypeSell = new DocumentTypeSell();
        documentTypeSell.setId(id);
        return documentTypeSell;
    }
}
