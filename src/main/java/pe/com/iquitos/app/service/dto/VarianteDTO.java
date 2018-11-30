package pe.com.iquitos.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Variante entity.
 */
public class VarianteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nombre;

    @Size(max = 150)
    private String descripcion;

    @NotNull
    private Double precioVenta;

    @NotNull
    private Double precioCompra;

    @NotNull
    private Integer cantidad;

    private Set<ProductoDTO> productos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

        VarianteDTO varianteDTO = (VarianteDTO) o;
        if (varianteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), varianteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VarianteDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            ", precioCompra=" + getPrecioCompra() +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
