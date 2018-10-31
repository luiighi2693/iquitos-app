package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeCompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDeDocumentoDeCompra and its DTO TipoDeDocumentoDeCompraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeDocumentoDeCompraMapper extends EntityMapper<TipoDeDocumentoDeCompraDTO, TipoDeDocumentoDeCompra> {



    default TipoDeDocumentoDeCompra fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeDocumentoDeCompra tipoDeDocumentoDeCompra = new TipoDeDocumentoDeCompra();
        tipoDeDocumentoDeCompra.setId(id);
        return tipoDeDocumentoDeCompra;
    }
}
