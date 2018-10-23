package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.UnitMeasurementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UnitMeasurement and its DTO UnitMeasurementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnitMeasurementMapper extends EntityMapper<UnitMeasurementDTO, UnitMeasurement> {



    default UnitMeasurement fromId(Long id) {
        if (id == null) {
            return null;
        }
        UnitMeasurement unitMeasurement = new UnitMeasurement();
        unitMeasurement.setId(id);
        return unitMeasurement;
    }
}
