package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.Sex;

import pe.com.iquitos.app.domain.enumeration.EmployeeRole;

/**
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "empleado")
public class Empleado implements Serializable {

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
    @Column(name = "apellido", length = 150, nullable = false)
    private String apellido;

    @NotNull
    @Column(name = "dni", nullable = false)
    private Integer dni;

    @NotNull
    @Size(max = 150)
    @Column(name = "direccion", length = 150, nullable = false)
    private String direccion;

    @NotNull
    @Size(max = 150)
    @Column(name = "correo", length = 150, nullable = false)
    private String correo;

    @Column(name = "fecha_de_nacimiento")
    private LocalDate fechaDeNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sex sexo;

    @NotNull
    @Size(max = 150)
    @Column(name = "telefono", length = 150, nullable = false)
    private String telefono;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_empleado")
    private EmployeeRole rolEmpleado;

    @OneToOne    @JoinColumn(unique = true)
    private UsuarioExterno usuario;

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

    public Empleado nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Empleado apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getDni() {
        return dni;
    }

    public Empleado dni(Integer dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public Empleado direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public Empleado correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Empleado fechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
        return this;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Sex getSexo() {
        return sexo;
    }

    public Empleado sexo(Sex sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sex sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Empleado telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Empleado imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Empleado imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public EmployeeRole getRolEmpleado() {
        return rolEmpleado;
    }

    public Empleado rolEmpleado(EmployeeRole rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
        return this;
    }

    public void setRolEmpleado(EmployeeRole rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
    }

    public UsuarioExterno getUsuario() {
        return usuario;
    }

    public Empleado usuario(UsuarioExterno usuarioExterno) {
        this.usuario = usuarioExterno;
        return this;
    }

    public void setUsuario(UsuarioExterno usuarioExterno) {
        this.usuario = usuarioExterno;
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
        Empleado empleado = (Empleado) o;
        if (empleado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empleado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empleado{" +
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
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", rolEmpleado='" + getRolEmpleado() + "'" +
            "}";
    }
}
