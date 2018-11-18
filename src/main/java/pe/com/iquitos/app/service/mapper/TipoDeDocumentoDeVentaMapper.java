package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.TipoDeDocumentoDeVentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDeDocumentoDeVenta and its DTO TipoDeDocumentoDeVentaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeDocumentoDeVentaMapper extends EntityMapper<TipoDeDocumentoDeVentaDTO, TipoDeDocumentoDeVenta> {



    default TipoDeDocumentoDeVenta fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeDocumentoDeVenta tipoDeDocumentoDeVenta = new TipoDeDocumentoDeVenta();
        tipoDeDocumentoDeVenta.setId(id);
        return tipoDeDocumentoDeVenta;
    }
}
