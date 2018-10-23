package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.VariantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Variant and its DTO VariantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VariantMapper extends EntityMapper<VariantDTO, Variant> {


    @Mapping(target = "products", ignore = true)
    Variant toEntity(VariantDTO variantDTO);

    default Variant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Variant variant = new Variant();
        variant.setId(id);
        return variant;
    }
}
