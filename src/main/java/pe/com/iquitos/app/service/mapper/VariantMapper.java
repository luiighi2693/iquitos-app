package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.VariantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Variant and its DTO VariantDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface VariantMapper extends EntityMapper<VariantDTO, Variant> {



    default Variant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Variant variant = new Variant();
        variant.setId(id);
        return variant;
    }
}
