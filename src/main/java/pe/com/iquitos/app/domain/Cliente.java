package pe.com.iquitos.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.Sex;

import pe.com.iquitos.app.domain.enumeration.CivilStatus;

import pe.com.iquitos.app.domain.enumeration.ClientType;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Size(max = 150)
    @Column(name = "codigo", length = 150)
    private String codigo;

    @NotNull
    @Size(max = 150)
    @Column(name = "direccion", length = 150, nullable = false)
    private String direccion;

    @Size(max = 150)
    @Column(name = "correo", length = 150)
    private String correo;

    @Size(max = 150)
    @Column(name = "telefono", length = 150)
    private String telefono;

    @Column(name = "fecha_de_nacimiento")
    private LocalDate fechaDeNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private Sex sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus_civil")
    private CivilStatus estatusCivil;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_cliente")
    private ClientType tipoDeCliente;

    @OneToOne    @JoinColumn(unique = true)
    private UsuarioExterno usuario;

    @ManyToOne
    @JsonIgnoreProperties("")
    private TipoDeDocumento tipoDeDocumento;

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

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public Cliente direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public Cliente correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public Cliente telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public Cliente fechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
        return this;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Sex getSexo() {
        return sexo;
    }

    public Cliente sexo(Sex sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(Sex sexo) {
        this.sexo = sexo;
    }

    public CivilStatus getEstatusCivil() {
        return estatusCivil;
    }

    public Cliente estatusCivil(CivilStatus estatusCivil) {
        this.estatusCivil = estatusCivil;
        return this;
    }

    public void setEstatusCivil(CivilStatus estatusCivil) {
        this.estatusCivil = estatusCivil;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Cliente imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Cliente imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public ClientType getTipoDeCliente() {
        return tipoDeCliente;
    }

    public Cliente tipoDeCliente(ClientType tipoDeCliente) {
        this.tipoDeCliente = tipoDeCliente;
        return this;
    }

    public void setTipoDeCliente(ClientType tipoDeCliente) {
        this.tipoDeCliente = tipoDeCliente;
    }

    public UsuarioExterno getUsuario() {
        return usuario;
    }

    public Cliente usuario(UsuarioExterno usuarioExterno) {
        this.usuario = usuarioExterno;
        return this;
    }

    public void setUsuario(UsuarioExterno usuarioExterno) {
        this.usuario = usuarioExterno;
    }

    public TipoDeDocumento getTipoDeDocumento() {
        return tipoDeDocumento;
    }

    public Cliente tipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
        return this;
    }

    public void setTipoDeDocumento(TipoDeDocumento tipoDeDocumento) {
        this.tipoDeDocumento = tipoDeDocumento;
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
        Cliente cliente = (Cliente) o;
        if (cliente.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cliente.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cliente{" +
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
            ", imagenContentType='" + getImagenContentType() + "'" +
            ", tipoDeCliente='" + getTipoDeCliente() + "'" +
            "}";
    }
}
