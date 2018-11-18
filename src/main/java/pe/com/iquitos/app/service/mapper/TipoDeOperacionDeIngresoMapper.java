package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.TipoDeOperacionDeIngresoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDeOperacionDeIngreso and its DTO TipoDeOperacionDeIngresoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDeOperacionDeIngresoMapper extends EntityMapper<TipoDeOperacionDeIngresoDTO, TipoDeOperacionDeIngreso> {



    default TipoDeOperacionDeIngreso fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDeOperacionDeIngreso tipoDeOperacionDeIngreso = new TipoDeOperacionDeIngreso();
        tipoDeOperacionDeIngreso.setId(id);
        return tipoDeOperacionDeIngreso;
    }
}
