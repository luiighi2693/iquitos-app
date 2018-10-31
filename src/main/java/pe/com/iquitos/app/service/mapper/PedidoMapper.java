package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.PedidoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pedido and its DTO PedidoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, ProductoMapper.class})
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "proveedor.razonSocial", target = "proveedorRazonSocial")
    PedidoDTO toDto(Pedido pedido);

    @Mapping(source = "proveedorId", target = "proveedor")
    Pedido toEntity(PedidoDTO pedidoDTO);

    default Pedido fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pedido pedido = new Pedido();
        pedido.setId(id);
        return pedido;
    }
}
