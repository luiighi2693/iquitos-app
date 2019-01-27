package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.AmortizacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Amortizacion and its DTO AmortizacionDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoDeDocumentoDeVentaMapper.class, TipoDePagoMapper.class})
public interface AmortizacionMapper extends EntityMapper<AmortizacionDTO, Amortizacion> {

    @Mapping(source = "tipoDeDocumentoDeVenta.id", target = "tipoDeDocumentoDeVentaId")
    @Mapping(source = "tipoDeDocumentoDeVenta.nombre", target = "tipoDeDocumentoDeVentaNombre")
    @Mapping(source = "tipoDePago.id", target = "tipoDePagoId")
    @Mapping(source = "tipoDePago.nombre", target = "tipoDePagoNombre")
    AmortizacionDTO toDto(Amortizacion amortizacion);

    @Mapping(source = "tipoDeDocumentoDeVentaId", target = "tipoDeDocumentoDeVenta")
    @Mapping(source = "tipoDePagoId", target = "tipoDePago")
    Amortizacion toEntity(AmortizacionDTO amortizacionDTO);

    default Amortizacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Amortizacion amortizacion = new Amortizacion();
        amortizacion.setId(id);
        return amortizacion;
    }
}
