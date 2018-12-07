package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Proveedor and its DTO ProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioExternoMapper.class, CuentaProveedorMapper.class, ContactoProveedorMapper.class})
public interface ProveedorMapper extends EntityMapper<ProveedorDTO, Proveedor> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.dni", target = "usuarioDni")
    ProveedorDTO toDto(Proveedor proveedor);

    @Mapping(source = "usuarioId", target = "usuario")
    Proveedor toEntity(ProveedorDTO proveedorDTO);

    default Proveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Proveedor proveedor = new Proveedor();
        proveedor.setId(id);
        return proveedor;
    }
}
