package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ProductoDetalle.
 */
@Entity
@Table(name = "producto_detalle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "productodetalle")
public class ProductoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "producto_label")
    private String productoLabel;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "producto_detalle_variantes",
               joinColumns = @JoinColumn(name = "producto_detalles_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "variantes_id", referencedColumnName = "id"))
    private Set<Variante> variantes = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "producto_detalle_productos",
               joinColumns = @JoinColumn(name = "producto_detalles_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "productos_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public ProductoDetalle cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getProductoLabel() {
        return productoLabel;
    }

    public ProductoDetalle productoLabel(String productoLabel) {
        this.productoLabel = productoLabel;
        return this;
    }

    public void setProductoLabel(String productoLabel) {
        this.productoLabel = productoLabel;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public ProductoDetalle precioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
        return this;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Set<Variante> getVariantes() {
        return variantes;
    }

    public ProductoDetalle variantes(Set<Variante> variantes) {
        this.variantes = variantes;
        return this;
    }

    public ProductoDetalle addVariantes(Variante variante) {
        this.variantes.add(variante);
        return this;
    }

    public ProductoDetalle removeVariantes(Variante variante) {
        this.variantes.remove(variante);
        return this;
    }

    public void setVariantes(Set<Variante> variantes) {
        this.variantes = variantes;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public ProductoDetalle productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public ProductoDetalle addProductos(Producto producto) {
        this.productos.add(producto);
        return this;
    }

    public ProductoDetalle removeProductos(Producto producto) {
        this.productos.remove(producto);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductoDetalle productoDetalle = (ProductoDetalle) o;
        if (productoDetalle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDetalle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDetalle{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", productoLabel='" + getProductoLabel() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            "}";
    }
}
