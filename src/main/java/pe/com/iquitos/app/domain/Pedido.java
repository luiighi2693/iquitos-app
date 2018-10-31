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

import pe.com.iquitos.app.domain.enumeration.OrderStatus;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pedido")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nota", length = 150, nullable = false)
    private String nota;

    @NotNull
    @Size(max = 150)
    @Column(name = "guia", length = 150, nullable = false)
    private String guia;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private OrderStatus estatus;

    @NotNull
    @Size(max = 5000)
    @Column(name = "meta_data", length = 5000, nullable = false)
    private String metaData;

    @OneToOne    @JoinColumn(unique = true)
    private Proveedor proveedor;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "pedido_productos",
               joinColumns = @JoinColumn(name = "pedidos_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "productos_id", referencedColumnName = "id"))
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNota() {
        return nota;
    }

    public Pedido nota(String nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getGuia() {
        return guia;
    }

    public Pedido guia(String guia) {
        this.guia = guia;
        return this;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public OrderStatus getEstatus() {
        return estatus;
    }

    public Pedido estatus(OrderStatus estatus) {
        this.estatus = estatus;
        return this;
    }

    public void setEstatus(OrderStatus estatus) {
        this.estatus = estatus;
    }

    public String getMetaData() {
        return metaData;
    }

    public Pedido metaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Pedido proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Pedido productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Pedido addProductos(Producto producto) {
        this.productos.add(producto);
        return this;
    }

    public Pedido removeProductos(Producto producto) {
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
        Pedido pedido = (Pedido) o;
        if (pedido.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pedido.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", nota='" + getNota() + "'" +
            ", guia='" + getGuia() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", metaData='" + getMetaData() + "'" +
            "}";
    }
}
