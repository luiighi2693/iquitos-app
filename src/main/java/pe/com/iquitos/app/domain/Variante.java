package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Variante.
 */
@Entity
@Table(name = "variante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "variante")
public class Variante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Size(max = 150)
    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @NotNull
    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    @NotNull
    @Column(name = "precio_compra", nullable = false)
    private Double precioCompra;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "variante_productos",
               joinColumns = @JoinColumn(name = "variantes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "productos_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Variante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Variante descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public Variante precioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
        return this;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public Variante precioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
        return this;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Variante productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Variante addProductos(Producto producto) {
        this.productos.add(producto);
        return this;
    }

    public Variante removeProductos(Producto producto) {
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
        Variante variante = (Variante) o;
        if (variante.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), variante.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Variante{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precioVenta=" + getPrecioVenta() +
            ", precioCompra=" + getPrecioCompra() +
            "}";
    }
}
