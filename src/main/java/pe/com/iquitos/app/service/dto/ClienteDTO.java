package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import pe.com.iquitos.app.domain.enumeration.Sex;
import pe.com.iquitos.app.domain.enumeration.CivilStatus;
import pe.com.iquitos.app.domain.enumeration.ClientType;

/**
 * A DTO for the Cliente entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nombre;

    @Size(max = 150)
    private String codigo;

    @NotNull
    @Size(max = 150)
    private String direccion;

    @Size(max = 150)
    private String correo;

    @Size(max = 150)
    private String telefono;

    private LocalDate fechaDeNacimiento;

    private Sex sexo;

    private CivilStatus estatusCivil;

    @Lob
    private byte[] imagen;
    private String imagenContentType;

    private ClientType tipoDeCliente;

    private Long usuarioId;

    private String usuarioDni;

    private Long tipoDeDocumentoId;

    private String tipoDeDocumentoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Sex getSexo() {
        return sexo;
    }

    public void setSexo(Sex sexo) {
        this.sexo = sexo;
    }

    public CivilStatus getEstatusCivil() {
        return estatusCivil;
    }

    public void setEstatusCivil(CivilStatus estatusCivil) {
        this.estatusCivil = estatusCivil;
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

    public ClientType getTipoDeCliente() {
        return tipoDeCliente;
    }

    public void setTipoDeCliente(ClientType tipoDeCliente) {
        this.tipoDeCliente = tipoDeCliente;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioExternoId) {
        this.usuarioId = usuarioExternoId;
    }

    public String getUsuarioDni() {
        return usuarioDni;
    }

    public void setUsuarioDni(String usuarioExternoDni) {
        this.usuarioDni = usuarioExternoDni;
    }

    public Long getTipoDeDocumentoId() {
        return tipoDeDocumentoId;
    }

    public void setTipoDeDocumentoId(Long tipoDeDocumentoId) {
        this.tipoDeDocumentoId = tipoDeDocumentoId;
    }

    public String getTipoDeDocumentoNombre() {
        return tipoDeDocumentoNombre;
    }

    public void setTipoDeDocumentoNombre(String tipoDeDocumentoNombre) {
        this.tipoDeDocumentoNombre = tipoDeDocumentoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (clienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", fechaDeNacimiento='" + getFechaDeNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", estatusCivil='" + getEstatusCivil() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", tipoDeCliente='" + getTipoDeCliente() + "'" +
            ", usuario=" + getUsuarioId() +
            ", usuario='" + getUsuarioDni() + "'" +
            ", tipoDeDocumento=" + getTipoDeDocumentoId() +
            ", tipoDeDocumento='" + getTipoDeDocumentoNombre() + "'" +
            "}";
    }
}
