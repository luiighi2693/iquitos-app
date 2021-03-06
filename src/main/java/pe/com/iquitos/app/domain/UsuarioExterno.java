package pe.com.iquitos.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import pe.com.iquitos.app.domain.enumeration.UserType;

/**
 * A UsuarioExterno.
 */
@Entity
@Table(name = "usuario_externo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "usuarioexterno")
public class UsuarioExterno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dni", nullable = false)
    private Integer dni;

    @NotNull
    @Column(name = "pin", nullable = false)
    private Integer pin;

    @Column(name = "id_entity")
    private Integer idEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @NotNull
    @Size(max = 150)
    @Column(name = "jhi_role", length = 150, nullable = false)
    private String role;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDni() {
        return dni;
    }

    public UsuarioExterno dni(Integer dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Integer getPin() {
        return pin;
    }

    public UsuarioExterno pin(Integer pin) {
        this.pin = pin;
        return this;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Integer getIdEntity() {
        return idEntity;
    }

    public UsuarioExterno idEntity(Integer idEntity) {
        this.idEntity = idEntity;
        return this;
    }

    public void setIdEntity(Integer idEntity) {
        this.idEntity = idEntity;
    }

    public UserType getUserType() {
        return userType;
    }

    public UsuarioExterno userType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getRole() {
        return role;
    }

    public UsuarioExterno role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
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
        UsuarioExterno usuarioExterno = (UsuarioExterno) o;
        if (usuarioExterno.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuarioExterno.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsuarioExterno{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", pin=" + getPin() +
            ", idEntity=" + getIdEntity() +
            ", userType='" + getUserType() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
