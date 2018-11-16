package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ContactoProveedor.
 */
@Entity
@Table(name = "contacto_proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contactoproveedor")
public class ContactoProveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 150)
    @Column(name = "cargo", length = 150, nullable = false)
    private String cargo;

    @Column(name = "producto")
    private String producto;

    @Column(name = "telefono")
    private Integer telefono;

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

    public ContactoProveedor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public ContactoProveedor cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getProducto() {
        return producto;
    }

    public ContactoProveedor producto(String producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public ContactoProveedor telefono(Integer telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
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
        ContactoProveedor contactoProveedor = (ContactoProveedor) o;
        if (contactoProveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contactoProveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContactoProveedor{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", cargo='" + getCargo() + "'" +
            ", producto='" + getProducto() + "'" +
            ", telefono=" + getTelefono() +
            "}";
    }
}
