package pe.com.iquitos.app.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UsuarioExterno entity.
 */
public class UsuarioExternoDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer dni;

    @NotNull
    private Integer pin;

    private Long usuarioId;

    private String usuarioLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String userLogin) {
        this.usuarioLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsuarioExternoDTO usuarioExternoDTO = (UsuarioExternoDTO) o;
        if (usuarioExternoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usuarioExternoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsuarioExternoDTO{" +
            "id=" + getId() +
            ", dni=" + getDni() +
            ", pin=" + getPin() +
            ", usuario=" + getUsuarioId() +
            ", usuario='" + getUsuarioLogin() + "'" +
            "}";
    }
}
