package pe.com.iquitos.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.OrderStatus;

/**
 * A DTO for the Pedido entity.
 */
public class PedidoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nota;

    @NotNull
    @Size(max = 150)
    private String guia;

    private OrderStatus estatus;

    @NotNull
    @Size(max = 5000)
    private String metaData;

    private Long proveedorId;

    private String proveedorRazonSocial;

    private Set<ProductoDTO> productos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public OrderStatus getEstatus() {
        return estatus;
    }

    public void setEstatus(OrderStatus estatus) {
        this.estatus = estatus;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorRazonSocial() {
        return proveedorRazonSocial;
    }

    public void setProveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
    }

    public Set<ProductoDTO> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoDTO> productos) {
        this.productos = productos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (pedidoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedidoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", nota='" + getNota() + "'" +
            ", guia='" + getGuia() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", metaData='" + getMetaData() + "'" +
            ", proveedor=" + getProveedorId() +
            ", proveedor='" + getProveedorRazonSocial() + "'" +
            "}";
    }
}
