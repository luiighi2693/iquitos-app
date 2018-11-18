package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.PagoDeProveedorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PagoDeProveedor and its DTO PagoDeProveedorDTO.
 */
@Mapper(componentModel = "spring", uses = {TipoDeDocumentoDeCompraMapper.class, TipoDePagoMapper.class})
public interface PagoDeProveedorMapper extends EntityMapper<PagoDeProveedorDTO, PagoDeProveedor> {

    @Mapping(source = "tipoDeDocumentoDeCompra.id", target = "tipoDeDocumentoDeCompraId")
    @Mapping(source = "tipoDeDocumentoDeCompra.nombre", target = "tipoDeDocumentoDeCompraNombre")
    @Mapping(source = "tipoDePago.id", target = "tipoDePagoId")
    @Mapping(source = "tipoDePago.nombre", target = "tipoDePagoNombre")
    PagoDeProveedorDTO toDto(PagoDeProveedor pagoDeProveedor);

    @Mapping(source = "tipoDeDocumentoDeCompraId", target = "tipoDeDocumentoDeCompra")
    @Mapping(source = "tipoDePagoId", target = "tipoDePago")
    PagoDeProveedor toEntity(PagoDeProveedorDTO pagoDeProveedorDTO);

    default PagoDeProveedor fromId(Long id) {
        if (id == null) {
            return null;
        }
        PagoDeProveedor pagoDeProveedor = new PagoDeProveedor();
        pagoDeProveedor.setId(id);
        return pagoDeProveedor;
    }
}
