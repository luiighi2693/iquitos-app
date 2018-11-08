package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.EstatusDeProductoEntregadoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EstatusDeProductoEntregado and its DTO EstatusDeProductoEntregadoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EstatusDeProductoEntregadoMapper extends EntityMapper<EstatusDeProductoEntregadoDTO, EstatusDeProductoEntregado> {



    default EstatusDeProductoEntregado fromId(Long id) {
        if (id == null) {
            return null;
        }
        EstatusDeProductoEntregado estatusDeProductoEntregado = new EstatusDeProductoEntregado();
        estatusDeProductoEntregado.setId(id);
        return estatusDeProductoEntregado;
    }
}
