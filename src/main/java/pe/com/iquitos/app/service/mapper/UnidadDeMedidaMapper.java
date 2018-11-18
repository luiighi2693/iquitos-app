package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.UnidadDeMedidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UnidadDeMedida and its DTO UnidadDeMedidaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnidadDeMedidaMapper extends EntityMapper<UnidadDeMedidaDTO, UnidadDeMedida> {



    default UnidadDeMedida fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnidadDeMedida unidadDeMedida = new UnidadDeMedida();
        unidadDeMedida.setId(id);
        return unidadDeMedida;
    }
}
