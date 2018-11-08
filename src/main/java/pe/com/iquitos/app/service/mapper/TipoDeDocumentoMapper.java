package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDeDocumento and its DTO TipoDeDocumentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeDocumentoMapper extends EntityMapper<TipoDeDocumentoDTO, TipoDeDocumento> {



    default TipoDeDocumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeDocumento tipoDeDocumento = new TipoDeDocumento();
        tipoDeDocumento.setId(id);
        return tipoDeDocumento;
    }
}
