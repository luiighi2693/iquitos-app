package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.DocumentTypePurchaseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DocumentTypePurchase and its DTO DocumentTypePurchaseDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentTypePurchaseMapper extends EntityMapper<DocumentTypePurchaseDTO, DocumentTypePurchase> {



    default DocumentTypePurchase fromId(Long id) {
        if (id == null) {
            return null;
        }
        DocumentTypePurchase documentTypePurchase = new DocumentTypePurchase();
        documentTypePurchase.setId(id);
        return documentTypePurchase;
    }
}
