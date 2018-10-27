package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.UserLoginDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserLogin and its DTO UserLoginDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserLoginMapper extends EntityMapper<UserLoginDTO, UserLogin> {



    default UserLogin fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setId(id);
        return userLogin;
    }
}
