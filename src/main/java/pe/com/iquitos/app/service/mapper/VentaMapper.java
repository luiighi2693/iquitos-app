package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.VentaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Venta and its DTO VentaDTO.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, EmpleadoMapper.class, CajaMapper.class, TipoDeDocumentoDeVentaMapper.class, TipoDePagoMapper.class, EstatusDeProductoEntregadoMapper.class, ProductoMapper.class})
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    @Mapping(source = "empleado.id", target = "empleadoId")
    @Mapping(source = "empleado.nombre", target = "empleadoNombre")
    @Mapping(source = "caja.id", target = "cajaId")
    @Mapping(source = "tipoDeDocumentoDeVenta.id", target = "tipoDeDocumentoDeVentaId")
    @Mapping(source = "tipoDeDocumentoDeVenta.nombre", target = "tipoDeDocumentoDeVentaNombre")
    @Mapping(source = "tipoDePago.id", target = "tipoDePagoId")
    @Mapping(source = "tipoDePago.nombre", target = "tipoDePagoNombre")
    @Mapping(source = "estatusDeProductoEntregado.id", target = "estatusDeProductoEntregadoId")
    @Mapping(source = "estatusDeProductoEntregado.nombre", target = "estatusDeProductoEntregadoNombre")
    VentaDTO toDto(Venta venta);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "empleadoId", target = "empleado")
    @Mapping(source = "cajaId", target = "caja")
    @Mapping(target = "amortizacions", ignore = true)
    @Mapping(source = "tipoDeDocumentoDeVentaId", target = "tipoDeDocumentoDeVenta")
    @Mapping(source = "tipoDePagoId", target = "tipoDePago")
    @Mapping(source = "estatusDeProductoEntregadoId", target = "estatusDeProductoEntregado")
    Venta toEntity(VentaDTO ventaDTO);

    default Venta fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venta venta = new Venta();
        venta.setId(id);
        return venta;
    }
}
