package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.EmpleadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Empleado and its DTO EmpleadoDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioExternoMapper.class})
public interface EmpleadoMapper extends EntityMapper<EmpleadoDTO, Empleado> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.dni", target = "usuarioDni")
    EmpleadoDTO toDto(Empleado empleado);

    @Mapping(source = "usuarioId", target = "usuario")
    Empleado toEntity(EmpleadoDTO empleadoDTO);

    default Empleado fromId(Long id) {
        if (id == null) {
            return null;
        }
        Empleado empleado = new Empleado();
        empleado.setId(id);
        return empleado;
    }
}
