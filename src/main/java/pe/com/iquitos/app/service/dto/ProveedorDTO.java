package pe.com.iquitos.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Proveedor entity.
 */
public class ProveedorDTO implements Serializable {

    private Long id;

    @Size(max = 150)
    private String codigo;

    @NotNull
    @Size(max = 150)
    private String razonSocial;

    @NotNull
    @Size(max = 150)
    private String direccion;

    @Size(max = 150)
    private String correo;

    @Size(max = 150)
    private String telefono;

    @Size(max = 150)
    private String sector;

    private Set<CuentaProveedorDTO> cuentaProveedors = new HashSet<>();

    private Set<ContactoProveedorDTO> contactoProveedors = new HashSet<>();

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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Set<CuentaProveedorDTO> getCuentaProveedors() {
        return cuentaProveedors;
    }

    public void setCuentaProveedors(Set<CuentaProveedorDTO> cuentaProveedors) {
        this.cuentaProveedors = cuentaProveedors;
    }

    public Set<ContactoProveedorDTO> getContactoProveedors() {
        return contactoProveedors;
    }

    public void setContactoProveedors(Set<ContactoProveedorDTO> contactoProveedors) {
        this.contactoProveedors = contactoProveedors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProveedorDTO proveedorDTO = (ProveedorDTO) o;
        if (proveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProveedorDTO{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", sector='" + getSector() + "'" +
            "}";
    }
}
