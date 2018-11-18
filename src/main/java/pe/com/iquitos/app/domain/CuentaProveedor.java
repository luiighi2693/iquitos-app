package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.AccountTypeProvider;

/**
 * A CuentaProveedor.
 */
@Entity
@Table(name = "cuenta_proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cuentaproveedor")
public class CuentaProveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cuenta")
    private AccountTypeProvider tipoCuenta;

    @NotNull
    @Size(max = 150)
    @Column(name = "banco", length = 150, nullable = false)
    private String banco;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre_cuenta", length = 150, nullable = false)
    private String nombreCuenta;

    @Column(name = "numero_de_cuenta")
    private Integer numeroDeCuenta;

    @Column(name = "fecha")
    private LocalDate fecha;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountTypeProvider getTipoCuenta() {
        return tipoCuenta;
    }

    public CuentaProveedor tipoCuenta(AccountTypeProvider tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public void setTipoCuenta(AccountTypeProvider tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getBanco() {
        return banco;
    }

    public CuentaProveedor banco(String banco) {
        this.banco = banco;
        return this;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public CuentaProveedor nombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
        return this;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Integer getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public CuentaProveedor numeroDeCuenta(Integer numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
        return this;
    }

    public void setNumeroDeCuenta(Integer numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public CuentaProveedor fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
        CuentaProveedor cuentaProveedor = (CuentaProveedor) o;
        if (cuentaProveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cuentaProveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CuentaProveedor{" +
            "id=" + getId() +
            ", tipoCuenta='" + getTipoCuenta() + "'" +
            ", banco='" + getBanco() + "'" +
            ", nombreCuenta='" + getNombreCuenta() + "'" +
            ", numeroDeCuenta=" + getNumeroDeCuenta() +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
