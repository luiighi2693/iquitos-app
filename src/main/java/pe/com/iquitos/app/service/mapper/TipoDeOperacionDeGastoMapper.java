package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeGastoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDeOperacionDeGasto and its DTO TipoDeOperacionDeGastoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeOperacionDeGastoMapper extends EntityMapper<TipoDeOperacionDeGastoDTO, TipoDeOperacionDeGasto> {



    default TipoDeOperacionDeGasto fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeOperacionDeGasto tipoDeOperacionDeGasto = new TipoDeOperacionDeGasto();
        tipoDeOperacionDeGasto.setId(id);
        return tipoDeOperacionDeGasto;
    }
}
