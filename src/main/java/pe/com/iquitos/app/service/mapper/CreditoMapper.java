package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.CreditoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Credito and its DTO CreditoDTO.
 */
@Mapper(componentModel = "spring", uses = {VentaMapper.class, CompraMapper.class})
public interface CreditoMapper extends EntityMapper<CreditoDTO, Credito> {

    @Mapping(source = "venta.id", target = "ventaId")
    @Mapping(source = "compra.id", target = "compraId")
    CreditoDTO toDto(Credito credito);

    @Mapping(source = "ventaId", target = "venta")
    @Mapping(source = "compraId", target = "compra")
    Credito toEntity(CreditoDTO creditoDTO);

    default Credito fromId(Long id) {
        if (id == null) {
            return null;
        }
        Credito credito = new Credito();
        credito.setId(id);
        return credito;
    }
}
