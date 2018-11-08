package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.UsuarioExternoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UsuarioExterno and its DTO UsuarioExternoDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UsuarioExternoMapper extends EntityMapper<UsuarioExternoDTO, UsuarioExterno> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.login", target = "usuarioLogin")
    UsuarioExternoDTO toDto(UsuarioExterno usuarioExterno);

    @Mapping(source = "usuarioId", target = "usuario")
    UsuarioExterno toEntity(UsuarioExternoDTO usuarioExternoDTO);

    default UsuarioExterno fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsuarioExterno usuarioExterno = new UsuarioExterno();
        usuarioExterno.setId(id);
        return usuarioExterno;
    }
}
