package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import pe.com.iquitos.app.domain.enumeration.ProductType;

/**
 * A DTO for the Producto entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String codigo;

    @Size(max = 150)
    private String descripcion;

    private LocalDate fechaExpiracion;

    private Boolean esFavorito;

    private Boolean visibleParaLaVenta;

    @Lob
    private byte[] imagen;
    private String imagenContentType;

    @NotNull
    private Integer stock;

    private Integer notificacionDeLimiteDeStock;

    private ProductType tipoDeProducto;

    private Long unidadDeMedidaId;

    private String unidadDeMedidaNombre;

    private Long categoriaId;

    private String categoriaNombre;

    private Set<VarianteDTO> variantes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Boolean isEsFavorito() {
        return esFavorito;
    }

    public void setEsFavorito(Boolean esFavorito) {
        this.esFavorito = esFavorito;
    }

    public Boolean isVisibleParaLaVenta() {
        return visibleParaLaVenta;
    }

    public void setVisibleParaLaVenta(Boolean visibleParaLaVenta) {
        this.visibleParaLaVenta = visibleParaLaVenta;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getNotificacionDeLimiteDeStock() {
        return notificacionDeLimiteDeStock;
    }

    public void setNotificacionDeLimiteDeStock(Integer notificacionDeLimiteDeStock) {
        this.notificacionDeLimiteDeStock = notificacionDeLimiteDeStock;
    }

    public ProductType getTipoDeProducto() {
        return tipoDeProducto;
    }

    public void setTipoDeProducto(ProductType tipoDeProducto) {
        this.tipoDeProducto = tipoDeProducto;
    }

    public Long getUnidadDeMedidaId() {
        return unidadDeMedidaId;
    }

    public void setUnidadDeMedidaId(Long unidadDeMedidaId) {
        this.unidadDeMedidaId = unidadDeMedidaId;
    }

    public String getUnidadDeMedidaNombre() {
        return unidadDeMedidaNombre;
    }

    public void setUnidadDeMedidaNombre(String unidadDeMedidaNombre) {
        this.unidadDeMedidaNombre = unidadDeMedidaNombre;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public Set<VarianteDTO> getVariantes() {
        return variantes;
    }

    public void setVariantes(Set<VarianteDTO> variantes) {
        this.variantes = variantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaExpiracion='" + getFechaExpiracion() + "'" +
            ", esFavorito='" + isEsFavorito() + "'" +
            ", visibleParaLaVenta='" + isVisibleParaLaVenta() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", stock=" + getStock() +
            ", notificacionDeLimiteDeStock=" + getNotificacionDeLimiteDeStock() +
            ", tipoDeProducto='" + getTipoDeProducto() + "'" +
            ", unidadDeMedida=" + getUnidadDeMedidaId() +
            ", unidadDeMedida='" + getUnidadDeMedidaNombre() + "'" +
            ", categoria=" + getCategoriaId() +
            ", categoria='" + getCategoriaNombre() + "'" +
            "}";
    }
}
