package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.CuentaProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CuentaProveedor and its DTO CuentaProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class})
public interface CuentaProveedorMapper extends EntityMapper<CuentaProveedorDTO, CuentaProveedor> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    CuentaProveedorDTO toDto(CuentaProveedor cuentaProveedor);

    @Mapping(source = "proveedorId", target = "proveedor")
    CuentaProveedor toEntity(CuentaProveedorDTO cuentaProveedorDTO);

    default CuentaProveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        CuentaProveedor cuentaProveedor = new CuentaProveedor();
        cuentaProveedor.setId(id);
        return cuentaProveedor;
    }
}
