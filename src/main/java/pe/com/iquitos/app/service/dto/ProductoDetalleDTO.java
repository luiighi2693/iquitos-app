package pe.com.iquitos.app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProductoDetalle entity.
 */
public class ProductoDetalleDTO implements Serializable {

    private Long id;

    private Integer cantidad;

    private String productoLabel;

    private Double precioVenta;

    private Set<VarianteDTO> variantes = new HashSet<>();

    private Set<ProductoDTO> productos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getProductoLabel() {
        return productoLabel;
    }

    public void setProductoLabel(String productoLabel) {
        this.productoLabel = productoLabel;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Set<VarianteDTO> getVariantes() {
        return variantes;
    }

    public void setVariantes(Set<VarianteDTO> variantes) {
        this.variantes = variantes;
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

        ProductoDetalleDTO productoDetalleDTO = (ProductoDetalleDTO) o;
        if (productoDetalleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDetalleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDetalleDTO{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", productoLabel='" + getProductoLabel() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            "}";
    }
}
