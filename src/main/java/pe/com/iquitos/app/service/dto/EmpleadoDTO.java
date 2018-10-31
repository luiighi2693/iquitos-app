package pe.com.iquitos.app.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import pe.com.iquitos.app.domain.enumeration.Sex;
import pe.com.iquitos.app.domain.enumeration.EmployeeRole;

/**
 * A DTO for the Empleado entity.
 */
public class EmpleadoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nombre;

    @NotNull
    @Size(max = 150)
    private String apellido;

    @NotNull
    private Integer dni;

    @NotNull
    @Size(max = 150)
    private String direccion;

    @NotNull
    @Size(max = 150)
    private String correo;

    private LocalDate fechaDeNacimiento;

    private Sex sexo;

    @NotNull
    @Size(max = 150)
    private String telefono;

    @Lob
    private byte[] imagen;
    private String imagenContentType;

    private EmployeeRole rolEmpleado;

    private Long usuarioId;

    private String usuarioDni;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public EmployeeRole getRolEmpleado() {
        return rolEmpleado;
    }

    public void setRolEmpleado(EmployeeRole rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmpleadoDTO empleadoDTO = (EmpleadoDTO) o;
        if (empleadoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empleadoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", dni=" + getDni() +
            ", direccion='" + getDireccion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", fechaDeNacimiento='" + getFechaDeNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", rolEmpleado='" + getRolEmpleado() + "'" +
            ", usuario=" + getUsuarioId() +
            ", usuario='" + getUsuarioDni() + "'" +
            "}";
    }
}
