package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.VentaDTO;

import org.mapstruct.*;
import pe.com.iquitos.app.service.dto.custom.VentaDTOCustom;

/**
 * Mapper for the entity Venta and its DTO VentaDTO.
 */
@Mapper(componentModel = "spring", uses = {CajaMapper.class, TipoDeDocumentoDeVentaMapper.class, TipoDePagoMapper.class, ClienteMapper.class, EmpleadoMapper.class, ProductoMapper.class, ProductoDetalleMapper.class, AmortizacionMapper.class})
public interface VentaMapper extends EntityMapper<VentaDTO, Venta> {

    @Mapping(source = "caja.id", target = "cajaId")
    @Mapping(source = "tipoDeDocumentoDeVenta.id", target = "tipoDeDocumentoDeVentaId")
    @Mapping(source = "tipoDeDocumentoDeVenta.nombre", target = "tipoDeDocumentoDeVentaNombre")
    @Mapping(source = "tipoDePago.id", target = "tipoDePagoId")
    @Mapping(source = "tipoDePago.nombre", target = "tipoDePagoNombre")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    @Mapping(source = "empleado.id", target = "empleadoId")
    @Mapping(source = "empleado.nombre", target = "empleadoNombre")
    VentaDTO toDto(Venta venta);

    @Mapping(source = "caja.id", target = "cajaId")
    @Mapping(source = "tipoDeDocumentoDeVenta.id", target = "tipoDeDocumentoDeVentaId")
    @Mapping(source = "tipoDeDocumentoDeVenta.nombre", target = "tipoDeDocumentoDeVentaNombre")
    @Mapping(source = "tipoDePago.id", target = "tipoDePagoId")
    @Mapping(source = "tipoDePago.nombre", target = "tipoDePagoNombre")
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    @Mapping(source = "empleado.id", target = "empleadoId")
    @Mapping(source = "empleado.nombre", target = "empleadoNombre")
    VentaDTOCustom toDtoCustom(Venta venta);

    @Mapping(source = "cajaId", target = "caja")
    @Mapping(source = "tipoDeDocumentoDeVentaId", target = "tipoDeDocumentoDeVenta")
    @Mapping(source = "tipoDePagoId", target = "tipoDePago")
    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "empleadoId", target = "empleado")
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
