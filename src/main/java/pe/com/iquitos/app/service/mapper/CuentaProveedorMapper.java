package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.CuentaProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CuentaProveedor and its DTO CuentaProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CuentaProveedorMapper extends EntityMapper<CuentaProveedorDTO, CuentaProveedor> {



    default CuentaProveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        CuentaProveedor cuentaProveedor = new CuentaProveedor();
        cuentaProveedor.setId(id);
        return cuentaProveedor;
    }
}
