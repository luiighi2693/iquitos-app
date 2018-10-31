package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.EstatusDeCompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EstatusDeCompra and its DTO EstatusDeCompraDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusDeCompraMapper extends EntityMapper<EstatusDeCompraDTO, EstatusDeCompra> {



    default EstatusDeCompra fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusDeCompra estatusDeCompra = new EstatusDeCompra();
        estatusDeCompra.setId(id);
        return estatusDeCompra;
    }
}
