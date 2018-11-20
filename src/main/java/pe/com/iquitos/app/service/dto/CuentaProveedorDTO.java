package pe.com.iquitos.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.AccountTypeProvider;

/**
 * A DTO for the CuentaProveedor entity.
 */
public class CuentaProveedorDTO implements Serializable {

    private Long id;

    private AccountTypeProvider tipoCuenta;

    @NotNull
    @Size(max = 150)
    private String banco;

    @NotNull
    @Size(max = 150)
    private String nombreCuenta;

    @NotNull
    private Integer numeroDeCuenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountTypeProvider getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(AccountTypeProvider tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public Integer getNumeroDeCuenta() {
        return numeroDeCuenta;
    }

    public void setNumeroDeCuenta(Integer numeroDeCuenta) {
        this.numeroDeCuenta = numeroDeCuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CuentaProveedorDTO cuentaProveedorDTO = (CuentaProveedorDTO) o;
        if (cuentaProveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cuentaProveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CuentaProveedorDTO{" +
            "id=" + getId() +
            ", tipoCuenta='" + getTipoCuenta() + "'" +
            ", banco='" + getBanco() + "'" +
            ", nombreCuenta='" + getNombreCuenta() + "'" +
            ", numeroDeCuenta=" + getNumeroDeCuenta() +
            "}";
    }
}
