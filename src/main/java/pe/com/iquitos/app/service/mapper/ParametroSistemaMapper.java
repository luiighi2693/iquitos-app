package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ParametroSistemaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ParametroSistema and its DTO ParametroSistemaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ParametroSistemaMapper extends EntityMapper<ParametroSistemaDTO, ParametroSistema> {



    default ParametroSistema fromId(Long id) {
        if (id == null) {
            return null;
        }
        ParametroSistema parametroSistema = new ParametroSistema();
        parametroSistema.setId(id);
        return parametroSistema;
    }
}
