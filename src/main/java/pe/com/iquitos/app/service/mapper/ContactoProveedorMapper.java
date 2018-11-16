package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.ContactoProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ContactoProveedor and its DTO ContactoProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactoProveedorMapper extends EntityMapper<ContactoProveedorDTO, ContactoProveedor> {



    default ContactoProveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        ContactoProveedor contactoProveedor = new ContactoProveedor();
        contactoProveedor.setId(id);
        return contactoProveedor;
    }
}
