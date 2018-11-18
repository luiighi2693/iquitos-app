package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.CajaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Caja and its DTO CajaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CajaMapper extends EntityMapper<CajaDTO, Caja> {

    @Mapping(source = "caja.id", target = "cajaId")
    CajaDTO toDto(Caja caja);

    @Mapping(source = "cajaId", target = "caja")
    Caja toEntity(CajaDTO cajaDTO);

    default Caja fromId(Long id) {
        if (id == null) {
            return null;
        }
        Caja caja = new Caja();
        caja.setId(id);
        return caja;
    }
}
