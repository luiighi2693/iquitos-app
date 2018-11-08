package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.VarianteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Variante and its DTO VarianteDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface VarianteMapper extends EntityMapper<VarianteDTO, Variante> {



    default Variante fromId(Long id) {
        if (id == null) {
            return null;
        }
        Variante variante = new Variante();
        variante.setId(id);
        return variante;
    }
}
