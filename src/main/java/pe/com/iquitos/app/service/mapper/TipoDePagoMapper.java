package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.TipoDePagoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TipoDePago and its DTO TipoDePagoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoDePagoMapper extends EntityMapper<TipoDePagoDTO, TipoDePago> {



    default TipoDePago fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoDePago tipoDePago = new TipoDePago();
        tipoDePago.setId(id);
        return tipoDePago;
    }
}
