package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "proveedor")
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "codigo", length = 150, nullable = false)
    private String codigo;

    @NotNull
    @Size(max = 150)
    @Column(name = "razon_social", length = 150, nullable = false)
    private String razonSocial;

    @NotNull
    @Size(max = 150)
    @Column(name = "direccion", length = 150, nullable = false)
    private String direccion;

    @NotNull
    @Size(max = 150)
    @Column(name = "correo", length = 150, nullable = false)
    private String correo;

    @NotNull
    @Size(max = 150)
    @Column(name = "telefono", length = 150, nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "proveedor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CuentaProveedor> cuentaProveedors = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Proveedor codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Proveedor razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public Proveedor direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public Proveedor correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Proveedor telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<CuentaProveedor> getCuentaProveedors() {
        return cuentaProveedors;
    }

    public Proveedor cuentaProveedors(Set<CuentaProveedor> cuentaProveedors) {
        this.cuentaProveedors = cuentaProveedors;
        return this;
    }

    public Proveedor addCuentaProveedor(CuentaProveedor cuentaProveedor) {
        this.cuentaProveedors.add(cuentaProveedor);
        cuentaProveedor.setProveedor(this);
        return this;
    }

    public Proveedor removeCuentaProveedor(CuentaProveedor cuentaProveedor) {
        this.cuentaProveedors.remove(cuentaProveedor);
        cuentaProveedor.setProveedor(null);
        return this;
    }

    public void setCuentaProveedors(Set<CuentaProveedor> cuentaProveedors) {
        this.cuentaProveedors = cuentaProveedors;
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
        Proveedor proveedor = (Proveedor) o;
        if (proveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proveedor{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            "}";
    }
}
