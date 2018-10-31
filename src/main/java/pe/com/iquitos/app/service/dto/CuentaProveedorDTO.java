package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import pe.com.iquitos.app.domain.enumeration.ProviderStatus;

/**
 * A DTO for the CuentaProveedor entity.
 */
public class CuentaProveedorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String codigo;

    private ProviderStatus estatus;

    @NotNull
    @Size(max = 150)
    private String banco;

    @NotNull
    @Size(max = 150)
    private String nombreCuenta;

    private Integer numeroDeCuenta;

    private LocalDate fecha;

    private Long proveedorId;

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

    public ProviderStatus getEstatus() {
        return estatus;
    }

    public void setEstatus(ProviderStatus estatus) {
        this.estatus = estatus;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
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
            ", codigo='" + getCodigo() + "'" +
            ", estatus='" + getEstatus() + "'" +
            ", banco='" + getBanco() + "'" +
            ", nombreCuenta='" + getNombreCuenta() + "'" +
            ", numeroDeCuenta=" + getNumeroDeCuenta() +
            ", fecha='" + getFecha() + "'" +
            ", proveedor=" + getProveedorId() +
            "}";
    }
}
