package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.BoxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Box and its DTO BoxDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BoxMapper extends EntityMapper<BoxDTO, Box> {

    @Mapping(source = "box.id", target = "boxId")
    BoxDTO toDto(Box box);

    @Mapping(source = "boxId", target = "box")
    Box toEntity(BoxDTO boxDTO);

    default Box fromId(Long id) {
        if (id == null) {
            return null;
        }
        Box box = new Box();
        box.setId(id);
        return box;
    }
}
