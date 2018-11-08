package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.UsuarioExternoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UsuarioExterno and its DTO UsuarioExternoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsuarioExternoMapper extends EntityMapper<UsuarioExternoDTO, UsuarioExterno> {



    default UsuarioExterno fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsuarioExterno usuarioExterno = new UsuarioExterno();
        usuarioExterno.setId(id);
        return usuarioExterno;
    }
}
