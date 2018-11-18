package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.OperacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Operacion and its DTO OperacionDTO.
 */
@Mapper(componentModel = "spring", uses = {CajaMapper.class, TipoDePagoMapper.class, TipoDeOperacionDeIngresoMapper.class, TipoDeOperacionDeGastoMapper.class})
public interface OperacionMapper extends EntityMapper<OperacionDTO, Operacion> {

    @Mapping(source = "caja.id", target = "cajaId")
    @Mapping(source = "tipoDePago.id", target = "tipoDePagoId")
    @Mapping(source = "tipoDePago.nombre", target = "tipoDePagoNombre")
    @Mapping(source = "tipoDeOperacionDeIngreso.id", target = "tipoDeOperacionDeIngresoId")
    @Mapping(source = "tipoDeOperacionDeIngreso.nombre", target = "tipoDeOperacionDeIngresoNombre")
    @Mapping(source = "tipoDeOperacionDeGasto.id", target = "tipoDeOperacionDeGastoId")
    @Mapping(source = "tipoDeOperacionDeGasto.nombre", target = "tipoDeOperacionDeGastoNombre")
    OperacionDTO toDto(Operacion operacion);

    @Mapping(source = "cajaId", target = "caja")
    @Mapping(source = "tipoDePagoId", target = "tipoDePago")
    @Mapping(source = "tipoDeOperacionDeIngresoId", target = "tipoDeOperacionDeIngreso")
    @Mapping(source = "tipoDeOperacionDeGastoId", target = "tipoDeOperacionDeGasto")
    Operacion toEntity(OperacionDTO operacionDTO);

    default Operacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Operacion operacion = new Operacion();
        operacion.setId(id);
        return operacion;
    }
}
