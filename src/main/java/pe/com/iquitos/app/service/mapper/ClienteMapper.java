package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cliente and its DTO ClienteDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioExternoMapper.class, TipoDeDocumentoMapper.class})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.dni", target = "usuarioDni")
    @Mapping(source = "tipoDeDocumento.id", target = "tipoDeDocumentoId")
    @Mapping(source = "tipoDeDocumento.nombre", target = "tipoDeDocumentoNombre")
    ClienteDTO toDto(Cliente cliente);

    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "tipoDeDocumentoId", target = "tipoDeDocumento")
    Cliente toEntity(ClienteDTO clienteDTO);

    default Cliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(id);
        return cliente;
    }
}
