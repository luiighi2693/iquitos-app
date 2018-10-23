package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ProviderAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProviderAccount and its DTO ProviderAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {ProviderMapper.class})
public interface ProviderAccountMapper extends EntityMapper<ProviderAccountDTO, ProviderAccount> {

    @Mapping(source = "provider.id", target = "providerId")
    ProviderAccountDTO toDto(ProviderAccount providerAccount);

    @Mapping(source = "providerId", target = "provider")
    ProviderAccount toEntity(ProviderAccountDTO providerAccountDTO);

    default ProviderAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProviderAccount providerAccount = new ProviderAccount();
        providerAccount.setId(id);
        return providerAccount;
    }
}
