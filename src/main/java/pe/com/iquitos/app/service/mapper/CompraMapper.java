package pe.com.iquitos.app.service.mapper;

import pe.com.iquitos.app.domain.*;
import pe.com.iquitos.app.service.dto.CompraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Compra and its DTO CompraDTO.
 */
@Mapper(componentModel = "spring", uses = {ProveedorMapper.class, TipoDeDocumentoDeCompraMapper.class, EstatusDeCompraMapper.class, CajaMapper.class, ProductoMapper.class})
public interface CompraMapper extends EntityMapper<CompraDTO, Compra> {

    @Mapping(source = "proveedor.id", target = "proveedorId")
    @Mapping(source = "tipoDeDocumentoDeCompra.id", target = "tipoDeDocumentoDeCompraId")
    @Mapping(source = "tipoDeDocumentoDeCompra.nombre", target = "tipoDeDocumentoDeCompraNombre")
    @Mapping(source = "estatusDeCompra.id", target = "estatusDeCompraId")
    @Mapping(source = "estatusDeCompra.nombre", target = "estatusDeCompraNombre")
    @Mapping(source = "caja.id", target = "cajaId")
    CompraDTO toDto(Compra compra);

    @Mapping(source = "proveedorId", target = "proveedor")
    @Mapping(source = "tipoDeDocumentoDeCompraId", target = "tipoDeDocumentoDeCompra")
    @Mapping(source = "estatusDeCompraId", target = "estatusDeCompra")
    @Mapping(source = "cajaId", target = "caja")
    Compra toEntity(CompraDTO compraDTO);

    default Compra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Compra compra = new Compra();
        compra.setId(id);
        return compra;
    }
}
